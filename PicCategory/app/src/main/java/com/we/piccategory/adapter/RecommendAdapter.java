package com.we.piccategory.adapter;

import com.we.piccategory.R;
import com.we.piccategory.bean.UserImage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/5/30
 * Time: 14:53
 * Description:
 */
public class RecommendAdapter extends BaseRecycleAdapter<UserImage> {

    public RecommendAdapter(List<UserImage> list, int layoutId) {
        super(list, layoutId);
    }

    @Override
    protected void convert(RecycleViewHolder holder, UserImage userImage) {
        Date createTime = userImage.getCreateTime();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(createTime);

        holder.setImgUrl(R.id.user_img, userImage.getImageUrl());
        holder.setImgUrl(R.id.recommend_head_img, userImage.getHeadImage());
        holder.setText(R.id.recommend_name, userImage.getUserName());
        holder.setText(R.id.item_time, time);

    }
}
