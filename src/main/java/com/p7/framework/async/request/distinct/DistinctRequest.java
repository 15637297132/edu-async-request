package com.p7.framework.async.request.distinct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.p7.framework.async.request.common.CommonRequest;
import com.p7.framework.async.request.common.interfaces.RequestAsyncService;

/**
 * @author Yangzhen
 * @Description 通用request，玩家的service实现需要实现RequestAsyncService
 * @see com.p7.framework.async.request.common.interfaces.RequestAsyncService#asyncProcess(D data)
 * @date 2018-12-24 19:39
 **/
public class DistinctRequest<D> extends CommonRequest<D> {

	private static final long serialVersionUID = -888247490114542248L;

	private static final Logger LOGGER = LoggerFactory.getLogger(DistinctRequest.class);

	/**
	 * 请求去重的key = routeId + 用户定义的key，用于本地缓存请求
	 */
	private String distinctKey;

	/**
	 * 构造器
	 * @param routeId 路由id，例如：deviceId + userId
	 * @param distinctKey 一类请求的key，去重队列必要参数，否则会出现问题，例如：
	 * @param data
	 * @param requestAsyncService
	 */
	public DistinctRequest(String routeId, String distinctKey, D data, RequestAsyncService<D> requestAsyncService) {
		super(routeId, data, requestAsyncService);
		this.distinctKey = routeId + "-" + distinctKey;
	}

	/**
	 * 请求去重的key = routeId + 用户定义的key，用于本地缓存请求
	 * @Title: getdistinctKey 
	 * @return  
	 * @date 2018年12月28日 下午3:29:58
	 * @author yz
	 */
	public String getDistinctKey() {
		return this.distinctKey;
	}

	@Override
	public void process() {
		LOGGER.info("正在处理的数据routeId : {} ，distinctKey : {}，数据 : {}", getRouteId(), this.distinctKey, data);
		requestAsyncService.asyncProcess(data);
		LOGGER.info("数据routeId : {} ，distinctKey : {} 已完成处理", getRouteId(), this.distinctKey);
	}
}
