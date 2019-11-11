package com.p7.framework.async.request.custom.thread;

import java.util.concurrent.ArrayBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.p7.framework.async.request.custom.interfaces.Request;

/** 
 * 处理请求线程
 * @ClassName: RequestProcessorThread 
 * @author yz
 * @date 2018年11月8日 下午6:38:47 
 */
public class RequestProcessorThread implements Runnable {

	private static final Logger LOGGER = LoggerFactory.getLogger(RequestProcessorThread.class);

	private ArrayBlockingQueue<Request> queue = null;

	public RequestProcessorThread(ArrayBlockingQueue<Request> queue) {
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
				LOGGER.error("RequestProcessorThread：{}", e.toString());
			}
		}

	}

}
