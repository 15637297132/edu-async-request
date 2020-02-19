package com.p7.framework.async.request.base.processor;

import com.p7.framework.async.request.distinct.DistinctRequest;
import com.p7.framework.async.request.distinct.cache.DistinctCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;

/** 
 * 处理请求线程
 * @ClassName: DistinctRequestProcessorThread
 * @author yz
 * @date 2018年11月8日 下午6:38:47 
 */
public class DistinctRequestProcessorThread implements Runnable {

	private static final Logger LOGGER = LoggerFactory.getLogger(DistinctRequestProcessorThread.class);

	private ArrayBlockingQueue<String> queue = null;

	public DistinctRequestProcessorThread(ArrayBlockingQueue<String> queue) {
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
				LOGGER.error("DistinctRequestProcessorThread：{}", e.toString());
			}
		}
	}

}
