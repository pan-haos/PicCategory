package com.we.piccategory.decorator;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.we.piccategory.bean.Label;
import com.we.piccategory.bean.RgbResult;
import com.we.piccategory.mvp.IModel;
import com.we.piccategory.net.HttpUtil;
import com.we.piccategory.net.SuccessRespHandler;
import com.we.piccategory.util.LoginManger;
import com.we.piccategory.util.MD5Util;

import java.security.NoSuchAlgorithmException;

import static com.we.piccategory.util.Constant.ENCRYPT;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/5/25
 * Time: 16:25
 * Description:
 */
public class MarkTagDecorator extends ModelDecorator {
    private Label label;

    public MarkTagDecorator(IModel model, Label label) {
        super(model);
        this.label = label;
    }


    @Override
    public void load(OnCompletedListener listener) {

        try {
            String dest = "/user/label/userMarkImage";
            Gson gson = new Gson();
            String labelJson = gson.toJson(label);
            String token = LoginManger.getToken();
            int userId = LoginManger.getUserId();
            String md5 = MD5Util.getMD5(labelJson + ENCRYPT);
            RequestParams params = new RequestParams();
            params.put("labelJson", labelJson);
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
                    mListener.onFail("标签上传失败");
                }

                @Override
                protected void onStatusOk(RgbResult rgbResult) {
                    mListener.onCompleted("标签上传成功!");
                }
            });


        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


    }
}
