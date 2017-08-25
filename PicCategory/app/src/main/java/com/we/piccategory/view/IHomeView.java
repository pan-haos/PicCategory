package com.we.piccategory.view;

import android.app.Dialog;

import com.we.piccategory.bean.ImageLabel;

import java.util.List;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/5/18
 * Time: 16:02
 * Description:
 */
public interface IHomeView {

    void showView(List<ImageLabel> list);

    void showPage(List<?> list);

    void replaceData(List<ImageLabel> list);

    void addValues(List<ImageLabel> list);

    Dialog showLoading();

    void skip(Class clazz, String url,String label);


}
