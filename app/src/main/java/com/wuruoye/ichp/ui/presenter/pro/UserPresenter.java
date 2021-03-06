package com.wuruoye.ichp.ui.presenter.pro;

import android.support.v4.util.ArrayMap;

import com.wuruoye.ichp.base.model.Api;
import com.wuruoye.ichp.base.util.NetResultUtil;
import com.wuruoye.ichp.ui.contract.pro.UserContract;
import com.wuruoye.ichp.ui.model.UserCache;
import com.wuruoye.ichp.ui.model.bean.User;
import com.wuruoye.library.model.Listener;
import com.wuruoye.library.util.net.WNet;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * @Created : wuruoye
 * @Date : 2018/4/13 14:48.
 * @Description :
 */

public class UserPresenter extends UserContract.Presenter {
    private UserCache mUserCache = UserCache.getInstance();

    @Override
    public void requestUserInfo() {
        ArrayMap<String, String> values = new ArrayMap<>();
        values.put("token", mUserCache.getToken());
        values.put("user_id", String.valueOf(mUserCache.getUserId()));
        WNet.postInBackGround(Api.INSTANCE.getUSER_INFO(), values, new Listener<String>() {
            @Override
            public void onSuccessful(String s) {
                try {
                    List<User> userList = NetResultUtil.parseDataList(s, User.class);
                    if (userList.size() == 0) {
                        if (isAvailable()) {
                            getView().onResultError("user not found");
                        }
                    }else {
                        getView().onResultUserInfo(userList.get(0));
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

    @Override
    public void requestLogout() {
        ArrayMap<String, String> values = new ArrayMap<>();
        values.put("token", mUserCache.getToken());

        WNet.postInBackGround(Api.INSTANCE.getLOGOUT(), values, new Listener<String>() {
            @Override
            public void onSuccessful(String s) {
                if (isAvailable()) {
                    try {
                        JSONObject obj = new JSONObject(s);
                        if (obj.getInt("code") == 0) {
                            mUserCache.setLogin(false);
                            mUserCache.setToken("");
                            getView().onResultLogout();
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
    public boolean isLogin() {
        return mUserCache.isLogin();
    }

    @Override
    public boolean isConfirm() {
        return mUserCache.getUserConfirm();
    }

}
