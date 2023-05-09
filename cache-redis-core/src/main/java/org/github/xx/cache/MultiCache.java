package org.github.xx.cache;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MultiCache extends AbstractCache {
    Logger logger = LoggerFactory.getLogger(MultiCache.class);
    private AbstractCache caffeine;
    private AbstractCache redisCache;

    public MultiCache(String name, AbstractCache caffeine, AbstractCache redisCache) {
        super(name);
        this.caffeine = caffeine;
        this.redisCache = redisCache;
    }



    @Override
    public <T> T get(String key) {
        // 先查一级缓存
        T result = caffeine.get(key);
        if (result != null) {
            return result;
        }

        // 再查二级缓存
        result =  this.redisCache.get(key);

        // 判断是不是热key
        return null;
    }

    @Override
    public <T> void put(String key, T value) {

    }


    public AbstractCache getCaffeine() {
        return caffeine;
    }

    public AbstractCache getRedisCache() {
        return redisCache;
    }
}
