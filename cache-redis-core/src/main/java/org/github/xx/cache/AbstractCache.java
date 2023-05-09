package org.github.xx.cache;

/**
 * 利用模板方法模式，对各种接口相同的部分进行实现
 */
public abstract class AbstractCache implements Cache {

    private String name;

    public AbstractCache() {
    }

    public AbstractCache(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
