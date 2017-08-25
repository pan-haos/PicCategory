package com.we.piccategory.widget;

import android.content.Context;
import android.support.v4.app.FragmentTabHost;
import android.util.AttributeSet;

/**
 * Created with IntelliJ IDEA.
 * User: 潘浩
 * School 南华大学
 * Date: 2017/4/1
 * Time: 22:23
 * Description: 自定义的FragMentTabHost
 */

public class MyFragmentTabHost extends FragmentTabHost {
    private String currentTag;

    private String changTag;


    public MyFragmentTabHost(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void onTabChanged(String tag) {

        if (tag.equals(changTag)) {
            setCurrentTabByTag(currentTag);
        } else {
            super.onTabChanged(tag);
            currentTag = tag;
        }
    }

    public void setNoTabChangedTag(String tag) {
        this.changTag = tag;
    }


}
