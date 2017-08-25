package com.we.piccategory.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/4/27
 * Time: 23:48
 * Description:
 */
public class AutoScrollView extends ScrollView {
    public AutoScrollView(Context context) {
        super(context);
    }

    public AutoScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public AutoScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    public static final String TAGS = "AlphaTitleScrollView";
    private int mSlop;
    private RelativeLayout toolbar;
    private RelativeLayout outLayout;

    private TextView topTv;
    private boolean isChanged = false;


    private void init(Context context) {
        mSlop = 10;
        Log.i(TAGS, mSlop + "");
    }

    public void setTitleAndHead(RelativeLayout toolbar, RelativeLayout outLayout,  TextView topTv) {
        this.toolbar = toolbar;
        this.topTv = topTv;
        this.outLayout = outLayout;
        this.toolbar.getBackground().setAlpha(0);
        this.topTv.setTextColor(Color.argb(0, 255, 255, 255));
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        float headHeight = outLayout.getMeasuredHeight() - this.getMeasuredHeight();
        int alpha = (int) (((float) t / headHeight) * 255);
        if (alpha >= 255)
            alpha = 255;
        if (alpha <= mSlop)
            alpha = 0;
        if (alpha == 0) {
            isChanged = true;
            toolbar.getBackground().setAlpha(0);
            topTv.setTextColor(Color.argb(0, 255, 255, 255));
        } else {
            if (isChanged) {
                isChanged = false;
            }
            topTv.setTextColor(Color.argb(alpha, 255, 255, 255));
            toolbar.getBackground().setAlpha(alpha);
        }
        super.onScrollChanged(l, t, oldl, oldt);
    }


    @Override
    protected int computeScrollDeltaToGetChildRectOnScreen(Rect rect) {
        return 0;
    }

}
