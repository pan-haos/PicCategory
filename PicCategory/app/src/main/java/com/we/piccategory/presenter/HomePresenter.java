package com.we.piccategory.presenter;

import android.app.Dialog;

import com.we.piccategory.bean.ImageLabel;
import com.we.piccategory.model.HomeDataModel;
import com.we.piccategory.model.HomeModel;
import com.we.piccategory.model.HomeVpModel;
import com.we.piccategory.mvp.BasePresenter;
import com.we.piccategory.mvp.IModel;
import com.we.piccategory.view.IHomeView;

import java.util.List;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/5/16
 * Time: 16:28
 * Description:
 */
public class HomePresenter extends BasePresenter<IHomeView> {
    private int count = 1;

    /**
     * 绑定网络数据
     */
    public void fetch() {

        model = new HomeDataModel(count);
        final IHomeView iHomeView = viewRef.get();
        //弹出加载dialog
        final Dialog dialog = iHomeView.showLoading();
        dialog.show();
        model.load(new IModel.OnCompletedListener<List<ImageLabel>>() {

            @Override
            public void onCompleted(List<ImageLabel> data) {
                count++;
                dialog.dismiss();
                iHomeView.showView(data);
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


    /**
     * 上拉刷新
     */
    public void fetchReplace() {
        model = new HomeDataModel(count);
        final IHomeView iHomeView = viewRef.get();
        //弹出加载dialog
        final Dialog dialog = iHomeView.showLoading();

        model.load(new IModel.OnCompletedListener<List<ImageLabel>>() {


            @Override
            public void onCompleted(List<ImageLabel> data) {
                count++;
                iHomeView.replaceData(data);
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
     * 下拉加载
     */
    public void fetchMore() {
        model = new HomeDataModel(count);
        final IHomeView iHomeView = viewRef.get();
        //弹出加载dialog
        final Dialog dialog = iHomeView.showLoading();
        model.load(new IModel.OnCompletedListener<List<ImageLabel>>() {

            @Override
            public void onCompleted(List<ImageLabel> data) {
                count++;
                iHomeView.addValues(data);
            }

            @Override
            public void onCompleted() {
            }

            @Override
            public void onFail(String msg) {
            }
        });
    }


    public void bindVpData() {
        model = new HomeVpModel();
        final IHomeView iHomeView = viewRef.get();
        model.load(new IModel.OnCompletedListener<List<Integer>>() {

            @Override
            public void onCompleted(List<Integer> data) {
                iHomeView.showPage(data);
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
     * 绑定本地数据
     */
    public void bindViewModel() {
        model = new HomeModel();
        final IHomeView iHomeView = viewRef.get();
        model.load(new IModel.OnCompletedListener<List<?>>() {

            @Override
            public void onCompleted(List<?> data) {
                if (count == 0) {
                    iHomeView.showView((List<ImageLabel>) data);
                } else {

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
