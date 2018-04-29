package com.wuruoye.ichp.ui.contract.pro;

import com.wuruoye.ichp.ui.model.bean.User;
import com.wuruoye.library.contract.WIView;
import com.wuruoye.library.contract.WPresenter;

/**
 * @Created : wuruoye
 * @Date : 2018/4/13 14:47.
 * @Description :
 */

public interface UserContract {
    interface View extends WIView {
        void onResultError(String error);
        void onResultUserInfo(User user);
        void onResultLogout();
    }

    abstract class Presenter extends WPresenter<View> {
        abstract public void requestUserInfo();
        abstract public void requestLogout();
        abstract public boolean isLogin();
        abstract public boolean isConfirm();
    }
}
