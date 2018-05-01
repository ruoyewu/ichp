package com.wuruoye.ichp.ui.contract.pro;

import com.wuruoye.library.contract.WIView;
import com.wuruoye.library.contract.WPresenter;

/**
 * @Created : wuruoye
 * @Date : 2018/4/30 17:49.
 * @Description :
 */

public interface ModifyPwdContract {
    interface View extends WIView {
        void onResultError(String error);
        void onResultModify();
    }

    abstract class Presenter extends WPresenter<View> {
        abstract public void requestChange(String pwd);
    }
}
