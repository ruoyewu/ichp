package com.wuruoye.ichp.ui.contract.pro;

import com.wuruoye.ichp.ui.model.bean.Note;
import com.wuruoye.library.contract.WIView;
import com.wuruoye.library.contract.WPresenter;

import java.util.List;

/**
 * @Created : wuruoye
 * @Date : 2018/4/14 00:21.
 * @Description :
 */

public interface MapContract {
    interface View extends WIView {
        void onResultError(String error);
        void onResultNote(List<Note> noteList);
    }

    abstract class Presenter extends WPresenter<View> {
        abstract public void requestNote(int type);
    }
}
