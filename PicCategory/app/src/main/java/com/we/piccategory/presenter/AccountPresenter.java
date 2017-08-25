package com.we.piccategory.presenter;

import com.we.piccategory.bean.User;
import com.we.piccategory.model.UserModel;
import com.we.piccategory.mvp.BasePresenter;
import com.we.piccategory.mvp.IModel;
import com.we.piccategory.view.IAccountView;

/**
 * Created with Android Studio
 * User: 潘浩
 * School 南华大学
 * Date: 2017/5/23
 * Time: 12:47
 * Description:
 */
public class AccountPresenter extends BasePresenter<IAccountView> {

    public void loadUser() {
        model = new UserModel();

        model.load(new IModel.OnCompletedListener<User>() {

            @Override
            public void onCompleted(User data) {
                IAccountView iAccountView = viewRef.get();
                if (iAccountView != null) {
                    iAccountView.showUser(data);
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
