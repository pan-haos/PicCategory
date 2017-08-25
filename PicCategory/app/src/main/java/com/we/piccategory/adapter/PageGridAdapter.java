package com.we.piccategory.adapter;

import android.content.res.Resources;
import android.widget.TextView;

import com.we.piccategory.R;

import java.util.List;
import java.util.Random;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/5/12
 * Time: 10:47
 * Description:
 */
public class PageGridAdapter extends AutoAdapter<String> {

    private int colors[] = {R.drawable.circle, R.drawable.circle_blue, R.drawable.circle_green,
            R.drawable.circle_normal, R.drawable.circle_red, R.drawable.circle_yellow, R.drawable.cire_dark};
    Random random = new Random();
    private Resources res;

    public PageGridAdapter(List<String> list, Resources res) {
        super(list);
        this.res = res;
    }

    @Override
    protected void convert(ListViewHolder holder, String item) {
        TextView tv=holder.getView(R.id.tv_page);
        int color=colors[random.nextInt(7)];
        tv.setBackgroundResource(color);
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_page;
    }

}
