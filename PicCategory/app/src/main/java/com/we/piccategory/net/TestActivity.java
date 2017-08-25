package com.we.piccategory.net;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import java.io.IOException;

/**
 * 作者：潘浩
 * 学校：南华大学
 * 时间：17-8-19
 */
public class TestActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final MyUrlHttp myUrlHttp = new MyUrlHttp();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    myUrlHttp.sendReqGet("http://192.168.1.26:8080/remeber");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
