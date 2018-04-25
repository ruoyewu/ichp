package com.wuruoye.ichp.ui.presenter.pro;

import android.support.v4.util.ArrayMap;

import com.wuruoye.ichp.base.model.Api;
import com.wuruoye.ichp.base.util.NetResultUtil;
import com.wuruoye.ichp.ui.contract.pro.MapContract;
import com.wuruoye.ichp.ui.model.UserCache;
import com.wuruoye.ichp.ui.model.bean.Note;
import com.wuruoye.library.model.Listener;
import com.wuruoye.library.util.net.WNet;

import java.util.List;

import static com.wuruoye.ichp.ui.contract.pro.MapContract.TYPE_ALL;

/**
 * @Created : wuruoye
 * @Date : 2018/4/14 00:23.
 * @Description :
 */

public class MapPresenter extends MapContract.Presenter {
    private UserCache mUserCache = UserCache.getInstance();

    @Override
    public void requestNote(int type) {
        ArrayMap<String, String> values = new ArrayMap<>();
        String url;
        if (type == TYPE_ALL) {
            url = Api.INSTANCE.getGET_ALL_REC();
        }else {
            url = Api.INSTANCE.getGET_USER_REC();
            values.put("recorder", String.valueOf(mUserCache.getUserId()));
        }
        values.put("token", mUserCache.getToken());

        WNet.postInBackGround(url, values, new Listener<String>() {
            @Override
            public void onSuccessful(String s) {
                if (isAvailable()) {
                    try {
                        List<Note> noteList = NetResultUtil.parseDataList(s, Note.class);
                        noteList = NetResultUtil.net2localNote(noteList);
                        getView().onResultNote(noteList);
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
