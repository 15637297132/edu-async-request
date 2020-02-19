package com.p7.framework.async.request.tools;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RouteUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(RouteUtil.class);

    /**
     * 获取路由下标
     * 对hash值取模，将hash值路由到指定的内存队列中，比如内存队列大小8
     * 用内存队列的数量对hash值取模之后，结果一定是在0~7之间
     *
     * @param key
     * @param factor
     * @return
     */
    public static int doRoute(String key, int factor) {
        int h;
        int hash = (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
        LOGGER.info("key：{} , hash：{} , factor：{}", key, hash, factor);
        return factor & hash;
    }
}
