package com.wuruoye.ichp.ui.presenter.pro;

import com.wuruoye.ichp.ui.contract.pro.PersonCollectContract;
import com.wuruoye.ichp.ui.model.UserCache;

/**
 * @Created : wuruoye
 * @Date : 2018/4/24 20:24.
 * @Description :
 */

public class PersonCollectPresenter extends PersonCollectContract.Presenter {
    private UserCache mUserCache = UserCache.getInstance();

    @Override
    public void requestData(int type) {
        switch (type) {
            case PersonCollectContract.TYPE_NOTE:
                break;
            case PersonCollectContract.TYPE_COURSE:
                break;
            case PersonCollectContract.TYPE_ENTRY:
                break;
        }
    }

    @Override
    public void requestRemove(int type, int id) {
        switch (type) {
            case PersonCollectContract.TYPE_NOTE:
                break;
            case PersonCollectContract.TYPE_COURSE:
                break;
            case PersonCollectContract.TYPE_ENTRY:
                break;
        }
    }
}
