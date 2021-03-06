package com.p7.framework.async.request.tools;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Yangzhen
 * @Description 单元测试类继承此类，重写code方法，定义自己的并发代码；重写data方法，在code方法中调用，实现数据的拼装
 * @date 2018-12-26 10:06
 **/
public abstract class AbstractConcurrentControl {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractConcurrentControl.class);

    public AtomicLong longCounter = new AtomicLong(0);

    public AtomicInteger intCounter = new AtomicInteger(0);

    /**
     * 默认并发线程数为2000个，子类可重写
     */
    public static final int DEFAULT_CONCURRENT_CONTROL = 2000;

    private CountDownLatch latch;

    private int concurrentThreadNum;

    private ThreadPoolExecutor threadPool;

    private BlockingQueue blockingQueue;

    public AbstractConcurrentControl() {
        this(DEFAULT_CONCURRENT_CONTROL);
    }

    public AbstractConcurrentControl(int concurrentThreadNum) {
        this.concurrentThreadNum = concurrentThreadNum;
        latch = new CountDownLatch(concurrentThreadNum);
        blockingQueue = new LinkedBlockingQueue<Runnable>();
        threadPool = new ThreadPoolExecutor(concurrentThreadNum, concurrentThreadNum, 0L, TimeUnit.MILLISECONDS, blockingQueue);
    }

    /**
     * 并发执行线程
     *
     * @Title: process
     * @date 2018年12月26日 上午11:19:20
     * @author yz
     */
    public final void process() {
        for (int i = 0; i < concurrentThreadNum; i++) {

            threadPool.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        latch.await();
                        code();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });

            // 最后一个线程时可以打个断点
            if (i == concurrentThreadNum - 1) {
                latch.countDown();
            } else {
                latch.countDown();
            }
        }
        lock();
    }

    /**
     * 并发代码
     *
     * @Title: code
     * @date 2018年12月26日 下午2:05:25
     * @author yz
     */
    protected abstract void code();

    /**
     * 并发数据
     *
     * @return
     * @Title: data
     * @date 2018年12月26日 下午2:06:14
     * @author yz
     */
    protected abstract <T> T data();

    /**
     * 阻塞主线程，防止JVM关闭，不建议使用Xxx.class.wait，可以使用TimeUnit.Seconds.sleep(200);
     * 如果使用TimeUnit.Seconds.sleep(200)，可能会出现异常，因为JVM已经关闭，而测试的线程可能没有执行完成
     *
     * @Title: lock
     * @date 2018年12月26日 下午6:55:03
     * @author yz
     */
    protected void lock() {

        if (this.threadPool == null) {
            return;
        }
        try {
            while (true) {
                if (latch.getCount() == 0 && this.threadPool.getQueue().size() <= 0) {
                    break;
                }
                TimeUnit.MILLISECONDS.sleep(500);
            }
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.threadPool.shutdown();
    }

}
