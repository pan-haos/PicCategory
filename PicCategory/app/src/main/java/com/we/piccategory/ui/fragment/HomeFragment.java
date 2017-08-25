package com.we.piccategory.ui.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.we.piccategory.R;
import com.we.piccategory.adapter.BaseRecycleAdapter;
import com.we.piccategory.adapter.HomeRecycleAdapter;
import com.we.piccategory.bean.ImageLabel;
import com.we.piccategory.builder.DialogBuilder;
import com.we.piccategory.presenter.HomePresenter;
import com.we.piccategory.ui.activity.PageActivity;
import com.we.piccategory.ui.activity.SearchActivity;
import com.we.piccategory.ui.base.BaseApp;
import com.we.piccategory.ui.base.BaseFragment;
import com.we.piccategory.view.IHomeView;
import com.we.piccategory.widget.SpacesItemDecoration;
import com.we.piccategory.widget.SwipeRecyclerView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/5/18
 * Time: 14:12
 * Description:
 */
public class HomeFragment extends BaseFragment<IHomeView, HomePresenter> implements IHomeView {


    private boolean flag = false;


    @InjectView(R.id.rv_main)
    public SwipeRecyclerView recyclerView;

    @InjectView(R.id.slider)
    public SliderLayout mSliderLayout;

    @InjectView(R.id.top_layout)
    public LinearLayout topLayout;
    //
    @InjectView(R.id.top_tv)
    public TextView topTv;

    @InjectView(R.id.ctl_main)
    public CollapsingToolbarLayout coll;

    private List<ImageLabel> mLabels = new ArrayList<>();
    private BaseRecycleAdapter recycleAdapter;

    @Override
    protected int initRes() {
        return R.layout.fragment_test;
    }

    @Override
    protected void init() {
        myPresenter.bindVpData();
        initAdapter();
        //从网络获取数据
        myPresenter.fetch();
        recyclerView.setOnLoadListener(onLoadListener);
    }

    private void initAdapter() {
        recycleAdapter = new HomeRecycleAdapter(mLabels, R.layout.home_layout);
        recyclerView.setAdapter(recycleAdapter);

        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2,
                StaggeredGridLayoutManager.VERTICAL);
        recyclerView.getRecyclerView().setLayoutManager(manager);
        //设置item之间的间隔
        SpacesItemDecoration decoration = new SpacesItemDecoration(2);
        recyclerView.getRecyclerView().addItemDecoration(decoration);

        recycleAdapter.setOnItemClickListener(new HomeRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.i("ph", "onItemClick: " + position);
                ImageLabel imageLabel = mLabels.get(position);
                skip(PageActivity.class, imageLabel.getImageUrl(), imageLabel.getLabel());
                Log.i("ph", imageLabel.getLabel());
            }
        });


    }

    @Override
    public HomePresenter createPresenter() {
        return new HomePresenter();
    }

    @Override
    public void showView(final List<ImageLabel> list) {
        mLabels = list;
        recycleAdapter.setData(mLabels);
    }

    @Override
    public void showPage(List<?> list) {
        List<Integer> ints = (List<Integer>) list;
        WeakReference<Activity> ref = new WeakReference<Activity>(getActivity());
        for (Integer anInt : ints) {
            BaseSliderView sliderView = new DefaultSliderView(ref.get());
            sliderView.image(anInt).setScaleType(BaseSliderView.ScaleType.Fit);
            mSliderLayout.addSlider(sliderView);
        }
        mSliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mSliderLayout.setCustomAnimation(new DescriptionAnimation());
        mSliderLayout.setPresetTransformer(SliderLayout.Transformer.ZoomOutSlide);
        mSliderLayout.setDuration(3000);
    }

    @Override
    public void replaceData(List<ImageLabel> list) {
        if (list != null) {
            mLabels.clear();
            mLabels = list;
            recycleAdapter.setData(mLabels);
        } else {
            flag = true;
            Toast.makeText(BaseApp.context, "没有更多数据了", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void addValues(List<ImageLabel> list) {
        if (list != null) {
            recycleAdapter.addData(list);
            recyclerView.stopLoadingMore();
        } else {
            flag = true;
            recyclerView.onNoMore("-- 没有更多数据了 --");

        }
    }

    @Override
    public Dialog showLoading() {
        DialogBuilder builder = new DialogBuilder();
        builder.attachView(this.getActivity());
        return builder.createLoadingDialog("", DialogBuilder.LOADING_MODEL);
    }

    @Override
    public void skip(Class clazz, String url, String label) {
        Intent intent = new Intent();
        intent.setClass(this.getActivity(), clazz);
        if (clazz.equals(PageActivity.class)) {
            intent.putExtra("url", url);
            intent.putExtra("label", label);
        }
        startActivity(intent);
    }

    @Override
    public void onStop() {
        super.onStop();
        mSliderLayout.stopAutoCycle();
    }

    @Override
    public void onResume() {
        super.onResume();
        mSliderLayout.startAutoCycle();
    }

    @OnClick(R.id.top_tv)
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.top_tv:
                this.skip(SearchActivity.class, "", "");
                break;
            default:
                break;
        }
    }

    SwipeRecyclerView.OnLoadListener onLoadListener = new SwipeRecyclerView.OnLoadListener() {
        @Override
        public void onRefresh() {
            if (flag == false) {
                myPresenter.fetchReplace();
            } else {
                Toast.makeText(BaseApp.context, "没有更多数据了", Toast.LENGTH_SHORT).show();
            }
            recyclerView.complete();
        }


        @Override
        public void onLoadMore() {
            if (flag == false) {
                myPresenter.fetchMore();
            } else {
                recyclerView.onNoMore("-- 没有更多数据了 --");
            }
        }
    };

}
