package com.we.piccategory.decorator;

import android.util.Log;

import com.loopj.android.http.RequestParams;
import com.we.piccategory.bean.RgbResult;
import com.we.piccategory.listener.CheckEventHandler;
import com.we.piccategory.mvp.IModel;
import com.we.piccategory.net.HttpUtil;
import com.we.piccategory.net.RespHandler;
import com.we.piccategory.ui.base.BaseApp;
import com.we.piccategory.util.CommonUtil;
import com.we.piccategory.util.Constant;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/4/20
 * Time: 12:37
 * Description:
 */
public class CheckModelDecorator extends ModelDecorator {
    private String phoneNum;

    public CheckModelDecorator(IModel model, String phoneNum) {
        super(model);
        this.phoneNum = phoneNum;
    }


    @Override
    public void load(OnCompletedListener listener) {
        //向服务器数据库访问检测是否存在该手机号
        checkIsExit(listener);
//        checkValue(listener);
    }

    private boolean checkIsExit(final OnCompletedListener listener) {
        //手机号为11位号码时
        if (CommonUtil.checkNum(phoneNum, 11)) {
            String dest = "/user/ckeckPhone";

            RequestParams params = new RequestParams();
            params.add("phone", phoneNum);

            Log.i("ph", "发送了请求到服务器");


            HttpUtil.doPost(dest, params, new RespHandler() {

                @Override
                public void success(String resp) {
                    RgbResult rgbResult = RgbResult.formatToPojo(resp, null);
                    Integer status = rgbResult.getStatus();
                    if (status.equals(RgbResult.STATUS_OK)) {
                        //向短信服务器发送验证获取验证码
                        Log.i("ph", "获取到200成功");
                        checkValue(listener);
                    } else if (status.equals(RgbResult.STATUS_FAIL)) {
                        listener.onFail("手机号已注册");
                    } else if (status.equals(RgbResult.STATUS_ERROR)) {
                        listener.onFail("系统错误");
                    }
                }

                @Override
                public void fail(int statusCode, String msg) {
                    listener.onFail(msg);
                }
            });
        }
        return false;
    }

    public void checkValue(final OnCompletedListener listener) {
        if (phoneNum != null && phoneNum.length() == 11) {
            //发送获取验证码的指令
            SMSSDK.initSDK(BaseApp.context, "1dd394b62fa8a", "eae763799122b4c25cda99b5ef83aa28");

            final EventHandler eh = new CheckEventHandler() {
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
                                listener.onCompleted();

                            }
                        } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                            //返回支持发送验证码的国家列表
                        }
                    } else {
                        listener.onFail("获取验证码失败");
                        ((Throwable) data).printStackTrace();
                    }
                    SMSSDK.unregisterEventHandler(this);
                }
            };

            SMSSDK.registerEventHandler(eh); //注册短信回调
            Constant.openFlag = true;
            SMSSDK.getVerificationCode("86", phoneNum);

        }
    }


}
