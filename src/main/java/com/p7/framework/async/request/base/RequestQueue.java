package com.p7.framework.async.request.base;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

/** 
 * 请求队列
 * @ClassName: RequestQueue 
 * @author yz
 * @date 2018年11月8日 下午6:35:19 
 */
public class RequestQueue {

	private List<ArrayBlockingQueue> queues = new ArrayList<>();

	private RequestQueue() {

	}

	private static class Singleton {

		private static RequestQueue requestQueue = null;
		static {
			requestQueue = new RequestQueue();
		}

		public static RequestQueue getInstance() {
			return requestQueue;
		}

	}

	public static RequestQueue getInstance() {
		return Singleton.getInstance();
	}

	/**
	 * 添加队列到集合
	 * @Title: addQueue 
	 * @param queue  
	 * @date 2018年11月12日 上午10:10:42
	 * @author yz
	 */
	public void addQueue(ArrayBlockingQueue queue) {
		queues.add(queue);
	}

	/**
	 * 队列集合的大小
	 * @Title: queueSize 
	 * @return  
	 * @date 2018年11月12日 上午10:10:39
	 * @author yz
	 */
	public int queueSize() {
		return queues.size();
	}

	/**
	 * 根据集合下表获取队列
	 * @Title: getQueue 
	 * @param index
	 * @return  
	 * @date 2018年11月12日 上午10:10:35
	 * @author yz
	 */
	public ArrayBlockingQueue getQueue(int index) {
		return queues.get(index);
	}

}
