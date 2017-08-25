package com.we.piccategory.ui.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.we.piccategory.R;
import com.we.piccategory.adapter.MyGridAdapter;
import com.we.piccategory.bean.Category;
import com.we.piccategory.presenter.CategoryPresenter;
import com.we.piccategory.ui.activity.CategoryItemActivity;
import com.we.piccategory.ui.base.BaseFragment;
import com.we.piccategory.view.ICategoryView;

import java.util.List;

import butterknife.InjectView;
import butterknife.OnItemClick;

/**
 * Created with Android Studio.
 * User: 潘浩
 * School 南华大学
 * Date: 2017/4/1
 * Time: 22:49
 * Description:
 */

public class CategoryFragment extends BaseFragment<ICategoryView, CategoryPresenter> implements ICategoryView {

    @InjectView(R.id.category_grid)
    public GridView gridView;

    private List<Category> list;

    @Override
    protected int initRes() {
        return R.layout.layout_categroy;
    }

    @Override
    public CategoryPresenter createPresenter() {
        return new CategoryPresenter();
    }


    @Override
    protected void init() {
        myPresenter.bindViewModel();
    }


    @OnItemClick(R.id.category_grid)
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String categoryName = list.get(position).getTextChina();
        skip(CategoryItemActivity.class, categoryName);
    }


    @Override
    public void showView(List<?> list) {
        this.list = (List<Category>) list;
        MyGridAdapter myGridAdapter
                = new MyGridAdapter((List<Category>) list);
        gridView.setAdapter(myGridAdapter);
    }

    @Override
    public Dialog showLoading() {
        return null;
    }

    @Override
    public void skip(Class clazz, String categoryName) {
        Intent intent = new Intent();
        intent.putExtra("categoryName", categoryName);
        intent.setClass(this.getActivity(), clazz);
        startActivity(intent);
    }


}
