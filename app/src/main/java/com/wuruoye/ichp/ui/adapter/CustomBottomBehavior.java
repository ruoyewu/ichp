package com.wuruoye.ichp.ui.adapter;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.wuruoye.ichp.base.util.Dp2PxUtil;

/**
 * Created by wuruoye on 2018/2/3.
 * this file is to
 */

public class CustomBottomBehavior extends CoordinatorLayout.Behavior<View> {

    private static final String TAG = "CustomBottomBehavior";

    public CustomBottomBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        float bottom = dependency.getBottom();
        float height = dependency.getMeasuredHeight() -
                Dp2PxUtil.INSTANCE.dp2px(parent.getContext(), 50F);
        float progress = (dependency.getMeasuredHeight() - bottom) / height;
        child.setTranslationY(child.getMeasuredHeight() * progress);
        return true;
    }

    private void log(String message) {
        Log.e(TAG, message);
    }
}
