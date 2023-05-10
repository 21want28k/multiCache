package org.github.xx.cache;

import org.github.xx.plugins.MultiPluginManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MultiCache extends AbstractCache {
    Logger logger = LoggerFactory.getLogger(MultiCache.class);
    private AbstractCache caffeine;
    private AbstractCache redisCache;

    private MultiPluginManager pluginManager;

    public MultiCache(String name, AbstractCache caffeine, AbstractCache redisCache, MultiPluginManager pluginManager) {
        super(name);
        this.caffeine = caffeine;
        this.redisCache = redisCache;
        this.pluginManager = pluginManager;
    }


    @Override
    public <T> T get(String key) {
        // 先查一级缓存
        T result = caffeine.get(key);
        if (result != null) {
            return result;
        }

        // 再查二级缓存
        result = this.redisCache.get(key);

        // 通过插件去判断key，比如缓存空key等，hotkey自动存进本地缓存中，大key问题
        pluginManager.getFirst().handle(key);
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
