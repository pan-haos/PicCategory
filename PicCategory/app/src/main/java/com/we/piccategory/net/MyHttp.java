package com.we.piccategory.net;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


/**
 * 作者：潘浩
 * 学校：南华大学
 * 时间：17-8-19
 */
public class MyHttp {

    public void sendReqGet(String url) throws IOException {
        HttpParams mParams = getParams();
        //创建httpClient对象，并设置默认的请求参数
        HttpClient mClient = new DefaultHttpClient(mParams);
        HttpGet httpGet = new HttpGet(url);
        httpGet.addHeader("Connection", "Keep-Alive");
        //执行http请求
        HttpResponse response = mClient.execute(httpGet);
        //响应体
        HttpEntity entity = response.getEntity();
        if (entity == null) {
            return;
        }
        InputStream inputStream = entity.getContent();
        //解析请求结果：
        String result = getContent(inputStream);
        Log.d("ph", "###请求结果为：" + result);
        inputStream.close();
    }

    public void SendReqPost(String url) throws IOException {
        HttpParams params = getParams();
        DefaultHttpClient mClient = new DefaultHttpClient(params);
        HttpPost httpPost = new HttpPost(url);
        //构造请求头
        httpPost.addHeader("Connection", "Keep-Alive");

        List<NameValuePair> postParams = new ArrayList<>();
        //添加参数
        postParams.add(new BasicNameValuePair("username", "zhangsan"));
        postParams.add(new BasicNameValuePair("pwd", "123"));
        //实例化对象
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(postParams);
        //设置参数到httpPost里
        httpPost.setEntity(entity);
        HttpResponse response = mClient.execute(httpPost);
        HttpEntity respEntity = response.getEntity();
        if (respEntity != null) {
            InputStream inputStream = respEntity.getContent();
            String result = getContent(inputStream);
            inputStream.close();
            Log.d("ph", result);
        }
    }


    private HttpParams getParams() {
        //1.设置http的请求参数：
        HttpParams mParams = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(mParams, 10000);
        HttpConnectionParams.setSoTimeout(mParams, 15000);
        HttpConnectionParams.setTcpNoDelay(mParams, true);
        //关闭旧连接检查的配置为false
        HttpConnectionParams.setStaleCheckingEnabled(mParams, false);
        //协议参数
        HttpProtocolParams.setVersion(mParams, HttpVersion.HTTP_1_1);
        //持续握手
        HttpProtocolParams.setUseExpectContinue(mParams, true);
        return mParams;
    }


    private String getContent(InputStream inputStream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder builder = new StringBuilder();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                builder.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }
}
