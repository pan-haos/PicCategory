package com.we.piccategory.mvp;

/**
 * Created by 86119 on 2017/3/21.
 */

public interface IModel {


    /**
     * 获取数据
     *
     * @param listener 监听回调
     */
    void load(OnCompletedListener listener);

    interface OnCompletedListener<T> {
        /**
         * 获取到数据并且数据要交互到界面成功时
         *
         * @param data
         */
        void onCompleted(T data);

        /**
         * 仅仅提交请求不需要数据时成功
         */
        void onCompleted();

        /**
         * 提交请求结果失败时
         */
        void onFail(String msg);
    }
}
