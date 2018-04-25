package com.wuruoye.ichp.ui.contract.pro;

import com.wuruoye.library.contract.WIView;
import com.wuruoye.library.contract.WPresenter;

/**
 * @Created : wuruoye
 * @Date : 2018/3/25.
 * @Description : 用户登录 Contract
 */

public interface UserLoginContract {
    interface View extends WIView {
        void onLoginResult(boolean result, String token);
    }

    abstract class Presenter extends WPresenter<View> {
        public abstract void requestLogin(String name, String pwd);
    }
}
