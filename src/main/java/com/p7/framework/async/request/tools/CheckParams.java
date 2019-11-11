package com.p7.framework.async.request.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * @author Yangzhen
 * @Description 检查配置信息
 * @date 2018-12-26 11:29
 **/
public class CheckParams {

	private static final Logger LOGGER = LoggerFactory.getLogger(CheckParams.class);

	/**
	 * 检查线程池配置信息
	 * @Title: checkParams 
	 * @param threadPool
	 * @param queueNum
	 * @param perQueueSize  
	 * @date 2018年12月27日 下午8:08:35
	 * @author yz
	 */
	public static void checkParams(ThreadPoolTaskExecutor threadPool, int queueNum, int perQueueSize) {

		if (null == threadPool) {
			LOGGER.error("没有配置线程池...");
			throw new RuntimeException("没有配置线程池...");
		}

		if (!powerOfTwo(queueNum)) {
			LOGGER.error("队列数量必须是2的次幂...");
			throw new RuntimeException("队列数量必须是2的次幂...");
		}

		LOGGER.info("线程池核心线程数: {} ，队列数量：{}", threadPool.getCorePoolSize(), queueNum);
		if (threadPool.getCorePoolSize() < queueNum) {
			LOGGER.error("线程池核心线程数必须大于等于内存队列数量...");
			throw new RuntimeException("线程池核心线程数必须大于等于内存队列数量...");
		}

		LOGGER.info("每个内存队列的大小：{}", perQueueSize);
		if (perQueueSize <= 0) {
			LOGGER.error("内存队列大小必须大于0...");
			throw new RuntimeException("内存队列大小必须大于0...");
		}
	}

	/**
	 * 判断参数是否是2的次幂
	 * @Title: powerOfTwo 
	 * @param queueNum
	 * @return  
	 * @date 2018年12月27日 下午8:08:16
	 * @author yz
	 */
	public static boolean powerOfTwo(int queueNum) {

		LOGGER.info("队列数量 ：{} ...", queueNum);

		if (queueNum < 2) {
			return false;
		}
		if ((queueNum & queueNum - 1) == 0) {
			return true;
		}
		return false;
	}

	public static void checkParams(int queueNum, int perQueueSize) {

		if (!powerOfTwo(queueNum)) {
			LOGGER.error("队列数量必须是2的次幂...");
			throw new RuntimeException("队列数量必须是2的次幂...");
		}

		LOGGER.info("每个内存队列的大小：{}", perQueueSize);
		if (perQueueSize <= 0) {
			LOGGER.error("内存队列大小必须大于0...");
			throw new RuntimeException("内存队列大小必须大于0...");
		}
	}
}
