package com.wuruoye.ichp.ui.contract.pro;

import com.wuruoye.library.contract.WIView;
import com.wuruoye.library.contract.WPresenter;

/**
 * @Created : wuruoye
 * @Date : 2018/4/7.
 * @Description :
 */

public interface NoteShowContract {
    interface View extends WIView {
        void onResultPraise();
        void onResultComment();
        void onResultCollect();
    }

    abstract class Presenter extends WPresenter<View> {
        abstract public void requestPraise(int id);
        abstract public void requestComment(int id, String content);
        abstract public void requestCollect(int id);
    }
}
