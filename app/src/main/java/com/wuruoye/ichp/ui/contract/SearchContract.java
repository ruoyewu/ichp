package com.wuruoye.ichp.ui.contract;

import com.wuruoye.ichp.base.presenter.AbsPresenter;
import com.wuruoye.ichp.base.presenter.IView;

import java.util.List;

/**
 * Created by wuruoye on 2018/2/2.
 * this file is to
 */

public interface SearchContract {
    interface View extends IView {
        void onSearchResult(List<Object> resultList, boolean isAdd);
    }

    abstract class Presenter extends AbsPresenter<View> {
        public abstract void requestSearchResult(String query, int type, boolean isAdd);
    }
}
