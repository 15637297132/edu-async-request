package com.p7.framework.async.request.concurrent.service;

import com.p7.framework.async.request.common.interfaces.Request;
import com.p7.framework.async.request.common.interfaces.RequestAsyncProcessService;
import com.p7.framework.async.request.concurrent.thread.RequestData;
import com.p7.framework.async.request.concurrent.thread.RequestProcessorConcurrentTask;
import com.p7.framework.async.request.concurrent.thread.RequestQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 请求异步处理
 *
 * @author yz
 * @ClassName: RequestAsyncProcessServiceImpl
 * @date 2018年11月9日 下午3:12:52
 */
public class RequestAsyncProcessServiceImpl implements RequestAsyncProcessService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RequestAsyncProcessServiceImpl.class);

    @Override
    public void process(Request<?> request) {

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
    private RequestData doRoutingQueue(String key) {

        RequestQueue instance = RequestQueue.getInstance();

        int h;
        int hash = (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);

        // 对hash值取模，将hash值路由到指定的内存队列中，比如内存队列大小8
        // 用内存队列的数量对hash值取模之后，结果一定是在0~7之间
        int factor = instance.queueSize() - 1;
        int index = factor & hash;

        RequestData queue = instance.getQueue(index);
        LOGGER.info("key : {} ,  factor : {} , queueId : {} , queue size : {}", key,  factor, index, queue.getQueues().size());
        return queue;
    }

}
