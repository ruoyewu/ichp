package com.wuruoye.ichp.ui.contract.pro;

import com.wuruoye.ichp.ui.model.bean.User;
import com.wuruoye.library.contract.WIView;
import com.wuruoye.library.contract.WPresenter;

import java.util.List;

/**
 * Created by wuruoye on 2018/2/3.
 * this file is to
 */

public interface UserAttentionContract {
    int TYPE_ATTEN = 1;
    int TYPE_ATTED = 2;

    interface View extends WIView {
        void onResultError(String error);
        void onResultData(List<User> userList);
    }

    abstract class Presenter extends WPresenter<View> {
        public abstract void requestData(int userId, int type);
        public abstract String getTitle(int userId, int type);
    }
}
