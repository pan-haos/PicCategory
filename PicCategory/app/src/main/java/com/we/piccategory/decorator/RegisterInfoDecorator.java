package com.we.piccategory.decorator;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.we.piccategory.bean.RgbResult;
import com.we.piccategory.bean.User;
import com.we.piccategory.mvp.IModel;
import com.we.piccategory.net.HttpUtil;
import com.we.piccategory.net.SuccessRespHandler;
import com.we.piccategory.ui.base.BaseApp;
import com.we.piccategory.util.Constant;
import com.we.piccategory.util.MD5Util;
import com.we.piccategory.util.RSAUtils;

import java.io.InputStream;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/5/15
 * Time: 0:21
 * Description:
 */
public class RegisterInfoDecorator extends ModelDecorator {

    private User user;

    public RegisterInfoDecorator(IModel model, User user) {
        super(model);
        this.user = user;
    }

    @Override
    public void load(OnCompletedListener listener) {
        registerInfo(listener);
    }

    private void registerInfo(final OnCompletedListener listener) {
        if (user == null)
            return;
        try {
            Gson gson = new Gson();
            String password = user.getPassword();
            InputStream is = BaseApp.assets.open("gy");
            password = RSAUtils.encrypt(password, is);
            user.setPassword(password);

            String json = gson.toJson(user);
            String all = json + Constant.ENCRYPT;
            String md5 = MD5Util.getMD5(all);
            RequestParams params = new RequestParams();
            params.put("userJson", json);
            params.put("md5", md5);

            String dest = "/user/regist";
            HttpUtil.doPost(dest, params, new SuccessRespHandler(listener) {

                @Override
                protected RgbResult getResult(String resp) {
                    return RgbResult.formatToPojo(resp, null);
                }

                @Override
                protected void onStatusFail(RgbResult rgbResult) {
                    mListener.onFail("用户名已存在，请更换用户名");
                }

                @Override
                protected void onStatusOk(RgbResult rgbResult) {
                    String str = "注册成功";
                    mListener.onCompleted(str);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
