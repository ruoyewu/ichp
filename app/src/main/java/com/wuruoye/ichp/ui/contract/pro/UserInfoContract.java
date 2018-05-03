package com.wuruoye.ichp.ui.contract.pro;

import com.wuruoye.library.contract.WIView;
import com.wuruoye.library.contract.WPresenter;

/**
 * @Created : wuruoye
 * @Date : 2018/4/25 17:24.
 * @Description :
 */

public interface UserInfoContract {
    interface View extends WIView {
        void onResultError(String error);
    }

    abstract class Presenter extends WPresenter<View> {
        abstract public void requestAttention(boolean att, int userId);
    }
}
