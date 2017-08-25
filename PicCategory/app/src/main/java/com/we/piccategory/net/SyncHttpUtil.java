package com.we.piccategory.net;

import android.util.Log;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/5/29
 * Time: 15:01
 * Description:
 */
public class SyncHttpUtil {
    //    114.115.143.200
//    192.168.1.101
    private static final String cons = "http://";
    private static final String ip = "192.168.1.101";
    private static final String post = ":8080";
    public static StringBuffer url;

    public static SyncHttpClient client;

    static {
        url = new StringBuffer();
        url.append(cons).append(ip).append(post);
        client = new SyncHttpClient();
    }

    public static void doPost(String dest, RequestParams params, RespHandler handler) {
        Log.i("ph", "url==" + url + dest);
        client.post(String.valueOf(url) + dest, params, handler);
    }


    public static void doPostUrl(String url, RequestParams params, RespHandler handler) {
        client.post(url, params, handler);
    }


}
