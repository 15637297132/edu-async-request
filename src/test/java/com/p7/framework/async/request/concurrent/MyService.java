package com.p7.framework.async.request.concurrent;


import com.p7.framework.async.request.common.RequestAsyncService;

/**
 * @author Yangzhen
 * @Description
 * @date 2019-08-12 15:21
 **/
public class MyService implements RequestAsyncService<String> {

    @Override
    public void asyncProcess(String data) {
        System.out.println("处理数据..." + data);
    }
}

