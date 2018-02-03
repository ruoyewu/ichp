package com.wuruoye.ichp.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;

import com.wuruoye.ichp.R;
import com.wuruoye.ichp.base.BaseActivity;
import com.wuruoye.ichp.base.adapter.FragmentVPAdapter;
import com.wuruoye.ichp.base.adapter.OnPageChangeListenerAdapter;
import com.wuruoye.ichp.ui.util.ISearchView;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by wuruoye on 2018/2/1.
 * this file is to
 */

public class SearchActivity extends BaseActivity {
    public static final String[] ITEM_TITLE = {
            "记录", "活动", "词条", "用户"
    };
    public static final int TYPE_NOTE = 0;
    public static final int TYPE_COURSE = 1;
    public static final int TYPE_ENTRY = 2;
    public static final int TYPE_USER = 3;
    private static final String TAG = "SearchActivity";

    private SearchView svSearch;
    private TabLayout tlSearch;
    private ViewPager vpSearch;

    private String mQueryString;
    private List<ISearchView> mSearchViewList;
    private int mCurrentFragment;

    @Override
    public int getContentView() {
        return R.layout.activity_search;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        if (bundle != null) {
            mQueryString = bundle.getString("query");
        }
    }

    @Override
    public void initView() {
        svSearch = findViewById(R.id.sv_search);
        tlSearch = findViewById(R.id.tl_layout);
        vpSearch = findViewById(R.id.vp_layout);

        initLayout();
        initViewPager();

        if (mQueryString != null) {
            svSearch.setQuery(mQueryString, true);
        }
    }

    private void initLayout() {
        svSearch.setIconified(false);
        svSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (TextUtils.isEmpty(query)) {
                    return true;
                }else {
                    queryString(query);
                    return false;
                }
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void initViewPager() {
        mSearchViewList = new ArrayList<>();
        List<Fragment> fragmentList = new ArrayList<>();
        for (int i = 0; i < ITEM_TITLE.length; i++) {
            Bundle bundle = new Bundle();
            bundle.putInt("type", i);
            SearchListFragment fragment = new SearchListFragment();
            fragment.setArguments(bundle);
            fragmentList.add(fragment);
            mSearchViewList.add(fragment);
        }

        FragmentVPAdapter adapter = new FragmentVPAdapter(getSupportFragmentManager(),
                Arrays.asList(ITEM_TITLE), fragmentList);
        vpSearch.setAdapter(adapter);
        tlSearch.setupWithViewPager(vpSearch);

        vpSearch.addOnPageChangeListener(new OnPageChangeListenerAdapter() {
            @Override
            public void onPageSelected(int position) {
                mCurrentFragment = position;
            }
        });

        mCurrentFragment = 0;
    }

    private void queryString(String query) {
        log(query + " " + mCurrentFragment);
        mSearchViewList.get(mCurrentFragment).search(query);
    }

    private void log(String message) {
        Log.e(TAG, message);
    }
}
