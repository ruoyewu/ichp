package com.wuruoye.ichp.ui.contract.pro;

import com.wuruoye.ichp.ui.model.bean.User;
import com.wuruoye.ichp.ui.model.bean.UserPoint;
import com.wuruoye.library.contract.WIView;
import com.wuruoye.library.contract.WPresenter;

/**
 * @Created : wuruoye
 * @Date : 2018/4/30 10:52.
 * @Description :
 */

public interface UserLevelContract {
    interface View extends WIView {
        void onResultError(String s);
        void onResultUser(User user);
        void onResultLevel(UserPoint point);
    }

    abstract class Presenter extends WPresenter<View> {
        abstract public void requestUserInfo();
        abstract public void requestLevelInfo();
    }
}
