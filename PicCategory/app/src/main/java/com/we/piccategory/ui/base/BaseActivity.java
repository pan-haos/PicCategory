package com.we.piccategory.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.we.piccategory.builder.DialogBuilder;
import com.we.piccategory.mvp.BasePresenter;

import butterknife.ButterKnife;



public abstract class BaseActivity<V, T extends BasePresenter<V>> extends AppCompatActivity {

    protected T myPresenter;

    public DialogBuilder builder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        //调用子类的方法获取persenter
        myPresenter = createPresenter();
        myPresenter.attachView((V) this);

        setContentView(initRes());
        ButterKnife.inject(this);

        builder = new DialogBuilder();
        builder.attachView(this);


        initData();
    }


    protected abstract int initRes();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myPresenter.detachView();
        builder.detachView();
    }

    /**
     * 创建presenter对象
     *
     * @return 子类中的实例presenter对象
     */
    public abstract T createPresenter();

    protected void initData() {
    }
}
