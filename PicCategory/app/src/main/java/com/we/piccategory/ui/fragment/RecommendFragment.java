package com.we.piccategory.ui.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.we.piccategory.R;
import com.we.piccategory.adapter.BaseRecycleAdapter;
import com.we.piccategory.adapter.RecommendAdapter;
import com.we.piccategory.bean.UserImage;
import com.we.piccategory.builder.DialogBuilder;
import com.we.piccategory.presenter.RecommendPresenter;
import com.we.piccategory.ui.activity.PageActivity;
import com.we.piccategory.ui.base.BaseFragment;
import com.we.piccategory.view.IRecommendView;
import com.we.piccategory.widget.SwipeRecyclerView;

import java.util.List;

import butterknife.InjectView;

/**
 * Created with Android Studio.
 * User: 潘浩
 * School 南华大学
 * Date: 2017/4/1
 * Time: 22:29
 * Description:
 */

public class RecommendFragment extends BaseFragment<IRecommendView, RecommendPresenter> implements IRecommendView {

    @InjectView(R.id.recommend_recycle)
    public SwipeRecyclerView recycle;


    private List<UserImage> userImages;
    private RecommendAdapter adapter;


    private boolean flag = false;

    @Override
    protected int initRes() {
        return R.layout.recommend_fragment;
    }

    @Override
    public RecommendPresenter createPresenter() {
        return new RecommendPresenter();
    }

    @Override
    protected void init() {
        //加载数据
        myPresenter.fetch();
        recycle.setOnLoadListener(onLoadListener);
    }


    @Override
    public void loadData(List<UserImage> list) {
        userImages = list;
        adapter = new RecommendAdapter(list, R.layout.item_recommend);
        recycle.setAdapter(adapter);
        LinearLayoutManager manager = new LinearLayoutManager(this.getActivity());
        recycle.getRecyclerView().setLayoutManager(manager);
        adapter.setOnItemClickListener(new BaseRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                UserImage userImage = userImages.get(position);
                skip(PageActivity.class, userImage.getImageUrl(), userImage.getImageLabel());
            }
        });
    }

    private void skip(Class clazz, String url, String label) {
        Intent intent = new Intent();
        intent.setClass(this.getActivity(), clazz);
        intent.putExtra("url", url);
        intent.putExtra("label", label);
        startActivity(intent);
    }

    @Override
    public void loadMore(List<UserImage> list) {
        if (list != null) {
            adapter.addData(list);
            recycle.stopLoadingMore();
        } else {
            flag = true;
            recycle.onNoMore("--没有更多数据了--");
        }
    }

    @Override
    public void update(List<UserImage> list) {
        if (!list.get(0).equals(userImages.get(0))) {//当第一条数据不相等时
            userImages.clear();
            userImages = list;
            adapter.setData(userImages);
        }
    }

    @Override
    public Dialog loading() {
        DialogBuilder builder = new DialogBuilder();
        builder.attachView(this.getActivity());
        AlertDialog dialog = builder.createLoadDialog();
        dialog.show();
        return dialog;
    }

    @Override
    public void showFail() {

    }


    SwipeRecyclerView.OnLoadListener onLoadListener = new SwipeRecyclerView.OnLoadListener() {
        @Override
        public void onRefresh() {
            myPresenter.fetch();
            recycle.complete();
        }

        @Override
        public void onLoadMore() {
            if (flag == false) {
                myPresenter.loadMore();
            } else {
                recycle.onNoMore("--没有更多数据了--");
            }
        }
    };

}
