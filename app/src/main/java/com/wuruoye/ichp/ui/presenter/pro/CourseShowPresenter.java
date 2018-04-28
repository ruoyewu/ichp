package com.wuruoye.ichp.ui.presenter.pro;

import android.support.v4.util.ArrayMap;

import com.wuruoye.ichp.base.model.Api;
import com.wuruoye.ichp.base.util.NetResultUtil;
import com.wuruoye.ichp.ui.contract.pro.CourseShowContract;
import com.wuruoye.ichp.ui.model.UserCache;
import com.wuruoye.ichp.ui.model.bean.Entry;
import com.wuruoye.ichp.ui.model.bean.Media;
import com.wuruoye.ichp.ui.model.bean.User;
import com.wuruoye.library.model.Listener;
import com.wuruoye.library.util.DateUtil;
import com.wuruoye.library.util.net.WNet;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @Created : wuruoye
 * @Date : 2018/4/14 08:09.
 * @Description :
 */

public class CourseShowPresenter extends CourseShowContract.Presenter {
    private UserCache mUserCache = UserCache.getInstance();

    @Override
    public void requestUserInfo(int id) {
        ArrayMap<String, String> values = new ArrayMap<>();
        values.put("token", mUserCache.getToken());
        values.put("user_id", String.valueOf(id));

        WNet.postInBackGround(Api.INSTANCE.getUSER_INFO(), values, new Listener<String>() {
            @Override
            public void onSuccessful(String s) {
                if (isAvailable()) {
                    try {
                        List<User> userList = NetResultUtil.parseDataList(s, User.class);
                        if (userList.size() > 0) {
                            getView().onResultUserInfo(userList.get(0));
                        }else {
                            getView().onResultError("user not found");
                        }
                    } catch (Exception e) {
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
    public void requestEntryList(String s) {
        final String[] ss = s.split(",");
        final List<Entry> entryList = new ArrayList<>();
        for (int i = 0; i < ss.length; i++) {
            ArrayMap<String, String> values = new ArrayMap<>();
            values.put("token", mUserCache.getToken());
            values.put("entry_id", ss[i]);
            WNet.postInBackGround(Api.INSTANCE.getGET_ENTRY(), values, new Listener<String>() {
                @Override
                public void onSuccessful(String s) {
                    try {
                        List<Entry> entries = NetResultUtil.parseDataList(s, Entry.class);
                        entryList.addAll(entries);
                        if (entryList.size() == ss.length) {
                            if (isAvailable()) {
                                getView().onResultEntryList(entryList);
                            }
                        }
                    } catch (Exception e) {
                        if (isAvailable()) {
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
    }

    @Override
    public void requestCollect(int id, boolean collect) {
        ArrayMap<String, String> values = new ArrayMap<>();
        values.put("token", mUserCache.getToken());
        values.put("act_id", "" + id);
        String url = collect ? Api.INSTANCE.getCOLLECT_ACT() : Api.INSTANCE.getDELETE_COLL_ACT();

        WNet.postInBackGround(url, values, new Listener<String>() {
            @Override
            public void onSuccessful(String s) {
                if (isAvailable()) {
                    try {
                        JSONObject obj = new JSONObject(s);
                        if (obj.getInt("code") == 0) {
                            getView().onResultError("操作成功");
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
    public List<Media> parseMedia(String url, String type) {
        List<Media> mediaList = new ArrayList<>();
        String[] urls = url.split(",");
        String[] types = type.split(",");
        if (urls[0].equals("")) {
            return mediaList;
        }
        for (int i = 0; i < urls.length; i++) {
            mediaList.add(new Media(getType(types[i]), urls[i]));
        }
        return mediaList;
    }

    @Override
    public String parseDate(float time) {
        return DateUtil.formatTime((long)(time * 1000), "yyyy-MM-dd");
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
        return null;
    }
}
