package com.we.piccategory.net;

import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/4/18
 * Time: 16:30
 * Description: 简化AsyncHttpResponseHandler的内容，用模板方法将相同的功能抽出来
 */
public abstract class RespHandler extends AsyncHttpResponseHandler {

    public abstract void success(String resp);


    public abstract void fail(int statusCode, String msg);

    @Override
    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
        String resp = new String(responseBody);
        success(resp);
    }


    @Override
    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
        fail(statusCode, "连接失败");

//        if (statusCode == 404) {
//            Toast.makeText(BaseApp.context, "服务器无响应", Toast.LENGTH_SHORT).show();
//        } else if (statusCode == 500) {
//            Toast.makeText(BaseApp.context, "访问请求出错", Toast.LENGTH_SHORT).show();
//        } else {
////            Toast.makeText(BaseApp.context, "未知错误,错误码" + statusCode, Toast.LENGTH_SHORT).show();
//        }
    }


}
