package org.github.xx.redis;

import org.github.xx.properties.RedisProperties;
import org.github.xx.serializer.ProtostuffRedisSerializer;
import org.github.xx.util.SerialFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
@EnableConfigurationProperties({RedisProperties.class})
public class RedisConfig {
    /**
     * 创建RedisTemplate
     *
     * @param redisConnectionFactory 连接工厂
     * @param <T>                    值类型
     * @return RedisTemplate
     */
    @Bean
    public <T> RedisTemplate<String, T> redisTemplate(RedisConnectionFactory redisConnectionFactory, RedisProperties redisProperties) {
        RedisTemplate<String, T> redisTemplate = new RedisTemplate<>();
        // 设置连接工厂
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // String序列化对象
        RedisSerializer<String> stringRedisSerializer = RedisSerializer.string();


        // ProtoStuff序列化
        ProtostuffRedisSerializer<T> protoStuffRedisSerializer = new ProtostuffRedisSerializer<>();
        // 序列化配置=>Key
        redisTemplate.setKeySerializer(stringRedisSerializer); // 所有Key都设置为字符串，方便阅读
        redisTemplate.setHashKeySerializer(stringRedisSerializer); //  设置Hash数据结构中的Key
        // 序列化配置=>Value
        redisTemplate.setValueSerializer(SerialFactory.getInstance(redisProperties, Object.class)); // 所有Value
        redisTemplate.setHashValueSerializer(SerialFactory.getInstance(redisProperties, Object.class));  //  Hash数据结构中的Value
//        redisTemplate.setValueSerializer(protoStuffRedisSerializer);
        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }

}
