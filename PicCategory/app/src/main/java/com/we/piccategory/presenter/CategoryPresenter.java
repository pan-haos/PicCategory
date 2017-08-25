package com.we.piccategory.presenter;

import com.we.piccategory.bean.Category;
import com.we.piccategory.model.CategoryModel;
import com.we.piccategory.mvp.BasePresenter;
import com.we.piccategory.mvp.IModel;
import com.we.piccategory.view.ICategoryView;

import java.util.List;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/5/9
 * Time: 8:52
 * Description:
 */
public class CategoryPresenter extends BasePresenter<ICategoryView> {


    public void bindViewModel() {
        model = new CategoryModel();
        final ICategoryView iCategoryView = viewRef.get();
        model.load(new IModel.OnCompletedListener<List<?>>() {
            @Override
            public void onCompleted(List<?> data) {
                if (iCategoryView != null) {
                    iCategoryView.showView((List<Category>) data);
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
