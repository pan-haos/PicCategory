package com.we.piccategory.view;

import android.app.Dialog;

import java.util.List;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/5/19
 * Time: 12:32
 * Description:
 */
public interface ISearchView {
    void showMsg(String msg);

    Dialog showLoading();

    void showView(List<String> list);
}
