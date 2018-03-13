package com.wuruoye.ichp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.wuruoye.ichp.R;
import com.wuruoye.ichp.base.BaseFragment;
import com.wuruoye.ichp.base.model.Listener;
import com.wuruoye.ichp.base.util.LocationUtil;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuruoye on 2018/1/27.
 * this file is to
 */

public class MapFragment extends BaseFragment implements AMap.OnMarkerClickListener {
    private MapView mv;

    private AMap mAMap;
    private Bundle mSaveInstanceState;

    private List<LatLng> mLLList;

    @Override
    public int getContentView() {
        return R.layout.fragment_map;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        mLLList = new ArrayList<>();
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
        settings.setScrollGesturesEnabled(true);
        LocationUtil.INSTANCE.getLocation(getContext(), new Listener<Double[]>() {
            @Override
            public void onSuccess(Double[] model) {
                LatLng latLng = new LatLng(model[0], model[1]);
                mAMap.addMarker(new MarkerOptions().position(latLng).title("1"));
            }

            @Override
            public void onFail(@NotNull String message) {
                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
            }
        });
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

    @Override
    public boolean onMarkerClick(Marker marker) {
        Toast.makeText(getContext(), marker.getId(), Toast.LENGTH_SHORT).show();
        return true;
    }
}
