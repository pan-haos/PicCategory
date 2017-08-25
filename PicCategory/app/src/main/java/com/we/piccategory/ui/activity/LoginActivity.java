package com.we.piccategory.ui.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.we.piccategory.MainActivity;
import com.we.piccategory.R;
import com.we.piccategory.builder.DialogBuilder;
import com.we.piccategory.presenter.LoginPresenter;
import com.we.piccategory.ui.base.BaseActivity;
import com.we.piccategory.util.CommonUtil;
import com.we.piccategory.util.LoginManger;
import com.we.piccategory.view.ILoginView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by 86119 on 2017/4/9.
 */

public class LoginActivity extends BaseActivity<ILoginView, LoginPresenter> implements View.OnClickListener, ILoginView {

    //inject的是login界面的控件
    @InjectView(R.id.user_name)
    public EditText etUserName;//用户名

    @InjectView(R.id.password)
    public EditText etPassword;//密码

    @InjectView(R.id.btn_login)
    public Button loginBtn;//登录

    @InjectView(R.id.btn_to_regist)
    public Button toRegisterBtn;//跳转到注册的按钮

    @InjectView(R.id.tv_forget)
    public TextView forget;//忘记密码

    //以下是register界面的控件
    private EditText etPhone;
    private EditText etCheck;

    private Button register;

    private Button toLoginBtn;

    private TextView tvIdentify;

    @Override
    protected int initRes() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {
        boolean login_state = LoginManger.getLoginState();
        if (login_state == true) {
            skip(MainActivity.class);
        }
    }

    @Override
    public LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    @OnClick({R.id.btn_login, R.id.btn_to_regist, R.id.tv_forget})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                String userName = etUserName.getText().toString();
                String password = etPassword.getText().toString();
                myPresenter.login(userName, password);
                break;
            case R.id.btn_to_regist:
                initToRegister();
                break;
            case R.id.tv_forget:
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this, ForgetActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_to_login:
                Log.i("ph", "aaa");
                setContentView(R.layout.activity_login);
                ButterKnife.inject(this);
                break;
            case R.id.btn_register:
                String num = etPhone.getText().toString().trim();
                String value = etCheck.getText().toString().trim();
                myPresenter.checkValues(num, value);
//                skip(RegisterInfoActivity.class);
                break;
            case R.id.tv_identify://验证码
                String telNum = etPhone.getText().toString();
                if (CommonUtil.checkNum(telNum, 11)) {
                    myPresenter.checkPhone(telNum);
                }
                break;

            default:
                break;
        }
    }

    private void initToRegister() {
        setContentView(R.layout.activity_register);
        etPhone = (EditText) findViewById(R.id.phone_num);
        etCheck = (EditText) findViewById(R.id.check_value);
        register = (Button) findViewById(R.id.btn_register);
        toLoginBtn = (Button) findViewById(R.id.btn_to_login);
        tvIdentify = (TextView) findViewById(R.id.tv_identify);
        register.setOnClickListener(this);
        toLoginBtn.setOnClickListener(this);
        tvIdentify.setOnClickListener(this);
    }

    @Override
    public void skip(Class clazz) {
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this, clazz);
        if (etPhone != null) {
            intent.putExtra("telNum", etPhone.getText().toString());
        }
        startActivity(intent);
        this.finish();
    }

    @Override
    public void showMsgDialog(final String msg) {
        AlertDialog dialog = builder.createLoadingDialog(msg, DialogBuilder.COMMON_MODEL);
        dialog.show();
    }

    @Override
    public Dialog showLoading() {
        return builder.createLoadingDialog("", DialogBuilder.LOADING_MODEL);
    }

}
