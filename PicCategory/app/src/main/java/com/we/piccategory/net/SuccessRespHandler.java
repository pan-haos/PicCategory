package com.we.piccategory.net;

import android.util.Log;

import com.we.piccategory.bean.RgbResult;
import com.we.piccategory.mvp.IModel;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/5/14
 * Time: 14:27
 * Description:
 */
public abstract class SuccessRespHandler extends RespHandler {


    protected IModel.OnCompletedListener mListener;


    public SuccessRespHandler(IModel.OnCompletedListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void success(String resp) {
        RgbResult rgbResult = getResult(resp);

        //当返回结果不为空的时候
        if (rgbResult != null) {
            int status = rgbResult.getStatus();
            if (status == RgbResult.STATUS_OK) {
                onStatusOk(rgbResult);
            } else if (status == RgbResult.STATUS_FAIL) {
                onStatusFail(rgbResult);
            } else if (status == RgbResult.STATUS_ERROR) {
                mListener.onFail("系统繁忙");
            } else if (status == RgbResult.STATUS_LOGINOUT) {
                mListener.onFail("设备已登出");
            } else if (status == RgbResult.STATUS_NOLOGIN) {
                mListener.onFail("未登录，无操作权限");
            }
        }
    }

    @Override
    public void fail(int statusCode, String msg) {
        Log.i("ph", "访问失败，服务器无响应，请求码" + statusCode);

        mListener.onFail("服务器无响应," + msg + "statusCode==" + statusCode);
    }

    protected abstract RgbResult getResult(String resp);

    protected abstract void onStatusFail(RgbResult rgbResult);

    protected abstract void onStatusOk(RgbResult rgbResult);

}
