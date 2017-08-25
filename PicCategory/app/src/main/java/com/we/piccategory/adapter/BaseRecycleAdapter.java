package com.we.piccategory.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/5/19
 * Time: 19:59
 * Description:
 */
public abstract class BaseRecycleAdapter<T> extends RecyclerView.Adapter<RecycleViewHolder> {
    protected List<T> list;
    protected int layoutId;
    protected OnItemClickListener mItemListener;


    public BaseRecycleAdapter(List<T> list, int layoutId) {
        this.list = list;
        this.layoutId = layoutId;
    }

    public void setData(List<T> list) {
        if (this.list != null && list != null) {
            this.list.clear();
        }
        this.list = list;
        notifyDataSetChanged();
    }


    public void addData(List<T> list) {
        if (this.list != null && list != null) {
            this.list.addAll(list);
            notifyDataSetChanged();
            System.out.println("addall以后" + this.list.size());
        }
    }


    public void setOnItemClickListener(OnItemClickListener mItemListener) {
        this.mItemListener = mItemListener;
    }

    @Override
    public RecycleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecycleViewHolder holder = RecycleViewHolder.get(parent.getContext(), parent, layoutId);
        return holder;
    }

    @Override
    public void onBindViewHolder(final RecycleViewHolder holder, int position) {
        convert(holder, list.get(position));

        if (mItemListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int layoutPosition = holder.getLayoutPosition();
                    mItemListener.onItemClick(holder.itemView, layoutPosition);
                }
            });
        }
    }

    protected abstract void convert(RecycleViewHolder holder, T t);

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public interface OnItemClickListener {

        void onItemClick(View view, int position);
    }


}
