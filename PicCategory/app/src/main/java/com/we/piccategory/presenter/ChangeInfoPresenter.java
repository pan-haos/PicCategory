package com.we.piccategory.presenter;

import android.app.Dialog;

import com.we.piccategory.bean.User;
import com.we.piccategory.decorator.ChangeUserModel;
import com.we.piccategory.model.UserModel;
import com.we.piccategory.mvp.BasePresenter;
import com.we.piccategory.mvp.IModel;
import com.we.piccategory.view.IInfoView;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/5/11
 * Time: 16:09
 * Description:
 */
public class ChangeInfoPresenter extends BasePresenter<IInfoView> {

    /**
     * 加载用户信息
     */
    public void loadUser() {
        model = new UserModel();
        final IInfoView iInfoView = viewRef.get();
        model.load(new IModel.OnCompletedListener<User>() {
            @Override
            public void onCompleted(User data) {
                if (iInfoView != null) {
                    iInfoView.showUser(data);
                }
            }

            @Override
            public void onCompleted() {
            }

            @Override
            public void onFail(String msg) {
            }
        });
    }


    public void changeInfo(User user) {
        model = new ChangeUserModel(model, user);
        final IInfoView iInfoView = viewRef.get();
        final Dialog dialog = iInfoView.showLoading();
        dialog.show();
        model.load(new IModel.OnCompletedListener() {
            @Override
            public void onCompleted(Object data) {
            }

            @Override
            public void onCompleted() {

                if (iInfoView != null) {
                    dialog.dismiss();
                    //退出该界面
                    iInfoView.skip(null);
                }
            }

            @Override
            public void onFail(String msg) {
                if (iInfoView != null) {
                    dialog.dismiss();
                    iInfoView.showMsgDialog("用户名已存在");
                }
            }
        });


    }


}
