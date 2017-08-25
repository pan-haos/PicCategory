package com.we.piccategory.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 作者：潘浩
 * 公司：圆周率网络科技
 * 时间：17-8-24
 */
public class Client {
    IService mService;

    public void onCreate() {
        mService = new Presenter();
    }


    public void loginV() {



    }

    public void loginQQ() {

    }

    public void login() {

    }

    class proxyHandler implements InvocationHandler {
        private IService service;

        public proxyHandler(IService iService) {
            this.service = iService;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            //访问控制～

            return method.invoke(service);

        }

    }
}
