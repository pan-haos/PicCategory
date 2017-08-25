package com.we.piccategory.decorator;

import com.google.gson.Gson;
import com.loopj.android.http.RequestParams;
import com.we.piccategory.bean.RgbResult;
import com.we.piccategory.bean.UserImage;
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
 * Date: 2017/5/30
 * Time: 19:14
 * Description:
 */
public class UpLoadDecorator extends ModelDecorator {

    private UserImage img;

    public UpLoadDecorator(IModel model, UserImage img) {
        super(model);
        this.img = img;
    }

    @Override
    public void load(OnCompletedListener listener) {

        try {
            String dest = "/user/uploadPicture";
            Gson gson = new Gson();
            String json = gson.toJson(img);
            String token = LoginManger.getToken();
            int userId = LoginManger.getUserId();
            String all = json + Constant.ENCRYPT;
            String md5 = MD5Util.getMD5(all);
            RequestParams params = new RequestParams();
            params.put("userId", userId);
            params.put("token", token);
            params.put("json", json);
            params.put("md5", md5);

            HttpUtil.doPost(dest, params, new SuccessRespHandler(listener) {
                @Override
                protected RgbResult getResult(String resp) {
                    return RgbResult.format(resp);
                }

                @Override
                protected void onStatusFail(RgbResult rgbResult) {
                    mListener.onFail("上传失败，文件异常!");
                }

                @Override
                protected void onStatusOk(RgbResult rgbResult) {
                    mListener.onCompleted("上传成功!");
                }
            });


        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


    }
}
