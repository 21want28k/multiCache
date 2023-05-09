package org.github.xx.cache;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSON;
import com.github.benmanes.caffeine.cache.Cache;
import org.github.xx.conf.LocalCaffeineCacheConf;
import org.github.xx.properties.LocalCacheProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

import static java.util.Objects.requireNonNull;

public class MyCaffeineCache extends AbstractCache {
    protected static final Logger logger = LoggerFactory.getLogger(MyCaffeineCache.class);


    private Cache<String, Object> cache;

    /**
     * caffeine 一级缓存的配置
     */
    private LocalCacheProperties caffeineProperties;

    public MyCaffeineCache() {
    }

    /**
     * 使用name 和 application.yaml中的{@link LocalCacheProperties}配置，进行创建（依赖spring boot）
     *
     * @param name 容器名称
     */
    public MyCaffeineCache(String name) {
        super(name);
        this.cache = SpringUtil.getBean(Cache.class);
    }

    /**
     * 使用name 和 配置对象进行创建，完全不依赖spring boot
     *
     * @param name               容器名称
     * @param caffeineProperties caffeine配置对象
     */
    public MyCaffeineCache(String name, LocalCacheProperties caffeineProperties) {
        super(name);
        this.cache = new LocalCaffeineCacheConf().caffeineCache(caffeineProperties);
        this.caffeineProperties = caffeineProperties;
    }

    @Override
    public <T> T get(String key) {
        T value = (T) cache.getIfPresent(key);
        logger.debug("caffeine缓存get操作 name={} key={} 获取的value={}", getName(), key, value);
        return value;
    }

    /**
     * caffeineCache不允许空值的，虽然可以给空值单独做一个对象，但是这里还是采取默认。
     *
     * @param key   key组成 caffeine:${key}
     * @param value value
     */
    @Override
    public <T> void put(String key, T value) {
        requireNonNull(value);
        logger.debug("caffeine缓存put操作 name={} key={} value={}", getName(), key, JSON.toJSONString(value));
        this.cache.put(key, value);
    }

//    public <T> void put(String key, T value, long expire, TimeUnit timeUnit) {
//        requireNonNull(value);
//        logger.debug("caffeine缓存 key={} value={} expire={} timeUnit={}", getKey(key), JSON.toJSONString(value), expire, timeUnit);
//        this.cache.policy().expireVariably().ifPresent(stringObjectVarExpiration -> stringObjectVarExpiration.put(key, value, expire, timeUnit));
//    }


}
