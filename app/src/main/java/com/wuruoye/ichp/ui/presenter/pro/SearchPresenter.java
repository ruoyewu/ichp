package com.wuruoye.ichp.ui.presenter.pro;

import android.support.v4.util.ArrayMap;

import com.wuruoye.ichp.base.model.Api;
import com.wuruoye.ichp.base.util.NetResultUtil;
import com.wuruoye.ichp.ui.contract.pro.SearchContract;
import com.wuruoye.ichp.ui.model.UserCache;
import com.wuruoye.ichp.ui.model.bean.Course;
import com.wuruoye.ichp.ui.model.bean.Entry;
import com.wuruoye.ichp.ui.model.bean.Note;
import com.wuruoye.ichp.ui.model.bean.User;
import com.wuruoye.library.model.Listener;
import com.wuruoye.library.util.net.WNet;

import java.util.ArrayList;
import java.util.List;

import static com.wuruoye.ichp.ui.contract.pro.SearchContract.TYPE_COURSE;
import static com.wuruoye.ichp.ui.contract.pro.SearchContract.TYPE_ENTRY;
import static com.wuruoye.ichp.ui.contract.pro.SearchContract.TYPE_NOTE;
import static com.wuruoye.ichp.ui.contract.pro.SearchContract.TYPE_USER;

/**
 * @Created : wuruoye
 * @Date : 2018/4/24 21:23.
 * @Description :
 */

public class SearchPresenter extends SearchContract.Presenter{
    private UserCache mUserCache = UserCache.getInstance();

    @Override
    public void requestData(final int type, String query) {
        ArrayMap<String, String> values = new ArrayMap<>();
        values.put("token", mUserCache.getToken());
        String url;
        switch (type) {
            case TYPE_NOTE:
                url = Api.INSTANCE.getSEARCH_REC();
                values.put("searchW", query);
                break;
            case TYPE_COURSE:
                url = Api.INSTANCE.getSEARCH_ACT();
                values.put("searchAct", query);
                break;
            case TYPE_ENTRY:
                url = Api.INSTANCE.getSEARCH_ENTRY();
                values.put("searchEntry", query);
                break;
            case TYPE_USER:
                url = Api.INSTANCE.getSEARCH_USER_INFO();
                values.put("searchW", query);
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
                            case TYPE_USER:
                                objList.addAll(NetResultUtil.parseDataList(s, User.class));
                                break;
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
}
