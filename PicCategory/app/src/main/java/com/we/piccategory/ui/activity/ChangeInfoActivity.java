package com.we.piccategory.ui.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.we.piccategory.R;
import com.we.piccategory.bean.User;
import com.we.piccategory.builder.DialogBuilder;
import com.we.piccategory.presenter.ChangeInfoPresenter;
import com.we.piccategory.ui.base.BaseBackActivity;
import com.we.piccategory.util.Constant;
import com.we.piccategory.view.IInfoView;
import com.we.piccategory.widget.CheckSpinner;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/5/11
 * Time: 16:02
 * Description:
 */
public class ChangeInfoActivity extends BaseBackActivity<IInfoView, ChangeInfoPresenter> implements IInfoView {

//    @InjectView(R.id.chang_head_img)
//    public CircleImageView civ;

    @InjectView(R.id.chang_et_set_name)
    public EditText etName;

    @InjectView(R.id.change_sex_group)
    public RadioGroup sexGroup;

    @InjectView(R.id.chang_job_spinner)
    public Spinner jobSpinner;

    @InjectView(R.id.chang_pref_spinner)
    public CheckSpinner prefSpinner;

    public static TextView tvShow;


    private ArrayAdapter<CharSequence> adapter;

    @Override
    protected int initRes() {
        return R.layout.activity_info_change;
    }

    @Override
    protected void initData() {
        Bundle bundle = getIntent().getParcelableExtra("BUNDLER");
        User user = bundle.getParcelable("USER");
        tvShow = (TextView) findViewById(R.id.chang_tv_show);
        adapter = ArrayAdapter.createFromResource(this, R.array.spinner_arr, android.R.layout.simple_spinner_item);
        jobSpinner.setAdapter(adapter);
        showUser(user);
    }

    @Override
    public ChangeInfoPresenter createPresenter() {
        return new ChangeInfoPresenter();
    }


    @Override
    public void showUser(User user) {
        if (user != null) {
            etName.setText(user.getUserName());
            String sex = user.getSex();
            if ("男".equals(sex)) {
                sexGroup.check(R.id.change_rb_man);
            } else if ("女".equals(sex)) {
                sexGroup.check(R.id.chang_rb_women);
            }
            String job = user.getJop();
            int position = adapter.getPosition(job);
            jobSpinner.setSelection(position);
            String pref = user.getPreference();
            tvShow.setText(pref);
        }

    }

    @Override
    public Dialog showMsgDialog(String msg) {
        return null;
    }

    @Override
    public Dialog showLoading() {
        return builder.createLoadingDialog("", DialogBuilder.LOADING_MODEL);
    }

    @Override
    public void skip(Class clazz) {
        setResult(Constant.SKIP_TO_CHANG);
        this.finish();
    }

    @Override
    public String getTitleStr() {
        return "修改个人资料";
    }

    @OnClick(R.id.chang_info)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.chang_info:
                changInfo();
                break;
            default:
                break;
        }
    }

    private void changInfo() {
        String name = etName.getText().toString().trim();
        int checkedRadioButtonId = sexGroup.getCheckedRadioButtonId();
        String sex = "";
        if (checkedRadioButtonId == R.id.change_rb_man) {
            sex = "男";
        } else if (checkedRadioButtonId == R.id.chang_rb_women) {
            sex = "女";
        }
        String job = (String) jobSpinner.getSelectedItem();
        String pref = tvShow.getText().toString().trim();
        User user = new User(name, sex, job, pref);
        user.setHeadImageUrl(null);
        //更改用户信息
        myPresenter.changeInfo(user);
    }
}
