package com.we.piccategory.ui.base;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.we.piccategory.R;
import com.we.piccategory.mvp.BasePresenter;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/4/21
 * Time: 19:55
 * Description:带有自定义顶部标题栏的activity
 * 通常带有回退按钮，很多activity都有这样顶部的布局，但是只是组件内容不相同
 */
public abstract class BaseBackActivity<V, T extends BasePresenter<V>> extends BaseActivity<V, T> {

    @Override
    protected void onResume() {
        super.onResume();
        initBackView();
    }

    public void initBackView() {
        //给左侧回退键加监听
        findViewById(R.id.back_iv_left).
                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
        //回调设置的text
        TextView textView = (TextView) findViewById(R.id.back_tv);
        textView.setText(getTitleStr());
        //设置右边的ImgeView
        ImageView ivRight = (ImageView) findViewById(R.id.back_iv_right);

        int rightRes = getRightRes();
        if (rightRes != 0) {
            ivRight.setBackgroundResource(rightRes);
        }
        ivRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleClick(v);
            }
        });
    }

    /**
     * 必须实现的--抽象
     *
     * @return title实际的值
     */
    public abstract String getTitleStr();


    public void handleClick(View v) {
        Log.i("ph", "click--");
    }

    /**
     * 可有可无的，在父类里面实现
     *
     * @return 位图对象
     */
    protected int getRightRes() {
        return 0;
    }


}
