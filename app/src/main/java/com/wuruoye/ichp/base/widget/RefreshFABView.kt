package com.wuruoye.ichp.base.widget

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.support.design.widget.FloatingActionButton
import android.util.AttributeSet
import android.view.animation.LinearInterpolator

/**
 * Created by wuruoye on 2017/9/24.
 * this file is to do
 */

class RefreshFABView : FloatingActionButton {
    constructor(context: Context) : super(context) { init() }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) { init() }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) { init() }

    private var mIsStart = false
    private lateinit var mAnimator: ValueAnimator

    private fun init(){
        mAnimator = ObjectAnimator.ofFloat(0f, 179f)
        mAnimator.duration = 800
        mAnimator.startDelay = 0
        mAnimator.interpolator = LinearInterpolator()
        mAnimator.repeatCount = ValueAnimator.INFINITE
        mAnimator.repeatMode = ValueAnimator.RESTART
        mAnimator.addUpdateListener { p0 ->
            val value = p0!!.animatedValue as Float
            rotation = value
        }
        mAnimator.addListener(object : Animator.AnimatorListener{
            override fun onAnimationRepeat(p0: Animator?) {
                if (!mIsStart){
                    mAnimator.cancel()
                }
            }

            override fun onAnimationEnd(p0: Animator?) {
            }

            override fun onAnimationCancel(p0: Animator?) {
            }

            override fun onAnimationStart(p0: Animator?) {

            }

        })
    }

    fun start(){
        if (!mIsStart) {
            mIsStart = true
            mAnimator.start()
        }
    }

    fun stop(){
        mIsStart = false
    }
}
