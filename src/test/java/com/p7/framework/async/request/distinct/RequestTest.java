package com.p7.framework.async.request.distinct;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import com.p7.framework.async.request.base.Request;
import com.p7.framework.async.request.base.RequestAsyncProcessService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.p7.framework.async.request.common.model.Data;
import com.p7.framework.async.request.common.service.MyService;
import com.p7.framework.async.request.tools.AbstractConcurrentControl;

/**
 * @author Yangzhen
 * @Description
 * @date 2018-12-24 10:01
 **/
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/spring-async-request-distinct.xml" })
public class RequestTest extends AbstractConcurrentControl {

	@Resource(name = "distinctRequestAsyncProcessService")
	private RequestAsyncProcessService requestAsyncProcessService;

	private MyService myService = new MyService();

	@Test
	public void test() {
		process();
	}

	@Override
	public void code() {
		Data data = (Data) data();

		Request<Data> request = new DistinctRequest<Data>(data.getId() + "", "code", data, myService);

		requestAsyncProcessService.process(request);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T data() {
		Data data = new Data();
		String name = "a";
		data.setId(6);
		data.setName(name + ":" + 6);
		return (T) data;
	}

}
