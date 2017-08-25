package com.we.piccategory.presenter;

import android.app.Dialog;
import android.util.Log;

import com.we.piccategory.bean.ImageLabel;
import com.we.piccategory.decorator.CategoryDecorator;
import com.we.piccategory.mvp.BasePresenter;
import com.we.piccategory.mvp.IModel;
import com.we.piccategory.view.IItemView;

import java.util.List;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/5/17
 * Time: 22:50
 * Description:
 */
public class CateItemPresenter extends BasePresenter<IItemView> {

    private int count = 1;


    public void loadData(String type) {
        model = new CategoryDecorator(model, type, count);
        final IItemView iItemView = viewRef.get();
        final Dialog dialog = iItemView.showLoading();
        dialog.show();
        Log.i("ph", "loadData: 展示加载中");

        model.load(new IModel.OnCompletedListener<List<ImageLabel>>() {
            @Override
            public void onCompleted(List<ImageLabel> data) {
                if (iItemView != null) {
                    count++;
                    dialog.dismiss();
                    iItemView.showView(data);
                }
            }

            @Override
            public void onCompleted() {
            }

            @Override
            public void onFail(String msg) {
                dialog.dismiss();
            }
        });
    }


    public void fetchReplace(String type) {
        final IItemView iItemView = viewRef.get();
        model = new CategoryDecorator(model, type, count);
        model.load(new IModel.OnCompletedListener<List<ImageLabel>>() {
            @Override
            public void onCompleted(List<ImageLabel> data) {
                if (iItemView != null) {
                    count++;
                    iItemView.showReplace(data);
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


    public void fetchMore(String type) {
        model = new CategoryDecorator(model, type, count);
        final IItemView iItemView = viewRef.get();
        model.load(new IModel.OnCompletedListener<List<ImageLabel>>() {
            @Override
            public void onCompleted(List<ImageLabel> data) {
                if (iItemView != null) {
                    count++;
                    iItemView.showMore(data);
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


}
