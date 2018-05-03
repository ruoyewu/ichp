package com.wuruoye.ichp.ui.presenter.pro;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;

import com.wuruoye.ichp.base.App;
import com.wuruoye.ichp.base.model.Api;
import com.wuruoye.ichp.base.model.Config;
import com.wuruoye.ichp.base.model.Listener;
import com.wuruoye.ichp.base.util.DateUtil;
import com.wuruoye.ichp.base.util.LocationUtil;
import com.wuruoye.ichp.base.util.NetResultUtil;
import com.wuruoye.ichp.ui.contract.pro.AddNoteContract;
import com.wuruoye.ichp.ui.model.UserCache;
import com.wuruoye.ichp.ui.model.bean.Course;
import com.wuruoye.ichp.ui.model.bean.Media;
import com.wuruoye.ichp.ui.model.bean.Note;
import com.wuruoye.library.util.net.WNet;
import com.zxy.tiny.Tiny;
import com.zxy.tiny.callback.FileCallback;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuruoye on 2018/1/28.
 * this file is to
 */

public class NoteAddPresenter extends AddNoteContract.Presenter {
    private UserCache mUserCache = UserCache.getInstance();

    @Override
    public void requestLocation(final Context context) {
        LocationUtil.INSTANCE.getLocation(context, new Listener<Double[]>() {
            @Override
            public void onSuccess(final Double[] model) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Geocoder geocoder = new Geocoder(context);
                            List<Address> addresses = geocoder.getFromLocation(model[0],
                                    model[1], 1);
                            if (addresses.size() > 0) {
                                final String[] location = new String[addresses.get(0)
                                        .getMaxAddressLineIndex()];
                                for (int i = 0; i < addresses.get(0)
                                        .getMaxAddressLineIndex(); i++) {
                                    location[i] = addresses.get(0).getAddressLine(i);
                                }
                                if (isAvailable()) {
                                    App.runOnMainThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            getView().onResultLocation(model, location);
                                        }
                                    });
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
            @Override
            public void onFail(@NotNull final String message) {
                if (isAvailable()) {
                    App.runOnMainThread(new Runnable() {
                        @Override
                        public void run() {
                            getView().onResultLocationError(message);
                        }
                    });
                }
            }
        });
    }

    @Override
    public void requestUploadFile(final String fileName, final String type) {
        final ArrayMap<String, String> values = new ArrayMap<>();
        values.put("token", mUserCache.getToken());
        final ArrayMap<String, String> files = new ArrayMap<>();
        files.put("the_file", fileName);
        if (type.equals("image/*")) {
            Tiny.getInstance().source(fileName).asFile()
                    .compress(new FileCallback() {
                        @Override
                        public void callback(boolean isSuccess, String outfile, Throwable t) {
                            if (isSuccess) {
                                files.put("the_file", outfile);
                                WNet.uploadFileInBackground(Api.INSTANCE.getUPLOAD(), values, files,
                                        type, new com.wuruoye.library.model.Listener<String>() {
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
                                                        getView().onResultUpload(isOk, url);
                                                    }
                                                } catch (JSONException e) {
                                                    if (isAvailable()) {
                                                        getView().onResultUpload(false,
                                                                e.getMessage());
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onFail(String s) {
                                                if (isAvailable()) {
                                                    getView().onResultUpload(false, s);
                                                }
                                            }
                                        });
                            }else {
                                if (isAvailable()) {
                                    getView().onResultUpload(false, t.getMessage());
                                }
                            }
                        }
                    });
        }else if (type.equals("audio/*") || type.equals("video/*")) {
            WNet.uploadFileInBackground(Api.INSTANCE.getUPLOAD(), values, files,
                    type, new com.wuruoye.library.model.Listener<String>() {
                        @Override
                        public void onSuccessful(String s) {
                            try {
                                JSONObject object = new JSONObject(s);
                                boolean isOk;
                                String url;
                                if (object.getInt("code") == 0) {
                                    isOk = true;
                                    url = object.getString("addr");
                                } else {
                                    isOk = false;
                                    url = object.getString("msg");
                                }
                                if (isAvailable()) {
                                    getView().onResultUpload(isOk, url);
                                }
                            } catch (JSONException e) {
                                if (isAvailable()) {
                                    getView().onResultUpload(false,
                                            e.getMessage());
                                }
                            }
                        }

                        @Override
                        public void onFail(String s) {
                            if (isAvailable()) {
                                getView().onResultUpload(false, s);
                            }
                        }
                    });
        }
    }

    @Override
    public void requestUpNote(Note note, final boolean modify) {
        ArrayMap<String, String> values = new ArrayMap<>();
        values.put("token", mUserCache.getToken());
        values.put("title", note.getTitle());
        values.put("discribe", note.getDiscribe());
        values.put("type", "0");
        values.put("addr", note.getAddr());
        values.put("labels_id_str", note.getLabels_id_str());

        try {
            values.put("url", NetResultUtil.generateUrl(note.getUrl(), note.getType()));
        } catch (JSONException e) {
            getView().onResultError(e.getMessage());
            return;
        }

        String url;
        if (modify) {
            url = Api.INSTANCE.getMODIFY_REC();
            values.put("rec_id", "" + note.getRec_id());
            values.put("dicribe", note.getDiscribe());
        }else {
            url = Api.INSTANCE.getADD_REC();
        }

        WNet.postInBackGround(url, values, new com.wuruoye.library.model.Listener<String>() {
            @Override
            public void onSuccessful(String s) {
                if (isAvailable()) {
                    try {
                        JSONObject obj = new JSONObject(s);
                        if (obj.getInt("code") == 0) {
                            if (modify) {
                                getView().onResultModify();
                            }else {
                                getView().onResultAddNote();
                            }
                        }else {
                            getView().onResultError(obj.getString("msg"));
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
    public void requestUpCourse(Course course, String date, final boolean modify) {
        ArrayMap<String, String> values = new ArrayMap<>();
        values.put("token", mUserCache.getToken());
        values.put("title", course.getTitle());
        values.put("content", course.getContent());
        values.put("hold_date", date);
        values.put("hold_addr", course.getHold_addr());
        values.put("act_src", course.getAct_src());
        values.put("labels_id_str", course.getLabels_id_str());

        try {
            values.put("image_src", NetResultUtil.generateUrl(course.getImage_src(), course.getType()));
        } catch (JSONException e) {
            getView().onResultError(e.getMessage());
            return;
        }

        String url;
        if (modify) {
            values.put("act_id", "" + course.getAct_id());
            url = Api.INSTANCE.getMODIFY_ACT();
        }else {
            url = Api.INSTANCE.getISSUE_ACT();
        }

        WNet.postInBackGround(url, values, new com.wuruoye.library.model.Listener<String>() {
            @Override
            public void onSuccessful(String s) {
                if (isAvailable()) {
                    try {
                        JSONObject obj = new JSONObject(s);
                        if (obj.getInt("code") == 0) {
                            if (modify) {
                                getView().onResultModify();
                            }else {
                                getView().onResultAddCourse();
                            }
                        }else {
                            getView().onResultError(obj.getString("msg"));
                        }
                    } catch (JSONException e) {
                        getView().onResultError(s);
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

    @Override
    public String formatDate(float date) {
        return DateUtil.INSTANCE.formatTime((long)(date * 1000), "yyyy-MM-dd");
    }

    @Override
    public List<Media> parseMedia(String url, String type) {
        List<Media> mediaList = new ArrayList<>();
        String[] urls = url.split(",");
        String[] types = type.split(",");
        if (!TextUtils.isEmpty(urls[0])) {
            for (int i = 0; i < urls.length; i++) {
                mediaList.add(new Media(getType(types[i]), urls[i]));
            }
        }

        return mediaList;
    }

    private Media.Type getType(String type) {
        switch (type) {
            case "1":
                return Media.Type.IMAGE;
            case "2":
                return Media.Type.VIDEO;
            case "3":
                return Media.Type.RECORD;
        }
        return Media.Type.IMAGE;
    }
}
