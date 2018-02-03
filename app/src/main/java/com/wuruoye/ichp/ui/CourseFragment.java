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

public class CourseFragment extends BaseFragment {
    public static final String[] ITEM_TITLE = {
            "网络活动", "官方活动"
    };
    public static final int GOVERNMENT = 1;

    private TabLayout tlCourse;
    private ViewPager vpCourse;
    private FloatingActionButton fabCourseAdd;

    @Override
    public int getContentView() {
        return R.layout.fragment_course;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public void initView(@NotNull View view) {
        tlCourse = view.findViewById(R.id.tl_layout);
        vpCourse = view.findViewById(R.id.vp_layout);
        fabCourseAdd = view.findViewById(R.id.fab_course_add);

        initLayout();
        initViewPager();
    }

    private void initLayout() {
        fabCourseAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFabClick();
            }
        });
        fabCourseAdd.setVisibility(View.INVISIBLE);
    }

    private void initViewPager() {
        List<Fragment> fragmentList = new ArrayList<>();
        for (int i = 0; i < ITEM_TITLE.length; i ++) {
            Bundle bundle = new Bundle();
            bundle.putInt("type", i);
            Fragment fragment = new CourseListFragment();
            fragment.setArguments(bundle);
            fragmentList.add(fragment);
        }

        FragmentVPAdapter adapter = new FragmentVPAdapter(getChildFragmentManager(),
                Arrays.asList(ITEM_TITLE), fragmentList);
        vpCourse.setAdapter(adapter);
        tlCourse.setupWithViewPager(vpCourse);

        vpCourse.addOnPageChangeListener(new OnPageChangeListenerAdapter() {
            @Override
            public void onPageSelected(int position) {
                showFab(position);
            }
        });
    }

    private void onFabClick() {
        Toast.makeText(getContext(), "fab click", Toast.LENGTH_SHORT).show();
    }

    private void showFab(int position) {
        if (position == GOVERNMENT) {
            fabCourseAdd.show();
        }else {
            fabCourseAdd.hide();
        }
    }
}
