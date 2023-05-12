package org.github.xx.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "multicache.plugins")
public class PluginProperties {
    List<String> pluginNames;

    public List<String> getPluginNames() {
        return pluginNames;
    }

    public void setPluginNames(List<String> pluginNames) {
        this.pluginNames = pluginNames;
    }
}
