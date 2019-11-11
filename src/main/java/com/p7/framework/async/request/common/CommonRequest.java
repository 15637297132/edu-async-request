package com.p7.framework.async.request.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.p7.framework.async.request.common.interfaces.Request;
import com.p7.framework.async.request.common.interfaces.RequestAsyncService;

import java.io.Serializable;

/**
 * @author Yangzhen
 * @Description 通用request，玩家的service实现需要实现RequestAsyncService
 * @see com.p7.framework.async.request.common.interfaces.RequestAsyncService#asyncProcess(D data)
 * @date 2018-12-24 19:39
 **/
public class CommonRequest<D> implements Request<D>, Serializable {

	private static final long serialVersionUID = -842955304608772510L;

	private static final Logger LOGGER = LoggerFactory.getLogger(CommonRequest.class);

	/**
	 * 标识，用于内存队列的
	 */
	protected String routeId;

	/**
	 * 数据
	 */
	protected D data;

	/**
	 * 实现类
	 */
	protected RequestAsyncService<D> requestAsyncService;

	public CommonRequest(String routeId, D data, RequestAsyncService<D> requestAsyncService) {
		this.requestAsyncService = requestAsyncService;
		this.data = data;
		this.routeId = routeId;
	}

	@Override
	public String getRouteId() {
		return routeId;
	}

	@Override
	public void process() {
		LOGGER.info("正在处理的数据id : {} ，数据 : {}", this.routeId, this.data);
		requestAsyncService.asyncProcess(data);
		LOGGER.info("数据id : {} ，数据 : {} 已完成处理", this.routeId, this.data);
	}
}
