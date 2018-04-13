package com.wuruoye.ichp.ui.presenter;


import android.support.v4.util.ArrayMap;

import com.wuruoye.ichp.base.model.Api;
import com.wuruoye.ichp.ui.contract.MainContract;
import com.wuruoye.ichp.ui.model.UserCache;
import com.wuruoye.library.model.Listener;
import com.wuruoye.library.util.net.WNet;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @Created : wuruoye
 * @Date : 2018/3/25.
 * @Description : MainActivity 对应 presenter 层逻辑
 */

public class MainPresenter extends MainContract.Presenter {
    private UserCache mUserCache = UserCache.getInstance();

    @Override
    public void requestLogin(final String id, final String pwd) {
        ArrayMap<String, String> map = new ArrayMap<>();
        map.put("username", id);
        map.put("psw", pwd);
        mUserCache.setLogin(false);
        WNet.post(Api.INSTANCE.getLOGIN(), map, new Listener<String>() {
            @Override
            public void onSuccessful(String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    int code = object.getInt("code");
                    if (code == 0) {
                        String token = object.getString("token");
                        getView().onLoginResult(true, token);
                        mUserCache.setName(id);
                        mUserCache.setPwd(pwd);
                        mUserCache.setToken(token);
                        mUserCache.setLogin(true);
                    }else {
                        String token = object.getString("msg");
                        getView().onLoginResult(false, token);
                        mUserCache.setLogin(false);
                    }
                } catch (JSONException e) {
                    getView().onLoginResult(false, "json string is unavailable");
                    mUserCache.setLogin(false);
                }
            }

            @Override
            public void onFail(String s) {
                getView().onLoginResult(false, "net request is unavailable");
                mUserCache.setLogin(false);
            }
        });
    }

    @Override
    public boolean isUserLogin() {
        return mUserCache.isLogin();
    }

    @Override
    public String getUserName() {
        return mUserCache.getName();
    }

    @Override
    public String getUserPwd() {
        return mUserCache.getPwd();
    }
}
