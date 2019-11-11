package com.p7.framework.async.request.distinct.service;

import java.util.concurrent.ArrayBlockingQueue;

import com.p7.framework.async.request.distinct.cache.DistinctCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.p7.framework.async.request.common.interfaces.Request;
import com.p7.framework.async.request.common.interfaces.RequestAsyncProcessService;
import com.p7.framework.async.request.distinct.DistinctRequest;
import com.p7.framework.async.request.distinct.thread.RequestQueue;

/**
 * 请求异步处理，使用本地缓存去重
 * @ClassName: RequestAsyncProcessServiceImpl
 * @author yz
 * @date 2018年11月9日 下午3:12:52
 */
@Component("requestAsyncProcessService")
public class RequestAsyncProcessServiceImpl implements RequestAsyncProcessService {

	private static final Logger LOGGER = LoggerFactory.getLogger(RequestAsyncProcessServiceImpl.class);

	@Override
	public void process(Request<?> request) {

		try {
			ArrayBlockingQueue<String> queue = doRoutingQueue(request.getRouteId());
			DistinctRequest<?> distinctRequest = (DistinctRequest<?>) request;
			// 先放本地缓存再放队列，确保队列监听到key时，本地缓存可以获取到值
			DistinctCache.set(distinctRequest.getDistinctKey(), distinctRequest);
			queue.put(distinctRequest.getDistinctKey());
			LOGGER.info("当前队列有 {} 个元素（参考值）", queue.size());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 路由队列
	 * @Title: doRoutingQueue
	 * @param key
	 * @return
	 * @date 2018年11月12日 下午3:44:23
	 * @author yz
	 */
	private ArrayBlockingQueue<String> doRoutingQueue(String key) {

		RequestQueue instance = RequestQueue.getInstance();

		int h;
		int hash = (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);

		// 对hash值取模，将hash值路由到指定的内存队列中，比如内存队列大小8
		// 用内存队列的数量对hash值取模之后，结果一定是在0~7之间
		int factor = instance.queueSize() - 1;

		int index = factor & hash;

		LOGGER.info("key：{} , hash：{} , factor：{} , 所在队列：{}", key, hash, factor, index);
		ArrayBlockingQueue<String> queue = instance.getQueue(index);
		LOGGER.info("当前队列有 {} 个元素", queue.size());
		return queue;
	}

}
