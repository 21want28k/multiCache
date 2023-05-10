package org.github.xx.plugins;

import cn.hutool.core.util.StrUtil;
import org.github.xx.enums.BuiltInPluginEnum;
import org.github.xx.factory.BeanFactory;
import org.github.xx.properties.PluginProperties;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 插件管理器，其实就是一个工厂，在工厂创建的时候完成相关的插件初始化工作
 */
public class MultiPluginManager implements PluginManager {
    private List<Plugin> plugins;
    /**
     * 插件的名称list，通过application.yaml文件去配置的
     */
    private List<String> pluginNames;

    private Plugin first;

//    /**
//     * 从插件名称中创建list（不依赖spring容器）
//     *
//     * @param pluginNames 插件名称list
//     */
//    public MultiPluginManager(List<String> pluginNames) {
//        this.pluginNames = pluginNames;
//    }

    /**
     * 从插件名称中创建list（不依赖spring容器）
     */
    public MultiPluginManager(PluginProperties pluginProperties) {
        this.pluginNames = pluginProperties.getPluginNames();
        initPlugins();
    }

    public Plugin getFirst() {
        return first;
    }

    /**
     * 初始化所有的插件，并且按照责任链模式去把他们按照编写顺序串起来。
     */
    public void init() {
        Set<String> plugins = new HashSet<>(pluginNames);
        if (plugins.isEmpty()) {
            return;
        }
        // 将配置的插件全都加载进来
        for (String pluginName : plugins) {
            if (StrUtil.isEmpty(pluginName)) {
                return;
            }
            // 思路：通过名字去创建，首先想到的是简单工厂模式，但是插件多了，还得再改，所以用反射去创建吧
            // 如果只有内置插件的话，还是比较少，那么就要考虑到自定义插件，自定义插件就需要通过反射的思维，根据全限定名获取class对象
            Plugin plugin = this.getPlugin(pluginName);
            this.plugins.add(plugin);
        }

        // 设置责任链第一个（不知道这么干对不对）
        this.first = this.plugins.get(0);

        // 设置好责任链
        for (int i = 0; i < this.plugins.size(); i++) {
            Plugin nextPlugin = this.plugins.get(i + 1);
            if (nextPlugin != null) {
                this.plugins.get(i).setNextPlugin(nextPlugin);
            }
        }
    }

    /**
     * 如果是内建的插件，通过 {@link BuiltInPluginEnum} 中的名称就行，不然就需要给出自定义插件的类全限定名
     *
     * @param classOrName 内建的插件名称，或者是说自定义插件的类全限定名
     * @return 插件 {@link Plugin} 对象
     */
    public Plugin getPlugin(String classOrName) {
        try {
            Class<?> clazz;
            BuiltInPluginEnum builtInPluginEnum = BuiltInPluginEnum.getByName(classOrName);
            if (builtInPluginEnum != null) {
                clazz = builtInPluginEnum.getClazz();
            } else {
                clazz = Class.forName(classOrName);
            }
            return (Plugin) BeanFactory.INSTANCE.getBean(clazz);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("初始化插件失败，原因是插件" + classOrName + "不存在", e);
        }

    }

    @Override
    public void initPlugins() {
        this.init();
    }
}
