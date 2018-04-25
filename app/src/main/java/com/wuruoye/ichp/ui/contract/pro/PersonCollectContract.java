package com.wuruoye.ichp.ui.contract.pro;

import com.wuruoye.library.contract.WIView;
import com.wuruoye.library.contract.WPresenter;

import java.util.List;

/**
 * @Created : wuruoye
 * @Date : 2018/4/24 20:22.
 * @Description :
 */

public interface PersonCollectContract {
    int TYPE_NOTE = 1;
    int TYPE_COURSE = 2;
    int TYPE_ENTRY = 3;

    interface View extends WIView {
        void onResultError(String error);
        void onResultRemove(int id, boolean result);
        void onResultData(List<Object> dataList);
    }

    abstract class Presenter extends WPresenter<View> {
        abstract public void requestData(int type);
        abstract public void requestRemove(int type, int id);
        abstract public void requestRemove(int type, List<Integer> id);
    }
}
