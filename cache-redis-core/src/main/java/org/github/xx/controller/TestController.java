package org.github.xx.controller;

import com.github.benmanes.caffeine.cache.Cache;
import org.github.xx.cache.MyCaffeineCache;
import org.github.xx.properties.RedisProperties;
import org.github.xx.cache.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
public class TestController {

//    @Autowired
//    public RedisCache service;

    @Autowired
    public RedisProperties properties;

//    @Autowired
//    public MyCaffeineCache cache;

    @Autowired
    public Cache<String, Object> cache;

    @Autowired
    public Cache<String, Object> cache2;

    @GetMapping("/test")
    public void test() throws Exception {

        // 测试序列化
//        properties.getSerializer();
//        service.setCacheObject("123", new Test("123"));
//        Test test = service.getCacheObject("123");
//        cache.put("123", null); // 测试空异常
        cache.put("1234", "123");
//        cache.get("1234");
    }
}