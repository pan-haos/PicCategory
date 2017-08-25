package com.we.piccategory.decorator;

import android.util.Log;

import com.we.piccategory.listener.CheckEventHandler;
import com.we.piccategory.mvp.IModel;
import com.we.piccategory.ui.base.BaseApp;
import com.we.piccategory.util.Constant;

import java.util.HashMap;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/5/13
 * Time: 17:52
 * Description:
 */
public class CheckValuesDecorator extends ModelDecorator {
    private String telNum;
    private String checkValue;

    public CheckValuesDecorator(IModel model, String telNum, String checkValue) {
        super(model);
        this.telNum = telNum;
        this.checkValue = checkValue;
    }

    @Override
    public void load(OnCompletedListener listener) {
        checkValues(listener);
    }

    public void checkValues(final OnCompletedListener listener) {
        SMSSDK.initSDK(BaseApp.context, "1dd394b62fa8a", "eae763799122b4c25cda99b5ef83aa28");
        EventHandler eh = new CheckEventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //获取验证码成功
                        Log.i("ph", "验证码验证成功");
                        //当校验验证码通过时
                        HashMap<String, Object> phoneMap = (HashMap<String, Object>) data;
                        String phone = (String) phoneMap.get("phone");
                        System.out.println(phone);
                        listener.onCompleted(phone);
                    }
                } else {
                    Log.i("ph", "afterEvent: 获取验证码失败");
                    listener.onFail("获取验证码失败");
                }
                SMSSDK.unregisterEventHandler(this);
            }
        };
        SMSSDK.registerEventHandler(eh); //注册短信回调
        Constant.openFlag = true;
        SMSSDK.submitVerificationCode("86", telNum, checkValue);
    }

}
