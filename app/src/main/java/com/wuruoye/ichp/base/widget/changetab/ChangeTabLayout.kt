package com.wuruoye.ichp.base.widget.changetab

import android.animation.ArgbEvaluator
import android.content.Context
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.widget.LinearLayout
import com.wuruoye.ichp.R
import com.wuruoye.ichp.base.util.loge

/**
 * Created by wuruoye on 2017/12/23.
 * this file is to
 */
class ChangeTabLayout : LinearLayout {
    private val mTabItems: ArrayList<ChangeTabItem> = arrayListOf<ChangeTabItem>()
    private val mArgbEvaluator: ArgbEvaluator = ArgbEvaluator()

    private var mNormalTextColor: Int = 0
    private var mSelectTextColor: Int = 0
    private var mNormalImageBorderColor: Int = 0
    private var mSelectImageBorderColor: Int = 0
    private var mNormalImageStuffColor: Int = 0
    private var mSelectImageStuffColor: Int = 0

    private var mViewPager: ViewPager? = null
    private var mCurrentTab: Int = 0

    private fun init(){
        orientation = HORIZONTAL
        post {
            initChild()
        }
    }

    private fun initChild(){
        if (mViewPager != null && mViewPager!!.adapter!!.count != childCount){
            throw IllegalArgumentException("viewpager's size must be " +
                    "equal to the changeTabItem's size")
        }

        for (i in 0 until childCount){
            val view = getChildAt(i)
            if (view is ChangeTabItem){
                mTabItems.add(view)
                if (mViewPager != null){
                    view.setTitle(mViewPager!!.adapter!!.getPageTitle(i)!!.toString())
                }
                view.setOnClickListener { onTabClick(i) }
            }
        }

        mViewPager?.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float,
                                        positionOffsetPixels: Int) {
                onPageSlide(position, positionOffset)
            }

            override fun onPageSelected(position: Int) {
                onPageChange(position)
            }

        })

        changeTabColor()
    }

    private fun onPageSlide(position: Int, positionOffset: Float){
        if (position < mTabItems.size - 1){
            val left = position
            val right = position + 1
            val leftProgress = 1 - positionOffset
            val rightProgress = positionOffset
            log("$left:$right, $leftProgress:$rightProgress")
            val leftColor = getColor(leftProgress)
            val rightColor = getColor(rightProgress)
            if (leftColor != null && rightColor != null){
                mTabItems[left].setColor(leftColor[0], leftColor[1], leftColor[2], leftProgress)
                mTabItems[right].setColor(rightColor[0], rightColor[1], rightColor[2], rightProgress)
            }
            (0 until mTabItems.size)
                    .filter { it != left && it != right }
                    .forEach {
                        mTabItems[it].setColor(mNormalTextColor, mNormalImageBorderColor,
                                mNormalImageStuffColor, 0F)
                    }
        }
    }

    private fun getColor(progress: Float): IntArray?{
        val textColor: Int
        val borderColor: Int
        val stuffColor: Int
        val dProgress: Float
        if (progress < 0.5 && progress > 0) {
            dProgress = progress * 2
            textColor = mArgbEvaluator.evaluate(dProgress,
                    mNormalTextColor, mSelectTextColor) as Int
            borderColor = mArgbEvaluator.evaluate(dProgress,
                    mNormalImageBorderColor, mSelectImageBorderColor) as Int
            stuffColor = mNormalImageStuffColor
            return intArrayOf(textColor, borderColor, stuffColor)
        } else if (progress > 0.5 && progress < 1) {
            dProgress = progress * 2 - 1
            textColor = mSelectTextColor
            borderColor = mSelectImageBorderColor
            stuffColor = mArgbEvaluator.evaluate(dProgress,
                    mNormalImageStuffColor, mSelectImageStuffColor) as Int
            return intArrayOf(textColor, borderColor, stuffColor)
        }
        return null
    }

    private fun onPageChange(position: Int){
        mCurrentTab = position
//        changeTabColor()
    }

    private fun onTabClick(index: Int){
        mViewPager?.currentItem = index
        if (mViewPager == null) {
            onPageChange(index)
        }
    }

    private fun changeTabColor(){
        for (i in 0 until mTabItems.size){
            if (i == mCurrentTab){
                mTabItems[i].setColor(mSelectTextColor, mSelectImageBorderColor,
                        mSelectImageStuffColor, 1F)
            }else {
                mTabItems[i].setColor(mNormalTextColor, mNormalImageBorderColor,
                        mNormalImageStuffColor, 0F)
            }
        }
    }

    private fun setAttr(context: Context, attrs: AttributeSet){
        val ta = context.obtainStyledAttributes(attrs, R.styleable.ChangeTabLayout)
        mNormalTextColor = ta.getColor(R.styleable.ChangeTabLayout_normalTextColor, 0)
        mSelectTextColor = ta.getColor(R.styleable.ChangeTabLayout_selectTextColor, 0)
        mNormalImageBorderColor = ta.getColor(R.styleable.ChangeTabLayout_normalBorderColor, 0)
        mSelectImageBorderColor = ta.getColor(R.styleable.ChangeTabLayout_selectBorderColor, 0)
        mNormalImageStuffColor = ta.getColor(R.styleable.ChangeTabLayout_normalStuffColor, 0)
        mSelectImageStuffColor = ta.getColor(R.styleable.ChangeTabLayout_selectStuffColor, 0)
        ta.recycle()
    }

    public fun attachViewPager(viewPager: ViewPager){
        mViewPager = viewPager
    }

    public fun getTabAt(index: Int): ChangeTabItem = mTabItems[index]

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

    private fun log(message: Any){
//        loge("ChangeTabLayout: " + message)
    }
}