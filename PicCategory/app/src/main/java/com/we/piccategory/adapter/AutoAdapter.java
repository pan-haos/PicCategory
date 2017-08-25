package com.we.piccategory.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.we.piccategory.ui.base.BaseApp;

import java.util.List;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/5/20
 * Time: 13:30
 * Description:
 */
public abstract class AutoAdapter<T> extends BaseAdapter {

    protected List<T> list;

    public AutoAdapter(List<T> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position) == null ? null : list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListViewHolder holder = ListViewHolder.getHolder(BaseApp.context, convertView, parent, getLayoutId(), position);
        convert(holder, (T) getItem(position));
        return holder.getConvertView();
    }

    protected abstract void convert(ListViewHolder holder, T item);

    public abstract int getLayoutId();


}
