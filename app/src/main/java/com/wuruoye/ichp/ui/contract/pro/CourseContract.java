package com.wuruoye.ichp.ui.contract.pro;

import com.wuruoye.ichp.ui.model.bean.Course;
import com.wuruoye.library.contract.WIView;
import com.wuruoye.library.contract.WPresenter;

import java.util.List;

/**
 * @Created : wuruoye
 * @Date : 2018/3/31.
 * @Description : 新的「用户界面」对应 contract
 */

public interface CourseContract {
    interface View extends WIView {
        void onResultError(String error);
        void onResultCourse(List<Course> courseList, boolean isAdd);
    }

    abstract class Presenter extends WPresenter<View> {
        public abstract void requestCourse(boolean isAdd);
    }
}
