package com.p7.framework.async.request.common.interfaces;

/**
 * 请求接口，玩家可以直接使用CommonRequest
 * @see com.p7.framework.async.request.common.CommonRequest<D>
 * @ClassName: Request 
 * @author yz
 * @date 2018年11月8日 下午6:37:08 
 */
public interface Request<D> {

	/**
	 * 请求路由到队列的id，用于hash取模确定请求所属的RequestQueue#queues的下标
	 * @see com.p7.framework.async.request.common.thread.RequestQueue#queues
	 * @see
	 * @Title: getRouteId
	 * @return  
	 * @date 2018年11月12日 下午3:38:36
	 * @author yz
	 */
	String getRouteId();

	/**
	 * 处理请求
	 * @Title: asyncProcess
	 * @date 2018年11月12日 下午3:39:50
	 * @author yz
	 */
	void process();
}
