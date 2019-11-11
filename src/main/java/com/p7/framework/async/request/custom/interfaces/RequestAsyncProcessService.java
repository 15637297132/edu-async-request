package com.p7.framework.async.request.custom.interfaces;

/**
 * 请求异步处理
 * @ClassName: RequestAsyncProcessService 
 * @author yz
 * @date 2018年11月9日 下午3:13:50 
 */
public interface RequestAsyncProcessService {

	/**
	 * 处理请求
	 * @Title: asyncProcess
	 * @param request  
	 * @date 2018年11月12日 下午3:41:43
	 * @author yz
	 */
	void process(Request request);
}
