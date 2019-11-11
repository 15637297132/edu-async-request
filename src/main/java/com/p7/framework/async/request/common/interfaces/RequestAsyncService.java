package com.p7.framework.async.request.common.interfaces;

/**
 * @author Yangzhen
 * @Description 玩家的service要实现此接口
 * @date 2018-12-24 19:11
 **/
public interface RequestAsyncService<D> {

	/**
	 * 异步执行请求的方法，玩家需实现此方法，方法实现可以加事务
	 * @Title: asyncProcess 
	 * @param data  
	 * @date 2018年12月25日 上午9:25:17
	 * @author yz
	 */
    void asyncProcess(D data) ;
}
