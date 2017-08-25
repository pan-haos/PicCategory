package com.we.piccategory.view;

import android.app.Dialog;

import com.we.piccategory.bean.UserImage;

import java.util.List;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/5/30
 * Time: 14:57
 * Description:
 */
public interface IRecommendView {

    void loadData(List<UserImage> list);

    void loadMore(List<UserImage> list);

    void update(List<UserImage> list);

    Dialog loading();

    void showFail();


}
