package com.p7.framework.async.request.distinct.thread;

import java.util.concurrent.ArrayBlockingQueue;

import com.p7.framework.async.request.distinct.cache.DistinctCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.p7.framework.async.request.distinct.DistinctRequest;

/** 
 * 处理请求线程
 * @ClassName: RequestProcessorThread 
 * @author yz
 * @date 2018年11月8日 下午6:38:47 
 */
public class RequestProcessorThread implements Runnable {

	private static final Logger LOGGER = LoggerFactory.getLogger(RequestProcessorThread.class);

	private ArrayBlockingQueue<String> queue = null;

	public RequestProcessorThread(ArrayBlockingQueue<String> queue) {
		this.queue = queue;
	}

	@Override
	public void run() {

		while (true) {
			try {
				String key = queue.take();
				DistinctRequest<?> request = (DistinctRequest<?>) DistinctCache.get(key);
				if (null != request) {
					LOGGER.info("处理请求：{}", request.getRouteId());
					// 此处可能丢失最新的消息
					DistinctCache.remove(key);
					request.process();
				} else {
					LOGGER.info("key:{} 请求已处理或请求已失效...", key);
				}
			} catch (Exception e) {
				LOGGER.error("RequestProcessorThread：{}", e.toString());
			}
		}
	}

}
