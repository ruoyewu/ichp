package com.wuruoye.ichp.ui.presenter.pro;

import android.support.v4.util.ArrayMap;

import com.wuruoye.ichp.base.model.Api;
import com.wuruoye.ichp.base.util.NetResultUtil;
import com.wuruoye.ichp.ui.contract.pro.PersonCollectContract;
import com.wuruoye.ichp.ui.model.UserCache;
import com.wuruoye.ichp.ui.model.bean.Course;
import com.wuruoye.ichp.ui.model.bean.Entry;
import com.wuruoye.ichp.ui.model.bean.Note;
import com.wuruoye.library.model.Listener;
import com.wuruoye.library.util.net.WNet;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.wuruoye.ichp.ui.contract.pro.PersonCollectContract.TYPE_COURSE;
import static com.wuruoye.ichp.ui.contract.pro.PersonCollectContract.TYPE_ENTRY;
import static com.wuruoye.ichp.ui.contract.pro.PersonCollectContract.TYPE_NOTE;

/**
 * @Created : wuruoye
 * @Date : 2018/4/24 20:24.
 * @Description :
 */

public class PersonCollectPresenter extends PersonCollectContract.Presenter {
    private UserCache mUserCache = UserCache.getInstance();

    @Override
    public void requestData(final int type) {
        ArrayMap<String, String> values = new ArrayMap<>();
        values.put("token", mUserCache.getToken());
        String url;
        switch (type) {
            case TYPE_NOTE:
                url = Api.INSTANCE.getGET_COLL_REC();
                break;
            case TYPE_COURSE:
                url = Api.INSTANCE.getGET_COLL_ACT();
                break;
            case TYPE_ENTRY:
                url = Api.INSTANCE.getGET_COLL_ENTRY();
                break;
            default:
                url = "";
                break;
        }

        WNet.postInBackGround(url, values, new Listener<String>() {
            @Override
            public void onSuccessful(String s) {
                if (isAvailable()) {
                    try {
                        List<Object> objList = new ArrayList<>();
                        switch (type) {
                            case TYPE_NOTE:
                                objList.addAll(NetResultUtil.net2localNote(
                                        NetResultUtil.parseDataList(s, Note.class)));
                                break;
                            case TYPE_COURSE:
                                objList.addAll(NetResultUtil.net2localCourse(
                                        NetResultUtil.parseDataList(s, Course.class)));
                                break;
                            case TYPE_ENTRY:
                                objList.addAll(NetResultUtil.parseDataList(s, Entry.class));
                                break;
                        }
                        Collections.reverse(objList);
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
    public void requestRemove(int type, final int id) {
        ArrayMap<String, String> values = new ArrayMap<>();
        values.put("token", mUserCache.getToken());
        String url;
        switch (type) {
            case TYPE_NOTE:
                url = Api.INSTANCE.getDELETE_COLL_REC();
                values.put("rec_id", "" + id);
                break;
            case TYPE_COURSE:
                url = Api.INSTANCE.getDELETE_COLL_ACT();
                values.put("act_id", "" + id);
                break;
            case TYPE_ENTRY:
                url = Api.INSTANCE.getDELETE_COLL_ENTRY();
                values.put("entry_id", "" + id);
                break;
            default:
                url = "";
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
    public void requestRemove(int type, List<Integer> id) {
        for (int i : id) {
            requestRemove(type, i);
        }
    }
}
