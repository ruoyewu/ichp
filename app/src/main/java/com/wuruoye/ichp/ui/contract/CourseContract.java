package com.wuruoye.ichp.ui.contract;

import com.wuruoye.ichp.base.presenter.AbsPresenter;
import com.wuruoye.ichp.base.presenter.IView;
import com.wuruoye.ichp.ui.model.bean.Course;

import java.util.List;

/**
 * Created by wuruoye on 2018/1/27.
 * this file is to
 */

public interface CourseContract {
    interface View extends IView{
        void onCourseListResult(List<Course> courseList, boolean isAdd);
    }

    abstract class Presenter extends AbsPresenter<View> {
        public abstract void requestCourseList(boolean isAdd, int type);
    }
}
