package com.we.piccategory.presenter;

import android.app.Dialog;

import com.we.piccategory.bean.UserImage;
import com.we.piccategory.decorator.UpLoadDecorator;
import com.we.piccategory.mvp.BasePresenter;
import com.we.piccategory.mvp.IModel;
import com.we.piccategory.view.IMainView;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/5/30
 * Time: 19:10
 * Description:
 */
public class UpLoadPresenter extends BasePresenter<IMainView> {

    public void upLoad(final UserImage img) {
        model = new UpLoadDecorator(model, img);
        final IMainView iMainView = viewRef.get();
        final Dialog dialog = iMainView.showLoading();


        model.load(new IModel.OnCompletedListener<String>() {

            @Override
            public void onCompleted(String data) {
                if (iMainView != null) {
                    dialog.dismiss();
                    iMainView.showMsg(data);
                }
            }

            @Override
            public void onCompleted() {

            }

            @Override
            public void onFail(String msg) {
                if (iMainView != null) {
                    dialog.dismiss();
                    iMainView.showMsg(msg);
                }
            }
        });

    }

}
