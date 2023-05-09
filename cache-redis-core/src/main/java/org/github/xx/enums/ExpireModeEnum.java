package org.github.xx.enums;

/**
 * 缓存过期策略，每次访问或者写入的时候会重新计算时间的。参考：<a href="https://heapdump.cn/article/5113544">...</a>
 */
public enum ExpireModeEnum {
    WRITE("最后一次写入后配置时间到期失效"),

    ACCESS("最后一次访问后配置时间到期失效");

    private String description;

    ExpireModeEnum(String description) {
        this.description = description;
    }
}