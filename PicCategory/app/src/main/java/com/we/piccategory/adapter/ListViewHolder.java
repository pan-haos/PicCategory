package com.we.piccategory.adapter;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import static com.we.piccategory.ui.base.BaseApp.context;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/5/20
 * Time: 13:35
 * Description:
 */
public class ListViewHolder {

    private SparseArray<View> myViews;//view的缓存数组，性能比hashmap要好

    private View mContextView;//缓存的父类view


    private ListViewHolder(Context context, ViewGroup parent, int layoutId, int position) {
        this.myViews = new SparseArray<>();
        this.mContextView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        mContextView.setTag(this);
    }


    public static ListViewHolder getHolder(Context context, View convertView, ViewGroup parent, int layoutId, int position) {
        if (convertView == null) {
            return new ListViewHolder(context, parent, layoutId, position);
        }
        return (ListViewHolder) convertView.getTag();
    }

    /**
     * 通过控件的id来获取view
     *
     * @param viewId 控件的id
     * @param <T>    view的类型
     * @return view
     */
    public <T extends View> T getView(int viewId) {
        //先检测缓存view里是否存在该view，不存在则find出来添加进去
        View view = myViews.get(viewId);
        if (view == null) {
            view = mContextView.findViewById(viewId);
            myViews.put(viewId, view);
        }
        return (T) view;
    }


    public ListViewHolder setText(int viewId, String text) {
        TextView view = getView(viewId);
        view.setText(text);
        return this;
    }

    public ListViewHolder setImageRes(int viewId, int resId) {
        ImageView iv = getView(viewId);
        Glide.with(context).load(resId).fitCenter().centerCrop().into(iv);
        return this;
    }

    public ListViewHolder setImgUrl(int viewId, String url) {
        ImageView iv = getView(viewId);
        Glide.with(context).load(url).fitCenter().centerCrop().into(iv);
        return this;
    }


    public View getConvertView() {
        return mContextView;
    }


}
