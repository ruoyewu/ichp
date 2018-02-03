package com.wuruoye.ichp.base.util

import android.content.Context

/**
 * Created by wuruoye on 2018/2/3.
 * this file is to
 */
object Dp2PxUtil {
    fun dp2px(context: Context, dp: Float): Float {
        val scale = context.resources.displayMetrics.density
        return dp * scale + 0.5F
    }

    fun px2dp(context: Context, px: Float): Float {
        val scale = context.resources.displayMetrics.density
        return px / scale + 0.5F
    }
}