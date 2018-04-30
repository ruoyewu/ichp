package com.wuruoye.ichp.ui.contract.pro;

import com.wuruoye.library.contract.WIView;
import com.wuruoye.library.contract.WPresenter;

/**
 * @Created : wuruoye
 * @Date : 2018/4/30 09:38.
 * @Description :
 */

public interface ConfirmContract {
    interface View extends WIView {
        void onResultError(String error);
        void onResultUpload();
    }

    abstract class Presenter extends WPresenter<View> {
        abstract public void requestUp();
        abstract public String generatePhotoName();
    }
}
