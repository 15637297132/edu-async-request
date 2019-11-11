package com.p7.framework.async.request.common.interfaces;

/**
 * 请求异步处理，RequestAsyncProcessServiceImpl为默认实现
 * @see com.p7.framework.async.request.common.service.RequestAsyncProcessServiceImpl
 * @author yz
 * @ClassName: RequestAsyncProcessService
 * @date 2018年11月9日 下午3:13:50
 */
public interface RequestAsyncProcessService {

    /**
     * 处理请求
     *
     * @param request
     * @Title: asyncProcess
     * @date 2018年11月12日 下午3:41:43
     * @author yz
     */
    void process(Request<?> request);
}
