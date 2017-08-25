package com.we.piccategory.view;

import android.app.Dialog;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/5/1
 * Time: 0:39
 * Description:
 */
public interface ILoginView {

    void skip(Class clazz);

    void showMsgDialog(String msg);

    Dialog showLoading();

}
