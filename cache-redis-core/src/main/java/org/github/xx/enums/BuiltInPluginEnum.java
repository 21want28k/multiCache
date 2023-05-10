package org.github.xx.enums;

import org.github.xx.plugins.HotKeyPlugin;
import org.github.xx.plugins.Plugin;

import java.util.Arrays;

/**
 * 自带的插件，支持热key自动发现机制，可以通过名称以及class获得
 *
 * @author 肖鑫
 */
public enum BuiltInPluginEnum {

    /**
     * 热key发现插件
     */
    HOT_KEY_PLUGIN("hotKeyPlugin", HotKeyPlugin.class);

    private String alias;

    private Class<? extends Plugin> clazz;

    BuiltInPluginEnum(String alias, Class<? extends Plugin> clazz) {
        this.alias = alias;
        this.clazz = clazz;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public Class<? extends Plugin> getClazz() {
        return clazz;
    }

    public void setClazz(Class<? extends Plugin> clazz) {
        this.clazz = clazz;
    }

    public static BuiltInPluginEnum getByName(String name) {
        return Arrays.stream(BuiltInPluginEnum.values()).filter(builtInPluginEnum -> builtInPluginEnum.alias.equals(name)).findFirst().orElse(null);
    }
}
