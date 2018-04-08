package com.wuruoye.ichp.ui.contract.pro;

import com.wuruoye.ichp.ui.model.bean.Entry;
import com.wuruoye.library.contract.WIView;
import com.wuruoye.library.contract.WPresenter;

import java.util.List;

/**
 * @Created : wuruoye
 * @Date : 2018/4/2.
 * @Description : 「词条选择界面」
 */

public interface EntryChooseContract {
    interface View extends WIView {
        void onResultEntry(List<Entry> entryList);
        void onResultEntryError(String error);
    }

    abstract class Presenter extends WPresenter<View> {
        abstract public void requestEntry(String search);
    }
}
