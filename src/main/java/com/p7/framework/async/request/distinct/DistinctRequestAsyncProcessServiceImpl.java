package com.p7.framework.async.request.distinct;

import java.util.concurrent.ArrayBlockingQueue;

import com.p7.framework.async.request.base.Request;
import com.p7.framework.async.request.base.RequestAsyncProcessService;
import com.p7.framework.async.request.distinct.cache.DistinctCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 请求异步处理，使用本地缓存去重
 * @ClassName: DistinctRequestAsyncProcessServiceImpl
 * @author yz
 * @date 2018年11月9日 下午3:12:52
 */
@Component("distinctRequestAsyncProcessService")
public class DistinctRequestAsyncProcessServiceImpl extends RequestAsyncProcessService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DistinctRequestAsyncProcessServiceImpl.class);

	@Override
	public void process(Request request) {
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

}
