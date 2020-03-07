package com.p7.framework.async.request.concurrent;

import com.p7.framework.async.request.base.Request;
import com.p7.framework.async.request.tools.CheckParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import com.p7.framework.async.request.base.RequestQueue;

/**
 * 请求处理线程池
 *
 * @author yz
 * @ClassName: RequestProcessorThreadPool
 * @date 2018年11月8日 下午6:38:59
 */
public class ConcurrentRequestProcessorThreadPool {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConcurrentRequestProcessorThreadPool.class);


    /**
     * 内存队列的数量，必须为2的次幂
     */
    private int queueNum;

    /**
     * 每个内存队列的大小
     */
    private int perQueueSize;

    private String threadNamePrefix = "concurrentConsume";

    private int corePoolSize = 1;

    private int maxPoolSize = Integer.MAX_VALUE;

    private int keepAliveSeconds = 60;

    private boolean allowCoreThreadTimeOut = false;

    private int queueCapacity = Integer.MAX_VALUE;

    private RejectedExecutionHandler rejectedExecutionHandler = new java.util.concurrent.ThreadPoolExecutor.DiscardOldestPolicy();

    /**
     * 初始化内存队列，spring加载此bean后立即执行的方法
     *
     * @Title: init
     * @date 2018年12月25日 上午9:29:11
     * @author yz
     */
    public void init() {
        CheckParams.checkParams(queueNum, perQueueSize);
        RequestQueue requestQueue = RequestQueue.getInstance();
        for (int i = 0; i < queueNum; i++) {
            ArrayBlockingQueue<Request> queue = new ArrayBlockingQueue<Request>(perQueueSize);
            ThreadPoolTaskExecutor threadPool = threadPoolTaskExecutor(i);
            RequestData requestData = new RequestData();
            requestData.setQueues(queue);
            requestData.setThreadPool(threadPool);
            requestQueue.addQueue(requestData);
        }
        LOGGER.info("内存队列初始化完成...");
    }

    public Integer getQueueNum() {
        return queueNum;
    }

    public void setQueueNum(Integer queueNum) {
        this.queueNum = queueNum;
    }

    public Integer getPerQueueSize() {
        return perQueueSize;
    }

    public void setPerQueueSize(Integer perQueueSize) {
        this.perQueueSize = perQueueSize;
    }

    public String getThreadNamePrefix() {
        return threadNamePrefix;
    }

    public void setThreadNamePrefix(String threadNamePrefix) {
        this.threadNamePrefix = threadNamePrefix;
    }

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public int getKeepAliveSeconds() {
        return keepAliveSeconds;
    }

    public void setKeepAliveSeconds(int keepAliveSeconds) {
        this.keepAliveSeconds = keepAliveSeconds;
    }

    public boolean isAllowCoreThreadTimeOut() {
        return allowCoreThreadTimeOut;
    }

    public void setAllowCoreThreadTimeOut(boolean allowCoreThreadTimeOut) {
        this.allowCoreThreadTimeOut = allowCoreThreadTimeOut;
    }

    public int getQueueCapacity() {
        return queueCapacity;
    }

    public void setQueueCapacity(int queueCapacity) {
        this.queueCapacity = queueCapacity;
    }

    public RejectedExecutionHandler getRejectedExecutionHandler() {
        return rejectedExecutionHandler;
    }

    public void setRejectedExecutionHandler(
            RejectedExecutionHandler rejectedExecutionHandler) {
        this.rejectedExecutionHandler = rejectedExecutionHandler;
    }

    public void setQueueNum(int queueNum) {
        this.queueNum = queueNum;
    }

    public void setPerQueueSize(int perQueueSize) {
        this.perQueueSize = perQueueSize;
    }

    private ThreadPoolTaskExecutor threadPoolTaskExecutor(int i) {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        //如果池中的实际线程数小于corePoolSize,无论是否其中有空闲的线程，都会给新的任务产生新的线程
        taskExecutor.setCorePoolSize(corePoolSize);
        //连接池中保留的最大连接数。
        taskExecutor.setMaxPoolSize(maxPoolSize);
        //queueCapacity 线程池所使用的缓冲队列
        taskExecutor.setQueueCapacity(queueCapacity);
        taskExecutor.setKeepAliveSeconds(keepAliveSeconds);
        taskExecutor.setAllowCoreThreadTimeOut(allowCoreThreadTimeOut);
        taskExecutor.setRejectedExecutionHandler(rejectedExecutionHandler);
        //强烈建议一定要给线程起一个有意义的名称前缀，便于分析日志
        taskExecutor.setThreadNamePrefix(threadNamePrefix + " Thread-" + i);
        taskExecutor.initialize();

        return taskExecutor;
    }
}
