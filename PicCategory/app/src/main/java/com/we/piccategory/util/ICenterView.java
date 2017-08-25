package com.we.piccategory.util;

import android.app.Dialog;

import com.we.piccategory.bean.User;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/5/23
 * Time: 12:34
 * Description:
 */
public interface ICenterView {

    void setInfo(User user);

    Dialog showLoading();

    Dialog showMsg(String msg);

    void chgImg();

}
