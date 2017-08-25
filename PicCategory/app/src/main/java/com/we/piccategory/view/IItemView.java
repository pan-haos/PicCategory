package com.we.piccategory.view;

import android.app.Dialog;

import com.we.piccategory.bean.ImageLabel;

import java.util.List;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/5/17
 * Time: 22:46
 * Description:
 */
public interface IItemView {
    void showView(List<ImageLabel> list);

    Dialog showLoading();

    void showMore(List<ImageLabel> list);

    void showReplace(List<ImageLabel> list);

    void skip(Class clazz,String url,String label);

}
