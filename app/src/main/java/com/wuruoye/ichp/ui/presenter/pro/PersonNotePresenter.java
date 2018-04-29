package com.wuruoye.ichp.ui.presenter.pro;


import android.support.v4.util.ArrayMap;

import com.wuruoye.ichp.base.model.Api;
import com.wuruoye.ichp.base.util.NetResultUtil;
import com.wuruoye.ichp.ui.contract.pro.PersonNoteContract;
import com.wuruoye.ichp.ui.model.UserCache;
import com.wuruoye.ichp.ui.model.bean.Course;
import com.wuruoye.ichp.ui.model.bean.Note;
import com.wuruoye.library.model.Listener;
import com.wuruoye.library.util.net.WNet;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @Created : wuruoye
 * @Date : 2018/4/24 13:17.
 * @Description :
 */

public class PersonNotePresenter extends PersonNoteContract.Presenter {
    private UserCache mUserCache = UserCache.getInstance();

    @Override
    public void requestData(final int type) {
        ArrayMap<String, String> values = new ArrayMap<>();
        values.put("token", mUserCache.getToken());

        String url;
        if (type == PersonNoteContract.TYPE_NOTE) {
            values.put("recorder", "" + mUserCache.getUserId());
            url = Api.INSTANCE.getGET_USER_REC();
        }else {
            values.put("publisher", "" + mUserCache.getUserId());
            url = Api.INSTANCE.getGET_USER_ACT();
        }

        WNet.postInBackGround(url, values, new Listener<String>() {
            @Override
            public void onSuccessful(String s) {
                if (isAvailable()) {
                    try {
                        List<Object> objList = new ArrayList<>();
                        if (type == PersonNoteContract.TYPE_NOTE) {
                            List<Note> noteList = NetResultUtil.parseDataList(s, Note.class);
                            noteList = NetResultUtil.net2localNote(noteList);
                            Collections.reverse(noteList);
                            objList.addAll(noteList);
                        }else {
                            List<Course> courseList = NetResultUtil.net2localCourse(
                                    NetResultUtil.parseDataList(s, Course.class));
                            Collections.reverse(courseList);
                            objList.addAll(courseList);
                        }
                        getView().onResultData(objList);
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
    public void requestRemove(final int id, final int type) {
        ArrayMap<String, String> values = new ArrayMap<>();
        values.put("token", mUserCache.getToken());

        String url;
        if (type == PersonNoteContract.TYPE_NOTE) {
            url = Api.INSTANCE.getDELETE_REC();
            values.put("rec_id", "" + id);
        }else {
            url = Api.INSTANCE.getDELETE_ACT();
            values.put("act_id", "" + id);
        }
        WNet.postInBackGround(url, values, new Listener<String>() {
            @Override
            public void onSuccessful(String s) {
                if (isAvailable()) {
                    try {
                        JSONObject obj = new JSONObject(s);
                        if (obj.getInt("code") == 0) {
                            getView().onResultRemove(id, true);
                        }else {
                            getView().onResultRemove(id, false);
                        }
                    } catch (JSONException e) {
                        getView().onResultRemove(id, false);
                    }
                }
            }

            @Override
            public void onFail(String s) {
                if (isAvailable()) {
                    getView().onResultRemove(id, false);
                }
            }
        });
    }

    @Override
    public void requestRemove(List<Integer> id, int type) {
        for (int i : id) {
            requestRemove(i, type);
        }
    }
}
