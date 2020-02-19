package com.p7.framework.async.request.custom;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import com.p7.framework.async.request.base.Request;
import com.p7.framework.async.request.base.RequestAsyncProcessService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.p7.framework.async.request.common.model.Data;
import com.p7.framework.async.request.tools.AbstractConcurrentControl;

/**
 * @author Yangzhen
 * @Description
 * @date 2018-12-24 10:01
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-async-request-base.xml" })
public class RequestTest extends AbstractConcurrentControl{

	@Resource
	private RequestAsyncProcessService requestAsyncProcessService;

	private MyService myService = new MyService();

	
	@Test
	public void test() {
		process();
	}

	@Override
	public void code() {
		Data data = (Data) data();

		Request request = new MyRequest(data.getId() + "", data, myService);

		requestAsyncProcessService.process(request);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T data() {
		Data data = new Data();
		String name = "a";
		int i = intCounter.incrementAndGet();
		data.setId(i);
		data.setName(name + ":" + i);
		return (T) data;
	}

	@Override
	public void lock() {
		try {
			TimeUnit.SECONDS.sleep(20);
		} catch (Exception e) {
		}
	}
}
