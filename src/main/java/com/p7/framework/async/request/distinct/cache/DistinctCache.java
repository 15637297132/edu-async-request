package com.p7.framework.async.request.distinct.cache;

import java.util.concurrent.ConcurrentHashMap;

public class DistinctCache {

    private static ConcurrentHashMap cache = null;

    static {
        cache = new ConcurrentHashMap();
    }

    public static void set(String key, Object obj) {
        cache.put(key, obj);
    }

    public static Object get(String key) {
        return cache.get(key);
    }

    public static void remove(String key) {
        cache.remove(key);
    }
}
