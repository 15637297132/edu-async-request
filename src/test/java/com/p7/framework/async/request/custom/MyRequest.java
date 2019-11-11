package com.p7.framework.async.request.custom;

import com.p7.framework.async.request.common.model.Data;
import com.p7.framework.async.request.custom.interfaces.Request;

/**
 * @author Yangzhen
 * @Description
 * @date 2018-12-24 9:39
 **/
public class MyRequest implements Request {

	private MyService myService;

	private Data data;

	private String id;

	public MyRequest(String id, Data data, MyService myService) {
		this.myService = myService;
		this.data = data;
		this.id = id;
	}

	@Override
	public void process() {
		myService.process(data);
	}

	@Override
	public String getRouteId() {
		// TODO Auto-generated method stub
		return id;
	}
}
