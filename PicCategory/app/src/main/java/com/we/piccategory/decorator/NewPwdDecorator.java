package com.we.piccategory.decorator;

import com.loopj.android.http.RequestParams;
import com.we.piccategory.bean.RgbResult;
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
 * Date: 2017/5/21
 * Time: 15:54
 * Description:
 */
public class NewPwdDecorator extends ModelDecorator {

    private String telNum;
    private String newPwd;

    public NewPwdDecorator(IModel model, String telNum, String newPwd) {
        super(model);
        this.telNum = telNum;
        this.newPwd = newPwd;
    }

    @Override
    public void load(OnCompletedListener listener) {
        updatePwd(listener);
    }

    private void updatePwd(final OnCompletedListener listener) {
        try {
            RequestParams params = new RequestParams();
            params.put("phone", telNum);

            String all = telNum + newPwd + Constant.ENCRYPT;
            String md5 = MD5Util.getMD5(all);
            params.put("md5", md5);

            InputStream is = BaseApp.assets.open("gy");
            newPwd = RSAUtils.encrypt(newPwd, is);
            is.close();

            params.put("password", newPwd);


            HttpUtil.doPost("/user/updatePwdByPhone", params, new SuccessRespHandler(listener) {
                @Override
                protected RgbResult getResult(String resp) {
                    return RgbResult.format(resp);
                }

                @Override
                protected void onStatusFail(RgbResult rgbResult) {
                    listener.onFail("更改密码失败，密码过于简单");
                }

                @Override
                protected void onStatusOk(RgbResult rgbResult) {
                    listener.onCompleted("密码更改成功");
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}
