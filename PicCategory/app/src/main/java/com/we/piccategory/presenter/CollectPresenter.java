package com.we.piccategory.presenter;

import com.we.piccategory.bean.Task;
import com.we.piccategory.model.CollectModel;
import com.we.piccategory.mvp.BasePresenter;
import com.we.piccategory.mvp.IModel;
import com.we.piccategory.view.ICollectView;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/5/29
 * Time: 18:33
 * Description:
 */
public class CollectPresenter extends BasePresenter<ICollectView> {

    public void fetch() {
        model = new CollectModel();
        final ICollectView iCollectView = viewRef.get();
        model.load(new IModel.OnCompletedListener<Task>() {
            @Override
            public void onCompleted(Task task) {
                if (iCollectView != null) {
                    iCollectView.showTask(task);
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


}
