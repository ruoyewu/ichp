package com.wuruoye.ichp.ui.contract.pro;

import com.wuruoye.library.contract.WIView;
import com.wuruoye.library.contract.WPresenter;

/**
 * @Created : wuruoye
 * @Date : 2018/4/28 21:03.
 * @Description :
 */

public interface EntryInfoContract {
    interface View extends WIView {
        void onResultError(String error);
    }

    abstract class Presenter extends WPresenter<View> {
        abstract public void requestCollectEntry(int id, boolean collect);
    }
}
