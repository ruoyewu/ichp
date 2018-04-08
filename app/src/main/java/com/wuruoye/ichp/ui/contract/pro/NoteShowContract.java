package com.wuruoye.ichp.ui.contract.pro;

import com.wuruoye.ichp.ui.model.bean.Entry;
import com.wuruoye.ichp.ui.model.bean.Media;
import com.wuruoye.ichp.ui.model.bean.NoteComment;
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
        void onResultEntry(List<Entry> entryList);
        void onResultError(String error);
        void onResultNoteComment(List<NoteComment> commentList);
    }

    abstract class Presenter extends WPresenter<View> {
        abstract public void requestPraise(int id);
        abstract public void requestComment(int id, String content);
        abstract public void requestCollect(int id);
        abstract public void requestEntry(String entry);
        abstract public void requestCommentList(int id);
        abstract public String parseDate(float time);
        abstract public List<Media> parseMedia(String url, String type)
                throws IllegalArgumentException;
        public abstract String[] parseLocation(String location);
    }
}
