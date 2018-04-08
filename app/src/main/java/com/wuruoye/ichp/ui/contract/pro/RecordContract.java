package com.wuruoye.ichp.ui.contract.pro;

import com.wuruoye.ichp.ui.model.bean.Note;
import com.wuruoye.library.contract.WIView;
import com.wuruoye.library.contract.WPresenter;

import java.util.List;

/**
 * @Created : wuruoye
 * @Date : 2018/4/1.
 * @Description :
 */

public interface RecordContract {
    interface View extends WIView {
        void onResultRecord(List<Note> noteList, boolean isAdd);
        void onErrorRecord(String error);
    }

    abstract class Presenter extends WPresenter<View> {
        public abstract void requestRecord(boolean isAdd, int type);
    }
}
