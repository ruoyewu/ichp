package com.wuruoye.ichp.base

import android.annotation.SuppressLint
import android.content.Context
import com.wuruoye.library.ui.WBaseApp
import com.wuruoye.library.util.net.IWNet
import com.wuruoye.library.util.net.OKHttpNet
import com.wuruoye.library.util.net.WNet

/**
 * Created by wuruoye on 2017/9/15.
 * this file is to do
 */
class App : WBaseApp() {

    override fun onCreate() {
        super.onCreate()
        mContext = this
        WNet.init(OKHttpNet())
        WNet.setType(IWNet.PARAM_TYPE.JSON)
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        private var mContext: Context? = null

        fun getApp(): Context? = mContext
    }
}