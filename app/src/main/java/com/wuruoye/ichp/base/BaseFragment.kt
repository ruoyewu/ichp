package com.wuruoye.ichp.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by wuruoye on 2017/9/15.
 * this file is to do
 */
abstract class BaseFragment : Fragment(){
    abstract val contentView: Int
    abstract fun initData(bundle: Bundle?)
    abstract fun initView(view: View)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = LayoutInflater.from(context)
                .inflate(contentView, null)

        initData(arguments)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
    }
}