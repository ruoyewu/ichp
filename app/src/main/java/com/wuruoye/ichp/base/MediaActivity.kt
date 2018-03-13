package com.wuruoye.ichp.base

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.media.MediaRecorder.MEDIA_RECORDER_INFO_MAX_DURATION_REACHED
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import com.wuruoye.ichp.R
import com.wuruoye.ichp.base.model.Config
import com.wuruoye.ichp.base.util.*
import java.io.File

/**
 * Created by wuruoye on 2017/9/26.
 * this file is to do
 */
abstract class MediaActivity : BaseActivity(), MediaView {
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

    private lateinit var dlgRecord: AlertDialog
    private var mMediaRecorder: MediaRecorder? = null
    private lateinit var mRecordAnimator1: ValueAnimator
    private lateinit var mRecordAnimator2: ValueAnimator
    private lateinit var mRecordBack1: View
    private lateinit var mRecordBack2: View
    private lateinit var mRecordTitle: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initRecordDialog()
    }

    @SuppressLint("InflateParams", "ClickableViewAccessibility")
    private fun initRecordDialog() {
        val recordView = LayoutInflater.from(this)
                .inflate(R.layout.dlg_record, null)
        mRecordTitle = recordView.findViewById(R.id.tv_record)
        mRecordBack1 = recordView.findViewById<View>(R.id.v_record_back_1)
        mRecordBack2 = recordView.findViewById<View>(R.id.v_record_back_2)
        initRecordAnimator(mRecordBack1, mRecordBack2)
        val ibRecord = recordView.findViewById<ImageButton>(R.id.iv_record)
        ibRecord.isClickable = true
        ibRecord.setOnTouchListener({ _, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    startRecord()
                }
                MotionEvent.ACTION_UP -> {
                    stopRecord()
                }
            }
            true
        })
        dlgRecord = AlertDialog.Builder(this)
                .setView(recordView)
                .create()
    }

    private fun initRecordAnimator(back1: View, back2: View) {
        mRecordAnimator1 = ValueAnimator.ofFloat(1F, 2F)
        mRecordAnimator2 = ValueAnimator.ofFloat(1F, 2F)
        mRecordAnimator1.duration = TIME_RECORD_ANIMATOR
        mRecordAnimator2.duration = TIME_RECORD_ANIMATOR
        mRecordAnimator1.repeatCount = -1
        mRecordAnimator2.repeatCount = -1
        mRecordAnimator1.repeatMode = ValueAnimator.REVERSE
        mRecordAnimator2.repeatMode = ValueAnimator.REVERSE
        mRecordAnimator1.addUpdateListener { animator ->
            val scale = animator.animatedValue as Float
            loge("scale 1: $scale")
            back1.scaleX = scale
            back1.scaleY = scale
        }
        mRecordAnimator2.addUpdateListener { animator ->
            val scale = animator.animatedValue as Float
            loge("scale 2: $scale")
            back2.scaleX = scale
            back2.scaleY = scale
        }
    }

    private fun startRecord() {
        if (mMediaRecorder != null) {
            mMediaRecorder!!.setOutputFile(filePath)
            mMediaRecorder!!.prepare()
            mMediaRecorder?.start()

            mRecordAnimator1.start()
            Handler().postDelayed({
                mRecordAnimator2.start()
            }, TIME_RECORD_ANIMATOR / 2)
            mRecordTitle.text = "正在录制中..."
        }else {
            toast("音频录制初始化失败...")
        }
    }

    private fun stopRecord() {
        if (mMediaRecorder != null) {
            mMediaRecorder!!.stop()
            mMediaRecorder!!.release()
            mMediaRecorder = null
            dlgRecord.dismiss()
            onMediaBack(filePath)
        }

        mRecordAnimator1.cancel()
        mRecordAnimator2.cancel()
        mRecordBack1.scaleX = 1F
        mRecordBack1.scaleY = 1F
        mRecordBack2.scaleX = 1F
        mRecordBack2.scaleY = 1F
        mRecordTitle.text = "长按录制视频"
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PHOTO_CHOOSE && resultCode == Activity.RESULT_OK){
            //相册选取返回
            if (isCrop) {
                val filePath = FilePathUtil.getPathFromUri(applicationContext, data!!.data)!!
                val uri = FileProvider.getUriForFile(applicationContext, Config.PROVIDER_AUTHORITY, File(filePath))
                cropPhoto(uri)
            }else{
                val filePath = FilePathUtil.getPathFromUri(applicationContext, data!!.data)!!
                onMediaBack(filePath)
            }
        }else if (requestCode == PHOTO_TAKE && resultCode == Activity.RESULT_OK){
            //拍照返回
            if (isCrop){
                val uri = FileProvider.getUriForFile(applicationContext, Config.PROVIDER_AUTHORITY, File(filePath))
                cropPhoto(uri)
            }else{
                onMediaBack(filePath)
            }
        }else if (requestCode == PHOTO_CROP && resultCode == Activity.RESULT_OK){
            //剪裁返回
            onMediaBack(filePath)
        }else if (requestCode == VIDEO_CHOOSE && resultCode == Activity.RESULT_OK) {
            val filePath = FilePathUtil.getPathFromUri(applicationContext, data!!.data)!!
            onMediaBack(filePath)
        }else if (requestCode == VIDEO_TAKE && resultCode == Activity.RESULT_OK) {
            val filePath = FilePathUtil.getPathFromUri(applicationContext, data!!.data)!!
            onMediaBack(filePath)
        }else if (requestCode == RECORD_TAKE && resultCode == Activity.RESULT_OK) {
            val filePath = FilePathUtil.getPathFromUri(applicationContext, data!!.data)!!
            onMediaBack(filePath)
        }else if (requestCode == RECORD_CHOOSE && resultCode == Activity.RESULT_OK) {
            val filePath = FilePathUtil.getPathFromUri(applicationContext, data!!.data)!!
            onMediaBack(filePath)
        }
        super.onActivityResult(requestCode, resultCode, data)
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
            startActivityForResult(intent, PHOTO_CHOOSE)
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
            startActivityForResult(intent, PHOTO_CHOOSE)
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
            startActivityForResult(intent, PHOTO_TAKE)
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
                        FileProvider.getUriForFile(applicationContext,
                                Config.PROVIDER_AUTHORITY, file)
                    }
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
            startActivityForResult(intent, PHOTO_TAKE)
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
            val intent = Intent("com.android.camera.action.PHOTO_CROP")
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
            startActivityForResult(intent, PHOTO_CROP)
        }
    }

    override fun chooseVideo() {
        if (PermissionUtil(this).requestPermission(Config.FILE_PERMISSION)) {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "video/*"
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            startActivityForResult(intent, PHOTO_CHOOSE)
        }
    }

    override fun takeVideo() {
        if (PermissionUtil(this).requestPermission(Config.FILE_PERMISSION) &&
                PermissionUtil(this).requestPermission(Config.CAMERA_PERMISSION)) {
            val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0.1)
            intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 600000)
            startActivityForResult(intent, VIDEO_TAKE)
        }
    }

    override fun chooseRecord() {
        if (PermissionUtil(this).requestPermission(Config.FILE_PERMISSION)) {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "audio/*"
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            startActivityForResult(intent, RECORD_CHOOSE)
        }
    }

    override fun takeRecord(filePath: String, timeLimit: Int) {
        if (PermissionUtil(this).requestPermission(Config.FILE_PERMISSION) &&
                PermissionUtil(this).requestPermission(Config.AUDIO_PERMISSION)) {
            FileUtil.checkAvailable(filePath)
            this.filePath = filePath
            if (mMediaRecorder == null) {
                mMediaRecorder = MediaRecorder()
                mMediaRecorder!!.setAudioSource(MediaRecorder.AudioSource.MIC)
                mMediaRecorder!!.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                mMediaRecorder!!.setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
                mMediaRecorder!!.setMaxDuration(timeLimit)
                mMediaRecorder!!.setOnInfoListener { mr, what, extra ->
                    if (what == MEDIA_RECORDER_INFO_MAX_DURATION_REACHED) {
                        stopRecord()
                    }
                }
            }
            dlgRecord.show()
        }
    }

    companion object {
        const val PHOTO_CHOOSE = 1
        const val PHOTO_TAKE = 2
        const val PHOTO_CROP = 3
        const val VIDEO_CHOOSE = 4
        const val VIDEO_TAKE = 5
        const val RECORD_CHOOSE = 6
        const val RECORD_TAKE = 7

        const val TIME_RECORD_ANIMATOR = 1000L
    }
}