package com.wuruoye.ichp.base

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * Created by wuruoye on 2017/9/15.
 * this file is to do
 */

abstract class BaseActivity : AppCompatActivity(){
    abstract val contentView: Int
    abstract fun initData(bundle: Bundle?)
    abstract fun initView()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(contentView)

        initData(intent.extras)
        initView()
    }
}

