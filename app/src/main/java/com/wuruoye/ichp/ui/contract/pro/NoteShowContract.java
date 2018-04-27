package com.wuruoye.ichp.ui.contract.pro;

import com.wuruoye.ichp.ui.model.bean.Entry;
import com.wuruoye.ichp.ui.model.bean.Media;
import com.wuruoye.ichp.ui.model.bean.NoteComment;
import com.wuruoye.ichp.ui.model.bean.User;
import com.wuruoye.library.contract.WIView;
import com.wuruoye.library.contract.WPresenter;

import java.util.List;

/**
 * @Created : wuruoye
 * @Date : 2018/4/7.
 * @Description : 「展示记录页面」
 */

public interface NoteShowContract {
    interface View extends WIView {
        void onResultUserInfo(User user);
        void onResultEntry(List<Entry> entryList);
        void onResultError(String error);
        void onResultNoteComment(List<NoteComment> commentList);
        void onResultUpComment(boolean result, String info);
        void onResultModifyEntry(boolean result, String info);
    }

    abstract class Presenter extends WPresenter<View> {
        abstract public void requestPraise(int id, boolean praise);
        abstract public void requestComment(int id, String content);
        abstract public void requestCollect(int id, boolean collect);
        abstract public void requestCommentList(int id);
        abstract public void requestEntryList(String str);
        abstract public void requestUserInfo(int id);
        abstract public void requestModifyEntry(int recId, List<Entry> entryList);
        abstract public String parseDate(float time);
        abstract public List<Media> parseMedia(String url, String type)
                throws IllegalArgumentException;
        public abstract String[] parseLocation(String location);
    }
}
