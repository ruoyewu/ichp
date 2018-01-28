package com.wuruoye.ichp.ui.presenter;

import android.app.Activity;
import android.content.Context;

import com.wuruoye.ichp.base.model.Config;
import com.wuruoye.ichp.base.model.MainHandler;
import com.wuruoye.ichp.base.util.LocationUtil;
import com.wuruoye.ichp.ui.contract.AddNoteContract;

/**
 * Created by wuruoye on 2018/1/28.
 * this file is to
 */

public class DevAddNotePresenter extends AddNoteContract.Presenter {
    @Override
    public void requestLocation(final Context context) {
        try {
            Double[] location = LocationUtil.INSTANCE.getLocation(context);
            final String result = location[0] + "," + location[1];

            MainHandler.getInstance().post(new Runnable() {
                @Override
                public void run() {
                    if (isAvailable()) {
                        getView().onLocationResult(result);
                    }
                }
            });
        } catch (Exception e) {
            MainHandler.getInstance().post(new Runnable() {
                @Override
                public void run() {
                    if (isAvailable()) {
                        getView().onResultWorn("获取位置信息失败");
                    }
                }
            });
        }
    }

    @Override
    public String generateImageName() {
        return Config.INSTANCE.getSYSTEM_IMAGE_PATH() + System.currentTimeMillis() + ".jpg";
    }
}
