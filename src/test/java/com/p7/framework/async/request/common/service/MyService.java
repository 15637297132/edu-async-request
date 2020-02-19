package com.p7.framework.async.request.common.service;

import com.p7.framework.async.request.common.RequestAsyncService;
import com.p7.framework.async.request.common.model.Data;

/**
 * @author Yangzhen
 * @Description
 * @date 2018-12-24 9:42
 **/
public class MyService implements RequestAsyncService<Data> {

	@Override
	public void asyncProcess(Data data) {
		System.out.println(data.getId() + "-" + data.getName());
	}
}
