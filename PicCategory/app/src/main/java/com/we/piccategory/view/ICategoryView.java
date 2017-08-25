package com.we.piccategory.view;

import android.app.Dialog;

import java.util.List;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/5/9
 * Time: 8:49
 * Description:只需要绑定adapter展示的界面
 */
public interface ICategoryView {

    void showView(List<?> list);

    Dialog showLoading();

    void skip(Class clazz, String categoryName);

}
