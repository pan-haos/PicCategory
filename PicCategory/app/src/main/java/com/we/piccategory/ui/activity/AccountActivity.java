package com.we.piccategory.ui.activity;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.we.piccategory.R;
import com.we.piccategory.builder.DialogBuilder;
import com.we.piccategory.presenter.PhonePresenter;
import com.we.piccategory.ui.base.BaseBackActivity;
import com.we.piccategory.util.Constant;
import com.we.piccategory.util.LoginManger;
import com.we.piccategory.view.ILoginView;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/5/23
 * Time: 19:08
 * Description:用户关键信息的activity
 */
public class AccountActivity extends BaseBackActivity<ILoginView, PhonePresenter> {
    @InjectView(R.id.account_tel_num)
    public TextView tvNum;

    private String telNum;

    @Override
    protected int initRes() {
        return R.layout.activity_account;
    }

    @Override
    protected void initData() {
        telNum = LoginManger.getTelNum();
        tvNum.setText(telNum);
    }

    @Override
    public PhonePresenter createPresenter() {
        return new PhonePresenter();
    }

    @Override
    public String getTitleStr() {
        return "我的账号";
    }

    @OnClick({R.id.phone_ly, R.id.change_pwd_ly})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.phone_ly:
                start();
                break;
            case R.id.change_pwd_ly:
                skip(NewPwdActivity.class);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constant.SKIP_TO_PHONE_CHANGE) {
            String phoneNum = data.getStringExtra("phoneNum");
            String msg = data.getStringExtra("msg");
            tvNum.setText(phoneNum);
            builder.createLoadingDialog(msg, DialogBuilder.COMMON_MODEL).show();
        } else if (requestCode == Constant.SKIP_TO_NEW_PWD) {
            String msg = data.getStringExtra("msg");
            builder.createLoadingDialog(msg, DialogBuilder.COMMON_MODEL).show();
        }
    }

    private void start() {
        Intent intent = new Intent();
        intent.setClass(this, ChangePhoneActivity.class);
        startActivityForResult(intent, Constant.SKIP_TO_PHONE_CHANGE);
    }


    private void skip(Class clazz) {
        Intent intent = new Intent();
        intent.setClass(this, clazz);
        if (clazz.equals(NewPwdActivity.class)) {
            intent.putExtra("telNum", LoginManger.getTelNum());
            intent.putExtra("model", Constant.ACCOUNT_MODEL);
        }
        startActivityForResult(intent, Constant.SKIP_TO_NEW_PWD);

    }


}
