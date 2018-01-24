package com.wuruoye.ichp.base.util

import android.content.Context
import android.graphics.Typeface

/**
 * Created by wuruoye on 2017/10/19.
 * this file is to do use custom font
 */
object FontUtil {
    fun changeFont(context: Context, name: String){
        try {
            val typeface = Typeface.createFromAsset(context.assets, name)
            val field = Typeface::class.java.getDeclaredField("SERIF")
            field.isAccessible = true
            field.set(null, typeface)
        } catch (e: Exception) {
            loge(e)
        }
    }
}