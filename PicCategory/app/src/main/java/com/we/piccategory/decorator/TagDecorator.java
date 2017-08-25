package com.we.piccategory.decorator;

import com.loopj.android.http.RequestParams;
import com.we.piccategory.bean.RgbResult;
import com.we.piccategory.mvp.IModel;
import com.we.piccategory.net.HttpUtil;
import com.we.piccategory.net.SuccessRespHandler;
import com.we.piccategory.util.CommonUtil;
import com.we.piccategory.util.Constant;
import com.we.piccategory.util.LoginManger;
import com.we.piccategory.util.MD5Util;

import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/5/25
 * Time: 16:23
 * Description:
 */
public class TagDecorator extends ModelDecorator {
    private String imgUrl;

    public TagDecorator(IModel model, String imgUrl) {
        super(model);
        this.imgUrl = imgUrl;
    }

    @Override
    public void load(final OnCompletedListener listener) {
        try {
            String dest = "/user/label/selectLabelByImageUrl";
            String token = LoginManger.getToken();
            int userId = LoginManger.getUserId();
            String md5 = MD5Util.getMD5(imgUrl + Constant.ENCRYPT);
            RequestParams params = new RequestParams();
            params.put("imageUrl", imgUrl);
            params.put("token", token);
            params.put("userId", userId);
            params.put("md5", md5);

            HttpUtil.doPost(dest, params, new SuccessRespHandler(listener) {
                @Override
                protected RgbResult getResult(String resp) {
                    return RgbResult.formatToPojo(resp, String.class);
                }

                @Override
                protected void onStatusFail(RgbResult rgbResult) {
                    mListener.onFail("没拿到数据");
                }

                @Override
                protected void onStatusOk(RgbResult rgbResult) {
                    String msg = (String) rgbResult.getData();
                    List<String> list = CommonUtil.cutStr(msg);
                    mListener.onCompleted(list);
                }
            });


        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


    }
}
