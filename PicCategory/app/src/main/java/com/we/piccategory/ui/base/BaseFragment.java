package com.we.piccategory.ui.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.we.piccategory.builder.DialogBuilder;
import com.we.piccategory.mvp.BasePresenter;

import java.lang.ref.SoftReference;

import butterknife.ButterKnife;

/**
 * Created by 86119 on 2017/3/30.
 */

public abstract class BaseFragment<V, T extends BasePresenter<V>> extends Fragment {

    protected T myPresenter;//presenter层的引用

    public DialogBuilder builder;

//    protected View rootView; //所有的fragment都会保留一个缓存的view，避免切换时重复加载

    protected SoftReference<View> rootRef;//软引用的缓存，用来缓存fragment
    // 同时内存过高时可以移除不必要的fragment缓存


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myPresenter = createPresenter();
        myPresenter.attachView((V) this);

        //创建并绑定dialog

        DialogBuilder builder = new DialogBuilder();
        builder.attachView(this.getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        int resId = initRes();
        View view;
        if (rootRef == null) {
            view = inflater.inflate(resId, null);
            //软引用存住该view
            rootRef = new SoftReference<>(view);
            ButterKnife.inject(this, view);
            init();
        } else {
            view = rootRef.get();
            if (view != null) {
                ViewGroup parent = (ViewGroup) view.getParent();
                if (parent != null) {
                    parent.removeView(view);
                }
            }

        }
        return view;

//        if (rootView == null) {
//            rootView = inflater.inflate(resId, null);
//            ButterKnife.inject(this, rootView);
//            init();
//        } else {
//            ViewGroup parent = (ViewGroup) rootView.getParent();
//            if (parent != null) {
//                parent.removeView(rootView);
//            }
//        }
//        return rootView;
    }


    /**
     * 返回资源的id
     *
     * @return 资源id
     */
    protected abstract int initRes();

    protected abstract void init();


//    protected abstract void doInBack();
//
//    protected abstract void onFinish();

    @Override
    public void onDestroy() {
        super.onDestroy();
        myPresenter.detachView();
        if (builder != null) {
            builder.detachView();
        }
        rootRef.clear();
    }

    /**
     * 创建presenter对象
     *
     * @return 子类中的实例presenter对象
     */
    public abstract T createPresenter();

//    protected static class BaseTask extends AsyncTask<Void, Integer, Void> {
//
//        private WeakReference<BaseFragment> viewRef;
//
//        public BaseTask(BaseFragment fragment) {
//            this.viewRef = new WeakReference<>(fragment);
//        }
//
//        @Override
//        protected Void doInBackground(Void... params) {
//            BaseFragment fragment = viewRef.get();
//            if (fragment != null) {
//                fragment.doInBack();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(Void aVoid) {
//            BaseFragment fragment = viewRef.get();
//            if (fragment != null) {
//                fragment.onFinish();
//            }
//        }
//    }


}
