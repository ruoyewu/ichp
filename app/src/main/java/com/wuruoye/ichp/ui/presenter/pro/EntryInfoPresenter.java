package com.wuruoye.ichp.ui.presenter.pro;

import com.wuruoye.ichp.ui.contract.pro.EntryInfoContract;
import com.wuruoye.ichp.ui.model.UserCache;

import static com.wuruoye.ichp.ui.contract.pro.EntryInfoContract.TYPE_COURSE;
import static com.wuruoye.ichp.ui.contract.pro.EntryInfoContract.TYPE_NOTE;

/**
 * @Created : wuruoye
 * @Date : 2018/4/25 08:18.
 * @Description :
 */

public class EntryInfoPresenter extends EntryInfoContract.Presenter {
    private UserCache mUserCache = UserCache.getInstance();

    @Override
    public void requestData(int type) {
        switch (type) {
            case TYPE_NOTE:
                break;
            case TYPE_COURSE:
                break;
        }
    }
}
