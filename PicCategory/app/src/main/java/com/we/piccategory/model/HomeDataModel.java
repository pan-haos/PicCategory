package com.we.piccategory.model;

import android.util.Log;

import com.loopj.android.http.RequestParams;
import com.we.piccategory.bean.ImageLabel;
import com.we.piccategory.bean.RgbResult;
import com.we.piccategory.mvp.IModel;
import com.we.piccategory.net.HttpUtil;
import com.we.piccategory.net.SuccessRespHandler;
import com.we.piccategory.util.Constant;
import com.we.piccategory.util.LoginManger;
import com.we.piccategory.util.MD5Util;

import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/5/16
 * Time: 16:31
 * Description:
 */
public class HomeDataModel implements IModel {

    private int count = 1;//记录请求第几页


    public HomeDataModel(int count) {
        this.count = count;
    }

    @Override
    public void load(final OnCompletedListener listener) {

        try {
            String dest = "/user/label/pushImageToUser";
            RequestParams params = new RequestParams();
            params.put("page", count);
            params.put("rows", Constant.rows);

            String token = LoginManger.getToken();
            params.put("token", token);


            int userId = LoginManger.getUserId();
            params.put("userId", userId);


            StringBuffer sb = new StringBuffer();
            StringBuffer append = sb.append(userId).append(count).append(Constant.rows).append(Constant.ENCRYPT);
            Log.i("ph", "append==" + append);

            String md5 = MD5Util.getMD5(append.toString());
            params.put("md5", md5);


            Log.i("ph", "发送请求的是第几页" + count + "发送的值为：" + append.toString());


            HttpUtil.doPost(dest, params, new SuccessRespHandler(listener) {
                @Override
                protected RgbResult getResult(String resp) {


                    return RgbResult.formatToList(resp, ImageLabel.class);
                }

                @Override
                protected void onStatusFail(RgbResult rgbResult) {
                    listener.onFail("获取信息失败");
                }

                @Override
                protected void onStatusOk(RgbResult rgbResult) {
                    List<ImageLabel> labels = (List<ImageLabel>) rgbResult.getData();
                    listener.onCompleted(labels);

                }
            });


        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }
}
