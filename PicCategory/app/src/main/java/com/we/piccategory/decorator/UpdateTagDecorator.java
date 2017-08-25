package com.we.piccategory.decorator;

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
 * Date: 2017/5/28
 * Time: 0:02
 * Description:
 */
public class UpdateTagDecorator extends ModelDecorator {

    private Label label;

    public UpdateTagDecorator(IModel model, Label label) {
        super(model);
        this.label = label;
    }

    @Override
    public void load(OnCompletedListener listener) {
        try {
            String dest = "/user/label/updateUserLabel";
            int userId = LoginManger.getUserId();
            String token = LoginManger.getToken();
            String imageUrl = label.getImageUrl();
            String imageLabel = label.getImageLabel();
            String all = userId + imageLabel + imageUrl + ENCRYPT;
            String md5 = MD5Util.getMD5(all);

            RequestParams params = new RequestParams();
            params.put("userId", userId);
            params.put("token", token);
            params.put("imageUrl", imageUrl);
            params.put("imageLabel", imageLabel);
            params.put("md5", md5);

            HttpUtil.doPost(dest, params, new SuccessRespHandler(listener) {
                @Override
                protected RgbResult getResult(String resp) {
                    return RgbResult.format(resp);
                }

                @Override
                protected void onStatusFail(RgbResult rgbResult) {
                    mListener.onFail("更新失败!系统繁忙");
                }

                @Override
                protected void onStatusOk(RgbResult rgbResult) {
                    mListener.onCompleted("标签更改成功");
                }
            });


        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


    }
}
