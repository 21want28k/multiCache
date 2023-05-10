package org.github.xx.manager;

import org.github.xx.cache.*;
import org.github.xx.conf.MultiCacheProperties;
import org.github.xx.plugins.MultiPluginManager;

public class MultiCacheManager extends AbstractCacheManager {
    @Override
    public Cache createCache(String name, MultiCacheProperties multiCacheProperties) {
        AbstractCache caffeineCache = new MyCaffeineCache(name, multiCacheProperties.getLocalCacheProperties());
        // 笔者能力有限，所以还是用的RedisTemplate，所以是和spring强依赖的关系。
        AbstractCache redisCache = new RedisCache(name);
        MultiPluginManager manager = new MultiPluginManager(multiCacheProperties.getPluginProperties());
        return new MultiCache(name, caffeineCache, redisCache, manager);
    }

//    public Cache createCache(String name) {
//        MyCaffeineCache caffeineCache = new MyCaffeineCache(name);
//        RedisCache redisCache = new RedisCache(name);
//        return new MultiCache(name, caffeineCache, redisCache);
//    }
}
