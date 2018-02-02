package com.wuruoye.ichp.base

import android.net.Uri

/**
 * Created by wuruoye on 2017/12/29.
 * this file is to
 */
interface MediaView {
    fun onMediaBack(filePath: String)
    fun choosePhoto(filePath: String, aX: Int, aY: Int, oX: Int, oY: Int)
    fun choosePhoto()
    fun takePhoto(filePa: String, aX: Int, aY: Int, oX: Int, oY: Int)
    fun takePhoto(filePath: String)
    fun cropPhoto(filePath: String, fileName: String, aX: Int, aY: Int, oX: Int, oY: Int)
    fun cropPhoto(uri: Uri)
    fun chooseVideo()
    fun takeVideo()
    fun chooseRecord()
    fun takeRecord(filePath: String, timeLimit: Int)
}