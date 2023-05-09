package org.github.xx.conf;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.Expiry;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.github.xx.enums.ExpireModeEnum;
import org.github.xx.properties.LocalCacheProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableConfigurationProperties(LocalCacheProperties.class)
public class LocalCaffeineCacheConf {

    @Bean
//    @Scope("prototype")
    public Cache<String, Object> caffeineCache(LocalCacheProperties localCacheProperties) {
        // 根据配置创建Caffeine builder,很明显是建造者模式，又学到了
        Caffeine<Object, Object> builder = Caffeine.newBuilder();
        builder.initialCapacity(localCacheProperties.getInitialCapacity());
        builder.maximumSize(localCacheProperties.getMaximumSize());

        // 开启的化，value会被用softReference包装一下，软引用会在OOM的时候被释放
        builder.softValues();
        if (ExpireModeEnum.WRITE.equals(localCacheProperties.getExpireMode())) {
            // 一个元素将会在其创建或者最近一次被更新之后的一段时间后被认定为过期项
            builder.expireAfterWrite(localCacheProperties.getExpireTime(), localCacheProperties.getTimeUnit());
        } else if (ExpireModeEnum.ACCESS.equals(localCacheProperties.getExpireMode())) {
            // 一个元素在上一次读写操作后一段时间之后，在指定的时间后没有被再次访问将会被认定为过期项。
            builder.expireAfterAccess(localCacheProperties.getExpireTime(), localCacheProperties.getTimeUnit());
        }
        // 也可以自定义去实现
//        builder.expireAfter(new Expiry<Object, Object>() {
//            @Override
//            public long expireAfterCreate(@NonNull Object key, @NonNull Object value, long currentTime) {
//                return 0;
//            }
//
//            @Override
//            public long expireAfterUpdate(@NonNull Object key, @NonNull Object value, long currentTime, @NonNegative long currentDuration) {
//                return 0;
//            }
//
//            @Override
//            public long expireAfterRead(@NonNull Object key, @NonNull Object value, long currentTime, @NonNegative long currentDuration) {
//                return 0;
//            }
//        });
        return builder.build();
    }
//    @Bean
//    public Cache<String, Object> caffeineCache(LocalCacheProperties localCacheProperties) {
//        Caffeine<Object, Object> builder = Caffeine.newBuilder()
//                // 设置最后一次写入或访问后经过固定时间过期
//                // 初始的缓存空间大小
//                .initialCapacity(localCacheProperties.getInitialCapacity())
//                // 缓存的最大条数
//                .maximumSize(localCacheProperties.getMaximumSize());
//        builder.expireAfterWrite(localCacheProperties.getExpireTime(), localCacheProperties.getTimeUnit());
//        return builder.build();
//    }
//    @Bean("caffeineCacheManager")
//    public CacheManager cacheManager(LocalCacheProperties localCacheProperties) {
//        CaffeineCacheManager cacheManager = new CaffeineCacheManager();
//        cacheManager.setCaffeine(getCache(localCacheProperties));
//        return cacheManager;
//    }
}
