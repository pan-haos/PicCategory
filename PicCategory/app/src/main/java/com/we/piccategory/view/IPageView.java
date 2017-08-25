package com.we.piccategory.view;

import android.app.Dialog;

import com.we.piccategory.bean.Label;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/5/10
 * Time: 19:21
 * Description:界面层，对界面改变时供外部调用
 */
public interface IPageView {


    void ensureCommit();

    void skip();

    void showMsgDialog(String msg);


    void showData(Label label);

    Dialog showLoading();


}
