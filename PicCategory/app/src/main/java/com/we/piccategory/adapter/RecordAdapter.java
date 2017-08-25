package com.we.piccategory.adapter;

import com.we.piccategory.R;
import com.we.piccategory.bean.Record;

import java.util.List;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/5/27
 * Time: 16:05
 * Description:
 */
public class RecordAdapter extends AutoAdapter<Record> {

    public RecordAdapter(List<Record> list) {
        super(list);
    }

    public void setData(List<Record> list) {
        this.list = list;
        this.notifyDataSetChanged();
    }

    @Override
    protected void convert(ListViewHolder holder, Record record) {
        holder.setText(R.id.item_sys_page, record.getImageLabel());
        holder.setText(R.id.item_my_page, record.getUserLabel());
        holder.setText(R.id.item_time, record.getUpdateTime());
        holder.setImgUrl(R.id.item_iv_record, record.getImgUrl());
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_lv_record;
    }
}
