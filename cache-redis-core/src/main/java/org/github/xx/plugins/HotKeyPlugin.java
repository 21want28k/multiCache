package org.github.xx.plugins;

/**
 * 热key发现插件机制。
 */
public class HotKeyPlugin extends AbstractPlugin {
    @Override
    public void handle(String key) {
        Plugin nextPlugin = this.getNextPlugin();
        if (nextPlugin != null) {
            nextPlugin.handle(key);
        }
    }
}