package com.wuruoye.ichp.ui.presenter.pro;

import android.support.v4.util.ArrayMap;

import com.wuruoye.ichp.base.model.Api;
import com.wuruoye.ichp.base.util.NetResultUtil;
import com.wuruoye.ichp.ui.contract.pro.RecordContract;
import com.wuruoye.ichp.ui.model.Note;
import com.wuruoye.ichp.ui.model.UserCache;
import com.wuruoye.library.model.Listener;
import com.wuruoye.library.util.net.WNet;

import java.util.Collections;
import java.util.List;

import static com.wuruoye.ichp.ui.HomeFragment.TYPE_ATTENTION;

/**
 * @Created : wuruoye
 * @Date : 2018/4/1.
 * @Description :
 */

public class RecordPresenter extends RecordContract.Presenter {
    private UserCache mUserCache;

    public RecordPresenter() {
        mUserCache = new UserCache();
    }

    @Override
    public void requestRecord(final boolean isAdd, int type) {
        if (type == TYPE_ATTENTION) {
            ArrayMap<String, String> map = new ArrayMap<>();
            map.put("token", mUserCache.getToken());
            WNet.postInBackGround(Api.INSTANCE.getGET_ALL_REC(), map, new Listener<String>() {
                @Override
                public void onSuccessful(String s) {
                    try {
                        List<Note> result = NetResultUtil.parseDataList(s, Note.class);
                        Collections.reverse(result);
                        getView().onResultRecord(result, isAdd);
                    } catch (Exception e) {
                        if (isAvailable()) {
                            getView().onErrorRecord(e.getMessage());
                        }
                    }
                }

                @Override
                public void onFail(String s) {
                    if (isAvailable()) {
                        getView().onErrorRecord(s);
                    }
                }
            });
        }else {

        }
    }
}
