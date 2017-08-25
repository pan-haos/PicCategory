package com.we.piccategory.presenter;

import android.app.Dialog;

import com.we.piccategory.bean.Label;
import com.we.piccategory.decorator.MarkTagDecorator;
import com.we.piccategory.decorator.MyTagDecorator;
import com.we.piccategory.decorator.UpdateTagDecorator;
import com.we.piccategory.mvp.BasePresenter;
import com.we.piccategory.mvp.IModel;
import com.we.piccategory.util.Constant;
import com.we.piccategory.view.IPageView;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/5/10
 * Time: 19:05
 * Description:打标签页的业务类
 */
public class PagePresenter extends BasePresenter<IPageView> {


    /**
     * 根据图片路径加载标签内容
     *
     * @param url 图片路径
     */
    public void loadTag(String url) {
        model = new MyTagDecorator(model, url);
        final IPageView iPageView = viewRef.get();
        model.load(new IModel.OnCompletedListener<Label>() {

            @Override
            public void onCompleted(Label data) {
                if (iPageView != null) {
                    iPageView.showData(data);

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


    /**
     * 打标签的业务
     *
     * @param label 标签对象
     */
    public void markTag(Label label) {
        model = new MarkTagDecorator(model, label);
        final IPageView iPageView = viewRef.get();
        final Dialog dialog = iPageView.showLoading();
        model.load(new IModel.OnCompletedListener<String>() {
            @Override
            public void onCompleted(String data) {
                Constant.isTag = false;
                if (iPageView != null) {

                    dialog.dismiss();
                    iPageView.ensureCommit();
                    iPageView.showMsgDialog(data);

//                    iPageView.skip();
                }
            }

            @Override
            public void onCompleted() {
            }

            @Override
            public void onFail(String msg) {
                if (iPageView != null) {
                    dialog.dismiss();
                }
            }
        });
    }


    public void updateTag(Label label) {
        model = new UpdateTagDecorator(model, label);
        final IPageView iPageView = viewRef.get();

        model.load(new IModel.OnCompletedListener<String>() {

            @Override
            public void onCompleted(String data) {
                if (iPageView != null) {
                    iPageView.showMsgDialog(data);
                }
            }

            @Override
            public void onCompleted() {
            }

            @Override
            public void onFail(String msg) {
                if (iPageView != null) {
                    iPageView.showMsgDialog(msg);
                }
            }
        });


    }

}
