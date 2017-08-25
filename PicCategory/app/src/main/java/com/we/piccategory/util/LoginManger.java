package com.we.piccategory.util;

import android.content.SharedPreferences;

import com.we.piccategory.ui.base.BaseApp;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/5/21
 * Time: 18:08
 * Description:
 */
public class LoginManger {

    public static boolean getLoginState() {
        SharedPreferences share = BaseApp.context.getSharedPreferences("LOGIN_TOKEN", MODE_PRIVATE);
        return share.getBoolean("LOGIN_STATE", false);
    }

    public static String getToken() {
        SharedPreferences share = BaseApp.context.getSharedPreferences("LOGIN_TOKEN", MODE_PRIVATE);
        return share.getString("TOKEN", "");
    }


    public static int getUserId() {
        SharedPreferences share = BaseApp.context.getSharedPreferences("LOGIN_TOKEN", MODE_PRIVATE);
        return share.getInt("USER_ID", 0);
    }


    public static void setLoginState(String tokenValue, int userId, boolean state) {
        SharedPreferences.Editor editor = BaseApp.context.getSharedPreferences("LOGIN_TOKEN", MODE_PRIVATE).edit();
        editor.putString("TOKEN", tokenValue).commit();
        editor.putInt("USER_ID", userId).commit();
        editor.putBoolean("LOGIN_STATE", true).commit();
    }


    public static void setUserInfo(String userName, String telNum, String job, String pref, String sex) {
        SharedPreferences.Editor editor = BaseApp.context.getSharedPreferences("USER_INFO", MODE_PRIVATE).edit();
        editor.putString("USER_NAME", userName).commit();
        editor.putString("TEL_NUM", telNum).commit();
        editor.putString("JOB", job).commit();
        editor.putString("PREF", pref).commit();
        editor.putString("SEX", sex).commit();
    }


    public static void removeLoginState() {
        SharedPreferences.Editor editor = BaseApp.context.getSharedPreferences("LOGIN_TOKEN", MODE_PRIVATE).edit();
        editor.putString("TOKEN", "").commit();
        editor.putInt("USER_ID", 0).commit();
        editor.putBoolean("LOGIN_STATE", false).commit();
    }

    public static void removeUserInfo() {
        SharedPreferences.Editor editor = BaseApp.context.getSharedPreferences("USER_INFO", MODE_PRIVATE).edit();
        editor.putString("USER_NAME", "").commit();
        editor.putString("TEL_NUM", "").commit();
        editor.putString("JOB", "").commit();
        editor.putString("PREF", "").commit();
        editor.putString("SEX", "").commit();
    }

    public static String getTelNum() {
        SharedPreferences share = BaseApp.context.getSharedPreferences("USER_INFO", MODE_PRIVATE);
        return share.getString("TEL_NUM", "");
    }

    public static void setTelNum(String telNum) {
        SharedPreferences.Editor editor = BaseApp.context.getSharedPreferences("USER_INFO", MODE_PRIVATE).edit();
        editor.putString("TEL_NUM", telNum).commit();
    }

}
