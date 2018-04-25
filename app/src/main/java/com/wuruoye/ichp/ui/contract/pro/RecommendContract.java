package com.wuruoye.ichp.ui.contract.pro;

import com.wuruoye.ichp.base.presenter.AbsPresenter;
import com.wuruoye.ichp.base.presenter.IView;
import com.wuruoye.ichp.ui.model.bean.Note;

import java.util.List;

/**
 * Created by wuruoye on 2018/1/27.
 * this file is to
 */

public interface RecommendContract {
    interface View extends IView{
        void onNoteListResult(List<Note> noteList, boolean isAdd);
    }

    abstract class Presenter extends AbsPresenter<View>{
         public abstract void requestNoteList(boolean isAdd, int type);
    }
}
