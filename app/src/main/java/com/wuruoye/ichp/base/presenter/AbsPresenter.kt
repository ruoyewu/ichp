package com.wuruoye.ichp.base.presenter

import java.lang.ref.WeakReference

/**
 * Created by wuruoye on 2017/9/15.
 * this file is to do
 */
@Suppress("UNCHECKED_CAST")
abstract class AbsPresenter<out V : IView> : IPresenter {
    private var mViewRef: WeakReference<V>? = null

    enum class Method{
        NET,
        LOCAL
    }

    override fun attachView(view: IView) {
        mViewRef = WeakReference(view as V)
    }

    override fun detachView() {
        if (mViewRef != null){
            mViewRef!!.clear()
            mViewRef = null
        }
    }

    protected fun isAttached(): Boolean = mViewRef != null

    protected fun isAvailable(): Boolean = getView() != null

    protected fun getView(): V? {
        return if (mViewRef != null){
            mViewRef!!.get()
        }else{
            null
        }
    }
}