package com.wuruoye.ichp.base.util

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.support.v4.content.FileProvider
import com.wuruoye.ichp.base.model.Config
import java.io.File

/**
 * Created by wuruoye on 2017/10/14.
 * this file is to do share operator
 */

object ShareUtil{
    fun shareImage(imagePath: String, context: Context){
        val file = File(imagePath)
        val uri = FileProvider.getUriForFile(context, Config.PROVIDER_AUTHORITY, file)

        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_STREAM, uri)

        val resInfoList = context.packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
        for (resolveInfo in resInfoList) {
            loge(resolveInfo.activityInfo.packageName)
            val packageName = resolveInfo.activityInfo.packageName
            context.grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        context.startActivity(intent)
    }

    fun shareBitmap(bitmap: Bitmap, context: Context) {
        shareBitmap(bitmap, context, null)
    }

    fun shareBitmap(bitmap: Bitmap, context: Context, desc: String?) {
        val path = MediaStore.Images.Media.insertImage(context.contentResolver, bitmap,
                "分享", desc)
        val uri = Uri.parse(path)
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_STREAM, uri)
        context.startActivity(intent)
    }

    fun shareText(text: String, context: Context) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, text)
        context.startActivity(intent)
    }
}
