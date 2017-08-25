package com.we.piccategory.model;

import com.we.piccategory.R;
import com.we.piccategory.bean.Category;
import com.we.piccategory.mvp.IModel;

import java.util.Arrays;
import java.util.List;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/5/9
 * Time: 9:08
 * Description:分类页面的Model数据
 */
public class CategoryModel implements IModel {
    @Override
    public void load(OnCompletedListener listener) {
        Category category = new Category(R.mipmap.scenery, "风景", "Scenery");
        Category category1 = new Category(R.mipmap.people, "人类", "People");
        Category category2 = new Category(R.mipmap.animal, "动物", "Animal");
        Category category3 = new Category(R.mipmap.plant, "植物", "Plant");
        Category category4 = new Category(R.mipmap.build, "建筑", "Build");
        Category category5 = new Category(R.mipmap.other, "其它", "Other");

        Category[] categories = {category, category1, category2, category3, category4, category5};
        List<Category> list = Arrays.asList(categories);
        listener.onCompleted(list);
    }
}
