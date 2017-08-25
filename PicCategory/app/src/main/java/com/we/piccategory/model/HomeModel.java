package com.we.piccategory.model;

import com.we.piccategory.R;
import com.we.piccategory.bean.Pic;
import com.we.piccategory.mvp.IModel;
import com.we.piccategory.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/5/18
 * Time: 16:30
 * Description:
 */
public class HomeModel implements IModel {


    @Override
    public void load(final OnCompletedListener listener) {

        List<Pic> list = new ArrayList<>();
        String[] strings = {"风景", "商品", "动物", "建筑", "植物", "人类"};



        int[] arr = {R.mipmap.scenery, R.mipmap.product, R.mipmap.animal, R.mipmap.build, R.mipmap.plant, R.mipmap.people,};
        Random random = new Random();
        for (int i = 0; i < 5; i++) {

            String num = (i + 5) * 21 + 7 + "";
            int nextInt = random.nextInt(5);
            int resId = arr[nextInt];
            String str = strings[nextInt];
            String s = CommonUtil.handleMsg(str);

            Pic pic = new Pic(s, num, resId);
            list.add(pic);
        }


        listener.onCompleted(list);

    }
}
