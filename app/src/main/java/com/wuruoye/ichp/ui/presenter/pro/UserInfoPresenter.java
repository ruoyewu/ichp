package com.wuruoye.ichp.ui.presenter.pro;

import android.support.v4.util.ArrayMap;

import com.wuruoye.ichp.base.model.Api;
import com.wuruoye.ichp.ui.contract.pro.UserInfoContract;
import com.wuruoye.ichp.ui.model.UserCache;
import com.wuruoye.library.model.Listener;
import com.wuruoye.library.util.net.WNet;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @Created : wuruoye
 * @Date : 2018/4/25 17:28.
 * @Description :
 */

public class UserInfoPresenter extends UserInfoContract.Presenter {
    private UserCache mUserCache = UserCache.getInstance();

    @Override
    public void requestAttention(boolean att, int userId) {
        ArrayMap<String, String> values = new ArrayMap<>();
        values.put("token", mUserCache.getToken());
        values.put("user_id", "" + userId);

        String url = att ? Api.INSTANCE.getCONCER_USER() : Api.INSTANCE.getCANCEL_CONCER_USER();
        WNet.postInBackGround(url, values, new Listener<String>() {
            @Override
            public void onSuccessful(String s) {
                if (isAvailable()) {
                    try {
                        JSONObject obj = new JSONObject(s);
                        if (obj.getInt("code") == 0) {
                            getView().onResultError("操作成功");
                        }else {
                            getView().onResultError(obj.getString("msg"));
                        }
                    } catch (JSONException e) {
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
