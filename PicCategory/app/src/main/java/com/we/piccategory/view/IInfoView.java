package com.we.piccategory.view;

import android.app.Dialog;

import com.we.piccategory.bean.User;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/5/11
 * Time: 16:11
 * Description:
 */
public interface IInfoView {

    void showUser(User user);


    Dialog showMsgDialog(String msg);

    Dialog showLoading();

    void skip(Class clazz);

}
