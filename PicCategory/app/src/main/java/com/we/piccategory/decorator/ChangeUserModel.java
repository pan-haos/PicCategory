package com.we.piccategory.decorator;

import android.util.Log;

import com.google.gson.Gson;
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
 * Time: 14:36
 * Description:
 */
public class ChangeUserModel extends ModelDecorator {

    private User user;

    public ChangeUserModel(IModel model, User user) {
        super(model);
        this.user = user;
    }

    @Override
    public void load(final OnCompletedListener listener) {
        if (user == null) {
            return;
        }
        try {
            String dest = "/user/updatePersonalData";
            Gson gson = new Gson();
            String json = gson.toJson(user);
            String token = LoginManger.getToken();
            int userId = LoginManger.getUserId();
            String all = userId + json + Constant.ENCRYPT;
            String md5 = MD5Util.getMD5(all);
            RequestParams params = new RequestParams();
            params.put("user", json);
            params.put("token", token);
            params.put("userId", userId);
            params.put("md5", md5);

            HttpUtil.doPost(dest, params, new SuccessRespHandler(listener) {
                @Override
                protected RgbResult getResult(String resp) {
                    return RgbResult.format(resp);
                }

                @Override
                protected void onStatusFail(RgbResult rgbResult) {
                    Log.i("ph", "进入失败");
                    listener.onFail("更改失败，用户名已存在");
                }

                @Override
                protected void onStatusOk(RgbResult rgbResult) {
                    Log.i("ph", "进入更改成功");
                    listener.onCompleted();
                }
            });


        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


    }
}
