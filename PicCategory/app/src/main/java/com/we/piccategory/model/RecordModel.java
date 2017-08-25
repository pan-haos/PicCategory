package com.we.piccategory.model;

import com.loopj.android.http.RequestParams;
import com.we.piccategory.bean.Record;
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
 * Date: 2017/5/27
 * Time: 17:15
 * Description:
 */
public class RecordModel implements IModel {

    private String type = "1";
    private int page = 1;

    public RecordModel(String type, int page) {
        this.type = type;
        this.page = page;
    }

    @Override
    public void load(OnCompletedListener listener) {
        try {
            String dest = "/user/label/selectImageByUserId";
            int userId = LoginManger.getUserId();
            String token = LoginManger.getToken();
            RequestParams params = new RequestParams();
            String md5 = MD5Util.getMD5("" + userId + type + page + rows + Constant.ENCRYPT);

            params.put("userId", userId);
            params.put("token", token);
            params.put("page", page);
            params.put("rows", rows);
            params.put("type", type);

            params.put("md5", md5);


            params.put("type", type);

            HttpUtil.doPost(dest, params, new SuccessRespHandler(listener) {
                @Override
                protected RgbResult getResult(String resp) {
                    return RgbResult.formatToList(resp, Record.class);
                }

                @Override
                protected void onStatusFail(RgbResult rgbResult) {
                    mListener.onFail("");
                }

                @Override
                protected void onStatusOk(RgbResult rgbResult) {
                    List<Record> list = (List<Record>) rgbResult.getData();
                    mListener.onCompleted(list);
                }
            });


        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        /*List<Record> list = new LinkedList<Record>();


        for (int i = 0; i < 10; i++) {
            Record record = new Record("http://123.207.27.202:8080/Tmall/proimages/product1.jpg", "动物、老虎", "狗、狮子", "2017-2-20 10:30", true);
            Record record1 = new Record("http://123.207.27.202:8080/Tmall/proimages/product2.jpg", "动物、老虎", "狗、狮子", "2017-2-20 10:30", false);
            list.add(record);
            list.add(record1);
        }
        listener.onCompleted(list);*/
    }
}
