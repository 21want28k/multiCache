package org.github.xx.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 通过反射去创建一个对象，同时由三级缓存机制启发，避免一直通过反射创建bean，所以使用了一个缓存
 *
 * @author 肖鑫
 */
public class BeanFactory {
    private static final Logger logger = LoggerFactory.getLogger(BeanFactory.class);

    /**
     * 单例的饿汉模式
     */
    public static final BeanFactory INSTANCE = new BeanFactory();
    /**
     * 防止无限次的通过反射创建bean，所以设置一个缓存
     */
    private ConcurrentHashMap<Class<?>, Object> cache = new ConcurrentHashMap<>();

    private BeanFactory() {

    }

    /**
     * 通过DCL双重检测来保证懒加载时的并发，从而只有一个对象产生。且并发synchronized的同步锁是单例工厂，
     *
     * @param tClass 反射时需要创建的class对象
     * @param <T>    泛型T
     * @return 实例
     */
    public <T> T getBean(Class<T> tClass) {
        Object instance = cache.get(tClass);
        if (instance == null) {
            try {
                synchronized (INSTANCE) {
                    if (cache.get(tClass) == null) {
                        logger.info("BeanFactory 尝试通过反射创建{}", tClass.getName());
                        instance = tClass.getConstructor().newInstance();
                        logger.info("BeanFactory 尝试通过反射创建{}成功", tClass.getName());
                        cache.put(tClass, instance);
                    }
                }
            } catch (Exception e) {
                logger.info("BeanFactory 创建{}失败", tClass.getName());
            }
        }
        return (T) instance;
    }
}
