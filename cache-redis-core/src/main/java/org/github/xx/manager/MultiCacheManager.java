package org.github.xx.manager;

import cn.hutool.extra.spring.SpringUtil;
import org.github.xx.cache.Cache;
import org.github.xx.cache.MultiCache;
import org.github.xx.cache.MyCaffeineCache;
import org.github.xx.cache.RedisCache;
import org.github.xx.conf.MultiCacheProperties;

public class MultiCacheManager extends AbstractCacheManager {
    @Override
    public Cache createCache(String name, MultiCacheProperties multiCacheProperties) {
        MyCaffeineCache caffeineCache = new MyCaffeineCache(name);
        RedisCache redisCache = new RedisCache(name);
        return new MultiCache(name, caffeineCache, redisCache);
    }

    public Cache createCache(String name) {
        MyCaffeineCache caffeineCache = new MyCaffeineCache(name);
        RedisCache redisCache = new RedisCache(name);
        return new MultiCache(name, caffeineCache, redisCache);
    }
}
