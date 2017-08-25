package com.we.piccategory.ui.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.we.piccategory.R;
import com.we.piccategory.adapter.HomeRecycleAdapter;
import com.we.piccategory.bean.ImageLabel;
import com.we.piccategory.presenter.ISearchShowPresenter;
import com.we.piccategory.ui.base.BaseActivity;
import com.we.piccategory.view.ISearchShowView;
import com.we.piccategory.widget.SpacesItemDecoration;
import com.we.piccategory.widget.SwipeRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/5/26
 * Time: 21:24
 * Description:
 */
public class SearchShowActivity extends BaseActivity<ISearchShowView, ISearchShowPresenter> implements ISearchShowView {

    @InjectView(R.id.show_top_et)
    public TextView topEdit;

    @InjectView(R.id.show_top_tv)
    public TextView topSearch;

    @InjectView(R.id.show_top_back)
    public ImageView ivBack;

    @InjectView(R.id.show_recycle)
    public SwipeRecyclerView showRecycle;

    private List<ImageLabel> labels = new ArrayList<>();

    private String keyWord;
    private HomeRecycleAdapter adapter;
    private boolean flag = false;


    @Override
    protected int initRes() {
        return R.layout.activity_search_finish;
    }

    @Override
    protected void initData() {
        keyWord = getIntent().getStringExtra("keyWord");
        if (keyWord != null && !"".equals(keyWord)) {
            myPresenter.search(keyWord);
            initAdapter();
            showRecycle.setOnLoadListener(onLoadListener);
        }
    }

    private void initAdapter() {
        adapter = new HomeRecycleAdapter(labels, R.layout.home_layout);
        showRecycle.setAdapter(adapter);

        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        showRecycle.getRecyclerView().setLayoutManager(manager);
        //设置item之间的间隔
        SpacesItemDecoration decoration = new SpacesItemDecoration(2);
        showRecycle.getRecyclerView().addItemDecoration(decoration);

        adapter.setOnItemClickListener(new HomeRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.i("ph", "onItemClick: " + position);
                ImageLabel imageLabel = labels.get(position);


                skip(PageActivity.class, imageLabel.getImageUrl(), imageLabel.getLabel());
                Log.i("ph", imageLabel.getLabel());
            }
        });

    }

    @Override
    public ISearchShowPresenter createPresenter() {
        return new ISearchShowPresenter();
    }


    @Override
    public Dialog showLoading() {
        AlertDialog dialog = builder.createLoadDialog();
        dialog.show();
        return dialog;
    }

    @Override
    public void showData(List<ImageLabel> labels) {
        this.labels = labels;
        adapter.setData(labels);
    }

    @Override
    public void addData(List<ImageLabel> labels) {
        if (labels != null) {
            adapter.addData(labels);
            showRecycle.stopLoadingMore();
        } else {
            flag = true;
            showRecycle.onNoMore("-- 没有更多数据了 --");
        }
    }

    private void skip(Class clazz, String url, String label) {
        Intent intent = new Intent();
        intent.setClass(this, clazz);
        if (clazz.equals(PageActivity.class)) {
            intent.putExtra("url", url);
            intent.putExtra("label", label);
        }
        startActivity(intent);
    }


    @OnClick({R.id.show_top_back, R.id.show_top_tv, R.id.show_top_et})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.show_top_back:
                this.finish();
                break;
            case R.id.show_top_tv:

                break;
            case R.id.show_top_et:

                break;

            default:
                break;
        }
    }

    SwipeRecyclerView.OnLoadListener onLoadListener = new SwipeRecyclerView.OnLoadListener() {
        @Override
        public void onRefresh() {
            myPresenter.search(keyWord);
            showRecycle.complete();
        }


        @Override
        public void onLoadMore() {
            if (flag == false) {
                if (keyWord != null && !"".equals(keyWord) && labels != null)
                    myPresenter.searchMore(keyWord);
            } else {
                showRecycle.onNoMore("-- 没有更多数据了 --");
            }
        }
    };


}
