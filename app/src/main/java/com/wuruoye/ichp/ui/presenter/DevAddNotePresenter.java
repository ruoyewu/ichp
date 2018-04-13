package com.wuruoye.ichp.ui.presenter;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.util.ArrayMap;

import com.wuruoye.ichp.base.model.Api;
import com.wuruoye.ichp.base.model.Config;
import com.wuruoye.ichp.base.model.Listener;
import com.wuruoye.ichp.base.util.DateUtil;
import com.wuruoye.ichp.base.util.LocationUtil;
import com.wuruoye.ichp.ui.contract.AddNoteContract;
import com.wuruoye.ichp.ui.model.UserCache;
import com.wuruoye.ichp.ui.model.bean.Course;
import com.wuruoye.ichp.ui.model.bean.Note;
import com.wuruoye.library.util.net.WNet;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

/**
 * Created by wuruoye on 2018/1/28.
 * this file is to
 */

public class DevAddNotePresenter extends AddNoteContract.Presenter {
    private UserCache mUserCache = UserCache.getInstance();

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
    public void requestUploadFile(String fileName, String type) {
        ArrayMap<String, String> values = new ArrayMap<>();
        values.put("token", mUserCache.getToken());
        ArrayMap<String, String> files = new ArrayMap<>();
        files.put("the_file", fileName);
        WNet.uploadFileInBackground(Api.INSTANCE.getUPLOAD(), values, files, type,
                new com.wuruoye.library.model.Listener<String>() {
                    @Override
                    public void onSuccessful(String s) {
                        try {
                            JSONObject object = new JSONObject(s);
                            boolean isOk;
                            String url;
                            if (object.getInt("code") == 0) {
                                isOk = true;
                                url = object.getString("addr");
                            }else {
                                isOk = false;
                                url = object.getString("msg");
                            }
                            if (isAvailable()) {
                                getView().onFileUploadResult(isOk, url);
                            }
                        } catch (JSONException e) {
                            if (isAvailable()) {
                                getView().onFileUploadResult(false, e.getMessage());
                            }
                        }
                    }

                    @Override
                    public void onFail(String s) {
                        if (isAvailable()) {
                            getView().onFileUploadResult(false, s);
                        }
                    }
                });
    }

    @Override
    public void requestUpNote(Note note) {
        ArrayMap<String, String> values = new ArrayMap<>();
        values.put("token", mUserCache.getToken());
        values.put("title", note.getTitle());
        values.put("discribe", note.getDiscribe());
//        values.put("url", note.getUrl());
        values.put("type", "0");
        values.put("addr", note.getAddr());
        values.put("labels_id_str", note.getLabels_id_str());
        String[] urls = note.getUrl().split(",");
        String[] type = note.getType().split(",");
        JSONArray array = null;
        try {
            array = new JSONArray();
            for (int i = 0; i < urls.length; i++) {
                JSONObject object = new JSONObject();
                object.put("url", urls[i]);
                object.put("type", type[i]);
                array.put(object.toString());
            }
        } catch (JSONException e) {
            if (isAvailable()) {
                getView().onNoteAddResult(false, e.getMessage());
            }
            return;
        }
        values.put("url", array.toString());
        WNet.postInBackGround(Api.INSTANCE.getADD_REC(), values,
                new com.wuruoye.library.model.Listener<String>() {
            @Override
            public void onSuccessful(String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.getInt("code") == 0) {
                        if (isAvailable()) {
                            getView().onNoteAddResult(true, null);
                        }
                    }else {
                        if (isAvailable()) {
                            getView().onNoteAddResult(false, object.getString("msg"));
                        }
                    }
                } catch (JSONException e) {
                    if (isAvailable()) {
                        getView().onNoteAddResult(false, e.getMessage());
                    }
                }
            }

            @Override
            public void onFail(String s) {
                if (isAvailable()) {
                    getView().onNoteAddResult(false, s);
                }
            }
        });
    }

    @Override
    public void requestUpCourse(Course course, String date) {
        ArrayMap<String, String> values = new ArrayMap<>();
        values.put("token", mUserCache.getToken());
        values.put("title", course.getTitle());
        values.put("content", course.getContent());
        values.put("hold_date", date);
        values.put("hold_addr", course.getHold_addr());
        values.put("act_src", course.getAct_src());
        values.put("image_src", course.getImage_src());

        WNet.postInBackGround(Api.INSTANCE.getISSUE_ACT(), values,
                new com.wuruoye.library.model.Listener<String>() {
            @Override
            public void onSuccessful(String s) {
                if (isAvailable()) {
                    try {
                        JSONObject object = new JSONObject(s);
                        if (object.getInt("code") == 0) {
                            getView().onResultCourse();
                        }else {
                            getView().onResultError(object.getString("msg"));
                        }
                    } catch (JSONException e) {
                        getView().onResultError(e.getMessage());
                    }

                }
            }

            @Override
            public void onFail(String s) {
                if (isAvailable()) {
                    getView().onResultError(s);
                }
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
