package com.p7.framework.async.request.common;

import com.p7.framework.async.request.base.BaseRequestProcessor;
import com.p7.framework.async.request.base.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;

/** 
 * 处理请求线程
 * @ClassName: RequestProcessor
 * @author yz
 * @date 2018年11月8日 下午6:38:47 
 */
public class RequestProcessor implements BaseRequestProcessor {

	private static final Logger LOGGER = LoggerFactory.getLogger(RequestProcessor.class);

	private ArrayBlockingQueue<Request> queue = null;

	public RequestProcessor(ArrayBlockingQueue<Request> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {

		while (true) {
			try {
				Request request = queue.take();
				LOGGER.info("处理请求：{}", request.getRouteId());
				request.process();
			} catch (Exception e) {
				LOGGER.error("DistinctRequestProcessor：{}", e.toString());
			}
		}
	}

}
