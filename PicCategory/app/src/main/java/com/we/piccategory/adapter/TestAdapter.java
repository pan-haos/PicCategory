package com.we.piccategory.adapter;

import com.we.piccategory.R;
import com.we.piccategory.bean.Pic;

import java.util.List;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/5/22
 * Time: 14:42
 * Description:
 */
public class TestAdapter extends BaseRecycleAdapter<Pic> {

    public TestAdapter(List<Pic> list, int layoutId) {
        super(list, layoutId);
    }

    public void setList(List<Pic> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }


    @Override
    protected void convert(RecycleViewHolder holder, Pic pic) {
        holder.setText(R.id.home_tv, pic.getName());
        holder.setImageRes(R.id.home_iv, pic.getResId());
        holder.buildHolder(R.id.home_iv);
    }
}
