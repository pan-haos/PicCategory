package com.we.piccategory.ui.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.we.piccategory.R;
import com.we.piccategory.presenter.LoginPresenter;
import com.we.piccategory.ui.base.BaseBackActivity;
import com.we.piccategory.util.Constant;
import com.we.piccategory.view.ILoginView;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/5/29
 * Time: 11:49
 * Description:
 */
public class ChangePhoneActivity extends BaseBackActivity<ILoginView, LoginPresenter> implements ILoginView {

    @InjectView(R.id.tel_change_et)
    public EditText etPhone;//新手机号

    @InjectView(R.id.tel_change_et_value)
    public EditText etValue;//验证码

    @InjectView(R.id.tel_change_tv_get)
    public TextView tvGetValue;//获取验证码

    @InjectView(R.id.tel_change_enter)
    public TextView next;//确认更改


    @Override
    protected int initRes() {
        return R.layout.activity_phone_change;
    }

    @Override
    public LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @Override
    public String getTitleStr() {
        return "更改手机号";
    }

    @Override
    public void skip(Class clazz) {
//        Intent intent = new Intent();
//        intent.putExtra("phoneNum", etPhone.getText().toString().trim());
//        setResult(Constant.SKIP_TO_PHONE_CHANGE, intent);
    }

    @Override
    public void showMsgDialog(String msg) {
        Intent intent = new Intent();
        intent.putExtra("phoneNum", etPhone.getText().toString().trim());
        intent.putExtra("msg", msg);
        setResult(Constant.SKIP_TO_PHONE_CHANGE, intent);
        this.finish();
    }

    @Override
    public Dialog showLoading() {
        AlertDialog loadDialog = builder.createLoadDialog();
        loadDialog.show();
        return loadDialog;
    }


    @OnClick({R.id.tel_change_tv_get, R.id.tel_change_enter})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tel_change_tv_get:
                String telNum = etPhone.getText().toString().trim();
                tvGetValue.setTextColor(R.color.lightgrey);
                myPresenter.checkPhone(telNum);
                break;
            case R.id.tel_change_enter:
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                //更新操作而不是插入操作
                String tel = etPhone.getText().toString().trim();
                String checkValue = etValue.getText().toString().trim();
                myPresenter.changeNum(tel, checkValue);
                break;
        }
    }

}
