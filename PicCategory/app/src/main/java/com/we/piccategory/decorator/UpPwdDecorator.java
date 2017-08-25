package com.we.piccategory.decorator;

import com.loopj.android.http.RequestParams;
import com.we.piccategory.bean.RgbResult;
import com.we.piccategory.mvp.IModel;
import com.we.piccategory.net.HttpUtil;
import com.we.piccategory.net.SuccessRespHandler;
import com.we.piccategory.ui.base.BaseApp;
import com.we.piccategory.util.Constant;
import com.we.piccategory.util.LoginManger;
import com.we.piccategory.util.MD5Util;
import com.we.piccategory.util.RSAUtils;

import java.io.InputStream;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/5/29
 * Time: 15:43
 * Description:
 */
public class UpPwdDecorator extends ModelDecorator {

    private String pwd;

    public UpPwdDecorator(IModel model, String pwd) {
        super(model);
        this.pwd = pwd;
    }

    @Override
    public void load(OnCompletedListener listener) {
        try {
            String dest = "/user/updatePwd";
            int userId = LoginManger.getUserId();
            String token = LoginManger.getToken();
            String md5 = MD5Util.getMD5("" + userId + pwd + Constant.ENCRYPT);
            InputStream is = BaseApp.assets.open("gy");
            pwd = RSAUtils.encrypt(pwd, is);
            RequestParams params = new RequestParams();
            params.put("userId", userId);
            params.put("password", pwd);
            params.put("token", token);
            params.put("md5", md5);

            HttpUtil.doPost(dest, params, new SuccessRespHandler(listener) {
                @Override
                protected RgbResult getResult(String resp) {
                    return RgbResult.format(resp);
                }

                @Override
                protected void onStatusFail(RgbResult rgbResult) {
                    mListener.onFail("更改失败，密码不能重复!");
                }

                @Override
                protected void onStatusOk(RgbResult rgbResult) {
                    mListener.onCompleted("更改成功!");
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
