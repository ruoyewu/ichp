package com.wuruoye.ichp.ui.contract.pro;

import com.wuruoye.ichp.ui.model.bean.User;
import com.wuruoye.library.contract.WIView;
import com.wuruoye.library.contract.WPresenter;

/**
 * @Created : wuruoye
 * @Date : 2018/4/13 15:57.
 * @Description :
 */

public interface PersonInfoContract {
    interface View extends WIView {
        void onResultError(String error);
        void onResultUploadFile(String url);
        void onResultUpload();
    }

    abstract class Presenter extends WPresenter<View> {
        abstract public void requestUploadFile(String path);
        abstract public void requestUpload(User user);
    }
}
