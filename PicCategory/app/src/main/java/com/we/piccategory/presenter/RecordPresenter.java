package com.we.piccategory.presenter;

import com.we.piccategory.bean.Record;
import com.we.piccategory.model.RecordModel;
import com.we.piccategory.mvp.BasePresenter;
import com.we.piccategory.mvp.IModel;
import com.we.piccategory.view.IRecordView;

import java.util.List;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/5/27
 * Time: 15:05
 * Description:
 */
public class RecordPresenter extends BasePresenter<IRecordView> {

    private int count = 1;

    public void loadAll(String type) {
        model = new RecordModel(type, count);
        final IRecordView iRecordView = viewRef.get();
        model.load(new IModel.OnCompletedListener<List<Record>>() {

            @Override
            public void onCompleted(List<Record> data) {
                iRecordView.loadData(data);
                count++;
            }

            @Override
            public void onCompleted() {
            }

            @Override
            public void onFail(String msg) {
                iRecordView.showMsg();
            }
        });
    }

    public void loadMore(String type) {
        model = new RecordModel(type, count);
        final IRecordView iRecordView = viewRef.get();
        model.load(new IModel.OnCompletedListener<List<Record>>() {

            @Override
            public void onCompleted(List<Record> data) {
                count++;
                if (iRecordView != null) {
                    iRecordView.addData(data);
                }

            }

            @Override
            public void onCompleted() {
            }

            @Override
            public void onFail(String msg) {

            }
        });


    }


//    public void loadCom() {
//
//    }
//
//    public void loadWait() {
//
//    }

}
