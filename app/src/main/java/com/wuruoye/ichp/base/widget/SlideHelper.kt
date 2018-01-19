package com.wuruoye.ichp.base.widget

import android.app.Activity
import android.view.View
import kotlin.collections.ArrayList

/**
 * Created by wuruoye on 2017/10/24.
 * this file is to do
 */
object SlideHelper {
    val activityList: ArrayList<Activity> = ArrayList()

    fun addActivity(activity: Activity){
        activityList.add(activity)
    }

    fun removeActivity(activity: Activity){
        activityList.remove(activity)
    }

    fun getPreActivityView(): View{
        val size = activityList.size
        if (size >= 2){
            return activityList[size - 2].window.decorView.rootView
        }else {
            throw Exception("没有上一层activity")
        }
    }
}