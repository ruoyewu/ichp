package com.wuruoye.ichp.ui.presenter.pro;

import android.support.v4.util.ArrayMap;

import com.wuruoye.ichp.base.model.Api;
import com.wuruoye.ichp.base.util.NetResultUtil;
import com.wuruoye.ichp.ui.contract.pro.UserInfoContract;
import com.wuruoye.ichp.ui.model.UserCache;
import com.wuruoye.ichp.ui.model.bean.Course;
import com.wuruoye.ichp.ui.model.bean.Note;
import com.wuruoye.library.model.Listener;
import com.wuruoye.library.util.net.WNet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.wuruoye.ichp.ui.contract.pro.UserInfoContract.TYPE_COURSE;
import static com.wuruoye.ichp.ui.contract.pro.UserInfoContract.TYPE_NOTE;

/**
 * @Created : wuruoye
 * @Date : 2018/4/25 10:52.
 * @Description :
 */

public class UserInfoPresenter extends UserInfoContract.Presenter {
    private UserCache mUserCache = UserCache.getInstance();

    @Override
    public void requestData(final int type, int userId) {
        ArrayMap<String, String> values = new ArrayMap<>();
        values.put("token", mUserCache.getToken());
        String url;
        switch (type) {
            case TYPE_NOTE:
                url = Api.INSTANCE.getGET_USER_REC();
                values.put("recorder", "" + userId);
                break;
            case TYPE_COURSE:
                url = Api.INSTANCE.getGET_USER_ACT();
                values.put("publisher", "" + userId);
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
}
