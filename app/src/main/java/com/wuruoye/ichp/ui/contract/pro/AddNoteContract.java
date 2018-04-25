package com.wuruoye.ichp.ui.contract.pro;

import android.content.Context;

import com.wuruoye.ichp.ui.model.bean.Course;
import com.wuruoye.ichp.ui.model.bean.Note;
import com.wuruoye.library.contract.WIView;
import com.wuruoye.library.contract.WPresenter;

/**
 * Created by wuruoye on 2018/1/28.
 * this file is to
 */

public interface AddNoteContract {
    interface View extends WIView {
        void onResultError(String error);
        void onLocationResult(Double[] addr, String[] location);
        void onLocationError(String error);
        void onFileUploadResult(boolean result, String url);
        void onNoteAddResult(boolean result, String id);
        void onResultCourse();
    }

    abstract class Presenter extends WPresenter<View> {
        abstract public void requestLocation(Context context);
        abstract public void requestUploadFile(String fileName, String type);
        abstract public void requestUpNote(Note note);
        abstract public void requestUpCourse(Course course, String date);
        abstract public String generateImageName();
        abstract public String generateVideoName();
        abstract public String generateRecordName();
    }
}
