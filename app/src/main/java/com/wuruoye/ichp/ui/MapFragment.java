package com.wuruoye.ichp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.PolylineOptions;
import com.wuruoye.ichp.R;
import com.wuruoye.ichp.base.BaseFragment;
import com.wuruoye.ichp.base.model.MainHandler;
import com.wuruoye.ichp.base.util.LocationUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by wuruoye on 2018/1/27.
 * this file is to
 */

public class MapFragment extends BaseFragment {
    private MapView mv;

    private AMap mAMap;
    private Bundle mSaveInstanceState;

    @Override
    public int getContentView() {
        return R.layout.fragment_map;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public void initView(@NotNull View view) {
        mv = view.findViewById(R.id.mv_map);
        mv.onCreate(mSaveInstanceState);

        initMap();
    }

    private void initMap() {
        mAMap = mv.getMap();
        UiSettings settings = mAMap.getUiSettings();
        settings.setZoomControlsEnabled(true);
        final LatLng llBJ = new LatLng(39.906901,116.39797);
        Marker marker = mAMap.addMarker(new MarkerOptions().position(llBJ));
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Double[] location = LocationUtil.INSTANCE.getLocation(getContext());
                MainHandler.getInstance().post(new Runnable() {
                    @Override
                    public void run() {
                        LatLng llLocation = new LatLng(location[0], location[1]);
                        mAMap.addMarker(new MarkerOptions().position(llLocation));
                        mAMap.addPolyline(new PolylineOptions().add(llBJ, llLocation).width(10).zIndex(0.5F));
//                        mAMap.moveCamera(CameraUpdateFactory.zoomTo(5F));
                        mAMap.moveCamera(CameraUpdateFactory.newLatLng(llLocation));
                    }
                });
            }
        }).start();
    }

    @Override
    public void onCreate(@android.support.annotation.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSaveInstanceState = savedInstanceState;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mv.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        mv.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mv.onPause();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mv.onSaveInstanceState(outState);
    }
}
