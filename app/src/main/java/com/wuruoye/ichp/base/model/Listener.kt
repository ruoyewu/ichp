package com.wuruoye.ichp.base.model

/**
 * Created by wuruoye on 2017/9/15.
 * this file is to do
 */
interface Listener<in T> {
    fun onSuccess(model: T)
    fun onFail(message: String)
}