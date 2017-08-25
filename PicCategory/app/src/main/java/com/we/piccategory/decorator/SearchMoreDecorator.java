package com.we.piccategory.decorator;

import com.loopj.android.http.RequestParams;
import com.we.piccategory.bean.ImageLabel;
import com.we.piccategory.bean.RgbResult;
import com.we.piccategory.mvp.IModel;
import com.we.piccategory.net.HttpUtil;
import com.we.piccategory.net.SuccessRespHandler;

import java.util.List;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/5/27
 * Time: 14:14
 * Description:
 */
public class SearchMoreDecorator extends ModelDecorator {
    private String keyWord;

    private int page = 1;

    public SearchMoreDecorator(IModel model, String keyWord, int page) {
        super(model);
        this.keyWord = keyWord;
        this.page = page;
    }


    @Override
    public void load(OnCompletedListener listener) {

        String url = "http://114.115.143.200:8080/search/search";
        int rows = 10;
        RequestParams params = new RequestParams();
        params.put("query", keyWord);
        params.put("page", page);
        params.put("rows", rows);
        HttpUtil.doPostUrl(url, params, new SuccessRespHandler(listener) {
            @Override
            protected RgbResult getResult(String resp) {
                return RgbResult.formatToList(resp, ImageLabel.class);
            }

            @Override
            protected void onStatusFail(RgbResult rgbResult) {
                mListener.onFail("");
            }

            @Override
            protected void onStatusOk(RgbResult rgbResult) {
                List<ImageLabel> labels = (List<ImageLabel>) rgbResult.getData();

                if (labels != null && labels.size() > 0) {
                    mListener.onCompleted(labels);
                }
            }
        });

    }


}
