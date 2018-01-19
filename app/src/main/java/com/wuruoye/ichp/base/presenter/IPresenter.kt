package com.wuruoye.ichp.base.presenter

/**
 * Created by wuruoye on 2017/12/28.
 * this file is to
 */
interface IPresenter {
    fun attachView(view: IView)
    fun detachView()
}