package com.we.piccategory.net;

import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;


/**
 * 作者：潘浩
 * 学校：南华大学
 * 时间：17-8-19
 */
public class MyUrlHttp {

    public void sendReqGet(String url) throws IOException {

        InputStream inputStream = null;
        try {
            URL newUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) newUrl.openConnection();
            conn.setReadTimeout(10 * 1000);
            conn.setConnectTimeout(15 * 1000);
            conn.setRequestMethod("POST");
            //设置接受输入流
            conn.setDoInput(true);
            //设置开启输出流，需要传递参数时开启
            conn.setDoOutput(true);
            conn.setRequestProperty("Connection", "Keep-Alive");
            List<NameValuePair> params = new ArrayList<>();
            //添加参数～
            params.add(new BasicNameValuePair("username", "ZHANG_SAN"));
            params.add(new BasicNameValuePair("pwd", "123"));
            //把参数写出去
            outPutParams(params, conn.getOutputStream());

            //发起请求
            conn.connect();
            inputStream = conn.getInputStream();
            String result = getContent(inputStream);
            Log.d("ph", "result==" + result);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }

    private void outPutParams(List<NameValuePair> params, OutputStream outputStream) throws IOException {
        StringBuilder builder = new StringBuilder();
        for (NameValuePair param : params) {
            if (builder.length() != 0) {
                builder.append("&");
            }
            String name = param.getName();
            String value = param.getValue();
            builder.append(URLEncoder.encode(name, "UTF-8"))
                    .append("=")
                    .append(URLEncoder.encode(value, "UTF-8"));
        }
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
        writer.write(builder.toString() + "\r\n");
        writer.flush();
        writer.close();
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
