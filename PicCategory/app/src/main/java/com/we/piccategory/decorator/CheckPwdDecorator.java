package com.we.piccategory.decorator;

import android.util.Log;

import com.loopj.android.http.RequestParams;
import com.we.piccategory.bean.RgbResult;
import com.we.piccategory.listener.CheckEventHandler;
import com.we.piccategory.mvp.IModel;
import com.we.piccategory.net.HttpUtil;
import com.we.piccategory.net.SuccessRespHandler;
import com.we.piccategory.ui.base.BaseApp;
import com.we.piccategory.util.CommonUtil;
import com.we.piccategory.util.Constant;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/5/16
 * Time: 13:45
 * Description:
 */
public class CheckPwdDecorator extends ModelDecorator {
    private String telNum;

    public CheckPwdDecorator(IModel model, String telNum) {
        super(model);
        this.telNum = telNum;
    }


    @Override
    public void load(OnCompletedListener listener) {
        checkPhone(listener);
    }

    private void checkPhone(final OnCompletedListener listener) {
        //手机号为11位号码时
        if (CommonUtil.checkNum(telNum, 11)) {
            String dest = "/user/ckeckPhone";
            RequestParams params = new RequestParams();
            params.add("phone", telNum);

            HttpUtil.doPost(dest, params, new SuccessRespHandler(listener) {
                @Override
                protected RgbResult getResult(String resp) {
                    return RgbResult.formatToPojo(resp, null);
                }

                @Override
                protected void onStatusFail(RgbResult rgbResult) {
                    Log.i("ph", "onStatusFail: 返回存在手机号");

                    checkValue(mListener);
                }

                @Override
                protected void onStatusOk(RgbResult rgbResult) {
                    Log.i("ph", "onStatusOk: 返回不存在手机号");
                    mListener.onFail("手机号不存在");
                }
            });

        }
    }

    public void checkValue(final OnCompletedListener listener) {
        if (CommonUtil.checkNum(telNum, 11)) {
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
                        Log.i("ph", "afterEvent: 获取验证码失败");
                        listener.onFail("获取验证码失败");
                        ((Throwable) data).printStackTrace();
                    }
                    SMSSDK.unregisterEventHandler(this);
                }
            };

            SMSSDK.registerEventHandler(eh); //注册短信回调
            Constant.openFlag = true;
            SMSSDK.getVerificationCode("86", telNum);

        }
    }


}
