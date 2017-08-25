package com.we.piccategory.presenter;

import android.app.Dialog;

import com.we.piccategory.decorator.NewPwdDecorator;
import com.we.piccategory.decorator.UpPwdDecorator;
import com.we.piccategory.mvp.BasePresenter;
import com.we.piccategory.mvp.IModel;
import com.we.piccategory.view.IPwdView;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/4/20
 * Time: 17:25
 * Description:
 */
public class NewPwdPresenter extends BasePresenter<IPwdView> {

    public void updatePwd(String telNum, String newPwd) {
        model = new NewPwdDecorator(model, telNum, newPwd);
        final IPwdView iPwdView = viewRef.get();
        final Dialog dialog = iPwdView.showLoading();
        dialog.show();
        model.load(new IModel.OnCompletedListener<String>() {

            @Override
            public void onCompleted(String data) {
                if (iPwdView != null) {
                    dialog.dismiss();
                    iPwdView.showSuccess(data);
                }
            }

            @Override
            public void onCompleted() {
            }

            @Override
            public void onFail(String msg) {
                if (iPwdView != null) {
                    dialog.dismiss();
                    iPwdView.showFail(msg);
                }
            }
        });
    }


    public void updatePwd(String newPwd) {
        model = new UpPwdDecorator(model, newPwd);
        final IPwdView iPwdView = viewRef.get();
        model.load(new IModel.OnCompletedListener<String>() {

            @Override
            public void onCompleted(String data) {
                if (iPwdView != null) {
                    iPwdView.showSuccess(data);
                }
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onFail(String msg) {
                if (iPwdView != null) {
                    iPwdView.showFail(msg);
                }
            }
        });

    }

}
