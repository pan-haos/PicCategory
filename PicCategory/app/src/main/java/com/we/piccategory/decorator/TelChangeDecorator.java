package com.we.piccategory.decorator;

import com.loopj.android.http.RequestParams;
import com.we.piccategory.bean.RgbResult;
import com.we.piccategory.mvp.IModel;
import com.we.piccategory.net.SuccessRespHandler;
import com.we.piccategory.net.SyncHttpUtil;
import com.we.piccategory.util.Constant;
import com.we.piccategory.util.LoginManger;
import com.we.piccategory.util.MD5Util;

import java.security.NoSuchAlgorithmException;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/5/29
 * Time: 12:44
 * Description:
 */
public class TelChangeDecorator extends ModelDecorator {
    private String telNum;

    public TelChangeDecorator(IModel model, String telNum) {
        super(model);
        this.telNum = telNum;
    }

    @Override
    public void load(OnCompletedListener listener) {
        try {
            String dest = "/user/updatePhone";

            int userId = LoginManger.getUserId();
            String token = LoginManger.getToken();
            String all = userId + telNum + Constant.ENCRYPT;
            String md5 = MD5Util.getMD5(all);

            RequestParams params = new RequestParams();
            params.put("userId", userId);
            params.put("token", token);
            params.put("phone", telNum);
            params.put("md5", md5);


            SyncHttpUtil.doPost(dest, params, new SuccessRespHandler(listener) {
                @Override
                protected RgbResult getResult(String resp) {
                    return RgbResult.format(resp);
                }

                @Override
                protected void onStatusFail(RgbResult rgbResult) {
                    mListener.onFail("更改手机号失败");
                }

                @Override
                protected void onStatusOk(RgbResult rgbResult) {
                    mListener.onCompleted("更改手机号成功");
                }
            });


        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


    }


}
