package com.we.piccategory.view;

import com.we.piccategory.bean.Record;

import java.util.List;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/5/27
 * Time: 15:03
 * Description:
 */
public interface IRecordView {

    void loadData(List<Record> list);

    void addData(List<Record> list);

    void showMsg();

}
