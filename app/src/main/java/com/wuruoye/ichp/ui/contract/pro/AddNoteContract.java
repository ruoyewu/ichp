package com.wuruoye.ichp.ui.contract.pro;

import android.content.Context;

import com.wuruoye.ichp.ui.model.bean.Course;
import com.wuruoye.ichp.ui.model.bean.Media;
import com.wuruoye.ichp.ui.model.bean.Note;
import com.wuruoye.library.contract.WIView;
import com.wuruoye.library.contract.WPresenter;

import java.util.List;

/**
 * Created by wuruoye on 2018/1/28.
 * this file is to
 */

public interface AddNoteContract {
    interface View extends WIView {
        void onResultError(String error);
        void onResultLocation(Double[] addr, String[] location);
        void onResultLocationError(String error);
        void onResultUpload(boolean result, String url);
        void onResultAddNote();
        void onResultAddCourse();
        void onResultModify();
    }

    abstract class Presenter extends WPresenter<View> {
        abstract public void requestLocation(Context context);
        abstract public void requestUploadFile(String fileName, String type);
        abstract public void requestUpNote(Note note, boolean modify);
        abstract public void requestUpCourse(Course course, String date, boolean modify);
        abstract public String generateImageName();
        abstract public String generateVideoName();
        abstract public String generateRecordName();
        abstract public String formatDate(float date);
        abstract public List<Media> parseMedia(String url, String type);
    }
}
