package com.p7.framework.async.request.base.impl;

import com.p7.framework.async.request.base.Request;
import com.p7.framework.async.request.base.RequestAsyncProcessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.ArrayBlockingQueue;

/** 
 * 请求异步处理
 * @ClassName: RequestAsyncProcessServiceImpl 
 * @author yz
 * @date 2018年11月9日 下午3:12:52 
 */
@Service("requestAsyncProcessService")
public class RequestAsyncProcessServiceImpl extends RequestAsyncProcessService {

	private static final Logger LOGGER = LoggerFactory.getLogger(RequestAsyncProcessServiceImpl.class);

	@Override
	public void process(Request request) {

		try {
			ArrayBlockingQueue<Request> queue = doRoutingQueue(request.getRouteId());
			queue.put(request);
			LOGGER.info("当前队列有 {} 个元素（参考值）", queue.size());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
