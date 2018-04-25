package com.wuruoye.ichp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.wuruoye.ichp.R;
import com.wuruoye.ichp.ui.contract.pro.MapContract;
import com.wuruoye.ichp.ui.model.bean.Note;
import com.wuruoye.ichp.ui.presenter.pro.MapPresenter;
import com.wuruoye.library.ui.WBaseFragment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created by wuruoye on 2018/1/27.
 * this file is to
 */

public class MapFragment extends WBaseFragment<MapContract.Presenter>
        implements AMap.OnMarkerClickListener, MapContract.View {
    private MapView mv;

    private AMap mAMap;
    private Bundle mSaveInstanceState;

    private int mType;
    private LatLng llCenter;

    @Override
    public int getContentView() {
        return R.layout.fragment_map;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        mType = bundle.getInt("type");
        llCenter = new LatLng(37, 104);
        setPresenter(new MapPresenter());
    }

    @Override
    public void initView(@NotNull View view) {
        mv = view.findViewById(R.id.mv_map);
        mv.onCreate(mSaveInstanceState);

        initMap();
        mPresenter.requestNote(mType);
    }

    private void initMap() {
        mAMap = mv.getMap();
        mAMap.setOnMarkerClickListener(this);
        mAMap.moveCamera(CameraUpdateFactory.newCameraPosition(new CameraPosition(llCenter,
                4, 0, 0)));
        UiSettings settings = mAMap.getUiSettings();
        settings.setZoomControlsEnabled(true);
        settings.setScrollGesturesEnabled(true);
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
        Note note = (Note) marker.getObject();
        Intent intent = new Intent(getContext(), NoteShowActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("note", note);
        intent.putExtras(bundle);
        startActivity(intent);
        return true;
    }

    @Override
    public void onResultError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResultNote(List<Note> noteList) {
        for (Note n : noteList) {
            String[] addrs = n.getAddr().split(",");
            LatLng ll = new LatLng(Double.parseDouble(addrs[1]), Double.parseDouble(addrs[2]));
            mAMap.addMarker(new MarkerOptions().position(ll).title(n.getTitle())).setObject(n);
        }
    }
}
