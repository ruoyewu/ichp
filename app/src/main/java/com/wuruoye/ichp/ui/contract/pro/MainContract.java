package com.wuruoye.ichp.ui.contract.pro;

import com.wuruoye.library.contract.WIView;
import com.wuruoye.library.contract.WPresenter;

/**
 * @Created : wuruoye
 * @Date : 2018/3/25.
 * @Description : MainActivity 对应 Contract
 */

public interface MainContract {
    interface View extends WIView {
        void checkLogin();
        void onLoginResult(boolean result, String token);
    }

    abstract class Presenter extends WPresenter<View> {
        public abstract void requestLogin(String id, String pwd);
        public abstract boolean isUserLogin();
        public abstract String getUserName();
        public abstract String getUserPwd();
    }
}
