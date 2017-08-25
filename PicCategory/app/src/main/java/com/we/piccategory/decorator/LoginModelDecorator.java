package com.we.piccategory.decorator;

import com.loopj.android.http.RequestParams;
import com.we.piccategory.bean.RgbResult;
import com.we.piccategory.bean.Token;
import com.we.piccategory.mvp.IModel;
import com.we.piccategory.net.HttpUtil;
import com.we.piccategory.net.SuccessRespHandler;
import com.we.piccategory.ui.base.BaseApp;
import com.we.piccategory.util.Constant;
import com.we.piccategory.util.MD5Util;
import com.we.piccategory.util.RSAUtils;

import java.io.InputStream;


public class LoginModelDecorator extends ModelDecorator {
    private String userName;
    private String password;

    public LoginModelDecorator(IModel model, String userName, String password) {
        super(model);
        this.userName = userName;
        this.password = password;
    }

    @Override
    public void load(OnCompletedListener listener) {
        this.login(listener);
    }

    private void login(final OnCompletedListener listener) {
        //校验数据
        try {
            String dest = "/user/login";
            RequestParams params = new RequestParams();
            params.put("userName", userName);
            //公钥对密码进行加密
            InputStream is = BaseApp.assets.open("gy");
            String encrypt = RSAUtils.encrypt(password, is);
            is.close();
            params.put("password", encrypt);
            String all = userName + password + Constant.ENCRYPT;
            String md5 = MD5Util.getMD5(all);
            params.put("md5", md5);
            HttpUtil.doPost(dest, params, new SuccessRespHandler(listener) {

                @Override
                protected RgbResult getResult(String resp) {
                    return RgbResult.formatToPojo(resp, Token.class);
                }

                @Override
                protected void onStatusFail(RgbResult rgbResult) {
                    mListener.onFail("用户名或密码不正确");
                }

                @Override
                protected void onStatusOk(RgbResult rgbResult) {
                    Token token = (Token) rgbResult.getData();
                    mListener.onCompleted(token);
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
