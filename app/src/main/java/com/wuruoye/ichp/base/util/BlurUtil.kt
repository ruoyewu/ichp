package com.wuruoye.ichp.base.util

import android.content.Context
import android.graphics.Bitmap
import android.support.v8.renderscript.Allocation
import android.support.v8.renderscript.Element
import android.support.v8.renderscript.RenderScript
import android.support.v8.renderscript.ScriptIntrinsicBlur

/**
 * Created by wuruoye on 2017/9/24.
 * this file is to do
 */
object BlurUtil {

    fun blurBitmap(context: Context, image: Bitmap): Bitmap{
        var scaleRatio = image.width / 100
        scaleRatio = if (scaleRatio < 1) 1 else scaleRatio
        val width = Math.round(image.width.toDouble() / scaleRatio).toInt()
        val height = Math.round(image.height.toDouble() / scaleRatio).toInt()

        val bitmap = Bitmap.createScaledBitmap(image, width, height, false)

        val rs = RenderScript.create(context)
        val blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs))

        val input = Allocation.createFromBitmap(rs, bitmap)
        val output = Allocation.createTyped(rs, input.type)

        blurScript.setRadius(5f)
        blurScript.setInput(input)
        blurScript.forEach(output)
        output.copyTo(bitmap)

        input.destroy()
        output.destroy()
        blurScript.destroy()
        rs.destroy()

        return bitmap
    }
}