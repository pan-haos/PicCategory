package com.we.piccategory.util;

import java.util.Arrays;
import java.util.List;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/5/13
 * Time: 14:22
 * Description:
 */
public class CommonUtil {

    public static boolean checkNum(String str, int len) {
        return (str != null && str.length() == len) ? true : false;
    }

    public static String handleMsg(String str) {
        return str.length() > 5 ? str.substring(0, 5) + "..." : str;
    }


    /**
     * 字符串数组中是否包含该字符串
     *
     * @param list
     * @param str
     * @return
     */
    public static boolean contains(List<String> list, String str) {
        for (String s : list) {
            if (str.equals(s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 将字符串数组转成字符串
     *
     * @param list
     * @return
     */
    public static String changeStrList(List<String> list) {
        StringBuffer sb = new StringBuffer("");
        for (int i = 0; i < list.size(); i++) {
            if (i != list.size() - 1) {
                sb.append(list.get(i)).append("/");
            } else {
                sb.append(list.get(i));
            }
        }
        return sb.toString();
    }


    public static List<String> cutStr(String str) {
        String[] split = str.split("/");
        List<String> list = Arrays.asList(split);
        return list;
    }


    public static float[] getCircle(float[] percent) {
        float[] news = new float[percent.length];
        for (int i = 0; i < percent.length; i++) {
            news[i] = percent[i] * 360;
        }
        return news;
    }




}
