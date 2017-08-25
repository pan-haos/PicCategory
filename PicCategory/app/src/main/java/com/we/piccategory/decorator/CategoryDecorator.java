package com.we.piccategory.decorator;

import android.util.Log;

import com.loopj.android.http.RequestParams;
import com.we.piccategory.bean.ImageLabel;
import com.we.piccategory.bean.RgbResult;
import com.we.piccategory.mvp.IModel;
import com.we.piccategory.net.HttpUtil;
import com.we.piccategory.net.SuccessRespHandler;
import com.we.piccategory.util.Constant;
import com.we.piccategory.util.LoginManger;
import com.we.piccategory.util.MD5Util;

import java.security.NoSuchAlgorithmException;
import java.util.List;

import static com.we.piccategory.util.Constant.rows;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/5/22
 * Time: 23:03
 * Description:
 */
public class CategoryDecorator extends ModelDecorator {
    private String type;

    private int count = 1;

    public CategoryDecorator(IModel model, String type, int count) {
        super(model);
        this.type = type;
        this.count = count;
    }

    @Override
    public void load(OnCompletedListener listener) {
        getData(listener);
    }

    private void getData(OnCompletedListener listener) {
        try {
            String dest = "/user/label/selectImageByType";
            RequestParams params = new RequestParams();
            params.put("type", type);
            params.put("page", count);
            params.put("rows", rows);
            params.put("token", LoginManger.getToken());
            params.put("userId", LoginManger.getUserId());
            String all = type + count + rows + Constant.ENCRYPT;

            System.out.println("all==" + all);

            String md5 = MD5Util.getMD5(all);
            params.put("md5", md5);


            HttpUtil.doPost(dest, params, new SuccessRespHandler(listener) {
                @Override
                protected RgbResult getResult(String resp) {
                    return RgbResult.formatToList(resp, ImageLabel.class);
                }

                @Override
                protected void onStatusFail(RgbResult rgbResult) {
                    mListener.onFail("获取数据失败");
                    Log.i("ph", "onStatusFail: ==");
                }

                @Override
                protected void onStatusOk(RgbResult rgbResult) {
                    Log.i("ph", "onStatusOk: ==");

                    List<ImageLabel> list = (List<ImageLabel>) rgbResult.getData();
                    mListener.onCompleted(list);
                }
            });

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }
}
