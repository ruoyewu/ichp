package com.wuruoye.ichp.ui.contract;

import com.wuruoye.ichp.base.presenter.AbsPresenter;
import com.wuruoye.ichp.base.presenter.IView;
import com.wuruoye.ichp.ui.model.bean.User;

import java.util.List;

/**
 * Created by wuruoye on 2018/2/4.
 * this file is to
 */

public interface CollectContract {
    interface View extends IView {
        void onDataResult(List<Object> dataList, boolean isAdd);
        void onRemoveResult(Object data);
        void onRemoveAllResult(List<Object> dataList);
    }

    abstract class Presenter extends AbsPresenter<View> {
        public abstract void requestData(User user, int type, boolean isAdd);
        public abstract void remove(User user, Object data);
        public abstract void removeAll(User user, List<Object> dataList);
    }
}
