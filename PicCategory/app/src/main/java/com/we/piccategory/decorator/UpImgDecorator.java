package com.we.piccategory.decorator;

import com.loopj.android.http.RequestParams;
import com.we.piccategory.bean.RgbResult;
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
 * Date: 2017/5/24
 * Time: 23:18
 * Description:
 */
public class UpImgDecorator extends ModelDecorator {
    private String imgBmp;

    public UpImgDecorator(IModel model, String imgBmp) {
        super(model);
        this.imgBmp = imgBmp;
    }

    @Override
    public void load(final OnCompletedListener listener) {
        try {
            String dest = "/user/updateHeadImage";
            RequestParams params = new RequestParams();
            int userId = LoginManger.getUserId();
            String all = userId + imgBmp + Constant.ENCRYPT;
            String md5 = MD5Util.getMD5(all);
            String token = LoginManger.getToken();


            params.put("userId", userId);
            params.put("headImageUrl", imgBmp);
            params.put("token", token);
            params.put("md5", md5);


            HttpUtil.doPost(dest, params, new SuccessRespHandler(listener) {
                @Override
                protected RgbResult getResult(String resp) {
                    return RgbResult.format(resp);
                }

                @Override
                protected void onStatusFail(RgbResult rgbResult) {
                    listener.onFail("更改头像失败");
                }

                @Override
                protected void onStatusOk(RgbResult rgbResult) {
                    listener.onCompleted("更改成功");
                }
            });


        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}
