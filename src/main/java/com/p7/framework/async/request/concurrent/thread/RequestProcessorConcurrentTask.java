package com.p7.framework.async.request.concurrent.thread;

import com.p7.framework.async.request.base.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;

/** 
 * 处理请求线程
 * @ClassName: DistinctRequestProcessorThread
 * @author yz
 * @date 2018年11月8日 下午6:38:47 
 */
public class RequestProcessorConcurrentTask implements Runnable {

	private static final Logger LOGGER = LoggerFactory.getLogger(RequestProcessorConcurrentTask.class);

	private ArrayBlockingQueue<Request<?>> queue = null;

	public RequestProcessorConcurrentTask(ArrayBlockingQueue<Request<?>> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		try {
			Request<?> request = queue.take();
			LOGGER.info("处理请求：{} , queue size : {}", request.getRouteId(), queue.size());
			request.process();
		} catch (Exception e) {
			LOGGER.error("DistinctRequestProcessorThread：{}", e.toString());
		}
	}

}
