package com.we.piccategory.presenter;

import com.we.piccategory.decorator.CheckPwdDecorator;
import com.we.piccategory.decorator.CheckValuesDecorator;
import com.we.piccategory.mvp.BasePresenter;
import com.we.piccategory.mvp.IModel;
import com.we.piccategory.ui.activity.NewPwdActivity;
import com.we.piccategory.view.ILoginView;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/4/20
 * Time: 12:33
 * Description:
 */
public class ForgetPresenter extends BasePresenter<ILoginView> {


    public void findCheckValue(String phoneNum) {
        model = new CheckPwdDecorator(model, phoneNum);
        final ILoginView iLoginView = viewRef.get();
        model.load(new IModel.OnCompletedListener() {

            @Override
            public void onCompleted(Object data) {
                if (iLoginView != null) {
                    iLoginView.skip(NewPwdActivity.class);
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


    public void checkValues(String telNum, String identifyNum) {
        //发送获取验证码的指令
        model = new CheckValuesDecorator(model, telNum, identifyNum);
        final ILoginView iLoginView = viewRef.get();
        model.load(new IModel.OnCompletedListener<String>() {

            @Override
            public void onCompleted(String data) {
                iLoginView.skip(NewPwdActivity.class);
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


}
