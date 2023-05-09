package org.github.xx.serializer;

import com.alibaba.fastjson.JSON;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import org.github.xx.util.SerializationUtils;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

/**
 * kryo 序列化方式
 */
public class KryoRedisSerializer<T> implements RedisSerializer<T> {
    // 线程不安全的，需要解决线程安全的问题
    private static final ThreadLocal<Kryo> KRYO = ThreadLocal.withInitial(Kryo::new);

    private Class<T> resultType;

    public KryoRedisSerializer() {
    }

    public KryoRedisSerializer(Class<T> clazz) {
        this.resultType = clazz;
    }

    @Override
    public byte[] serialize(T t) throws SerializationException {
        if (t == null) {
            return SerializationUtils.EMPTY_ARRAY;
        }

        Kryo kryo = KRYO.get();
        // 循环引用开关，设置成false 序列化速度更快，但是遇到循环应用序列化器会报栈内存溢出
        kryo.setReferences(false);
//        kryo.register(t.getClass());

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             Output output = new Output(baos)) {
            kryo.writeClassAndObject(output, t);
            output.flush();
            return baos.toByteArray();
        } catch (Exception e) {
            throw new SerializationException(String.format("KryoRedisSerializer 序列化异常: %s, 【%s】", e.getMessage(), JSON.toJSONString(t)), e);
        } finally {
            KRYO.remove();
        }
    }

    @Override
    public T deserialize(byte[] bytes) throws SerializationException {
        if (SerializationUtils.isEmpty(bytes)) {
            return null;
        }

        Kryo kryo = KRYO.get();
        // 设置成false 序列化速度更快，但是遇到循环应用序列化器会报栈内存溢出
        kryo.setReferences(false);
        kryo.register(resultType);

        try (Input input = new Input(bytes)) {
            Object result = kryo.readClassAndObject(input);
            return (T) result;
        } catch (Exception e) {
            throw new SerializationException(String.format("KryoRedisSerializer 反序列化异常: %s, 【%s】", e.getMessage(), JSON.toJSONString(bytes)), e);
        } finally {
            KRYO.remove();
        }
    }

}