package com.wuruoye.ichp.ui.contract;

import com.wuruoye.ichp.base.presenter.AbsPresenter;
import com.wuruoye.ichp.base.presenter.IView;
import com.wuruoye.ichp.ui.model.bean.Entry;

import java.util.List;

/**
 * Created by wuruoye on 2018/2/3.
 * this file is to
 */

public interface EntryInfoContract {
    interface View extends IView {
        void onDataListResult(List<Object> dataList, boolean isAdd);
    }

    abstract class Presenter extends AbsPresenter<View> {
        public abstract void requestDataList(Entry entry, boolean isAdd, int type);
    }
}
