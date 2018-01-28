package com.wuruoye.ichp.base

import android.os.Bundle
import com.wuruoye.ichp.base.presenter.IPresenter
import com.wuruoye.ichp.base.presenter.IView

/**
 * Created by wuruoye on 2018/1/24.
 * this file is to
 */
abstract class BaseViewActivity : BaseActivity(), IView{
    var mPresenter: IPresenter? = null

    fun setPresenter(presenter: IPresenter) {
        mPresenter = presenter
        mPresenter!!.attachView(this)
    }

    override fun onDestroy() {
        mPresenter?.detachView()
        super.onDestroy()
    }
}