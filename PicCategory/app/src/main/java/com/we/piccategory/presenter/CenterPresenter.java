package com.we.piccategory.presenter;

import android.app.Dialog;

import com.we.piccategory.bean.User;
import com.we.piccategory.decorator.UpImgDecorator;
import com.we.piccategory.model.UserModel;
import com.we.piccategory.mvp.BasePresenter;
import com.we.piccategory.mvp.IModel;
import com.we.piccategory.util.ICenterView;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/5/23
 * Time: 12:29
 * Description:
 */
public class CenterPresenter extends BasePresenter<ICenterView> {

    public void loadImg() {
        model = new UserModel();
        final ICenterView iCenterView = viewRef.get();
        model.load(new IModel.OnCompletedListener<User>() {
            @Override
            public void onCompleted(User data) {
                iCenterView.setInfo(data);
            }

            @Override
            public void onCompleted() {
            }

            @Override
            public void onFail(String msg) {
            }
        });
    }


    public void updateImg(String imgBmp) {
        model = new UpImgDecorator(model, imgBmp);
        final ICenterView iCenterView = viewRef.get();
        final Dialog dialog = iCenterView.showLoading();

        model.load(new IModel.OnCompletedListener<String>() {
            @Override
            public void onCompleted(String data) {
                if (iCenterView != null) {
                    dialog.dismiss();
                    iCenterView.chgImg();
                    iCenterView.showMsg(data);
                }
            }

            @Override
            public void onCompleted() {
            }

            @Override
            public void onFail(String msg) {
                if (iCenterView != null) {
                    dialog.dismiss();
                    iCenterView.showMsg(msg);
                }
            }
        });

    }

}
