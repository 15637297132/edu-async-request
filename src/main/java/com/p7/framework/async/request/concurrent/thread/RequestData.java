package com.p7.framework.async.request.concurrent.thread;

import com.p7.framework.async.request.base.Request;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * 请求处理
 *
 * @author Yangzhen
 * @date 2019/8/12 16:31
 **/
public class RequestData {

    private ArrayBlockingQueue<Request<?>> queues;
    private ThreadPoolTaskExecutor threadPool;

    public ArrayBlockingQueue<Request<?>> getQueues() {
        return queues;
    }

    public void setQueues(ArrayBlockingQueue<Request<?>> queues) {
        this.queues = queues;
    }

    public ThreadPoolTaskExecutor getThreadPool() {
        return threadPool;
    }

    public void setThreadPool(ThreadPoolTaskExecutor threadPool) {
        this.threadPool = threadPool;
    }

}
