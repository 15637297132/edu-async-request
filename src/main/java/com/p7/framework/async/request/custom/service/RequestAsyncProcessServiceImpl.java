package com.p7.framework.async.request.custom.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.p7.framework.async.request.custom.interfaces.Request;
import com.p7.framework.async.request.custom.interfaces.RequestAsyncProcessService;
import com.p7.framework.async.request.custom.thread.RequestQueue;

import java.util.concurrent.ArrayBlockingQueue;

/** 
 * 请求异步处理
 * @ClassName: RequestAsyncProcessServiceImpl 
 * @author yz
 * @date 2018年11月9日 下午3:12:52 
 */
@Component("requestAsyncProcessService")
public class RequestAsyncProcessServiceImpl implements RequestAsyncProcessService {

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

	/**
	 * 路由队列
	 * @Title doRoutingQueue
	 * @param key
	 * @return  
	 * @date 2018年11月12日 下午3:44:23
	 * @author yz
	 */
	private ArrayBlockingQueue<Request> doRoutingQueue(String key) {

		RequestQueue instance = RequestQueue.getInstance();

		int h;
		int hash = (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);

		// 对hash值取模，将hash值路由到指定的内存队列中，比如内存队列大小8
		// 用内存队列的数量对hash值取模之后，结果一定是在0~7之间
		int factor = instance.queueSize() - 1;

		int index = factor & hash;

		LOGGER.info("key：{} , hash：{} , factor：{} , 所在队列：{}", key, hash, factor, index);

		return instance.getQueue(index);
	}

}
