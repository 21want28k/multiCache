package org.github.xx.plugins;

public abstract class AbstractPlugin implements Plugin {
    private Plugin nextPlugin;

    public AbstractPlugin() {
    }

    public void setNextPlugin(Plugin nextPlugin) {
        this.nextPlugin = nextPlugin;
    }

    public Plugin getNextPlugin() {
        return nextPlugin;
    }
}
