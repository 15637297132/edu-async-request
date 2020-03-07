package com.p7.framework.async.request.base.processor;

import com.p7.framework.async.request.base.BaseRequestProcessor;
import com.p7.framework.async.request.base.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;

/** 
 * 处理请求线程
 * @ClassName: ConcurrentRequestProcessor
 * @author yz
 * @date 2018年11月8日 下午6:38:47 
 */
public class ConcurrentRequestProcessor implements BaseRequestProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConcurrentRequestProcessor.class);

	private ArrayBlockingQueue<Request> queue = null;

	public ConcurrentRequestProcessor(ArrayBlockingQueue<Request> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {
		try {
			Request<?> request = queue.take();
			LOGGER.info("处理请求：{} , queue size : {}", request.getRouteId(), queue.size());
			request.process();
		} catch (Exception e) {
			LOGGER.error("DistinctRequestProcessor：{}", e.toString());
		}
	}

}
