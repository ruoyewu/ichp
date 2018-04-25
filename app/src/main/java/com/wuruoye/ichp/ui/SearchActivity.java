package com.wuruoye.ichp.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;

import com.wuruoye.ichp.R;
import com.wuruoye.ichp.base.BaseActivity;
import com.wuruoye.ichp.base.adapter.FragmentVPAdapter;
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

    private SearchView svSearch;
    private TabLayout tlSearch;
    private ViewPager vpSearch;

    private List<ISearchView> mSearchViewList;

    @Override
    public int getContentView() {
        return R.layout.activity_search;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public void initView() {
        svSearch = findViewById(R.id.sv_search);
        tlSearch = findViewById(R.id.tl_layout);
        vpSearch = findViewById(R.id.vp_layout);

        initLayout();
        initViewPager();
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
            bundle.putInt("type", i + 1);
            SearchListFragment fragment = new SearchListFragment();
            fragment.setArguments(bundle);
            fragmentList.add(fragment);
            mSearchViewList.add(fragment);
        }

        FragmentVPAdapter adapter = new FragmentVPAdapter(getSupportFragmentManager(),
                Arrays.asList(ITEM_TITLE), fragmentList);
        vpSearch.setAdapter(adapter);
        tlSearch.setupWithViewPager(vpSearch);
    }

    public String getQuery() {
        return svSearch.getQuery().toString();
    }

    private void queryString(String query) {
        for (ISearchView search : mSearchViewList) {
            search.search(query);
        }
    }
}
