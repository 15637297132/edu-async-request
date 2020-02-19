package com.p7.framework.async.request.base;

import com.p7.framework.async.request.base.impl.DistinctRequestAsyncProcessServiceImpl;
import com.p7.framework.async.request.tools.RouteUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * 请求异步处理，默认实现
 * @see com.p7.framework.async.request.base.impl.RequestAsyncProcessServiceImpl
 * @see com.p7.framework.async.request.base.impl.DistinctRequestAsyncProcessServiceImpl
 * @author yz
 * @ClassName: RequestAsyncProcessService
 * @date 2018年11月9日 下午3:13:50
 */
public abstract class RequestAsyncProcessService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestAsyncProcessService.class);

    /**
     * 处理请求
     *
     * @param request
     * @Title: asyncProcess
     * @date 2018年11月12日 下午3:41:43
     * @author yz
     */
    public abstract void process(Request request);

    /**
     * 路由队列
     * @Title: doRoutingQueue
     * @param key
     * @return
     * @date 2018年11月12日 下午3:44:23
     * @author yz
     */
    protected <T> T doRoutingQueue(String key) {
        RequestQueue instance = RequestQueue.getInstance();
        int index = RouteUtil.doRoute(key, instance.queueSize() - 1);
        ArrayBlockingQueue<String> queue = instance.getQueue(index);
        LOGGER.info("key : {} ,  queueId : {} , queueSize : {}", key , index, queue.size());
        return (T) queue;
    }
}
