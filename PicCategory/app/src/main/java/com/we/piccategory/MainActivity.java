package com.we.piccategory;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.we.piccategory.bean.UserImage;
import com.we.piccategory.builder.DialogBuilder;
import com.we.piccategory.presenter.UpLoadPresenter;
import com.we.piccategory.ui.MTab;
import com.we.piccategory.ui.base.BaseActivity;
import com.we.piccategory.util.Constant;
import com.we.piccategory.util.ImageUtil;
import com.we.piccategory.util.LoginManger;
import com.we.piccategory.view.IMainView;
import com.we.piccategory.widget.AddPopWindow;
import com.we.piccategory.widget.MyFragmentTabHost;

import java.io.IOException;

import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity<IMainView, UpLoadPresenter> implements IMainView, TabHost.OnTabChangeListener, View.OnTouchListener {
    @InjectView(android.R.id.tabhost)
    public MyFragmentTabHost mTabHost;

    private boolean isCheck = false;

    @InjectView(R.id.quick_option_iv)
    public ImageView ivOption;

    private AddPopWindow addPopWindow;

    private Bitmap mBmp;


    @Override
    protected int initRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        showComponent();
        addPopWindow = new AddPopWindow(this, click);
        addPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                ivOption.setImageResource(R.drawable.btn_quickoption_selector);
                addPopWindow.setImg();
                isCheck = false;
                //回收bitmap避免oom
                if (mBmp != null) {
                    mBmp.recycle();
                }
            }
        });
    }

    @Override
    public UpLoadPresenter createPresenter() {
        return new UpLoadPresenter();
    }

    public native String stringFromJNI();

    @OnClick(R.id.quick_option_iv)
    public void onClick(View view) {
        if (isCheck == false) {
            ivOption.setImageResource(R.mipmap.check_circle);
            addPopWindow.showPopupWindow(ivOption);
            isCheck = true;
        } else {
            ivOption.setImageResource(R.drawable.btn_quickoption_selector);
            isCheck = false;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //摄像头
        if (requestCode == Constant.MAIN_OPEN_CAMERA) {
            if (data == null) {
                return;
            }
            Bundle extras = data.getExtras();
            if (extras != null) {
                Bitmap bmp = extras.getParcelable("data");
                Uri uri = ImageUtil.saveBitmap(bmp);
                mBmp = getBmp(uri);
                addPopWindow.setBmP(mBmp);
            }
            //图库
        } else if (requestCode == Constant.MAIN_OPEN_LIB) {
            if (data == null) {
                return;
            }
            Uri uri;
            uri = data.getData();
            Uri fileUri = ImageUtil.convertUri(uri);
            mBmp = getBmp(fileUri);
            addPopWindow.setBmP(mBmp);
        }
    }

    public void showComponent() {
        //初始化底部的tabhost
        mTabHost.setup(this, getSupportFragmentManager(), R.id.flContent);
        if (Build.VERSION.SDK_INT > 10) {
            mTabHost.getTabWidget().setShowDividers(0);
        }
        showTabs();
        mTabHost.setCurrentTab(0);
        mTabHost.setOnTabChangedListener(this);
    }


    private void showTabs() {
        MTab[] tabs = MTab.values();
        final int size = tabs.length;
        for (int i = 0; i < size; i++) {
            // 找到每一个枚举的Fragment对象
            MTab MTab = tabs[i];

            // 1. 创建一个新的选项卡
            TabHost.TabSpec tab = mTabHost.newTabSpec(getString(MTab.getRes()));
            // ------------------------------------------------- 自定义选项卡 ↓
            View indicator = LayoutInflater.from(getApplicationContext())
                    .inflate(R.layout.tab_indicator, null);
            //整块布局设置水波纹效果
            if (Build.VERSION.SDK_INT >= 21) {
                indicator.setBackgroundResource(R.drawable.ripple_bg);
            }
            TextView title = (TextView) indicator.findViewById(R.id.tab_title);
            Drawable drawable = this.getResources().getDrawable(
                    MTab.getImg());
            title.setCompoundDrawablesWithIntrinsicBounds(null, drawable, null,
                    null);
            if (i == 2) {
                indicator.setVisibility(View.INVISIBLE);
                mTabHost.setNoTabChangedTag(getString(MTab.getRes()));
            }
            title.setText(getString(MTab.getRes()));
            tab.setIndicator(indicator);

            Bundle bundle = new Bundle();
            bundle.putString("key",
                    "content: " + getString(MTab.getRes()));
            // 2. 把新的选项卡添加到TabHost中
            mTabHost.addTab(tab, MTab.getClazz(), bundle);

            mTabHost.getTabWidget().getChildAt(i).setOnTouchListener(this);
        }
        for (int i = 0; i < tabs.length; i++) {
            tabs[i] = null;
        }
    }


    @Override
    public void onTabChanged(String tabId) {
        switch (tabId) {
            case "":
                break;
            case "1":
                break;
        }
    }

    @Override
    protected void onDestroy() {
        mTabHost = null;
        super.onDestroy();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }


    private Bitmap getBmp(Uri uri) {
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    AddPopWindow.OnClick click = new AddPopWindow.OnClick() {
        @Override
        public void onClick(View view) {
//            //上传图片
            if (mBmp != null) {
                String codingBmp = ImageUtil.base64CodingBmp(mBmp);
                UserImage userImage = new UserImage();
                userImage.setUserId(LoginManger.getUserId());
                userImage.setImageUrl(codingBmp);
                myPresenter.upLoad(userImage);
            } else {
                Toast.makeText(MainActivity.this, "不可以传空图片", Toast.LENGTH_SHORT).show();
            }
        }
    };


    @Override
    public void showMsg(String msg) {
        builder.createLoadingDialog(msg, DialogBuilder.COMMON_MODEL).show();
    }

    @Override
    public Dialog showLoading() {
        AlertDialog dialog = builder.createLoadDialog();
        dialog.show();
        return dialog;
    }

}
