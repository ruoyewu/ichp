package com.wuruoye.ichp.ui.contract.pro;

import com.wuruoye.library.contract.WIPresenter;
import com.wuruoye.library.contract.WIView;
import com.wuruoye.library.contract.WPresenter;

import java.util.List;

/**
 * Created by wuruoye on 2018/2/4.
 * this file is to
 */

public interface PersonNoteContract {
    int TYPE_NOTE = 1;
    int TYPE_COURSE = 2;

    interface View extends WIView {
        void onResultError(String error);
        void onResultData(List<Object> dataList);
        void onResultRemove(int id, boolean deleted);
    }

    abstract class Presenter extends WPresenter<View> implements WIPresenter {
        public abstract void requestData(int type);
        public abstract void requestRemove(int id, int type);
        public abstract void requestRemove(List<Integer> id, int type);
    }
}
