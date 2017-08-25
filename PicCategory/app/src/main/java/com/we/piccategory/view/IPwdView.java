package com.we.piccategory.view;

import android.app.Dialog;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/5/16
 * Time: 14:00
 * Description:
 */
public interface IPwdView {

    Dialog showLoading();

    Dialog showFail(String msg);

    Dialog showSuccess(String msg);


    void skip(Class clazz);
}
