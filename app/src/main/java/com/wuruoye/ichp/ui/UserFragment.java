package com.wuruoye.ichp.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.wuruoye.ichp.R;
import com.wuruoye.ichp.ui.contract.pro.PersonNoteContract;
import com.wuruoye.ichp.ui.contract.pro.UserContract;
import com.wuruoye.ichp.ui.model.bean.User;
import com.wuruoye.ichp.ui.presenter.pro.UserPresenter;
import com.wuruoye.library.ui.WBaseFragment;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static com.wuruoye.ichp.ui.contract.pro.UserAttentionContract.TYPE_ATTED;
import static com.wuruoye.ichp.ui.contract.pro.UserAttentionContract.TYPE_ATTEN;

/**
 * Created by wuruoye on 2018/1/27.
 * this file is to
 */

public class UserFragment extends WBaseFragment<UserContract.Presenter>
        implements View.OnClickListener, UserContract.View{
    public static final String[] ITEM_TITLE = new String[] {
            "我的信息", "我的非遗活动", "我的非遗记录", "我的收藏", "我的认证", "我的非遗足迹",
        "非遗拾贝", "注销登录", "关于"
    };
    public static final int[] ITEM_ICON = new int[] {
        R.drawable.ic_user_black, R.drawable.ic_activity, R.drawable.ic_edit, R.drawable.ic_heart,
            R.drawable.ic_confirm, R.drawable.ic_map, R.drawable.ic_shopping, R.drawable.ic_login,
            R.drawable.ic_info
    };
    public final int USER_INFO = hashCode() % 10000;
    public final int USER_LOGIN = hashCode() % 10000 + 1;

    private CircleImageView civ;
    private TextView tvName;
    private TextView tvId;
    private TextView tvFocus;
    private TextView tvFocused;
    private LinearLayout llUser;

    private TextView tvLogin;
    private AlertDialog dlgLogout;

    private User mUser;

    @Override
    public int getContentView() {
        return R.layout.fragment_user;
    }

    @Override
    public void initData(@Nullable Bundle bundle) {
        setPresenter(new UserPresenter());
    }

    @Override
    public void initView(@NotNull View view) {
        llUser = view.findViewById(R.id.ll_user);
        civ = view.findViewById(R.id.civ_user);
        tvName = view.findViewById(R.id.tv_user_name);
        tvId = view.findViewById(R.id.tv_user_id);
        tvFocus = view.findViewById(R.id.tv_user_focus);
        tvFocused = view.findViewById(R.id.tv_user_focused);

        initLayout();
        initDlg();
        initItems();

        mPresenter.requestUserInfo();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == USER_INFO && resultCode == RESULT_OK) {
            mUser = data.getParcelableExtra("user");
        }else if (requestCode == USER_LOGIN && resultCode == RESULT_OK) {
            tvLogin.setText("注销登录");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void initLayout() {
        tvFocused.setOnClickListener(this);
        tvFocus.setOnClickListener(this);
        civ.setOnClickListener(this);
    }

    private void initDlg() {
        dlgLogout = new AlertDialog.Builder(getContext())
                .setTitle("提示")
                .setMessage("注销登录？")
                .setPositiveButton("注销", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mPresenter.requestLogout();
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .create();
    }

    private void initItems() {
        boolean confirm = mPresenter.isConfirm();
        for (int i = 0; i < ITEM_TITLE.length; i++) {
            View view = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_user, llUser, false);
            final int finalI = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClick(finalI);
                }
            });
            ImageView iv = view.findViewById(R.id.iv_item_user);
            TextView tv = view.findViewById(R.id.tv_item_user);
            iv.setScaleX(0.8F);
            iv.setScaleY(0.8F);
            iv.setImageResource(R.drawable.ic_home);
            tv.setText(ITEM_TITLE[i]);
            iv.setImageResource(ITEM_ICON[i]);
            llUser.addView(view);
            if (i == 7) {
                tvLogin = tv;
                tv.setText(mPresenter.isLogin() ? "注销登录" : "登录");
            }
            if (!confirm && i == 1) {
                view.setVisibility(View.GONE);
            }
        }
    }

    private boolean checkUserAvailable() {
        if (mUser == null) {
            if (mPresenter.isLogin()) {
                Toast.makeText(getContext(), "正在加载用户信息", Toast.LENGTH_SHORT).show();
                mPresenter.requestUserInfo();
            }else {
                Toast.makeText(getContext(), "请先登录", Toast.LENGTH_SHORT).show();
            }
            return false;
        }else {
            return true;
        }
    }

    private void onItemClick(int position) {
        Bundle bundle = new Bundle();
        Intent intent;
        switch (position) {
            case 0:
                // 我的信息
                if (checkUserAvailable()) {
                    intent = new Intent(getContext(), PersonInfoActivity.class);
                    bundle.putParcelable("user", mUser);
                    intent.putExtras(bundle);
                    startActivityForResult(intent, USER_INFO);
                }
                break;
            case 1:
                // 我的非遗活动
                intent = new Intent(getContext(), PersonNoteActivity.class);
                bundle.putInt("type", PersonNoteContract.TYPE_COURSE);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case 2:
                // 我的非遗记录
                intent = new Intent(getContext(), PersonNoteActivity.class);
                bundle.putInt("type", PersonNoteContract.TYPE_NOTE);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case 3:
                // 我的收藏
                intent = new Intent(getContext(), PersonCollectActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case 4:
                // 我的认证

                break;
            case 5:
                // 我的非遗足迹
                intent = new Intent(getContext(), PersonTraceActivity.class);
                startActivity(intent);
                break;
            case 6:
                // 非遗拾贝
                break;
            case 7:
                // 注销登录
                if (mPresenter.isLogin()) {
                    dlgLogout.show();
                }else {
                    startActivityForResult(new Intent(getContext(), UserLoginActivity.class),
                            USER_LOGIN);
                }
                break;
            case 8:
                // 关于
                break;
        }
    }

    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        Intent intent;
        switch (v.getId()) {
            case R.id.tv_user_focus:
                bundle.putInt("type", TYPE_ATTEN);
                intent = new Intent(getContext(), UserAttentionActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.tv_user_focused:
                bundle.putInt("type", TYPE_ATTED);
                intent = new Intent(getContext(), UserAttentionActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.civ_user:
                if (checkUserAvailable()) {
                    intent = new Intent(getContext(), UserInfoActivity.class);
                    bundle.putParcelable("user", mUser);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                break;
        }
    }

    @Override
    public void onResultError(String error) {
        Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResultUserInfo(User user) {
        mUser = user;
        tvName.setText(user.getAccount_name());
        tvId.setText(String.valueOf(user.getUser_id()));
        Glide.with(civ)
                .load(user.getImage_src())
                .into(civ);
    }

    @Override
    public void onResultLogout() {
        tvLogin.setText("登录");
        Toast.makeText(getContext(), "已注销登录", Toast.LENGTH_SHORT).show();
    }
}
