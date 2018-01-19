package com.wuruoye.ichp.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.wuruoye.ichp.base.presenter.IPresenter
import com.wuruoye.ichp.base.presenter.IView

/**
 * Created by wuruoye on 2017/9/15.
 * this file is to do
 */
abstract class BaseFragment : Fragment(), IView {
    abstract val contentView: Int
    abstract fun initData(bundle: Bundle?)
    abstract fun initView(view: View)
    abstract val presenter: IPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = LayoutInflater.from(context)
                .inflate(contentView, null)
        presenter.attachView(this)

        initData(arguments)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }
}