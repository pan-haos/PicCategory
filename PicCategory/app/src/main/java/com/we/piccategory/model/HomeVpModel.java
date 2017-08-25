package com.we.piccategory.model;

import com.we.piccategory.R;
import com.we.piccategory.mvp.IModel;

import java.util.Arrays;
import java.util.List;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/5/18
 * Time: 16:23
 * Description:
 */
public class HomeVpModel implements IModel {
    @Override
    public void load(OnCompletedListener listener) {
        Integer[] ids = {R.mipmap.image5, R.mipmap.image4, R.mipmap.image1};
        List<Integer> list = Arrays.asList(ids);
        listener.onCompleted(list);
    }
}
