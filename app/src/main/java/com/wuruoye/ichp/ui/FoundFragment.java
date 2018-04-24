package com.wuruoye.ichp.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.wuruoye.ichp.R;
import com.wuruoye.ichp.ui.contract.pro.FoundContract;
import com.wuruoye.ichp.ui.model.bean.Course;
import com.wuruoye.ichp.ui.model.bean.Note;
import com.wuruoye.ichp.ui.presenter.pro.FoundPresenter;
import com.wuruoye.library.ui.WBaseFragment;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuruoye on 2018/1/31.
 * this file is to
 */

public class FoundFragment extends WBaseFragment<FoundContract.Presenter> implements FoundContract.View {
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
        setPresenter(new FoundPresenter());
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
        tvMores[1].setText("推荐活动");

        initLayout();
        mPresenter.requestRecommend();
        mPresenter.requestCourse("0,0");
        mPresenter.requestNote("0,0");
    }

    private void initLayout() {
        svFound.setEnabled(false);
        svFound.clearFocus();
//        svFound.setIconified(false);
//        svFound.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                if (TextUtils.isEmpty(query)) {
//                    Toast.makeText(getContext(), "请输入查询内容",
//                            Toast.LENGTH_SHORT).show();
//                    return true;
//                }else {
//                    onQuery(query);
//                    return false;
//                }
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });

        for (int i = 0; i < ITEM_SIZE; i++) {
            tvMores[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onMoreClick();
                }
            });
        }

        banFound.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
    }

    private void onMoreClick() {
//        startActivity(new Intent(getContext(), SearchActivity.class));
        Toast.makeText(getContext(), "暂无", Toast.LENGTH_SHORT).show();
    }

    private void onObjClick(Object obj) {
        if (obj instanceof Note) {
            Intent intent = new Intent(getContext(), NoteShowActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("note", (Parcelable) obj);
            intent.putExtras(bundle);
            startActivity(intent);
        }else if (obj instanceof Course) {
            Intent intent = new Intent(getContext(), CourseShowActivity.class);
            Bundle bundle = new Bundle();
            bundle.putParcelable("course", (Parcelable) obj);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    private void onQuery(String query) {
        Bundle bundle = new Bundle();
        bundle.putString("query", query);
        Intent intent = new Intent(getContext(), SearchActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onResultRecommend(final List<Object> objects) {
        List<String> images = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        for (Object obj : objects) {
            images.add(mPresenter.getImg(obj));
            if (obj instanceof Note) {
                titles.add(((Note) obj).getTitle());
            }else if (obj instanceof Course) {
                titles.add(((Course) obj).getTitle());
            }
        }
        banFound.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Glide.with(imageView)
                        .load(path)
                        .into(imageView);
            }
        });
        banFound.setImages(images);
        banFound.setBannerTitles(titles);
        banFound.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                onObjClick(objects.get(position));
            }
        });
        banFound.start();
    }

    @Override
    public void onResultNote(List<Note> notes) {
        int size = notes.size() > 2 ? 2 : notes.size();
        for (int i = 0; i < size; i++) {
            final Note n = notes.get(i);
            ivItems[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onObjClick(n);
                }
            });
            Glide.with(ivItems[i])
                    .load(mPresenter.getImg(n))
                    .into(ivItems[i]);
            tvItems[i].setText(n.getTitle());
        }
    }

    @Override
    public void onResultCourse(List<Course> courses) {
        int size = courses.size() > 2 ? 2 : courses.size();
        for (int i = 0; i < size; i++) {
            final Course c = courses.get(i);
            ivItems[i + 2].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onObjClick(c);
                }
            });
            Glide.with(ivItems[i + 2])
                    .load(mPresenter.getImg(c))
                    .into(ivItems[i + 2]);
            tvItems[i + 2].setText(c.getTitle());
        }
    }

    @Override
    public void onResultError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }
}
