package com.we.piccategory.adapter;

import com.we.piccategory.R;
import com.we.piccategory.bean.Record;
import com.we.piccategory.util.CommonUtil;

import java.util.List;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/5/27
 * Time: 19:22
 * Description:
 */
public class RecordRecycleAdapter extends BaseRecycleAdapter<Record> {
    public RecordRecycleAdapter(List<Record> list, int layoutId) {
        super(list, layoutId);
    }

    @Override
    protected void convert(RecycleViewHolder holder, Record record) {

        String msg = CommonUtil.handleMsg(record.getImageLabel());

        String userLabel = record.getUserLabel();

        holder.setText(R.id.item_sys_page, msg);
        holder.setText(R.id.item_my_page, userLabel);
        holder.setText(R.id.item_time, record.getUpdateTime());
        holder.setImgUrl(R.id.item_iv_record, record.getImgUrl());

    }
}
