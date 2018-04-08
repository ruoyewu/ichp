package com.wuruoye.ichp.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.wuruoye.ichp.R;
import com.wuruoye.ichp.base.BaseActivity;
import com.wuruoye.ichp.base.adapter.FragmentVPAdapter;
import com.wuruoye.ichp.ui.model.bean.Entry;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by wuruoye on 2018/2/3.
 * this file is to
 */

public class EntryInfoActivity extends BaseActivity {
    public static final String[] ITEM_TITLE = { "词条动态", "词条活动" };

    private TabLayout tl;
    private ViewPager vp;
    private ImageView ivBack;
    private CircleImageView civ;
    private TextView tvTitle;
    private TextView tvIntro;

    private Entry mEntry;

    @Override
    public int getContentView() {
        return R.layout.activity_entry_info;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {

    }

    @Override
    public void initView() {
        ivBack = findViewById(R.id.iv_entry_info_back);
        civ = findViewById(R.id.civ_entry_info);
        tvTitle = findViewById(R.id.tv_entry_info_title);
        tvIntro = findViewById(R.id.tv_entry_info_intro);
        tl = findViewById(R.id.tl_entry_info);
        vp = findViewById(R.id.vp_entry_info);

        initLayout();
        initViewPager();
    }

    private void initLayout() {
        tvTitle.setText(mEntry.getName());
        tvIntro.setText(mEntry.getName());
    }

    private void initViewPager() {
        List<Fragment> fragmentList = new ArrayList<>();
        for (int i = 0; i < ITEM_TITLE.length; i++) {
            Bundle bundle = new Bundle();
            bundle.putInt("type", i);
            Fragment fragment = new EntryInfoListFragment();
            fragment.setArguments(bundle);
            fragmentList.add(fragment);
        }

        FragmentVPAdapter adapter = new FragmentVPAdapter(getSupportFragmentManager(),
                Arrays.asList(ITEM_TITLE), fragmentList);
        vp.setAdapter(adapter);
        tl.setupWithViewPager(vp);
    }
}
