package com.wuruoye.ichp.base

import android.os.Bundle
import com.wuruoye.ichp.base.presenter.IPresenter
import com.wuruoye.ichp.base.presenter.IView

/**
 * Created by wuruoye on 2018/1/24.
 * this file is to
 */
abstract class BaseViewActivity : BaseActivity(), IView{
    abstract val mPresenter: IPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        mPresenter.attachView(this)
        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        mPresenter.detachView()
        super.onDestroy()
    }
}