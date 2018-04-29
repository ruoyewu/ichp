package com.wuruoye.ichp.ui.model;

import com.wuruoye.library.model.WBaseCache;

/**
 * @Created : wuruoye
 * @Date : 2018/3/23.
 * @Description : 用户相关缓存数据
 */

public class UserCache extends WBaseCache {
    public static final String SP_NAME = "user";
    public static final String USER_NAME = "user_name";
    public static final String USER_PWD = "user_pwd";
    public static final String USER_LOGIN = "user_login";
    public static final String USER_TOKEN = "user_token";
    public static final String USER_ID = "user_id";
    public static final String USER_CONFIRM = "user_confirm";

    private static UserCache mUserCache;

    public static UserCache getInstance() {
        if (mUserCache == null) {
            synchronized (UserCache.class) {
                if (mUserCache == null) {
                    mUserCache = new UserCache();
                }
            }
        }
        return mUserCache;
    }

    private UserCache() {
        super(SP_NAME);
    }

    public String getName() {
        return getString(USER_NAME, "");
    }

    public void setName(String name) {
        putString(USER_NAME, name);
    }

    public boolean isLogin() {
        return getBoolean(USER_LOGIN, false);
    }

    public void setLogin(boolean isLogin) {
        putBoolean(USER_LOGIN, isLogin);
    }

    public String getPwd() {
        return getString(USER_PWD, "");
    }

    public void setPwd(String pwd) {
        putString(USER_PWD, pwd);
    }

    public String getToken() {
        return getString(USER_TOKEN, "");
    }

    public void setToken(String token) {
        putString(USER_TOKEN, token);
    }

    public int getUserId() {
        return getInt(USER_ID, 0);
    }

    public void setUserId(int id) {
        putInt(USER_ID, id);
    }

    public boolean getUserConfirm() {
        return getBoolean(USER_CONFIRM, false);
    }

    public void setUserConfirm(boolean confirm) {
        putBoolean(USER_CONFIRM, confirm);
    }
}
