package com.p7.framework.async.request.common.thread;

import java.util.concurrent.ArrayBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.p7.framework.async.request.common.interfaces.Request;
import com.p7.framework.async.request.tools.CheckParams;

/** 
 * 请求处理线程池
 * @ClassName: RequestProcessorThreadPool 
 * @author yz
 * @date 2018年11月8日 下午6:38:59 
 */
public class RequestProcessorThreadPool {

	private static final Logger LOGGER = LoggerFactory.getLogger(RequestProcessorThreadPool.class);

	/**
	 * 线程池
	 */
	private ThreadPoolTaskExecutor threadPool;

	/**
	 * 内存队列的数量，必须为2的次幂
	 */
	private int queueNum;

	/**
	 * 每个内存队列的大小
	 */
	private int perQueueSize;

	/**
	 * 初始化内存队列，spring加载此bean后立即执行的方法
	 * @Title: init   
	 * @date 2018年12月25日 上午9:29:11
	 * @author yz
	 */
	public void init() {
		CheckParams.checkParams(threadPool,queueNum,perQueueSize);
		RequestQueue requestQueue = RequestQueue.getInstance();
		for (int i = 0; i < queueNum; i++) {
			ArrayBlockingQueue<Request<?>> queue = new ArrayBlockingQueue<Request<?>>(perQueueSize);
			requestQueue.addQueue(queue);
			threadPool.submit(new RequestProcessorThread(queue));
		}
		LOGGER.info("内存队列初始化完成...");
	}

	public ThreadPoolTaskExecutor getThreadPool() {
		return threadPool;
	}

	public void setThreadPool(ThreadPoolTaskExecutor threadPool) {
		this.threadPool = threadPool;
	}

	public Integer getQueueNum() {
		return queueNum;
	}

	public void setQueueNum(Integer queueNum) {
		this.queueNum = queueNum;
	}

	public Integer getPerQueueSize() {
		return perQueueSize;
	}

	public void setPerQueueSize(Integer perQueueSize) {
		this.perQueueSize = perQueueSize;
	}

}
