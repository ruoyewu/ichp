package com.wuruoye.ichp.ui.contract.pro;

import com.wuruoye.library.contract.WIView;
import com.wuruoye.library.contract.WPresenter;

/**
 * @Created : wuruoye
 * @Date : 2018/4/12 16:49.
 * @Description :
 */

public interface EntryAddContract {
    interface View extends WIView {
        void onResultError(String error);
        void onResultAdd(boolean result, String info);
        void onResultUpload(boolean result, String info);
    }

    abstract class Presenter extends WPresenter<View> {
        abstract public void requestAddEntry(String name, String content, String url);
        abstract public void requestUpload(String path);
    }
}
