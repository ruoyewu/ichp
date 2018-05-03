package com.wuruoye.ichp.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.wuruoye.ichp.R;
import com.wuruoye.ichp.base.adapter.BaseRVAdapter;
import com.wuruoye.ichp.base.adapter.FragmentVPAdapter;
import com.wuruoye.ichp.base.util.ClipboardUtil;
import com.wuruoye.ichp.base.util.ShareUtil;
import com.wuruoye.ichp.ui.adapter.EntryChooseRVAdapter;
import com.wuruoye.ichp.ui.contract.pro.CourseShowContract;
import com.wuruoye.ichp.ui.model.bean.Course;
import com.wuruoye.ichp.ui.model.bean.Entry;
import com.wuruoye.ichp.ui.model.bean.Media;
import com.wuruoye.ichp.ui.model.bean.User;
import com.wuruoye.ichp.ui.presenter.pro.CourseShowPresenter;
import com.wuruoye.library.ui.WBaseActivity;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @Created : wuruoye
 * @Date : 2018/4/14 07:56.
 * @Description :
 */

public class CourseShowActivity extends WBaseActivity<CourseShowContract.Presenter>
        implements CourseShowContract.View, View.OnClickListener, BaseRVAdapter.OnItemClickListener<Entry> {
    public static final String[] ITEM_BOTTOM = {"返回", "入口地址", "收藏", "分享"};
    public static final int[] ICON_BOTTOM = {R.drawable.ic_goleft_black, R.drawable.ic_edit,
            R.drawable.ic_star_white, R.drawable.ic_share};

    public final int COURSE_MODIFY = hashCode() % 10000;

    private Toolbar toolbar;
    private ImageView ivBack;
    private TextView tvTitle;
    private TextView tvManager;
    private CircleImageView civ;
    private TextView tvName;
    private TextView tvTime;
    private ViewPager vp;
    private RecyclerView rvEntry;
    private TextView tvContent;
    private TextView tvDate;
    private TextView tvPlace;
    private LinearLayout llBottom;

    private TextView tvCollect;
    private ImageView ivCollect;
    private AlertDialog dlgEntrance;

    private User mUser;
    private Course mCourse;

    @Override
    protected int getContentView() {
        return R.layout.activity_course_show;
    }

    @Override
    protected void initData(Bundle bundle) {
        mCourse = bundle.getParcelable("course");
        setPresenter(new CourseShowPresenter());
    }

    @Override
    protected void initView() {
        toolbar = findViewById(R.id.toolbar);
        ivBack = findViewById(R.id.iv_tb_back);
        tvTitle = findViewById(R.id.tv_tb_title);
        tvManager = findViewById(R.id.tv_tb_manager);
        civ = findViewById(R.id.civ_course_show);
        tvName = findViewById(R.id.tv_course_show_user);
        tvContent = findViewById(R.id.tv_course_show_content);
        tvTime = findViewById(R.id.tv_course_show_time);
        vp = findViewById(R.id.vp_course_show);
        rvEntry = findViewById(R.id.rv_course_show_entry);
        tvDate = findViewById(R.id.tv_course_date);
        tvPlace = findViewById(R.id.tv_course_place);
        llBottom = findViewById(R.id.ll_course_show_bottom);

        initLayout();
        initDlg();
        initVP();
        initRV();
        mPresenter.requestUserInfo(mCourse.getPublisher());
//        mPresenter.requestEntryList(mCourse.getLabels_id_str());
        mPresenter.requestCourseInfo(mCourse.getAct_id());
    }

    private void initLayout() {
        setSupportActionBar(toolbar);
        ivBack.setOnClickListener(this);
        tvManager.setVisibility(View.INVISIBLE);
        tvTitle.setText(mCourse.getTitle());
        tvTime.setText(mPresenter.parseDate(mCourse.getIssue_date()));
        tvContent.setText(mCourse.getContent());
        tvPlace.setText(mCourse.getHold_addr());
        tvDate.setText(mPresenter.parseDate(mCourse.getHold_date()));

        for (int i = 0; i < llBottom.getChildCount(); i++) {
            LinearLayout ll = (LinearLayout) llBottom.getChildAt(i);
            ImageView iv = ll.findViewById(R.id.iv_icon_text);
            TextView tv = ll.findViewById(R.id.tv_icon_text);
            iv.setImageResource(ICON_BOTTOM[i]);
            tv.setText(ITEM_BOTTOM[i]);
            final int finalI = i;
            ll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBottomClick(finalI);
                }
            });
            if (i == 2) {
                tvCollect = tv;
                ivCollect = iv;
            }
        }
        civ.setOnClickListener(this);

        changeCollect(mCourse.isColl());

        checkModify();
    }

    private void initDlg() {
        dlgEntrance = new AlertDialog.Builder(this)
                .setTitle("入口地址")
                .setMessage(mCourse.getAct_src())
                .setPositiveButton("复制", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ClipboardUtil.copyText(mCourse.getAct_src(), CourseShowActivity.this);
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .create();
    }

    private void initVP() {
        List<Media> mediaList = mPresenter.parseMedia(mCourse.getImage_src(), mCourse.getType());
        if (mediaList.size() == 0) {
            vp.setVisibility(View.GONE);
        }else {
            vp.setVisibility(View.VISIBLE);
            List<Fragment> fragmentList = new ArrayList<>();
            for (Media m : mediaList) {
                Fragment fragment = new MediaFragment();
                Bundle bundle = new Bundle();
                bundle.putParcelable("media", m);
                fragment.setArguments(bundle);
                fragmentList.add(fragment);
            }
            FragmentVPAdapter adapter = new FragmentVPAdapter(getSupportFragmentManager(),
                    null, fragmentList);
            vp.setAdapter(adapter);
        }
    }

    private void initRV() {
        EntryChooseRVAdapter adapter = new EntryChooseRVAdapter();
        adapter.setOnItemClickListener(this);
        rvEntry.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.HORIZONTAL, false));
        DividerItemDecoration decoration = new DividerItemDecoration(this,
                DividerItemDecoration.HORIZONTAL);
        decoration.setDrawable(ActivityCompat.getDrawable(this,
                R.drawable.decoration_horizontal));
        rvEntry.addItemDecoration(decoration);
        rvEntry.setAdapter(adapter);
    }

    private void checkModify() {
        if (mCourse.getPublisher() == mPresenter.getUserId()) {
            tvManager.setVisibility(View.VISIBLE);
            tvManager.setText("修改");
            tvManager.setOnClickListener(this);
        }
    }

    private void changeCollect(boolean collect) {
        tvCollect.setText(collect ? "已收藏" : "收藏");
        tvCollect.setTextColor(collect ? Color.BLACK : Color.WHITE);
        ivCollect.setImageResource(collect ? R.drawable.ic_star_black : R.drawable.ic_star_white);
        ivCollect.setTag(collect);
    }

    @Override
    public void onResultError(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResultUserInfo(User user) {
        mUser = user;
        Glide.with(civ)
                .load(user.getImage_src())
                .into(civ);
        tvName.setText(user.getAccount_name());
    }

    @Override
    public void onResultEntryList(List<Entry> entryList) {
        EntryChooseRVAdapter adapter = (EntryChooseRVAdapter) rvEntry.getAdapter();
        adapter.setData(entryList);
    }

    @Override
    public void onResultCourseInfo(Course course) {
        mCourse = course;
        tvTitle.setText(course.getTitle());
        tvTime.setText(mPresenter.parseDate(course.getIssue_date()));
        tvContent.setText(course.getContent());
        tvPlace.setText(course.getHold_addr());
        tvDate.setText(mPresenter.parseDate(course.getHold_date()));

        changeCollect(course.isColl());
        mPresenter.requestUserInfo(course.getPublisher());
        mPresenter.requestEntryList(course.getLabels_id_str());
        initVP();
        initDlg();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_tb_back:
                onBackPressed();
                break;
            case R.id.tv_tb_manager:
                Intent intent = new Intent(this, NoteAddActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("type", NoteAddActivity.TYPE_COURSE);
                bundle.putParcelable("data", mCourse);
                bundle.putBoolean("modify", true);
                bundle.putParcelableArrayList("entry", (ArrayList<? extends Parcelable>)
                        ((EntryChooseRVAdapter)rvEntry.getAdapter()).getData());
                intent.putExtras(bundle);
                startActivityForResult(intent, COURSE_MODIFY);
                break;
            case R.id.civ_course_show:
                Intent intent1 = new Intent(this, UserInfoActivity.class);
                Bundle bundle1 = new Bundle();
                bundle1.putParcelable("user", mUser);
                intent1.putExtras(bundle1);
                startActivity(intent1);
                break;
        }
    }

    private void onBottomClick(int i) {
        switch (i) {
            case 0:
                onBackPressed();
                break;
            case 1:
                dlgEntrance.show();
                break;
            case 2:
                boolean coll = !mCourse.isColl();
                mCourse.setColl(coll);
                changeCollect(coll);
                mPresenter.requestCollect(mCourse.getAct_id(), coll);
                break;
            case 3:
                String text = mCourse.getTitle() + "\n" + mCourse.getContent();
                ShareUtil.INSTANCE.shareText(text, this);
                break;
        }
    }

    @Override
    public void onItemClick(Entry model) {
        Intent intent = new Intent(this, EntryInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("entry", model);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
