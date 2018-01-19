package com.wuruoye.ichp.base.widget.changetab

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.support.v4.graphics.drawable.DrawableCompat
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.wuruoye.ichp.R

/**
 * Created by wuruoye on 2017/12/23.
 * this file is to
 */
class ChangeTabItem : LinearLayout {
    private lateinit var iv: ImageView
    private lateinit var tv: TextView

    private var icon: Int = 0
    private var iconSelect: Int = 0
    private lateinit var text: String

    private fun init() {
        orientation = VERTICAL
        gravity = Gravity.CENTER
        val layoutParams = LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1F)
        this.layoutParams = layoutParams
        initView()
    }

    private fun initView(){
        LayoutInflater.from(context)
                .inflate(R.layout.view_change_tab_item, this)
        iv = findViewById(R.id.iv_change_tab)
        tv = findViewById(R.id.tv_change_tab)

        iv.setImageResource(icon)
        tv.text = text
    }

    public fun setColor(textColor: Int, imgBorderColor: Int, imgStuffColor: Int, progress: Float){
        tv.setTextColor(textColor)
        iv.setImageDrawable(getDrawable(imgBorderColor, imgStuffColor, progress))
    }

    public fun setTitle(text: String){
        tv.text = text
    }

    private fun getDrawable(borderColor: Int, stuffColor: Int, progress: Float): Drawable{
        if (progress <= 0.5){
            val borderDrawable = BitmapDrawable(resources,
                    BitmapFactory.decodeResource(resources, icon))
            DrawableCompat.setTint(borderDrawable, borderColor)
            return borderDrawable
        }else {
            val borderDrawable = BitmapDrawable(resources,
                    BitmapFactory.decodeResource(resources, icon))
            val stuffDrawable = BitmapDrawable(resources,
                    BitmapFactory.decodeResource(resources, iconSelect))
            DrawableCompat.setTint(borderDrawable, borderColor)
            DrawableCompat.setTint(stuffDrawable, stuffColor)
            val layerDrawable = LayerDrawable(arrayOf(borderDrawable, stuffDrawable))
            return layerDrawable
        }
    }

    private fun setAttr(context: Context, attrs: AttributeSet){
        val ta = context.obtainStyledAttributes(attrs, R.styleable.ChangeTabItem)
        text = ta.getString(R.styleable.ChangeTabItem_text)
        icon = ta.getResourceId(R.styleable.ChangeTabItem_icon, 0)
        iconSelect = ta.getResourceId(R.styleable.ChangeTabItem_iconSelect, 0)
        ta.recycle()
    }

    constructor(context: Context) : super(context){
        init()
    }
    constructor(context: Context, attrs: AttributeSet): super(context, attrs){
        setAttr(context, attrs)
        init()
    }
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int):
            super(context, attrs, defStyleAttr){
        setAttr(context, attrs)
        init()
    }
}