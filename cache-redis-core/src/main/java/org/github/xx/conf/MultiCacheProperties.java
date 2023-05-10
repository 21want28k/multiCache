package org.github.xx.conf;

import org.github.xx.properties.LocalCacheProperties;
import org.github.xx.properties.PluginProperties;
import org.github.xx.properties.RedisProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MultiCacheProperties {

    @Autowired
    private LocalCacheProperties localCacheProperties;

    @Autowired
    private RedisProperties redisProperties;

    @Autowired
    private PluginProperties pluginProperties;

    public LocalCacheProperties getLocalCacheProperties() {
        return localCacheProperties;
    }

    public RedisProperties getRedisProperties() {
        return redisProperties;
    }

    public PluginProperties getPluginProperties() {
        return pluginProperties;
    }
}
