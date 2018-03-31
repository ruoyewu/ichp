package com.wuruoye.ichp.ui.presenter;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.wuruoye.ichp.base.model.Config;
import com.wuruoye.ichp.base.model.Listener;
import com.wuruoye.ichp.base.util.DateUtil;
import com.wuruoye.ichp.base.util.LocationUtil;
import com.wuruoye.ichp.ui.contract.AddNoteContract;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

/**
 * Created by wuruoye on 2018/1/28.
 * this file is to
 */

public class DevAddNotePresenter extends AddNoteContract.Presenter {
    @Override
    public void requestLocation(final Context context) {
            LocationUtil.INSTANCE.getLocation(context, new Listener<Double[]>() {
                @Override
                public void onSuccess(Double[] model) {
                    try {
                        Geocoder geocoder = new Geocoder(context);
                        List<Address> addresses = geocoder.getFromLocation(model[0], model[1], 1);
                        StringBuilder sb = new StringBuilder();
                        String[] location = new String[addresses.get(0).getMaxAddressLineIndex()];
                        for (int i = 0; i < addresses.get(0).getMaxAddressLineIndex(); i++) {
                            location[i] = addresses.get(0).getAddressLine(i);
                        }
                        if (isAvailable()) {
                            getView().onLocationResult(model, location);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onFail(@NotNull String message) {
                    getView().onLocationError(message);
                }
            });
    }

    @Override
    public String generateImageName() {
        return Config.INSTANCE.getIMAGE_PATH() +
                DateUtil.INSTANCE.getDateString(System.currentTimeMillis()) + ".jpg";
    }

    @Override
    public String generateVideoName() {
        return Config.INSTANCE.getVIDEO_PATH() +
                DateUtil.INSTANCE.getDateString(System.currentTimeMillis()) + ".mp4";
    }

    @Override
    public String generateRecordName() {
        return Config.INSTANCE.getRECORD_PATH() +
                DateUtil.INSTANCE.getDateString(System.currentTimeMillis()) + ".m4a";
    }
}
