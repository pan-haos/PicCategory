package com.we.piccategory.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.we.piccategory.R;
import com.we.piccategory.presenter.SearchPresenter;
import com.we.piccategory.view.ISearchView;
import com.we.piccategory.widget.FlowLayout;

import java.util.Arrays;
import java.util.List;

import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/5/19
 * Time: 12:31
 * Description:
 */
public class SearchActivity extends BaseSearchActivity<ISearchView, SearchPresenter> implements ISearchView {

    @InjectView(R.id.search_top)
    public LinearLayout topLayout;


//    private EditText etSearch;
//
//    private TextView tvSearch;

    @InjectView(R.id.flow_layout)
    public FlowLayout mFlowLayout;

    private String mNames[] = {
            "蓝天", "白云", "一群在海边玩的人", "么大海", "动物", "动植物", "天使", "美算了开始看女", "其它",
    };

    @Override
    protected int initRes() {
        return R.layout.activity_search;
    }

    @Override
    public SearchPresenter createPresenter() {
        return new SearchPresenter();
    }


    @Override
    protected void initData() {

        //获取用户查询的历史记录，这部分可以从网络获取也可以从本地获取
        myPresenter.loadHistory();

        mFlowLayout.setData(Arrays.asList(mNames));
        mFlowLayout.setClickListener(clickListener);
    }


    @Override
    public void showMsg(String msg) {

    }

    @Override
    public Dialog showLoading() {
        return null;
    }


    @Override
    public void showView(List<String> list) {
    }


    @OnClick(R.id.clean)
    public void onClick(View v) {
        if (mFlowLayout.getChildCount() > 0) {
            mFlowLayout.removeAllViews();
        }
    }

    @Override
    protected void handleClick(View v) {
        String keyWord = etSearch.getText().toString().trim();
        if (keyWord != null && !"".equals(keyWord)) {
            //非空判断成立则会跳入下一个界面进行搜索
            Intent intent = new Intent();
            intent.setClass(this, SearchShowActivity.class);
            intent.putExtra("keyWord", keyWord);
            startActivity(intent);
        }
    }

    FlowLayout.ClickListener clickListener = new FlowLayout.ClickListener() {
        @Override
        public void click(View v) {
            if (mFlowLayout != null) {
                if (etSearch != null) {
                    if (mFlowLayout.getChildCount() > 0) {
                        TextView tv = (TextView) v;
                        String text = tv.getText().toString().trim();
                        etSearch.setText(text);
                        etSearch.setSelection(text.length());
                    }
                }
            }
        }
    };


}
