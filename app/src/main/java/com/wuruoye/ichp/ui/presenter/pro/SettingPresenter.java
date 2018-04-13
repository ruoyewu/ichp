package com.wuruoye.ichp.ui.presenter.pro;

import com.wuruoye.ichp.ui.contract.pro.SettingContract;
import com.wuruoye.ichp.ui.model.UserCache;

/**
 * @Created : wuruoye
 * @Date : 2018/4/12 21:12.
 * @Description :
 */

public class SettingPresenter extends SettingContract.Presenter {
    private UserCache mUserCache = UserCache.getInstance();

    @Override
    public boolean isLogin() {
        return mUserCache.isLogin();
    }

    @Override
    public void requestLogout() {
        if (mUserCache.isLogin()) {
            mUserCache.setLogin(false);
            mUserCache.setToken("");
            if (isAvailable()) {
                getView().onResultLogout(true, "注销成功");
            }
        }else {
            if (isAvailable()) {
                getView().onResultLogout(true, "用户尚未登录");
            }
        }
    }
}
