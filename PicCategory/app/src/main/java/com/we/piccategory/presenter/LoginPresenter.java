package com.we.piccategory.presenter;

import android.app.Dialog;
import android.util.Log;

import com.we.piccategory.MainActivity;
import com.we.piccategory.bean.Token;
import com.we.piccategory.decorator.CheckModelDecorator;
import com.we.piccategory.decorator.CheckValuesDecorator;
import com.we.piccategory.decorator.LoginModelDecorator;
import com.we.piccategory.decorator.TelChangeDecorator;
import com.we.piccategory.mvp.BasePresenter;
import com.we.piccategory.mvp.IModel;
import com.we.piccategory.ui.activity.RegisterInfoActivity;
import com.we.piccategory.util.LoginManger;
import com.we.piccategory.view.ILoginView;


public class LoginPresenter extends BasePresenter<ILoginView> {

    /**
     * 登录操作
     *
     * @param userName 用户名
     * @param password 密码
     */
    public void login(final String userName, String password) {
        //获取装饰类
        model = new LoginModelDecorator(model, userName, password);
        final ILoginView iLoginView = viewRef.get();

        final Dialog dialog = iLoginView.showLoading();
        dialog.show();

        model.load(new IModel.OnCompletedListener<Token>() {
            @Override
            public void onCompleted(Token token) {
                dialog.dismiss();
                //登录成功以后将token值写入本地
                String tokenValue = token.getToken();
                Integer userId = token.getUserId();
                LoginManger.setLoginState(tokenValue, userId, true);

                //跳转界面
                iLoginView.skip(MainActivity.class);

            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onFail(String msg) {
                dialog.dismiss();
                iLoginView.showMsgDialog(msg);
            }
        });

    }

    /**
     * 校验手机号是否正确
     * 请求
     *
     * @param telNum 手机号
     */
    public void checkPhone(String telNum) {
        model = new CheckModelDecorator(model, telNum);
        final ILoginView iLoginView = viewRef.get();

        model.load(new IModel.OnCompletedListener() {

            @Override
            public void onCompleted(Object data) {
            }

            @Override
            public void onCompleted() {
                Log.i("ph", "onCompleted: 获取验证码成功");
            }

            @Override
            public void onFail(String msg) {
                iLoginView.showMsgDialog(msg);
            }
        });
    }


    /**
     * 校验手机号和验证码是否正确
     * 这里的请求是发送给mob第三方
     *
     * @param telNum
     * @param identifyNum
     */
    public void checkValues(String telNum, String identifyNum) {
        //发送获取验证码的指令
        model = new CheckValuesDecorator(model, telNum, identifyNum);
        final ILoginView iLoginView = viewRef.get();
        model.load(new IModel.OnCompletedListener<String>() {

            @Override
            public void onCompleted(String data) {
                iLoginView.skip(RegisterInfoActivity.class);
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onFail(String msg) {
                iLoginView.showMsgDialog(msg);
            }
        });

    }


    public void changeNum(final String telNum, String identifyNum) {
        //发送获取验证码的指令
        model = new CheckValuesDecorator(model, telNum, identifyNum);
        final ILoginView iLoginView = viewRef.get();
        model.load(new IModel.OnCompletedListener<String>() {

            @Override
            public void onCompleted(String data) {
                //当第三方校验通过时
                changeTel(telNum, iLoginView);

            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onFail(String msg) {
                iLoginView.showMsgDialog(msg);
            }
        });
    }


    public void changeTel(final String telNum, final ILoginView iLoginView) {
        model = new TelChangeDecorator(model, telNum);
        model.load(new IModel.OnCompletedListener<String>() {
            @Override
            public void onCompleted(String text) {
                LoginManger.setTelNum(telNum);
                if (iLoginView != null) {
                    iLoginView.showMsgDialog(text);
                }
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onFail(String msg) {

                if (iLoginView != null) {
                    iLoginView.showMsgDialog(msg);
                }
            }
        });
    }


}
