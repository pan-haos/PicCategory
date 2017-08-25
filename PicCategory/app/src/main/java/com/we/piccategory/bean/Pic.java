package com.we.piccategory.bean;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/4/28
 * Time: 15:09
 * Description:
 */
public class Pic {
    private String name;

    private String num;

    private int resId;

    public Pic() {

    }


    public Pic(String name, String num, int resId) {
        this.name = name;
        this.num = num;
        this.resId = resId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
