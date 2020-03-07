package com.p7.framework.async.request.concurrent;

import com.p7.framework.async.request.base.Request;
import com.p7.framework.async.request.base.RequestAsyncProcessService;
import com.p7.framework.async.request.base.RequestQueue;
import com.p7.framework.async.request.tools.RouteUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * 请求异步处理
 *
 * @author yz
 * @ClassName: ConcurrentRequestAsyncProcessServiceImpl
 * @date 2018年11月9日 下午3:12:52
 */
@Service("concurrentRequestAsyncProcessService")
public class ConcurrentRequestAsyncProcessServiceImpl extends RequestAsyncProcessService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConcurrentRequestAsyncProcessServiceImpl.class);

    @Override
    public void process(Request request) {

        try {
            RequestData queue = doRoutingQueue(request.getRouteId());
            queue.getQueues().put(request);
            ArrayBlockingQueue<Request> queues = queue.getQueues();
            queue.getThreadPool().submit(new ConcurrentRequestProcessor(queues));
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

        RequestQueue<RequestData> instance = RequestQueue.getInstance();

        int index = RouteUtil.doRoute(key, instance.queueSize() - 1);
        RequestData queue = instance.getQueue(index);
        LOGGER.info("key : {} ,  queueId : {} , queueSize : {}", key, index, queue.getQueues().size());
        return queue;
    }

}
