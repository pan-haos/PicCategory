package com.we.piccategory.adapter;

import com.we.piccategory.bean.ImageLabel;

import java.util.List;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/5/27
 * Time: 13:21
 * Description:
 */
public class SearchShowAdapter extends BaseRecycleAdapter<List<ImageLabel>> {

    public SearchShowAdapter(List<List<ImageLabel>> list, int layoutId) {
        super(list, layoutId);
    }

    @Override
    protected void convert(RecycleViewHolder holder, List<ImageLabel> labels) {

    }
}
