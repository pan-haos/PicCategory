package com.we.piccategory.bean;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/5/9
 * Time: 8:30
 * Description:
 */
public class Category {
    private int id;
    private String textChina;
    private String textEnglish;

    public Category() {
    }

    public Category(int id, String textChina, String textEnglish) {
        this.id = id;
        this.textChina = textChina;
        this.textEnglish = textEnglish;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTextChina() {
        return textChina;
    }

    public void setTextChina(String textChina) {
        this.textChina = textChina;
    }

    public String getTextEnglish() {
        return textEnglish;
    }

    public void setTextEnglish(String textEnglish) {
        this.textEnglish = textEnglish;
    }
}
