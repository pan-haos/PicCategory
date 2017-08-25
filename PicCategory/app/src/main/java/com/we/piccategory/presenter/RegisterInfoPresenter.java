package com.we.piccategory.presenter;

import android.app.Dialog;

import com.we.piccategory.MainActivity;
import com.we.piccategory.bean.User;
import com.we.piccategory.decorator.RegisterInfoDecorator;
import com.we.piccategory.mvp.BasePresenter;
import com.we.piccategory.mvp.IModel;
import com.we.piccategory.view.IInfoView;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/4/20
 * Time: 17:47
 * Description:注册信息
 */
public class RegisterInfoPresenter extends BasePresenter<IInfoView> {

    public void registerInfo(User user) {
        model = new RegisterInfoDecorator(model, user);
        final IInfoView iInfoView = viewRef.get();

        //展示dialog
        final Dialog dialog = iInfoView.showLoading();
        dialog.show();

        model.load(new IModel.OnCompletedListener<String>() {
            @Override
            public void onCompleted(String data) {
                dialog.dismiss();
//                Dialog successDialog = iInfoView.showSuccessDialog(data);
//                successDialog.dismiss();
                iInfoView.skip(MainActivity.class);
            }

            @Override
            public void onCompleted() {
            }

            @Override
            public void onFail(String msg) {
                dialog.dismiss();
                Dialog dialog1 = iInfoView.showMsgDialog(msg);
                dialog1.show();
            }
        });


    }

}
