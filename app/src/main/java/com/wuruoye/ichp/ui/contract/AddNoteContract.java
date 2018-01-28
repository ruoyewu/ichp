package com.wuruoye.ichp.ui.contract;

import android.app.Activity;
import android.content.Context;

import com.wuruoye.ichp.base.presenter.AbsPresenter;
import com.wuruoye.ichp.base.presenter.IView;

/**
 * Created by wuruoye on 2018/1/28.
 * this file is to
 */

public interface AddNoteContract {
    interface View extends IView {
        void onLocationResult(String location);
    }

    abstract class Presenter extends AbsPresenter<View> {
        abstract public void requestLocation(Context context);
        abstract public String generateImageName();
    }
}
