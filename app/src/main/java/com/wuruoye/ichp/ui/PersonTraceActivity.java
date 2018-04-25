package com.wuruoye.ichp.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.wuruoye.ichp.R;
import com.wuruoye.ichp.ui.contract.pro.MapContract;
import com.wuruoye.library.ui.WBaseActivity;

/**
 * @Created : wuruoye
 * @Date : 2018/4/24 21:00.
 * @Description :
 */

public class PersonTraceActivity extends WBaseActivity {
    @Override
    protected int getContentView() {
        return R.layout.activity_person_trace;
    }

    @Override
    protected void initData(Bundle bundle) {

    }

    @Override
    protected void initView() {
        Fragment mapFragment = new MapFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", MapContract.TYPE_USER);
        mapFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_person_trace, mapFragment)
                .commit();
    }
}
