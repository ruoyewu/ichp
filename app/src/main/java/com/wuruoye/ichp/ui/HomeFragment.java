package com.wuruoye.ichp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.View;

import com.wuruoye.ichp.R;
import com.wuruoye.ichp.base.BaseFragment;
import com.wuruoye.ichp.base.adapter.FragmentVPAdapter;
import com.wuruoye.ichp.base.adapter.OnPageChangeListenerAdapter;
import com.wuruoye.ichp.base.widget.TouchViewPager;
import com.wuruoye.ichp.ui.contract.pro.MapContract;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.wuruoye.ichp.ui.NoteAddActivity.TYPE_NOTE;

/**
 * Created by wuruoye on 2018/1/27.
 * this file is to
 */

public class HomeFragment extends BaseFragment{
    public static final List<String> TITLE_LIST =
            Arrays.asList("关注", "推荐", "地图");
    public static final int TYPE_RECOMMEND = 1;
    public static final int TYPE_ATTENTION = 0;
    public static final int TYPE_MAP = 2;

    private TabLayout tlTab;
    private TouchViewPager vpPager;
    private FloatingActionButton fabAdd;

    @Override
    public int getContentView() {
        return R.layout.fragment_home;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public void initView(@NotNull View view) {
        tlTab = view.findViewById(R.id.tl_home);
        vpPager = view.findViewById(R.id.vp_home);
        fabAdd = view.findViewById(R.id.fab_home_add);

        initFragment();
        initFab();
    }

    private void initFragment() {
        List<Fragment> fragments = new ArrayList<>();
        Bundle bAttention = new Bundle();
        bAttention.putInt("type", TYPE_ATTENTION);
        Fragment attention = new RecordFragment();
        attention.setArguments(bAttention);
        Bundle bRecommend = new Bundle();
        bRecommend.putInt("type", TYPE_RECOMMEND);
        Fragment recommend = new RecordFragment();
        recommend.setArguments(bRecommend);
        fragments.add(attention);
        fragments.add(recommend);
        Fragment mapFragment = new MapFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", MapContract.TYPE_ALL);
        mapFragment.setArguments(bundle);
        fragments.add(mapFragment);

        FragmentVPAdapter adapter = new FragmentVPAdapter(
                getChildFragmentManager(), TITLE_LIST, fragments);
        vpPager.setAdapter(adapter);
        tlTab.setupWithViewPager(vpPager);

        vpPager.addOnPageChangeListener(new OnPageChangeListenerAdapter() {
            @Override
            public void onPageSelected(int position) {
                if (position == TYPE_MAP) {
                    showFab(false);
                }else {
                    showFab(true);
                }
            }
        });
    }

    private void initFab() {
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NoteAddActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("type", TYPE_NOTE);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void showFab(boolean isShow) {
        vpPager.setPagingEnable(isShow);
        if (isShow) {
            fabAdd.show();
        }else {
            fabAdd.hide();
        }
    }
}
