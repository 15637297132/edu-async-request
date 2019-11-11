package com.p7.framework.async.request.custom.interfaces;

/** 
 * 请求统一接口
 * @ClassName: Request 
 * @author yz
 * @date 2018年11月8日 下午6:37:08 
 */
public interface Request {

	/**
	 * 请求的唯一标识，用于hash取模确定请求所属的RequestQueue#queues的下标
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
