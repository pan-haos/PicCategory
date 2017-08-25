package com.we.piccategory.model;

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
import java.util.List;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/5/30
 * Time: 15:07
 * Description:
 */
public class RecommendModel implements IModel {

    @Override
    public void load(OnCompletedListener listener) {
        try {
            String dest = "/user/selectUserImage";
            int userId = LoginManger.getUserId();
            String token = LoginManger.getToken();
            int rows = Constant.rows;
            int page = 1;
            String all = "" + page + rows + Constant.ENCRYPT;
            String md5 = MD5Util.getMD5(all);

            RequestParams params = new RequestParams();
            params.put("userId", userId);
            params.put("rows", rows);
            params.put("page", page);
            params.put("token", token);
            params.put("md5", md5);


            HttpUtil.doPost(dest, params, new SuccessRespHandler(listener) {
                @Override
                protected RgbResult getResult(String resp) {
                    return RgbResult.formatToList(resp, UserImage.class);
                }

                @Override
                protected void onStatusFail(RgbResult rgbResult) {
                    mListener.onFail("没拿到数据");
                }

                @Override
                protected void onStatusOk(RgbResult rgbResult) {
                    List<UserImage> list = (List<UserImage>) rgbResult.getData();
                    mListener.onCompleted(list);

                }
            });
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


    }
}
