package com.wuruoye.ichp.base.util

import android.content.Context
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.wuruoye.ichp.R
import com.wuruoye.ichp.base.App

/**
 * Created by wuruoye on 2017/12/23.
 * this file is to
 */

fun loge(message: Any) {
    Log.e("wuruoye", message.toString())
}

fun toast(message: Any) {
    Toast.makeText(App.getApp(), message.toString(), Toast.LENGTH_SHORT).show()
}

fun loadImage(url: String, iv: ImageView, default: Int, error: Int) {
    val option = RequestOptions()
            .placeholder(default)
            .error(error)
    Glide.with(App.getApp())
            .load(url)
            .apply(option)
            .into(iv)
}