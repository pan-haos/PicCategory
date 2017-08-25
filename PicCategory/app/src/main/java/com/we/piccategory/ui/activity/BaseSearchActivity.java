package com.we.piccategory.ui.activity;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.we.piccategory.R;
import com.we.piccategory.mvp.BasePresenter;
import com.we.piccategory.ui.base.BaseActivity;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/5/27
 * Time: 3:56
 * Description:
 */
public abstract class BaseSearchActivity<V, T extends BasePresenter<V>> extends BaseActivity<V, T> {


    protected EditText etSearch;

    @Override
    protected void onResume() {
        super.onResume();
        initBackView();

    }

    private void initBackView() {
        findViewById(R.id.search_top_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseSearchActivity.this.finish();
            }
        });

        etSearch = (EditText) findViewById(R.id.search_top_et);
        TextView tvSearch = (TextView) findViewById(R.id.search_top_tv);
        tvSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseSearchActivity.this.handleClick(v);
            }
        });

    }


//
//    protected void editText(String text) {
//        if (this.etSearch != null) {
//            etSearch.setText(text);
//            etSearch.setSelection(text.length());
//        }
//    }


    protected void handleClick(View v) {
        Log.i("ph", "click--");
    }


}
