package com.wuruoye.ichp.base

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import com.wuruoye.ichp.base.model.Config
import com.wuruoye.ichp.base.util.FileUtil
import com.wuruoye.ichp.base.util.PermissionUtil
import android.content.pm.PackageManager
import com.wuruoye.ichp.base.util.FilePathUtil
import com.wuruoye.ichp.base.util.loge
import java.io.File


/**
 * Created by wuruoye on 2017/9/26.
 * this file is to do
 */
abstract class PhotoActivity : BaseActivity(), PhotoView{
    //是否需要剪裁
    private var isCrop = false
    //图片的输出文件名
    private lateinit var filePath: String
    //剪裁图片长宽比
    private var aspectX = 0
    private var aspectY = 0
    //剪裁图片输出大小
    private var outputX = 0
    private var outputY = 0

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode ==  CHOOSE_PHOTO && resultCode == Activity.RESULT_OK){
            //相册选取返回
            if (isCrop) {
                val filePath = FilePathUtil.getPathFromUri(applicationContext, data!!.data)!!
                val uri = FileProvider.getUriForFile(applicationContext, Config.PROVIDER_AUTHORITY, File(filePath))
                cropPhoto(uri)
            }else{
                val filePath = FilePathUtil.getPathFromUri(applicationContext, data!!.data)!!
                onPhotoBack(filePath)
            }
        }else if (requestCode == TAKE_PHOTO && resultCode == Activity.RESULT_OK){
            //拍照返回
            if (isCrop){
                val uri = FileProvider.getUriForFile(applicationContext, Config.PROVIDER_AUTHORITY, File(filePath))
                cropPhoto(uri)
            }else{
                onPhotoBack(filePath)
            }
        }else if (requestCode == CROP_PHOTO && resultCode == Activity.RESULT_OK){
            //剪裁返回
            onPhotoBack(filePath)
        }
    }

    /**
     * 相册选取，需要剪裁
     * @param filePath 剪裁之后的输出文件
     * @param aX,aY aX/aY 为宽高比
     * @param oX,oY 分别为输入文件的宽高
     */
    override fun choosePhoto(filePath: String, aX: Int, aY: Int, oX: Int, oY: Int){
        if (PermissionUtil(this).requestPermission(Config.FILE_PERMISSION)){
            aspectX = aX
            aspectY = aY
            outputX = oX
            outputY = oY
            this.filePath = filePath
            isCrop = true

            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            startActivityForResult(intent, CHOOSE_PHOTO)
        }
    }

    /**
     * 相册选取，不需剪裁
     */
    override fun choosePhoto() {
        if (PermissionUtil(this).requestPermission(Config.FILE_PERMISSION)){
            isCrop = false

            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "image/*"
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            startActivityForResult(intent, CHOOSE_PHOTO)
        }
    }

    /**
     * 拍照，需要剪裁
     * @param filePath 输出文件名
     * @param aX,aY 剪裁比例
     * @param oX,oY 输入文件大小
     */
    override fun takePhoto(filePath: String, aX: Int, aY: Int, oX: Int, oY: Int){
        if (PermissionUtil(this).requestPermission(Config.FILE_PERMISSION) &&
                PermissionUtil(this).requestPermission(Config.CAMERA_PERMISSION)){
            isCrop = true
            this.filePath = filePath
            aspectX = aX
            aspectY = aY
            outputX = oX
            outputY = oY

            val file = FileUtil.createFile(filePath)
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val uri =
                    if (Build.VERSION.SDK_INT < 21){
                        Uri.fromFile(file)
                    }else{
                        FileProvider.getUriForFile(applicationContext, Config.PROVIDER_AUTHORITY, file)
                    }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
            startActivityForResult(intent, TAKE_PHOTO)
        }
    }

    /**
     * 拍照，不需剪裁
     * @param filePath 输出文件路径
     */
    override fun takePhoto(filePath: String){
        if (PermissionUtil(this).requestPermission(Config.FILE_PERMISSION) &&
                PermissionUtil(this).requestPermission(Config.CAMERA_PERMISSION)){
            isCrop = false
            this.filePath = filePath

            val file = FileUtil.createFile(filePath)
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val uri =
                    if (Build.VERSION.SDK_INT < 21){
                        Uri.fromFile(file)
                    }else{
                        FileProvider.getUriForFile(applicationContext, Config.PROVIDER_AUTHORITY, file)
                    }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
            startActivityForResult(intent, TAKE_PHOTO)
        }
    }

    /**
     * 剪裁图片
     * @param filePath 需要剪裁的图片
     * @param fileName 剪裁之后的输出文件
     * @param aX,aY,oX,oY 同上
     */
    override fun cropPhoto(filePath: String, fileName: String, aX: Int, aY: Int, oX: Int, oY: Int){
        this.filePath = fileName
        aspectX = aX
        aspectY = aY
        outputX = oX
        outputY = oY
        val uri = FileProvider.getUriForFile(applicationContext, Config.PROVIDER_AUTHORITY, File(filePath))
        cropPhoto(uri)
    }

    override fun cropPhoto(uri: Uri){
        if (PermissionUtil(this).requestPermission(Config.FILE_PERMISSION)){
            val file = FileUtil.createFile(filePath)
            val outUri = FileProvider.getUriForFile(applicationContext, Config.PROVIDER_AUTHORITY, file)
            val intent = Intent("com.android.camera.action.CROP")
            intent.setDataAndType(uri, "image/*")
            intent.putExtra("crop", true)
            intent.putExtra("aspectX", aspectX)
            intent.putExtra("aspectY", aspectY)
            intent.putExtra("outputX", outputX)
            intent.putExtra("outputY", outputY)
            intent.putExtra("return-data", false)
            intent.putExtra(MediaStore.EXTRA_OUTPUT, outUri)
            //申请文件读写权限
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)

            /**
             * 获得所有能执行此intent的应用，并为它们申请权限
             */
            val resInfoList = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
            for (resolveInfo in resInfoList) {
                loge(resolveInfo.activityInfo.packageName)
                val packageName = resolveInfo.activityInfo.packageName
                grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION)
                grantUriPermission(packageName, outUri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            startActivityForResult(intent, CROP_PHOTO)
        }
    }

    companion object {
        val CHOOSE_PHOTO = 1
        val TAKE_PHOTO = 2
        val CROP_PHOTO = 3
    }
}