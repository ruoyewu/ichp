package com.wuruoye.ichp.ui.contract.pro;

import com.wuruoye.library.contract.WIView;
import com.wuruoye.library.contract.WPresenter;

/**
 * @Created : wuruoye
 * @Date : 2018/4/12 21:54.
 * @Description :
 */

public interface RegisterContract {
    interface View extends WIView {
        void onResultRegister(boolean result, String info);
    }

    abstract class Presenter extends WPresenter<View> {
        abstract public void requestRegister(String name, String pwd);
    }
}
