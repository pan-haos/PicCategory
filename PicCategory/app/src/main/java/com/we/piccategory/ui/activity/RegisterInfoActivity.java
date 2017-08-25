package com.we.piccategory.ui.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.we.piccategory.R;
import com.we.piccategory.bean.User;
import com.we.piccategory.builder.DialogBuilder;
import com.we.piccategory.presenter.RegisterInfoPresenter;
import com.we.piccategory.ui.base.BaseBackActivity;
import com.we.piccategory.util.Constant;
import com.we.piccategory.util.ImageUtil;
import com.we.piccategory.view.IInfoView;
import com.we.piccategory.widget.CheckSpinner;
import com.we.piccategory.widget.CircleImageView;

import java.lang.ref.WeakReference;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/4/20
 * Time: 17:46
 * Description: 注册信息填写的activity
 */
public class RegisterInfoActivity extends BaseBackActivity<IInfoView, RegisterInfoPresenter> implements IInfoView {

    @InjectView(R.id.commit_info)
    public TextView commitInfo;

    @InjectView(R.id.et_set_name)
    public EditText etUserName;

    @InjectView(R.id.et_set_pwd)
    public EditText etPwd;

    @InjectView(R.id.pref_spinner)
    public CheckSpinner prefSpinner;

    @InjectView(R.id.job_spinner)
    public Spinner jobSpinner;

    @InjectView(R.id.set_group)
    public RadioGroup group;

    @InjectView(R.id.head_img)
    public CircleImageView civ;

    public static TextView tvShow;

    private String telNum;

    private Dialog dialog;


    private Bitmap bm;

    @Override
    protected int initRes() {
        return R.layout.activity_info_regist;
    }


    @Override
    protected void initData() {
        tvShow = (TextView) findViewById(R.id.tv_show);
        Intent intent = getIntent();
        telNum = intent.getStringExtra("telNum");
    }

    @Override
    public String getTitleStr() {
        return "完善信息";
    }

    @Override
    public RegisterInfoPresenter createPresenter() {
        return new RegisterInfoPresenter();
    }


    @OnClick({R.id.commit_info, R.id.head_img})
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.commit_info://提交信息
                String userName = etUserName.getText().toString().trim();
                String password = etPwd.getText().toString().trim();
                int checkId = group.getCheckedRadioButtonId();
                String sex = null;
                if (checkId == R.id.rb_man) {
                    sex = "男";
                } else if (checkId == R.id.rb_women) {
                    sex = "女";
                }
                String job = (String) jobSpinner.getSelectedItem();

                String pref = tvShow.getText().toString().trim();

                String img = ImageUtil.base64CodingBmp(bm);

                User user = new User(userName, password, telNum, job, sex, pref, img);
                myPresenter.registerInfo(user);

                break;

            case R.id.head_img:
                showImgDialog();
                break;

            default:
                break;
        }

    }


    @Override
    public void skip(Class clazz) {
        Intent intent = new Intent();
        intent.setClass(this, clazz);
        startActivity(intent);
    }


    @Override
    public void showUser(User user) {

    }

    @Override
    public Dialog showMsgDialog(String msg) {
        AlertDialog dialog = builder.createLoadingDialog(msg, DialogBuilder.COMMON_MODEL);
        return dialog;
    }


    @Override
    public Dialog showLoading() {
        AlertDialog loadingDialog = builder.createLoadingDialog("", DialogBuilder.LOADING_MODEL);
        return loadingDialog;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //摄像头
        if (requestCode == Constant.OPEN_CAMERA_CODE) {
            if (data == null) {
                return;
            }
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap bmp = extras.getParcelable("data");
                Uri uri = ImageUtil.saveBitmap(bmp);
                startImgZoom(uri);
            }
            //图库
        } else if (requestCode == Constant.OPEN_LIB_CODE) {
            if (data == null) {
                return;
            }
            Uri uri;
            uri = data.getData();
            Uri fileUri = ImageUtil.convertUri(uri);
            startImgZoom(fileUri);
        } else if (requestCode == Constant.START_CROP) {
            if (data == null) {
                return;
            }
            Bundle extras = data.getExtras();
            bm = extras.getParcelable("data");
            civ.setImageBitmap(bm);
        }
    }

    private void startImgZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, Constant.START_CROP);
    }

    private void showImgDialog() {
        View layout = getLayoutInflater().inflate(R.layout.dialog_layout,
                (ViewGroup) findViewById(R.id.dialog_layout));
        TextView tvTakePho = (TextView) layout.findViewById(R.id.tack_photo);
        TextView tvGetSys = (TextView) layout.findViewById(R.id.get_system);
        TvClickListener tvClickListener = new TvClickListener(this);

        tvTakePho.setOnClickListener(tvClickListener);
        tvGetSys.setOnClickListener(tvClickListener);
        dialog = new AlertDialog.Builder(this).setView(layout).show();
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);//去背景
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tvShow = null;//因为这里tv是静态的，此时不移除掉对TextView实际对象的引用，会和application同生共死,
        //但是过了这个activity已经不需要该对象，通过monitor分析，textView不移除引用会多好费3m的内存
    }


    private static class TvClickListener implements View.OnClickListener {

        private WeakReference<RegisterInfoActivity> ref;

        public TvClickListener(RegisterInfoActivity activity) {
            ref = new WeakReference<>(activity);
        }

        @Override
        public void onClick(View v) {
            RegisterInfoActivity activity = ref.get();
            switch (v.getId()) {
                case R.id.tack_photo:
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (activity != null) {
                        activity.startActivityForResult(intent, Constant.OPEN_CAMERA_CODE);
                        activity.dialog.dismiss();
                    }
                    break;
                case R.id.get_system:
                    Intent intent1 = new Intent(Intent.ACTION_GET_CONTENT);
                    intent1.setType("image/*");
                    if (activity != null) {
                        activity.startActivityForResult(intent1, Constant.OPEN_LIB_CODE);
                        activity.dialog.dismiss();
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
