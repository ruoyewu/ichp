package com.wuruoye.ichp.ui.contract;

import com.wuruoye.ichp.base.presenter.AbsPresenter;
import com.wuruoye.ichp.base.presenter.IView;
import com.wuruoye.ichp.ui.model.bean.User;

import java.util.List;

/**
 * Created by wuruoye on 2018/2/3.
 * this file is to
 */

public interface UserAttentionContract {
    interface View extends IView {
        void onDataResult(List<Object> userList, boolean isAdd);
    }

    abstract class Presenter extends AbsPresenter<View> {
        public abstract void requestData(User user, int type, boolean isAdd);
    }
}
