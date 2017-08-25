package com.we.piccategory.ui.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;

import com.we.piccategory.R;
import com.we.piccategory.ui.activity.AccountActivity;
import com.we.piccategory.ui.activity.LoginActivity;
import com.we.piccategory.ui.activity.RecordActivity;
import com.we.piccategory.ui.base.BaseFragment;
import com.we.piccategory.util.LoginManger;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/4/25
 * Time: 22:32
 * Description:
 */
public class SettingFragment extends BaseFragment<IFragmentView, FragmentPresenter> {

    @InjectView(R.id.account_set_ly)
    public RelativeLayout accountLayout;

    @InjectView(R.id.msg_push_ly)
    public RelativeLayout pushLayout;

    @Override
    protected int initRes() {
        return R.layout.setting_layout;
    }

    @Override
    protected void init() {

    }

    @Override
    public FragmentPresenter createPresenter() {
        return new FragmentPresenter();
    }

    @OnClick({R.id.btn_quit, R.id.account_set_ly, R.id.operation_record_ly, R.id.msg_push_ly})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_quit:
                //移除登录状态
                LoginManger.removeLoginState();
                //移除用户基本信息
                LoginManger.removeUserInfo();
                skip(LoginActivity.class);
                this.getActivity().finish();
                break;
            case R.id.account_set_ly:
                skip(AccountActivity.class);
                break;
            case R.id.operation_record_ly:
                skip(RecordActivity.class);
                break;
            case R.id.msg_push_ly:

                break;
            default:

                break;
        }
    }

    private void skip(Class clazz) {
        Intent intent = new Intent();
        intent.setClass(this.getActivity(), clazz);
        startActivity(intent);
    }


}
