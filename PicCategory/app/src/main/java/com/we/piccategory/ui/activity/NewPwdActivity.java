package com.we.piccategory.ui.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.we.piccategory.R;
import com.we.piccategory.builder.DialogBuilder;
import com.we.piccategory.presenter.NewPwdPresenter;
import com.we.piccategory.ui.base.BaseBackActivity;
import com.we.piccategory.util.Constant;
import com.we.piccategory.view.IPwdView;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/4/20
 * Time: 13:57
 * Description: 设置新密码的界面
 */
public class NewPwdActivity extends BaseBackActivity<IPwdView, NewPwdPresenter> implements IPwdView {
    @InjectView(R.id.next2)
    public TextView next;

    @InjectView(R.id.et_new_pwd)
    public EditText etNewPwd;

    private String telNum;

    private String model;


    @Override
    protected int initRes() {
        return R.layout.activity_newpwd;
    }

    @Override
    protected void initData() {
        telNum = getIntent().getStringExtra("telNum");
        model = getIntent().getStringExtra("model");
    }

    @Override
    public String getTitleStr() {
        return "更改密码";
    }

    @Override
    public NewPwdPresenter createPresenter() {
        return new NewPwdPresenter();
    }

    @OnClick({R.id.next2})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.next2:

//                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
//                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                String newPwd = etNewPwd.getText().toString().trim();
                if (telNum != null && newPwd != null) {
                    if (Constant.ACCOUNT_MODEL.equals(model)) {
                        myPresenter.updatePwd(newPwd);
                    } else if (Constant.FORGET_MODEL.equals(model)) {
                        myPresenter.updatePwd(telNum, newPwd);
                    }
                }

                break;
            default:
                break;
        }
    }


    @Override
    public Dialog showLoading() {
        return builder.createLoadDialog();
    }

    @Override
    public Dialog showFail(String msg) {
        AlertDialog dialog = builder.createLoadingDialog(msg, DialogBuilder.COMMON_MODEL);
        dialog.show();
        return dialog;
    }

    @Override
    public Dialog showSuccess(String msg) {
        Intent intent = new Intent();
        intent.putExtra("msg", msg);
        setResult(Constant.SKIP_TO_NEW_PWD, intent);
        this.finish();
        return null;
    }

    @Override
    public void skip(Class clazz) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
