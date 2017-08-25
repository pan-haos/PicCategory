package com.we.piccategory.view;

import android.app.Dialog;

import com.we.piccategory.bean.ImageLabel;

import java.util.List;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/5/27
 * Time: 4:39
 * Description:
 */
public interface ISearchShowView {
    Dialog showLoading();

    void showData(List<ImageLabel> labels);

    void addData(List<ImageLabel> labels);

}
