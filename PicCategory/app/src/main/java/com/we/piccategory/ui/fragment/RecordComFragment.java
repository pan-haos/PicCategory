package com.we.piccategory.ui.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.we.piccategory.R;
import com.we.piccategory.adapter.HomeRecycleAdapter;
import com.we.piccategory.adapter.RecordRecycleAdapter;
import com.we.piccategory.bean.Record;
import com.we.piccategory.presenter.RecordPresenter;
import com.we.piccategory.ui.activity.PageActivity;
import com.we.piccategory.ui.base.BaseApp;
import com.we.piccategory.ui.base.BaseFragment;
import com.we.piccategory.view.IRecordView;
import com.we.piccategory.widget.SwipeRecyclerView;

import java.util.List;

import butterknife.InjectView;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/5/27
 * Time: 15:14
 * Description:
 */
public class RecordComFragment extends BaseFragment<IRecordView, RecordPresenter> implements IRecordView {

    @InjectView(R.id.record_recycle)
    public SwipeRecyclerView recycle;

    private List<Record> records;
    private boolean loadAll = false;

    private RecordRecycleAdapter adapter;

    private boolean flag = false;


    @Override
    protected int initRes() {
        return R.layout.fragment_record;
    }

    @Override
    protected void init() {
        recycle.setOnLoadListener(onLoadListener);
        initAdapter();
    }


    public void load() {
        myPresenter.loadAll("3");
    }


    private void initAdapter() {
        //设置适配器
        adapter = new RecordRecycleAdapter(records, R.layout.item_lv_record);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        recycle.getRecyclerView().setLayoutManager(manager);
        recycle.setAdapter(adapter);
        recycle.setRefreshEnable(false);
//        //设置item之间的间隔
//        SpacesItemDecoration decoration = new SpacesItemDecoration(2);
//        recycle.getRecyclerView().addItemDecoration(decoration);

        adapter.setOnItemClickListener(new HomeRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Record record = records.get(position);
                if (record != null) {
                    Intent intent = new Intent();
                    intent.setClass(RecordComFragment.this.getActivity(), PageActivity.class);
                    intent.putExtra("url", record.getImgUrl());
                    intent.putExtra("label", record.getImageLabel());
                    startActivity(intent);
                }
            }
        });

    }

    @Override
    public RecordPresenter createPresenter() {
        return new RecordPresenter();
    }


    /**
     * 刚进入界面加载到数据回调
     *
     * @param records
     */
    public void loadData(List<Record> records) {
        if (records != null) {
            this.records = records;
            adapter.setData(records);
        }
    }

    /**
     * 上拉刷新是加载数据回调
     *
     * @param list
     */
    @Override
    public void addData(List<Record> list) {
        if (list != null) {
            adapter.addData(list);
            recycle.stopLoadingMore();
        } else {
            flag = true;
            recycle.onNoMore("-- 没有更多数据了 --");
//            recyclerView.stopLoadingMore();//增加了
//            recycleAdapter.notifyDataSetChanged();//增加了

        }
    }

    @Override
    public void showMsg() {
        Toast.makeText(BaseApp.context, "没拿到数据", Toast.LENGTH_SHORT).show();
    }


    SwipeRecyclerView.OnLoadListener onLoadListener = new SwipeRecyclerView.OnLoadListener() {
        @Override
        public void onRefresh() {

            Toast.makeText(BaseApp.context, "没有更多数据了", Toast.LENGTH_SHORT).show();
            recycle.complete();
        }


        @Override
        public void onLoadMore() {
            if (flag == false) {
                myPresenter.loadMore("3");
            } else {
                recycle.onNoMore("-- 没有更多数据了 --");
            }
        }
    };


}
