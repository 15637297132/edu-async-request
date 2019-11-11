package com.p7.framework.async.request.concurrent.thread;

import java.util.ArrayList;
import java.util.List;

/** 
 * 请求队列
 * @ClassName: RequestQueue 
 * @author yz
 * @date 2018年11月8日 下午6:35:19 
 */
public class RequestQueue {

	private List<RequestData> requestDataList = new ArrayList<RequestData>();

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
	 * @param data
	 * @date 2018年11月12日 上午10:10:42
	 * @author yz
	 */
	public void addQueue(RequestData data) {
		requestDataList.add(data);
	}

	/**
	 * 队列集合的大小
	 * @Title: queueSize 
	 * @return  
	 * @date 2018年11月12日 上午10:10:39
	 * @author yz
	 */
	public int queueSize() {
		return requestDataList.size();
	}

	/**
	 * 根据集合下表获取队列
	 * @Title: getQueue 
	 * @param index
	 * @return  
	 * @date 2018年11月12日 上午10:10:35
	 * @author yz
	 */
	public RequestData getQueue(int index) {
		return requestDataList.get(index);
	}

}
