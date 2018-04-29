package com.wuruoye.ichp.ui.contract.pro;

import com.wuruoye.ichp.ui.model.bean.Entry;
import com.wuruoye.ichp.ui.model.bean.Media;
import com.wuruoye.ichp.ui.model.bean.User;
import com.wuruoye.library.contract.WIView;
import com.wuruoye.library.contract.WPresenter;

import java.util.List;

/**
 * @Created : wuruoye
 * @Date : 2018/4/14 08:07.
 * @Description :
 */

public interface CourseShowContract {
    interface View extends WIView {
        void onResultError(String error);
        void onResultUserInfo(User user);
        void onResultEntryList(List<Entry> entryList);
    }

    abstract class Presenter extends WPresenter<View> {
        abstract public void requestUserInfo(int id);
        abstract public void requestEntryList(String s);
        abstract public void requestCollect(int id, boolean collect);
        abstract public List<Media> parseMedia(String url, String type);
        abstract public String parseDate(float time);
        abstract public int getUserId();
    }
}
