package org.github.xx.serializer;

import com.alibaba.fastjson.JSON;
import org.github.xx.util.SerializationUtils;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.io.*;
import java.util.Arrays;

/**
 * JDK 序列化方式
 */
public class JdkRedisSerializer<T> implements RedisSerializer<T> {
    @Override
    public  byte[] serialize(T value) throws SerializationException {
        if (value == null) {
            return SerializationUtils.EMPTY_ARRAY;
        }

        if (!(value instanceof Serializable)) {
            throw new IllegalArgumentException(this.getClass().getSimpleName() + " requires a Serializable payload but received an object of type [" + value.getClass().getName() + "]");
        }

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream(1024);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {

            objectOutputStream.writeObject(value);
            objectOutputStream.flush();
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new SerializationException(String.format("JdkRedisSerializer 序列化异常: %s, 【%s】", e.getMessage(), JSON.toJSONString(value)), e);
        }
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (SerializationUtils.isEmpty(bytes)) {
            return null;
        }

//        if (Arrays.equals(getNullValueBytes(), bytes)) {
//            return null;
//        }

        try (ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
             ObjectInputStream stream = new ObjectInputStream(byteStream)) {
            return (T) stream.readObject();
        } catch (Exception e) {
            throw new SerializationException(String.format("JdkRedisSerializer 反序列化异常: %s, 【%s】", e.getMessage(), JSON.toJSONString(bytes)), e);
        }
    }
}