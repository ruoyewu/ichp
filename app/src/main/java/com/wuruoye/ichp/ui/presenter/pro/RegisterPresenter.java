package com.wuruoye.ichp.ui.presenter.pro;

import android.support.v4.util.ArrayMap;

import com.wuruoye.ichp.base.model.Api;
import com.wuruoye.ichp.ui.contract.pro.RegisterContract;
import com.wuruoye.library.model.Listener;
import com.wuruoye.library.util.net.WNet;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @Created : wuruoye
 * @Date : 2018/4/12 21:56.
 * @Description :
 */

public class RegisterPresenter extends RegisterContract.Presenter {
    @Override
    public void requestRegister(String name, String pwd, int role) {
        ArrayMap<String, String> values = new ArrayMap<>();
        values.put("username", name);
        values.put("psw", pwd);
        values.put("role", String.valueOf(role));

        WNet.postInBackGround(Api.INSTANCE.getREGISTER(), values, new Listener<String>() {
            @Override
            public void onSuccessful(String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    if (object.has("user_id")) {
                        if (isAvailable()) {
                            getView().onResultRegister(true, null);
                        }
                    }else if (object.getInt("code") == 0) {
                        if (isAvailable()) {
                            getView().onResultRegister(true, null);
                        }
                    }else {
                        if (isAvailable()) {
                            getView().onResultRegister(false, object.getString("msg"));
                        }
                    }
                } catch (JSONException e) {
                    if (isAvailable()) {
                        getView().onResultRegister(false, e.getMessage());
                    }
                }
            }

            @Override
            public void onFail(String s) {
                if (isAvailable()) {
                    getView().onResultRegister(false, s);
                }
            }
        });
    }
}
