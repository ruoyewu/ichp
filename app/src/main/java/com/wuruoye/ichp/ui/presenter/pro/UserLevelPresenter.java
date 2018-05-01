package com.wuruoye.ichp.ui.presenter.pro;

import android.support.v4.util.ArrayMap;

import com.google.gson.Gson;
import com.wuruoye.ichp.base.model.Api;
import com.wuruoye.ichp.base.util.NetResultUtil;
import com.wuruoye.ichp.ui.contract.pro.UserLevelContract;
import com.wuruoye.ichp.ui.model.UserCache;
import com.wuruoye.ichp.ui.model.bean.User;
import com.wuruoye.ichp.ui.model.bean.UserPoint;
import com.wuruoye.library.model.Listener;
import com.wuruoye.library.util.net.WNet;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * @Created : wuruoye
 * @Date : 2018/4/30 10:56.
 * @Description :
 */

public class UserLevelPresenter extends UserLevelContract.Presenter {
    private UserCache mUserCache = UserCache.getInstance();

    @Override
    public void requestUserInfo() {
        ArrayMap<String, String> values = new ArrayMap<>();
        values.put("token", mUserCache.getToken());
        values.put("user_id", "" + mUserCache.getUserId());

        WNet.postInBackGround(Api.INSTANCE.getUSER_INFO(), values, new Listener<String>() {
            @Override
            public void onSuccessful(String s) {
                if (isAvailable()) {
                    try {
                        List<User> userList = NetResultUtil.parseDataList(s, User.class);
                        if (userList.size() > 0) {
                            getView().onResultUser(userList.get(0));
                        }else {
                            getView().onResultError("user is null");
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
    public void requestLevelInfo() {
        ArrayMap<String, String> values = new ArrayMap<>();
        values.put("token", mUserCache.getToken());

        WNet.postInBackGround(Api.INSTANCE.getGET_POINT(), values, new Listener<String>() {
            @Override
            public void onSuccessful(String s) {
                if (isAvailable()) {
                    try {
                        JSONObject obj = new JSONObject(s);
                        if (obj.getInt("code") == 0) {
                            getView().onResultLevel(new Gson().fromJson(obj.getString("data"),
                                    UserPoint.class));
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

            }
        });
    }
}
