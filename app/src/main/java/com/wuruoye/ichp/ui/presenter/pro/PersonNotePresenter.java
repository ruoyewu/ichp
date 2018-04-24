package com.wuruoye.ichp.ui.presenter.pro;


import android.support.v4.util.ArrayMap;

import com.wuruoye.ichp.base.model.Api;
import com.wuruoye.ichp.base.util.NetResultUtil;
import com.wuruoye.ichp.ui.contract.pro.PersonNoteContract;
import com.wuruoye.ichp.ui.model.UserCache;
import com.wuruoye.ichp.ui.model.bean.Note;
import com.wuruoye.library.model.Listener;
import com.wuruoye.library.util.net.WNet;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * @Created : wuruoye
 * @Date : 2018/4/24 13:17.
 * @Description :
 */

public class PersonNotePresenter extends PersonNoteContract.Presenter {
    private UserCache mUserCache = UserCache.getInstance();

    @Override
    public void requestData() {
        ArrayMap<String, String> values = new ArrayMap<>();
        values.put("token", mUserCache.getToken());
        values.put("recorder", "" + mUserCache.getUserId());
        WNet.postInBackGround(Api.INSTANCE.getGET_USER_REC(), values, new Listener<String>() {
            @Override
            public void onSuccessful(String s) {
                if (isAvailable()) {
                    try {
                        List<Note> noteList = NetResultUtil.parseDataList(s, Note.class);
                        noteList = NetResultUtil.net2localNote(noteList);
                        getView().onResultData(noteList);
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
    public void requestRemove(final int id) {
        ArrayMap<String, String> values = new ArrayMap<>();
        values.put("token", mUserCache.getToken());
        values.put("rec_id", "" + id);
        WNet.postInBackGround(Api.INSTANCE.getDELETE_REC(), values, new Listener<String>() {
            @Override
            public void onSuccessful(String s) {
                if (isAvailable()) {
                    try {
                        JSONObject obj = new JSONObject(s);
                        if (obj.getInt("code") == 0) {
                            getView().onResultRemove(id);
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
    public void requestRemove(List<Integer> id) {
        for (int i : id) {
            requestRemove(i);
        }
    }
}
