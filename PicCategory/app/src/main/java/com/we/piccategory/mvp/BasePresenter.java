package com.we.piccategory.mvp;

import com.we.piccategory.model.IModelImpl;

import java.lang.ref.WeakReference;


public abstract class BasePresenter<T> {

    protected WeakReference<T> viewRef;
    protected IModel model = new IModelImpl();


    public void attachView(T view) {
        viewRef = new WeakReference<T>(view);
    }

    public void detachView() {
        if (viewRef != null) {
            viewRef.clear();
            viewRef = null;
        }
    }

    protected T getView() {
        return viewRef.get();
    }


}
