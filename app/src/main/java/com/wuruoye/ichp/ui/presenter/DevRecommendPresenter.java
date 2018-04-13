package com.wuruoye.ichp.ui.presenter;

import com.wuruoye.ichp.ui.contract.RecommendContract;
import com.wuruoye.ichp.ui.model.UserCache;

/**
 * Created by wuruoye on 2018/1/27.
 * this file is to
 */

public class DevRecommendPresenter extends RecommendContract.Presenter {
    private UserCache mUserCache = UserCache.getInstance();

    @Override
    public void requestNoteList(boolean isAdd, int type) {

    }
}
