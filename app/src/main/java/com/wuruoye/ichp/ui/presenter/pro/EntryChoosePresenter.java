package com.wuruoye.ichp.ui.presenter.pro;

import android.support.v4.util.ArrayMap;

import com.wuruoye.ichp.base.model.Api;
import com.wuruoye.ichp.base.util.NetResultUtil;
import com.wuruoye.ichp.ui.contract.pro.EntryChooseContract;
import com.wuruoye.ichp.ui.model.UserCache;
import com.wuruoye.ichp.ui.model.bean.Entry;
import com.wuruoye.library.model.Listener;
import com.wuruoye.library.util.net.WNet;

import java.util.List;

/**
 * @Created : wuruoye
 * @Date : 2018/4/2.
 * @Description :
 */

public class EntryChoosePresenter extends EntryChooseContract.Presenter {
    private UserCache mUserCache = UserCache.getInstance();

    @Override
    public void requestEntry(String search) {
        ArrayMap<String, String> values = new ArrayMap<>();
        values.put("token", mUserCache.getToken());
        values.put("searchEntry", search);
        WNet.postInBackGround(Api.INSTANCE.getSEARCH_ENTRY(), values, new Listener<String>() {
            @Override
            public void onSuccessful(String s) {
                try {
                    List<Entry> entries = NetResultUtil.parseDataList(s, Entry.class);
                    if (isAvailable()) {
                        getView().onResultEntry(entries);
                    }
                } catch (Exception e) {
                    if (isAvailable()) {
                        getView().onResultEntryError(e.getMessage());
                    }
                }
            }

            @Override
            public void onFail(String s) {
                if (isAvailable()) {
                    getView().onResultEntryError(s);
                }
            }
        });
    }
}
