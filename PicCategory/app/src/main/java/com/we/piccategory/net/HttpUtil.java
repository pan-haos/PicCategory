package com.we.piccategory.net;

import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/4/18
 * Time: 14:44
 * Description:http连接的二次封装类
 */
public class HttpUtil {
    //    114.115.143.200
//    192.168.1.101
    private static final String cons = "http://";
    private static final String ip = "192.168.1.102";
    private static final String post = ":8080";
    public static StringBuffer url;

    public static AsyncHttpClient client;

    static {
        url = new StringBuffer();
        url.append(cons).append(ip).append(post);
        client = new AsyncHttpClient();
    }

    public static void doPost(String dest, RequestParams params, RespHandler handler) {
        Log.i("ph", "url==" + url + dest);
        client.post(String.valueOf(url) + dest, params, handler);
    }


    public static void doPostUrl(String url, RequestParams params, RespHandler handler) {
        client.post(url, params, handler);
    }


}
