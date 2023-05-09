package org.github.xx.manager;

import org.github.xx.cache.Cache;
import org.github.xx.conf.MultiCacheProperties;

import java.util.Collection;

/**
 * 缓存管理器
 * 允许通过缓存名称来获的对应的 {@link Cache}.
 */
public interface CacheManager {

    /**
     * 根据缓存名称返回对应的{@link Collection}.
     *
     * @param name 缓存的名称 (不能为 {@code null})
     * @return 返回对应名称的Cache, 如果没找到返回 {@code null}
     */
    Cache getCache(String name);

    /**
     * 根据缓存名称返回对应的{@link Cache}，如果没有找到就新建一个并放到容器
     *
     * @param name                 缓存名称
     * @param multiCacheProperties 多级缓存配置(来源于spring)
     * @return {@link Cache}
     */
    Cache getCache(String name, MultiCacheProperties multiCacheProperties);

    /**
     * 获取所有缓存名称的集合
     *
     * @return 所有缓存名称的集合
     */
    Collection<String> getCacheNames();

    /**
     * 通过手动放入配置类去创建容器
     * @param name 容器{@link Cache}名称
     * @param multiCacheProperties 配置类
     * @return
     */
    Cache createCache(String name, MultiCacheProperties multiCacheProperties);

    /**
     * 通过spring boot application.yaml中的配置去创建
     * @param name 容器的名字
     * @return cache 对象
     */
    Cache createCache(String name);
}
