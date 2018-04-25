package com.wuruoye.ichp.ui.contract.pro;

import com.wuruoye.library.contract.WIView;
import com.wuruoye.library.contract.WPresenter;

import java.util.List;

/**
 * @Created : wuruoye
 * @Date : 2018/4/25 08:14.
 * @Description :
 */

public interface EntryInfoContract {
    int TYPE_NOTE = 1;
    int TYPE_COURSE = 2;

    interface View extends WIView {
        void onResultError(String error);
        void onResultData(List<Object> dataList);
    }

    abstract class Presenter extends WPresenter<View> {
        abstract public void requestData(int type);
    }
}
