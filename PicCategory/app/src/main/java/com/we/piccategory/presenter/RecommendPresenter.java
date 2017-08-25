package com.we.piccategory.presenter;

import com.we.piccategory.bean.UserImage;
import com.we.piccategory.decorator.RecommendDecorator;
import com.we.piccategory.model.RecommendModel;
import com.we.piccategory.mvp.BasePresenter;
import com.we.piccategory.mvp.IModel;
import com.we.piccategory.view.IRecommendView;

import java.util.List;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/5/30
 * Time: 15:03
 * Description:
 */
public class RecommendPresenter extends BasePresenter<IRecommendView> {

    private int count = 2;

    public void fetch() {
        model = new RecommendModel();//这个永远只加载第一页
        final IRecommendView view = viewRef.get();
        model.load(new IModel.OnCompletedListener<List<UserImage>>() {

            @Override
            public void onCompleted(List<UserImage> images) {
                if (view != null) {
                    if (images != null && images.size() > 0) {
                        //非空校验通过才会调用界面的方法
                        view.loadData(images);
                    }
                }
            }

            @Override
            public void onCompleted() {
            }

            @Override
            public void onFail(String msg) {
                if (view != null) {
                    view.showFail();
                }
            }
        });
    }


    /**
     * 加载更多
     */
    public void loadMore() {
        model = new RecommendDecorator(model, count);
        final IRecommendView iRecommendView = viewRef.get();
        model.load(new IModel.OnCompletedListener<List<UserImage>>() {

            @Override
            public void onCompleted(List<UserImage> data) {
                if (iRecommendView != null) {
                    count++;
                    iRecommendView.loadMore(data);
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
