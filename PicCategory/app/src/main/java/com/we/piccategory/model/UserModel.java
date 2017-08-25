package com.we.piccategory.model;

import android.util.Log;

import com.loopj.android.http.RequestParams;
import com.we.piccategory.bean.RgbResult;
import com.we.piccategory.bean.User;
import com.we.piccategory.mvp.IModel;
import com.we.piccategory.net.HttpUtil;
import com.we.piccategory.net.SuccessRespHandler;
import com.we.piccategory.util.Constant;
import com.we.piccategory.util.LoginManger;
import com.we.piccategory.util.MD5Util;

import java.security.NoSuchAlgorithmException;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/5/23
 * Time: 12:30
 * Description:
 */
public class UserModel implements IModel {

    @Override
    public void load(final OnCompletedListener listener) {
        try {
            String dest = "/user/queryUser";

            String token = LoginManger.getToken();
            int userId = LoginManger.getUserId();
            String all = userId + Constant.ENCRYPT;
            String md5 = MD5Util.getMD5(all);
            RequestParams params = new RequestParams();
            params.put("token", token);
            params.put("userId", userId);
            params.put("md5", md5);

            HttpUtil.doPost(dest, params, new SuccessRespHandler(listener) {
                @Override
                protected RgbResult getResult(String resp) {
                    return RgbResult.formatToPojo(resp, User.class);
                }

                @Override
                protected void onStatusFail(RgbResult rgbResult) {
                    listener.onFail("加载用户信息失败");
                }

                @Override
                protected void onStatusOk(RgbResult rgbResult) {
                    User user = (User) rgbResult.getData();
                    //将用户基本信息数据存在本地
                    LoginManger.setUserInfo(user.getUserName(), user.getPhone(), user.getJop(), user.getPreference(), user.getSex());
                    String telNum = LoginManger.getTelNum();
                    Log.i("ph", "telNum=" + telNum);
                    listener.onCompleted(user);
                }
            });


        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


    }
}
