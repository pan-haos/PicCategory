package com.we.piccategory.decorator;

import com.loopj.android.http.RequestParams;
import com.we.piccategory.bean.Label;
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
 * Date: 2017/5/27
 * Time: 23:09
 * Description:
 */
public class MyTagDecorator extends ModelDecorator {

    private String imgUrl;

    public MyTagDecorator(IModel model, String imgUrl) {
        super(model);
        this.imgUrl = imgUrl;
    }

    @Override
    public void load(OnCompletedListener listener) {
        try {
            String dest = "/user/label/selectOneUserLabel";
            int userId = LoginManger.getUserId();
            String token = LoginManger.getToken();
            String md5 = MD5Util.getMD5(userId + imgUrl + Constant.ENCRYPT);

            RequestParams params = new RequestParams();
            params.put("userId", userId);
            params.put("token", token);
            params.put("imageUrl", imgUrl);
            params.put("md5", md5);

            HttpUtil.doPost(dest, params, new SuccessRespHandler(listener) {
                @Override
                protected RgbResult getResult(String resp) {
                    return RgbResult.formatToPojo(resp, Label.class);
                }

                @Override
                protected void onStatusFail(RgbResult rgbResult) {
                }

                @Override
                protected void onStatusOk(RgbResult rgbResult) {
                    Label label = (Label) rgbResult.getData();
                        mListener.onCompleted(label);
                }
            });


        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


    }
}
