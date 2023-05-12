package org.github.xx.plugins;

/**
 * 利用责任链模式编写的各种插件的总接口
 */
public interface Plugin {

    void handle(String key);

    void setNextPlugin(Plugin plugin);

}
