package com.wuruoye.ichp.base

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

/**
 * Created by wuruoye on 2017/9/15.
 * this file is to do
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        mContext = this

    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var mContext: Context? = null

        fun getApp(): Context? = mContext
    }
}