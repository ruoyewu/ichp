package com.wuruoye.ichp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wuruoye.ichp.R;
import com.wuruoye.ichp.ui.contract.pro.FoundContract;
import com.wuruoye.ichp.ui.model.bean.Course;
import com.wuruoye.ichp.ui.model.bean.Note;
import com.wuruoye.ichp.ui.presenter.DevFoundPresenter;
import com.wuruoye.library.ui.WBaseFragment;
import com.youth.banner.Banner;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created by wuruoye on 2018/1/31.
 * this file is to
 */

public class FoundFragment extends WBaseFragment implements FoundContract.View {
    public static final int ITEM_SIZE = 2;

    private SearchView svFound;
    private Banner banFound;
    private LinearLayout[] llFound = new LinearLayout[2];
    private TextView[] tvMores = new TextView[2];
    private ImageView[] ivItems = new ImageView[4];
    private TextView[] tvItems = new TextView[4];

    @Override
    public int getContentView() {
        return R.layout.fragment_found;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        mPresenter = new DevFoundPresenter();
        mPresenter.attachView(this);
    }

    @Override
    public void initView(@NotNull View view) {
        svFound = view.findViewById(R.id.sv_search);
        banFound = view.findViewById(R.id.banner_found);
        llFound[0] = view.findViewById(R.id.ll_found_1);
        llFound[1] = view.findViewById(R.id.ll_found_2);

        for (int i = 0; i < ITEM_SIZE; i++) {
            tvMores[i] = llFound[i].findViewById(R.id.tv_found_more);
            ivItems[i * 2] = llFound[i].findViewById(R.id.iv_found_1);
            ivItems[i * 2 + 1] = llFound[i].findViewById(R.id.iv_found_2);
            tvItems[i * 2] = llFound[i].findViewById(R.id.tv_found_1);
            tvItems[i * 2 + 1] = llFound[i].findViewById(R.id.tv_found_2);
        }

        initLayout();
    }

    private void initLayout() {
        svFound.setIconified(false);
        svFound.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (TextUtils.isEmpty(query)) {
                    Toast.makeText(getContext(), "请输入查询内容",
                            Toast.LENGTH_SHORT).show();
                    return true;
                }else {
                    onQuery(query);
                    return false;
                }
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        for (int i = 0; i < ITEM_SIZE; i++) {
            tvMores[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onMoreClick();
                }
            });
        }
    }

    private void onMoreClick() {
        startActivity(new Intent(getContext(), SearchActivity.class));
    }

    private void onQuery(String query) {
        Bundle bundle = new Bundle();
        bundle.putString("query", query);
        Intent intent = new Intent(getContext(), SearchActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onResultRecommend(List<Object> objects) {

    }

    @Override
    public void onResultNote(List<Note> notes) {

    }

    @Override
    public void onResultCourse(List<Course> courses) {

    }

    @Override
    public void onResultError(String error) {

    }
}
