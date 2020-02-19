package com.p7.framework.async.request.concurrent.service;

import com.p7.framework.async.request.base.Request;
import com.p7.framework.async.request.base.RequestAsyncProcessService;
import com.p7.framework.async.request.concurrent.thread.RequestData;
import com.p7.framework.async.request.concurrent.thread.RequestProcessorConcurrentTask;
import com.p7.framework.async.request.concurrent.thread.RequestQueue;
import com.p7.framework.async.request.tools.RouteUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 请求异步处理
 *
 * @author yz
 * @ClassName: RequestAsyncProcessServiceImpl
 * @date 2018年11月9日 下午3:12:52
 */
public class RequestAsyncProcessServiceImpl extends RequestAsyncProcessService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestAsyncProcessServiceImpl.class);

    @Override
    public void process(Request request) {

        try {
        	RequestData queue = doRoutingQueue(request.getRouteId());
        	queue.getQueues().put(request);
        	queue.getThreadPool().submit(new RequestProcessorConcurrentTask(queue.getQueues()));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 路由队列
     *
     * @param key
     * @return
     * @Title: doRoutingQueue
     * @date 2018年11月12日 下午3:44:23
     * @author yz
     */
    public RequestData doRoutingQueue(String key) {

        RequestQueue instance = RequestQueue.getInstance();

        int index = RouteUtil.doRoute(key, instance.queueSize() - 1);
        RequestData queue = instance.getQueue(index);
        LOGGER.info("key : {} ,  queueId : {} , queueSize : {}", key , index, queue.getQueues().size());
        return queue;
    }

}
