package com.wuruoye.ichp.base.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by wuruoye on 2018/2/27.
 * this file is to
 */

public class TouchViewPager extends ViewPager {
    private boolean isPagingEnable = true;

    public TouchViewPager(@NonNull Context context) {
        super(context);
    }

    public TouchViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return isPagingEnable && super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return isPagingEnable && super.onInterceptTouchEvent(ev);
    }

    public void setPagingEnable(boolean enable) {
        isPagingEnable = enable;
    }
}
