package com.wuruoye.ichp.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wuruoye.ichp.R;
import com.wuruoye.ichp.base.adapter.BaseRVAdapter;
import com.wuruoye.ichp.ui.adapter.HeaderMediaRVAdapter;
import com.wuruoye.ichp.ui.contract.pro.ConfirmContract;
import com.wuruoye.ichp.ui.model.bean.Media;
import com.wuruoye.ichp.ui.presenter.pro.ConfirmPresenter;
import com.wuruoye.library.ui.WBaseActivity;
import com.wuruoye.library.util.media.IWPhoto;
import com.wuruoye.library.util.media.WPhoto;

/**
 * @Created : wuruoye
 * @Date : 2018/4/29 22:50.
 * @Description :
 */

public class UserConfirmActivity extends WBaseActivity<ConfirmContract.Presenter>
        implements View.OnClickListener, BaseRVAdapter.OnItemClickListener<Media>,
        BaseRVAdapter.OnItemLongClickListener<Media>, ConfirmContract.View,
        IWPhoto.OnWPhotoListener<String> {
    public static final String[] ITEM_BOTTOM = {"返回", "提交"};
    public static final int[] ICON_BOTTOM = {R.drawable.ic_goleft_black, R.drawable.ic_edit};
    public static final String[] ITEM_PHOTO = {"相册", "拍摄"};

    private Toolbar toolbar;
    private ImageView ivBack;
    private TextView tvTitle;
    private TextView tvManager;
    private EditText etName;
    private EditText etInfo;
    private RecyclerView rv;
    private LinearLayout llBottom;

    private AlertDialog dlgPhoto;
    private AlertDialog dlgTip;
    private AlertDialog dlgSuc;
    private WPhoto mPhotoGet;

    @Override
    protected int getContentView() {
        return R.layout.activity_confirm;
    }

    @Override
    protected void initData(Bundle bundle) {
        setPresenter(new ConfirmPresenter());
        mPhotoGet = new WPhoto(this);
    }

    @Override
    protected void initView() {
        toolbar = findViewById(R.id.toolbar);
        ivBack = findViewById(R.id.iv_tb_back);
        tvTitle = findViewById(R.id.tv_tb_title);
        tvManager = findViewById(R.id.tv_tb_manager);
        etName = findViewById(R.id.et_confirm_name);
        etInfo = findViewById(R.id.et_confirm_info);
        rv = findViewById(R.id.rv_confirm);
        llBottom = findViewById(R.id.ll_confirm_bottom);

        initLayout();
        initRV();
        initDlg();
    }

    private void initLayout() {
        setSupportActionBar(toolbar);
        ivBack.setOnClickListener(this);
        tvTitle.setText("官方用户认证");
        tvManager.setVisibility(View.GONE);

        for (int i = 0; i < ITEM_BOTTOM.length; i++) {
            View view = llBottom.getChildAt(i);
            ImageView iv = view.findViewById(R.id.iv_icon_text);
            TextView tv = view.findViewById(R.id.tv_icon_text);
            iv.setImageResource(ICON_BOTTOM[i]);
            tv.setText(ITEM_BOTTOM[i]);
            final int finalI = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onBottomClick(finalI);
                }
            });
        }
    }

    private void initRV() {
        HeaderMediaRVAdapter adapter = new HeaderMediaRVAdapter();
        adapter.setOnItemClickListener(this);
        adapter.setOnItemLongClickListener(this);
        rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,
                false));
        rv.setAdapter(adapter);
    }

    private void initDlg() {
        dlgPhoto = new AlertDialog.Builder(this)
                .setItems(ITEM_PHOTO, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i) {
                            case 0:
                                mPhotoGet.choosePhoto(UserConfirmActivity.this);
                                break;
                            case 1:
                                mPhotoGet.takePhoto(mPresenter.generatePhotoName(),
                                        UserConfirmActivity.this);
                                break;
                        }
                    }
                })
                .create();
        dlgTip = new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("正在上传中...")
                .create();
        dlgSuc = new AlertDialog.Builder(this)
                .setTitle("提示")
                .setMessage("您的认证已经提交审核，请耐心等待结果！")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .setCancelable(false)
                .create();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_tb_back:
                onBackPressed();
                break;
        }
    }

    private void onBottomClick(int position) {
        if (position == 0) {
            onBackPressed();
        } else if (position == 1) {
            dlgTip.show();
            mPresenter.requestUp();
        }
    }

    private void up() {
        String name = etName.getText().toString();
        String info = etInfo.getText().toString();
        if (TextUtils.isEmpty(name)) {
            onResultError("name is empty");
            return;
        }
        if (TextUtils.isEmpty(info)) {
            onResultError("info is empty");
            return;
        }
//        if (rv.getAdapter().getItemCount() <= 1) {
//            onResultError("image is empty");
//            return;
//        }

        mPresenter.requestUp();
    }

    @Override
    public void onItemClick(Media model) {
        if (model == null) {
            dlgPhoto.show();
        }else {
            if (model.getType() == Media.Type.IMAGE) {
                Intent intent = new Intent(this, ImgActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("img", model.getContent());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onItemLongClick(Media model) {
        ((HeaderMediaRVAdapter)rv.getAdapter()).removeData(model);
    }

    @Override
    public void onResultError(String error) {
        dlgTip.dismiss();
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResultUpload() {
        dlgTip.dismiss();
        dlgSuc.show();
    }

    @Override
    public void onPhotoResult(String s) {
        Media m = new Media(Media.Type.IMAGE, s);
        ((HeaderMediaRVAdapter)rv.getAdapter()).addData(m);
        rv.post(new Runnable() {
            @Override
            public void run() {
                rv.smoothScrollToPosition(rv.getAdapter().getItemCount() - 1);
            }
        });
    }

    @Override
    public void onPhotoError(String s) {
        onResultError(s);
    }
}
