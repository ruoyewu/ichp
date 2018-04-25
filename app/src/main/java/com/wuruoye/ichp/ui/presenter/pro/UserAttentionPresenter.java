package com.wuruoye.ichp.ui.presenter.pro;

import android.support.v4.util.ArrayMap;

import com.wuruoye.ichp.base.model.Api;
import com.wuruoye.ichp.base.util.NetResultUtil;
import com.wuruoye.ichp.ui.contract.UserAttentionContract;
import com.wuruoye.ichp.ui.model.UserCache;
import com.wuruoye.ichp.ui.model.bean.User;
import com.wuruoye.library.model.Listener;
import com.wuruoye.library.util.net.WNet;

import java.util.List;

/**
 * @Created : wuruoye
 * @Date : 2018/4/25 12:48.
 * @Description :
 */

public class UserAttentionPresenter extends UserAttentionContract.Presenter {
    private UserCache mUserCache = UserCache.getInstance();

    @Override
    public void requestData(int type) {
        ArrayMap<String, String> values = new ArrayMap<>();
        values.put("token", mUserCache.getToken());

        WNet.postInBackGround(Api.INSTANCE.getGET_ATTENTION(), values, new Listener<String>() {
            @Override
            public void onSuccessful(String s) {
                if (isAvailable()) {
                    try {
                        List<User> userList = NetResultUtil.parseDataList(s, User.class);
                        getView().onResultData(userList);
                    } catch (Exception e) {
                        getView().onResultError(e.getMessage());
                    }
                }
            }

            @Override
            public void onFail(String s) {
                if (isAvailable()) {
                    getView().onResultError(s);
                }
            }
        });
    }
}
