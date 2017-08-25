package com.we.piccategory.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.we.piccategory.R;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/5/22
 * Time: 11:25
 * Description:
 */
public class RecycleViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mViews;//存储view的数组

    private View mConvertView;//item 的视图view

    private Context context;

    public RecycleViewHolder(Context context, View itemView, ViewGroup parent) {
        super(itemView);
        this.context = context;
        this.mConvertView = itemView;
        this.mViews = new SparseArray<>();
    }


    public static RecycleViewHolder get(Context context, ViewGroup parent, int layoutId) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent,
                false);
        RecycleViewHolder holder = new RecycleViewHolder(context, itemView, parent);
        return holder;
    }

    /**
     * 通过viewId获取控件
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }


    public RecycleViewHolder setText(int viewId, String text) {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    public RecycleViewHolder setImageRes(int viewId, int resId) {
        ImageView iv = getView(viewId);
        Glide.with(context).load(resId).centerCrop().fitCenter().
                into(iv);
        return this;
    }

    public RecycleViewHolder setImgUrl(int viewId, String url) {
        ImageView iv = getView(viewId);
        Glide.with(context).load(url).
                diskCacheStrategy(DiskCacheStrategy.ALL).placeholder(R.mipmap.ic_launcher).
                crossFade().into(iv);
        return this;
    }

    public RecycleViewHolder setImgUrlWi(int viewId, String url) {
        ImageView iv = getView(viewId);
        Glide.with(context).load(url).
                into(iv);

        return this;
    }


    public RecycleViewHolder buildHolder(int viewId) {
        ImageView iv = getView(viewId);
        int width = ((Activity) iv.getContext()).getWindowManager().getDefaultDisplay().getWidth();
        ViewGroup.LayoutParams params = iv.getLayoutParams();
        //设置图片的相对于屏幕的宽高比
        params.width = width / 2 + 10;
        params.height = (int) (400 + Math.random() * 400);
        iv.setLayoutParams(params);
        return this;
    }

}
