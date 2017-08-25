package com.we.piccategory.adapter;

import com.we.piccategory.R;
import com.we.piccategory.bean.ImageLabel;
import com.we.piccategory.util.CommonUtil;

import java.util.List;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/5/22
 * Time: 11:49
 * Description:
 */
public class HomeRecycleAdapter extends BaseRecycleAdapter<ImageLabel> {

    public HomeRecycleAdapter(List<ImageLabel> list, int layoutId) {
        super(list, layoutId);
    }

    @Override
    protected void convert(RecycleViewHolder holder, ImageLabel label) {

        String labelStr = label.getLabel();

        String msg = CommonUtil.handleMsg(labelStr);
        holder.setText(R.id.home_tv, msg);
//        holder.setImageRes(R.id.home_iv, pic.getResId());
        holder.setImgUrl(R.id.home_iv, label.getImageUrl());
        holder.buildHolder(R.id.home_iv);

    }
}
