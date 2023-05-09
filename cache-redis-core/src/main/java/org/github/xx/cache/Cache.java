package org.github.xx.cache;

/**
 * redisCache和本地Cache的接口
 */
public interface Cache {


    public <T> T get(String key);

    <T> void put(String key, T value);

}
