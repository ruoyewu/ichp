package com.wuruoye.ichp.ui.contract;

import android.content.Context;

import com.wuruoye.library.contract.WIView;
import com.wuruoye.library.contract.WPresenter;

/**
 * Created by wuruoye on 2018/1/28.
 * this file is to
 */

public interface AddNoteContract {
    interface View extends WIView {
        void onLocationResult(Double[] addr, String[] location);
        void onLocationError(String error);
        void onFileUploadResult(boolean result, String url);
        void onNoteAddResult(boolean result, String id);
    }

    abstract class Presenter extends WPresenter<View> {
        abstract public void requestLocation(Context context);
        abstract public String generateImageName();
        abstract public String generateVideoName();
        abstract public String generateRecordName();
    }
}
