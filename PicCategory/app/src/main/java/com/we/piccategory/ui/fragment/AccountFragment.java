package com.we.piccategory.ui.fragment;

import android.widget.TextView;

import com.we.piccategory.R;
import com.we.piccategory.bean.User;
import com.we.piccategory.presenter.AccountPresenter;
import com.we.piccategory.ui.base.BaseFragment;
import com.we.piccategory.view.IAccountView;

import butterknife.InjectView;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/4/25
 * Time: 22:30
 * Description:
 */
public class AccountFragment extends BaseFragment<IAccountView, AccountPresenter> implements IAccountView {

    @InjectView(R.id.tv_name)
    TextView tvName;

    @InjectView(R.id.tv_num)
    TextView tvNum;

    @InjectView(R.id.tv_sex)
    TextView tvSex;

    @InjectView(R.id.tv_job)
    TextView tvJob;

    @InjectView(R.id.tv_pref)
    TextView tvPref;


    @Override
    protected int initRes() {
        return R.layout.fragment_account;
    }

    @Override
    protected void init() {
        myPresenter.loadUser();
    }

    @Override
    public AccountPresenter createPresenter() {
        return new AccountPresenter();
    }


    public void refreshData() {
        myPresenter.loadUser();
    }


    @Override
    public void showUser(User user) {
        tvName.setText(user.getUserName());


        Integer integer = user.getIntegral();
        if (integer != null) {
            tvNum.setText(String.valueOf(user.getIntegral()));//获取积分
        }
        tvSex.setText(user.getSex());
        tvJob.setText(user.getJop());
        tvPref.setText(user.getPreference());
    }


}
