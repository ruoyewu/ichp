package com.wuruoye.ichp.base

import android.net.Uri

/**
 * Created by wuruoye on 2017/12/29.
 * this file is to
 */
interface PhotoView {
    fun onPhotoBack(photoPath: String)
    fun choosePhoto(fileName: String, aX: Int, aY: Int, oX: Int, oY: Int)
    fun choosePhoto()
    fun takePhoto(fileName: String, aX: Int, aY: Int, oX: Int, oY: Int)
    fun takePhoto(fileName: String)
    fun cropPhoto(filePath: String, fileName: String, aX: Int, aY: Int, oX: Int, oY: Int)
    fun cropPhoto(uri: Uri)
}