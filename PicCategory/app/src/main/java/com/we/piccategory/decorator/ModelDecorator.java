package com.we.piccategory.decorator;

import com.we.piccategory.mvp.IModel;

/**
 * Created by 86119 on 2017/4/18.
 */

public class ModelDecorator implements IModel {
    private IModel model;

    public ModelDecorator(IModel model) {
        this.model = model;
    }

    @Override
    public void load(OnCompletedListener listener) {
        this.model.load(listener);
    }
}
