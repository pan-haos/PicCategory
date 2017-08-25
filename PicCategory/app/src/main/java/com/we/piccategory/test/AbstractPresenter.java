package com.we.piccategory.test;

/**
 * 作者：潘浩
 * 公司：圆周率网络科技
 * 时间：17-8-24
 */
public class AbstractPresenter implements IService{

    private IService mService;

    public AbstractPresenter(IService mService) {
        this.mService = mService;
    }

    @Override
    public void service() {
        //装饰者装饰被装饰者对象～
    }
}
