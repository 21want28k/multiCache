package org.github.xx.properties;

import org.github.xx.enums.ExpireModeEnum;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * 一级本地缓存配置项
 */
@ConfigurationProperties(prefix = "multicache.local")
public class LocalCacheProperties implements Serializable {

    /**
     * 缓存初始Size
     */
    private int initialCapacity = 10;

    /**
     * 缓存最大Size
     */
    private int maximumSize = 500;

    /**
     * 缓存有效时间
     */
    private int expireTime = 60000;

    /**
     * 缓存时间单位
     */
    private TimeUnit timeUnit = TimeUnit.MILLISECONDS;

    /**
     * 缓存过期策略，一共两种，默认是写，当然也可以自己去实现
     */
    private ExpireModeEnum expireMode = ExpireModeEnum.WRITE;

    public LocalCacheProperties() {
    }

    public LocalCacheProperties(int initialCapacity, int maximumSize, int expireTime, TimeUnit timeUnit, ExpireModeEnum expireMode) {
        this.initialCapacity = initialCapacity;
        this.maximumSize = maximumSize;
        this.expireTime = expireTime;
        this.timeUnit = timeUnit;
        this.expireMode = expireMode;
    }

    public int getInitialCapacity() {
        return initialCapacity;
    }

    public void setInitialCapacity(int initialCapacity) {
        this.initialCapacity = initialCapacity;
    }

    public int getMaximumSize() {
        return maximumSize;
    }

    public void setMaximumSize(int maximumSize) {
        this.maximumSize = maximumSize;
    }

    public int getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(int expireTime) {
        this.expireTime = expireTime;
    }

    public TimeUnit getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    public ExpireModeEnum getExpireMode() {
        return expireMode;
    }

    public void setExpireMode(ExpireModeEnum expireMode) {
        this.expireMode = expireMode;
    }

    public boolean isAllowNullValues() {
        return false;
    }
}
