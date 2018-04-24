package com.wuruoye.ichp.ui.contract.pro;

import com.wuruoye.ichp.ui.model.bean.Course;
import com.wuruoye.ichp.ui.model.bean.Note;
import com.wuruoye.library.contract.WIView;
import com.wuruoye.library.contract.WPresenter;

import java.util.List;

/**
 * Created by wuruoye on 2018/2/1.
 * this file is to
 */

public interface FoundContract {
    interface View extends WIView {
        void onResultRecommend(List<Object> objects);
        void onResultNote(List<Note> notes);
        void onResultCourse(List<Course> courses);
        void onResultError(String error);
    }

    abstract class Presenter extends WPresenter<View> {
        abstract public void requestRecommend();
        abstract public void requestNote(String addr);
        abstract public void requestCourse(String addr);
        abstract public String getImg(Object obj);
    }
}
