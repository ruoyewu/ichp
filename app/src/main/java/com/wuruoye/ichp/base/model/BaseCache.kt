package com.wuruoye.ichp.base.model

import android.content.Context
import android.content.SharedPreferences
import com.wuruoye.ichp.base.App

/**
 * Created by wuruoye on 2017/9/15.
 * this file is to do
 */
open class BaseCache() {

    init {
        if (mSP == null){
            synchronized(this, {
                if (mSP == null){
                    mSP = App.getApp()?.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
                }
            })
        }
    }

    fun getString(key: String, value: String): String =
            mSP!!.getString(key, value)

    fun setString(key: String, value: String) =
            mSP!!.edit().putString(key, value).apply()

    fun getInt(key: String, value: Int): Int =
            mSP!!.getInt(key, value)

    fun setInt(key: String, value: Int) =
            mSP!!.edit().putInt(key, value).apply()

    fun getBoolean(key: String, value: Boolean): Boolean =
            mSP!!.getBoolean(key, value)

    fun setBoolean(key: String, value: Boolean) =
            mSP!!.edit().putBoolean(key, value).apply()

    fun getLong(key: String, value: Long): Long =
            mSP!!.getLong(key, value)

    fun setLong(key: String, value: Long) =
            mSP!!.edit().putLong(key, value).apply()

    companion object {
        private var mSP: SharedPreferences? = null
        private val SP_NAME = "ruoye.sp"
    }
}