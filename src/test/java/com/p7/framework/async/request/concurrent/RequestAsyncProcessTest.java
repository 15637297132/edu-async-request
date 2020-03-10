package com.p7.framework.async.request.concurrent;

import com.p7.framework.async.request.base.RequestAsyncProcessService;
import com.p7.framework.async.request.base.RequestQueue;
import com.p7.framework.async.request.common.CommonRequest;
import com.p7.framework.async.request.tools.AbstractConcurrentControl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-async-request-concurrent.xml")
public class RequestAsyncProcessTest extends AbstractConcurrentControl {

    public RequestAsyncProcessTest() {
        super(30);
    }

    @Resource(name = "concurrentRequestAsyncProcessService")
    private RequestAsyncProcessService requestAsyncProcessService;
    private MyService myService = new MyService();

    @Override
    protected void code() {
        Integer o = data();
        CommonRequest<String> commonRequest = new CommonRequest<>(o.toString(), o.toString(), myService);
        requestAsyncProcessService.process(commonRequest);
    }

    @Override
    protected <T> T data() {
        int i = intCounter.incrementAndGet();
        return (T) Integer.valueOf(i);
    }

    @Override
    public void lock() {
        synchronized (this.getClass()) {
            try {
                RequestQueue<RequestData> requestQueue = RequestQueue.getInstance();
                int i = requestQueue.queueSize();
                while (true) {
                    for (int j = 0; j < i; j++) {
                        ThreadPoolTaskExecutor threadPool = requestQueue.getQueue(j).getThreadPool();
                        System.out.println("current index " + j + " , current active count " + threadPool.getActiveCount());
                    }
                    TimeUnit.SECONDS.sleep(5);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void test() {
        process();
    }
}