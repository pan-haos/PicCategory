package com.we.piccategory.ui;

import com.we.piccategory.R;
import com.we.piccategory.ui.fragment.CategoryFragment;
import com.we.piccategory.ui.fragment.CenterFragment;
import com.we.piccategory.ui.fragment.HomeFragment;
import com.we.piccategory.ui.fragment.RecommendFragment;

/**
 * Created by 86119 on 2017/4/7.
 */

public enum MTab {
    HOME(0, R.string.home_name, R.drawable.home_color_selector, HomeFragment.class),

    CATEGORY(3, R.string.category, R.drawable.category_color_selector, CategoryFragment.class),

    QUICK(2, R.string.quick, R.drawable.btn_quickoption_selector, null),

    RECOMMEND(1, R.string.recommend, R.drawable.focus_color_selector, RecommendFragment.class),

    USER_CENTER(4, R.string.user_center, R.drawable.user_center_color_selector, CenterFragment.class);

    private int id;
    private int res;
    private int img;
    private Class<?> clazz;


    MTab(int id, int res, int img, Class<?> clazz) {
        this.id = id;
        this.res = res;
        this.img = img;
        this.clazz = clazz;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRes() {
        return res;
    }

    public void setRes(int res) {
        this.res = res;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }
}
