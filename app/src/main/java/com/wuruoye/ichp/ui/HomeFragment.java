package com.wuruoye.ichp.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

import com.wuruoye.ichp.R;
import com.wuruoye.ichp.base.BaseFragment;
import com.wuruoye.ichp.base.adapter.FragmentVPAdapter;
import com.wuruoye.ichp.base.adapter.OnPageChangeListenerAdapter;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wuruoye on 2018/1/27.
 * this file is to
 */

public class HomeFragment extends BaseFragment {
    public static final List<String> TITLE_LIST =
            Arrays.asList("推荐", "关注", "词条");
    public static final int ENTRY_POSITION = 2;

    private TabLayout tlTab;
    private ViewPager vpPager;
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
        tlTab = view.findViewById(R.id.tl);
        vpPager = view.findViewById(R.id.vp);
        fabAdd = view.findViewById(R.id.fab_home_add);

        initFragment();
        initFab();
    }

    private void initFragment() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new RecommendFragment());
        fragments.add(new AttentionFragment());
        fragments.add(new EntryFragment());

        FragmentVPAdapter adapter = new FragmentVPAdapter(
                getChildFragmentManager(), TITLE_LIST, fragments);
        vpPager.setAdapter(adapter);
        tlTab.setupWithViewPager(vpPager);

        vpPager.addOnPageChangeListener(new OnPageChangeListenerAdapter() {
            @Override
            public void onPageSelected(int position) {
                if (position == ENTRY_POSITION) {
                    fabAdd.hide();
                }else {
                    fabAdd.show();
                }
            }
        });
    }

    private void initFab() {
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "fab click", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
