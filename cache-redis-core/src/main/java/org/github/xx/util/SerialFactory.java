package org.github.xx.util;


import cn.hutool.core.util.StrUtil;
import org.github.xx.enums.SerialEnum;
import org.github.xx.properties.RedisProperties;
import org.github.xx.serializer.JacksonRedisSerializer;
import org.github.xx.serializer.JdkRedisSerializer;
import org.github.xx.serializer.KryoRedisSerializer;
import org.github.xx.serializer.ProtostuffRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;

public class SerialFactory {

    public static <T> RedisSerializer<T> getInstance(RedisProperties properties, Class<T> clazz) {
        String serializerStr = properties.getSerializer();
        if (StrUtil.isEmpty(serializerStr)) {
            return new ProtostuffRedisSerializer<>();
        }
        if (serializerStr.equals(SerialEnum.PROTOSTUFF.getName())) {
            return new ProtostuffRedisSerializer<>();
        } else if (serializerStr.equals(SerialEnum.KYRO.getName())) {
            return new KryoRedisSerializer<>(clazz);
        } else if (serializerStr.equals(SerialEnum.JACKSON.getName())) {
            return new JacksonRedisSerializer<>(clazz);
//            也可以直接用系统自带的
//            Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(clazz);
//            ObjectMapper objectMapper = new ObjectMapper();
//            objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//            objectMapper.activateDefaultTyping(new LaissezFaireSubTypeValidator(),
//                    ObjectMapper.DefaultTyping.EVERYTHING);
//            jackson2JsonRedisSerializer.setObjectMapper(objectMapper);
//            return jackson2JsonRedisSerializer;
        } else {
            return new JdkRedisSerializer<>();
        }

    }
}
