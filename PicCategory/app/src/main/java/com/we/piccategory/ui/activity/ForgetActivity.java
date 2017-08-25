package com.we.piccategory.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.we.piccategory.R;
import com.we.piccategory.builder.DialogBuilder;
import com.we.piccategory.presenter.ForgetPresenter;
import com.we.piccategory.ui.base.BaseBackActivity;
import com.we.piccategory.util.CommonUtil;
import com.we.piccategory.util.Constant;
import com.we.piccategory.view.ILoginView;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/4/20
 * Time: 12:25
 * Description:
 */
public class ForgetActivity extends BaseBackActivity<ILoginView, ForgetPresenter> implements ILoginView {
    @InjectView(R.id.next)
    public TextView next;

    @InjectView(R.id.tv_get)
    public TextView tvGet;

    @InjectView(R.id.et_forget_tel)
    public EditText etTelNum;

    @InjectView(R.id.et_value)
    public EditText etValue;


    @Override
    protected int initRes() {
        return R.layout.activity_forget;
    }

    @Override
    public String getTitleStr() {
        return "找回密码";
    }

    @Override
    public ForgetPresenter createPresenter() {
        return new ForgetPresenter();
    }

    @Override
    public void skip(Class clazz) {
        Intent intent = new Intent();
        intent.setClass(ForgetActivity.this, clazz);
        String telNum = etTelNum.getText().toString().trim();
        intent.putExtra("telNum", telNum);
        intent.putExtra("model", Constant.FORGET_MODEL);
        startActivityForResult(intent, Constant.SKIP_TO_NEW_PWD);
    }

    @Override
    public void showMsgDialog(String msg) {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Constant.SKIP_TO_NEW_PWD) {
            this.finish();
        }
    }

    @Override
    public Dialog showLoading() {
        return builder.createLoadingDialog("", DialogBuilder.LOADING_MODEL);
    }

    @OnClick({R.id.next, R.id.tv_get})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next:
                String tel = etTelNum.getText().toString().trim();
                String identify = etValue.getText().toString().trim();
                myPresenter.checkValues(tel, identify);
                break;
            case R.id.tv_get:
                String telNum = etTelNum.getText().toString().trim();
                if (CommonUtil.checkNum(telNum, 11)) {
                    myPresenter.findCheckValue(telNum);
                }
                break;
            default:
                break;
        }
    }
}
