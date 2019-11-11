package com.p7.framework.async.request.custom;

import com.p7.framework.async.request.common.model.Data;

/**
 * @author Yangzhen
 * @Description
 * @date 2018-12-24 9:42
 **/
public class MyService {

    public void process(Data data) {
        System.out.println(data.getId() + "-" + data.getName());
    }
}
