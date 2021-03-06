package com.wuruoye.ichp.base.model

import android.Manifest
import android.os.Environment

/**
 * Created by wuruoye on 2017/9/15.
 * this file is to do
 */
object Config {
    private const val PACKAGE_NAME = "com.wuruoye.ichp"

    // 网络设置
    const val CONNECT_TIME_OUT = 30L
    const val READ_TIME_OUT = 30L

    // 文件路径设置
    private val APP_PATH = Environment
            .getExternalStorageDirectory().absolutePath + "/" + PACKAGE_NAME + "/"
    val FILE_PATH = APP_PATH + "file/"
    val IMAGE_PATH = APP_PATH + "image/"
    val VIDEO_PATH = APP_PATH + "video/"
    val RECORD_PATH = APP_PATH + "record/"
    const val PROVIDER_AUTHORITY = PACKAGE_NAME + ".fileprovider"

    // 动态申请权限
    val FILE_PERMISSION = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    val CAMERA_PERMISSION = arrayOf(
            Manifest.permission.CAMERA
    )

    val LOCATION_PERMISSION = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
    )

    val AUDIO_PERMISSION = arrayOf(
            Manifest.permission.RECORD_AUDIO
    )
}