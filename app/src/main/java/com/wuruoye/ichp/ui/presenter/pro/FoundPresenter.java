package com.wuruoye.ichp.ui.presenter.pro;

import android.support.v4.util.ArrayMap;

import com.google.gson.Gson;
import com.wuruoye.ichp.base.model.Api;
import com.wuruoye.ichp.base.util.NetResultUtil;
import com.wuruoye.ichp.ui.contract.pro.FoundContract;
import com.wuruoye.ichp.ui.model.UserCache;
import com.wuruoye.ichp.ui.model.bean.Course;
import com.wuruoye.ichp.ui.model.bean.Note;
import com.wuruoye.library.model.Listener;
import com.wuruoye.library.util.net.WNet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * @Created : wuruoye
 * @Date : 2018/4/13 23:43.
 * @Description :
 */

public class FoundPresenter extends FoundContract.Presenter {
    private UserCache mUserCache = UserCache.getInstance();

    @Override
    public void requestRecommend() {
        ArrayMap<String, String> values = new ArrayMap<>();
        values.put("token", mUserCache.getToken());
        WNet.postInBackGround(Api.INSTANCE.getRECOMMEND_ALL(), values, new Listener<String>() {
            @Override
            public void onSuccessful(String s) {
                if (isAvailable()) {
                    try {
                        JSONObject object = new JSONObject(s);
                        Gson gson = new Gson();
                        if (object.getInt("code") == 0) {
                            List<Note> noteList = new ArrayList<>();
                            List<Course> courseList = new ArrayList<>();
                            JSONArray noteArray = object.getJSONArray("dataRec");
                            JSONArray courseArray = object.getJSONArray("dataAct");
                            for (int i = 0; i < noteArray.length(); i++) {
                                noteList.add(gson.fromJson(noteArray.getString(i), Note.class));
                            }
                            for (int i = 0; i < courseArray.length(); i++) {
                                courseList.add(gson.fromJson(courseArray.getString(i), Course.class));
                            }

                            List<Object> objectList = new ArrayList<>();
                            objectList.addAll(NetResultUtil.net2localNote(noteList));
                            objectList.addAll(NetResultUtil.net2localCourse(courseList));
                            getView().onResultRecommend(objectList);
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
    public void requestNote(String addr) {
        ArrayMap<String, String> values = new ArrayMap<>();
        values.put("token", mUserCache.getToken());
        values.put("addr", addr);

        WNet.postInBackGround(Api.INSTANCE.getRECOMMEND_REC(), values, new Listener<String>() {
            @Override
            public void onSuccessful(String s) {
                if (isAvailable()) {
                    try {
                        List<Note> noteList = NetResultUtil.parseDataList(s, Note.class);
                        noteList = NetResultUtil.net2localNote(noteList);
                        getView().onResultNote(noteList);
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
    public void requestCourse(String addr) {
        ArrayMap<String, String> values = new ArrayMap<>();
        values.put("token", mUserCache.getToken());
        values.put("addr", addr);

        WNet.postInBackGround(Api.INSTANCE.getRECOMMEND_ACT(), values, new Listener<String>() {
            @Override
            public void onSuccessful(String s) {
                if (isAvailable()) {
                    try {
                        List<Course> courseList = NetResultUtil.parseDataList(s, Course.class);
                        courseList = NetResultUtil.net2localCourse(courseList);
                        getView().onResultCourse(courseList);
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
}
