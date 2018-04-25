package com.wuruoye.ichp.ui.contract.pro;

import com.wuruoye.ichp.ui.model.bean.Note;
import com.wuruoye.library.contract.WIPresenter;
import com.wuruoye.library.contract.WIView;
import com.wuruoye.library.contract.WPresenter;

import java.util.List;

/**
 * Created by wuruoye on 2018/2/4.
 * this file is to
 */

public interface PersonNoteContract {
    interface View extends WIView {
        void onResultError(String error);
        void onResultData(List<Note> dataList);
        void onResultRemove(int id, boolean deleted);
    }

    abstract class Presenter extends WPresenter<View> implements WIPresenter {
        public abstract void requestData();
        public abstract void requestRemove(int id);
        public abstract void requestRemove(List<Integer> id);
    }
}
