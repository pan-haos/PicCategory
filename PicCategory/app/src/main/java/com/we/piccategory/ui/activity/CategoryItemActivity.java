package com.we.piccategory.ui.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.we.piccategory.R;
import com.we.piccategory.adapter.BaseRecycleAdapter;
import com.we.piccategory.adapter.HomeRecycleAdapter;
import com.we.piccategory.bean.ImageLabel;
import com.we.piccategory.builder.DialogBuilder;
import com.we.piccategory.presenter.CateItemPresenter;
import com.we.piccategory.ui.base.BaseApp;
import com.we.piccategory.ui.base.BaseBackActivity;
import com.we.piccategory.view.IItemView;
import com.we.piccategory.widget.SpacesItemDecoration;
import com.we.piccategory.widget.SwipeRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/5/17
 * Time: 22:45
 * Description:
 */
public class CategoryItemActivity extends BaseBackActivity<IItemView, CateItemPresenter> implements IItemView {

    private String categoryName;//分类的名称

    @InjectView(R.id.item_swipe)
    public SwipeRecyclerView recyclerView;

    private BaseRecycleAdapter recyAdapter;


    private List<ImageLabel> mLabels = new ArrayList<>();

    private boolean flag = false;


    @Override
    protected int initRes() {
        return R.layout.activity_category_item;
    }

    @Override
    protected void initData() {
        categoryName = getIntent().getStringExtra("categoryName");
        myPresenter.loadData(categoryName);
        initAdapter();
        recyclerView.setOnLoadListener(onLoadListener);
        recyclerView.setLoadMoreEnable(true);
    }

    private void initAdapter() {
        recyclerView.getSwipeRefreshLayout()
                .setColorSchemeColors(getResources().getColor(R.color.primary));
        recyAdapter = new HomeRecycleAdapter(mLabels, R.layout.home_layout);
        recyclerView.setAdapter(recyAdapter);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        recyclerView.getRecyclerView().setLayoutManager(manager);
        //设置item之间的间隔
        SpacesItemDecoration decoration = new SpacesItemDecoration(2);
        recyclerView.getRecyclerView().addItemDecoration(decoration);
        recyAdapter.setOnItemClickListener(new HomeRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ImageLabel imageLabel = mLabels.get(position);
                if (imageLabel != null) {
                    skip(PageActivity.class, imageLabel.getImageUrl(), imageLabel.getLabel());
                }

            }
        });

    }

    @Override
    public CateItemPresenter createPresenter() {
        return new CateItemPresenter();
    }

    @Override
    public String getTitleStr() {
        return categoryName == null ? null : categoryName;
    }


    @Override
    public void showView(List<ImageLabel> list) {
        if (list == null) {
            Toast.makeText(this, "没有数据哦", Toast.LENGTH_SHORT).show();
            return;
        }
        mLabels = list;
        recyAdapter.setData(mLabels);
    }

    @Override
    public Dialog showLoading() {
        AlertDialog loadingDialog = builder.createLoadingDialog("", DialogBuilder.LOADING_MODEL);
        return loadingDialog;
    }

    @Override
    public void showMore(List<ImageLabel> list) {
        if (list != null && list.size() != 0) {
            recyAdapter.addData(list);
            recyclerView.stopLoadingMore();
        } else {
            flag = true;
            recyclerView.onNoMore("-- 没有更多数据了 --");
        }
    }

    @Override
    public void showReplace(List<ImageLabel> list) {
        if (list != null) {
            mLabels.clear();
            mLabels = list;
            recyAdapter.setData(mLabels);
        } else {
            flag = true;
            Toast.makeText(BaseApp.context, "没有更多数据了", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void skip(Class clazz, String url, String label) {
        Intent intent = new Intent();
        intent.setClass(this, clazz);
        intent.putExtra("url", url);
        intent.putExtra("label", label);
        startActivity(intent);

    }

    SwipeRecyclerView.OnLoadListener onLoadListener = new SwipeRecyclerView.OnLoadListener() {
        @Override
        public void onRefresh() {
            if (flag = false) {
                myPresenter.fetchReplace(categoryName);
            } else {
                Toast.makeText(BaseApp.context, "没有更多数据了", Toast.LENGTH_SHORT).show();
            }

            recyclerView.complete();
        }

        @Override
        public void onLoadMore() {
            Log.i("ph", "进入loadmore");
            if (flag == false) {
                myPresenter.fetchMore(categoryName);
            } else {
                recyclerView.onNoMore("-- 没有更多数据了 --");
            }
        }
    };
}


//Glide加载原型图片代码

       /* Glide.with(context).load(url).asBitmap().centerCrop().into(new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imageView.setImageDrawable(circularBitmapDrawable);
            }
        });*/