package com.we.piccategory.adapter;

import com.we.piccategory.R;
import com.we.piccategory.bean.Category;

import java.util.List;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/5/8
 * Time: 21:47
 * Description:
 */
public class MyGridAdapter extends AutoAdapter<Category> {

    public MyGridAdapter(List<Category> list) {
        super(list);
    }



    @Override
    protected void convert(ListViewHolder holder, Category item) {
        holder.setText(R.id.tv_name_item_category, item.getTextChina());
        holder.setText(R.id.tv_eng_item_category, item.getTextEnglish());
        holder.setImageRes(R.id.iv_item_category, item.getId());
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_categroy;
    }

}
