package org.github.xx.manager;

import org.github.xx.cache.Cache;
import org.github.xx.conf.MultiCacheProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 公共的实现 {@link CacheManager}，也就是模板方法，把公共的部分抽出来。
 * @author 肖鑫
 */
public abstract class AbstractCacheManager implements CacheManager {

    private Logger logger = LoggerFactory.getLogger(AbstractCacheManager.class);
    /**
     * 管理的容器，外层是两级缓存的name，可以根据不同的业务场景创建一个不同的cache
     */
    private final ConcurrentMap<String, Cache> cacheContainer = new ConcurrentHashMap<>();

    public Cache getCache(String name) {
        return this.cacheContainer.get(name);
    }

    /**
     * 懒加载的方式，当第一次获取{@link Cache}的时候才创建对应的cache
     *
     * @param name                 缓存名称
     * @param multiCacheProperties 多级缓存配置(来源于spring)
     * @return 返回对饮的cache
     */
    @Override
    public Cache getCache(String name, MultiCacheProperties multiCacheProperties) {
        // DCL 双重检测法实现高效并发，防止创建多个cache
        Cache cache = this.cacheContainer.get(name);
        if (cache != null) {
            return cache;
        }
        synchronized (cacheContainer) {
            cache = this.cacheContainer.get(name);
            if (cache == null) {
                Cache mutiCacheWrapper = new CacheWrapper(this.createCache(name));
                this.cacheContainer.put(name, mutiCacheWrapper);
                return mutiCacheWrapper;
            }
            return cache;
        }
    }

    /**
     * 获取缓存容器的所有名称{@link AbstractCacheManager#cacheContainer cacheContainer}
     *
     * @return {@link Cache} 的所有名称
     */
    @Override
    public Collection<String> getCacheNames() {
        return cacheContainer.keySet();
    }

    /**
     * 包装一下，装逼用，哈哈哈哈哈哈哈
     */
    public static class CacheWrapper implements Cache {
        Cache cache;

        public CacheWrapper() {
        }

        public CacheWrapper(Cache cache) {
            this.cache = cache;
        }

        @Override
        public <T> T get(String key) {
            return cache.get(key);
        }

        @Override
        public <T> void put(String key, T value) {
            cache.put(key, value);
        }

        @Override
        public String getKey(String key) {
            return cache.getKey(key);
        }
    }
}
