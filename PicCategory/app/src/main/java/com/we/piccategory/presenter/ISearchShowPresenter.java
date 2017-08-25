package com.we.piccategory.presenter;

import com.we.piccategory.bean.ImageLabel;
import com.we.piccategory.decorator.SearchDecorator;
import com.we.piccategory.decorator.SearchMoreDecorator;
import com.we.piccategory.mvp.BasePresenter;
import com.we.piccategory.mvp.IModel;
import com.we.piccategory.view.ISearchShowView;

import java.util.List;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/5/27
 * Time: 4:40
 * Description:
 */
public class ISearchShowPresenter extends BasePresenter<ISearchShowView> {

    private int count = 2;


    /**
     * 根据关键字搜索
     *
     * @param keyWord 关键字
     */
    public void search(String keyWord) {
        model = new SearchDecorator(model, keyWord);
        final ISearchShowView iSearchShowView = viewRef.get();
        model.load(new IModel.OnCompletedListener<List<ImageLabel>>() {
            @Override
            public void onCompleted(List<ImageLabel> labels) {
                if (iSearchShowView != null) {
                    iSearchShowView.showData(labels);
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


    public void searchMore(String keyWord) {
        model = new SearchMoreDecorator(model, keyWord, count);
        final ISearchShowView iSearchShowView = viewRef.get();
        model.load(new IModel.OnCompletedListener<List<ImageLabel>>() {

            @Override
            public void onCompleted(List<ImageLabel> data) {
                if (iSearchShowView != null) {
                    iSearchShowView.addData(data);
                    count++;
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
