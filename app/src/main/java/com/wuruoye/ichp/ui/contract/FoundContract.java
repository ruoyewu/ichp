package com.wuruoye.ichp.ui.contract;

import com.wuruoye.ichp.base.presenter.AbsPresenter;
import com.wuruoye.ichp.base.presenter.IView;
import com.wuruoye.ichp.ui.model.bean.Course;
import com.wuruoye.ichp.ui.model.bean.Entry;
import com.wuruoye.ichp.ui.model.bean.Note;

import java.util.List;

/**
 * Created by wuruoye on 2018/2/1.
 * this file is to
 */

public interface FoundContract {
    interface View extends IView {
        void onRecommendNoteResult(List<Note> noteList);
        void onRecommendEntryResult(List<Entry> entryList);
        void onRecommendCourseResult(List<Course> courseList);
    }

    abstract class Presenter extends AbsPresenter<View> {
        abstract public void requestRecommendNote();
        abstract public void requestRecommendEntry();
        abstract public void requestRecommendCourse();
    }
}
