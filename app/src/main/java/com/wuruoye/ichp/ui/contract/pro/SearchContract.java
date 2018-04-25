package com.wuruoye.ichp.ui.contract.pro;

import com.wuruoye.library.contract.WIView;
import com.wuruoye.library.contract.WPresenter;

import java.util.List;

/**
 * @Created : wuruoye
 * @Date : 2018/4/24 21:21.
 * @Description :
 */

public interface SearchContract {
    int TYPE_NOTE = 1;
    int TYPE_COURSE = 2;
    int TYPE_ENTRY = 3;
    int TYPE_USER = 4;

    interface View extends WIView {
        void onResultError(String error);
        void onResultData(List<Object> dataList);
    }

    abstract class Presenter extends WPresenter<View> {
        abstract public void requestData(int type, String query);
    }
}
