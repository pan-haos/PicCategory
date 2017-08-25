package com.we.piccategory.listener;

import android.util.Log;

import java.util.HashMap;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/5/13
 * Time: 17:13
 * Description:
 */
public class CheckEventHandler extends EventHandler {

    @Override
    public void afterEvent(int event, int result, Object data) {

        if (result == SMSSDK.RESULT_COMPLETE) {
            //回调完成
            if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                //提交验证码成功
                Log.i("ph", "提交验证码成功");
            } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                //获取验证码成功
                Log.i("ph", "获取验证码成功");
                Log.i("ph", "验证码：" + String.valueOf(result));
                if (result == SMSSDK.RESULT_COMPLETE) {
                    Boolean b = (Boolean) data;
                    System.out.println("b==" + b);
                }else if(result==SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE){
                    //当校验验证码通过时
                    HashMap<String,Object> phoneMap = (HashMap<String, Object>) data;
                    String country = (String) phoneMap.get("country");
                    String phone = (String) phoneMap.get("phone");

                }
            } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                //返回支持发送验证码的国家列表
            }
        } else {
            ((Throwable) data).printStackTrace();
        }
    }
}
