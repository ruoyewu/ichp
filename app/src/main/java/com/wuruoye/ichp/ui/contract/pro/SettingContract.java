package com.wuruoye.ichp.ui.contract.pro;

import com.wuruoye.library.contract.WIView;
import com.wuruoye.library.contract.WPresenter;

/**
 * @Created : wuruoye
 * @Date : 2018/4/12 21:10.
 * @Description :
 */

public interface SettingContract {
    interface View extends WIView {
        void onResultLogout(boolean result, String info);
    }

    abstract class Presenter extends WPresenter<View> {
        public abstract boolean isLogin();
        public abstract void requestLogout();
    }
}
