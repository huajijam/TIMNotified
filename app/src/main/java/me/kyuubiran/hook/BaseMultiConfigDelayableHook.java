package me.kyuubiran.hook;

import nil.nadph.qnotified.config.ConfigManager;
import nil.nadph.qnotified.config.MultiConfigItem;
import nil.nadph.qnotified.hook.BaseDelayableHook;
import nil.nadph.qnotified.util.Utils;

import java.io.IOException;

public abstract class BaseMultiConfigDelayableHook extends BaseDelayableHook implements MultiConfigItem {

    private final String _$shadow$ns$prefix = this.getClass().getSimpleName() + "$";

    public boolean hasConfig(String name) {
        if (name == null) throw new NullPointerException("name == null");
        ConfigManager cfg = ConfigManager.getDefaultConfig();
        return cfg.hasConfig(_$shadow$ns$prefix + name);
    }

    public boolean getBooleanConfig(String name) {
        if (name == null) throw new NullPointerException("name == null");
        ConfigManager cfg = ConfigManager.getDefaultConfig();
        return cfg.getBooleanOrDefault(_$shadow$ns$prefix + name, false);
    }

    public void setBooleanConfig(String name, boolean val) {
        if (name == null) throw new NullPointerException("name == null");
        ConfigManager cfg = ConfigManager.getDefaultConfig();
        cfg.putBoolean(_$shadow$ns$prefix + name, val);
    }

    public int getIntConfig(String name) {
        if (name == null) throw new NullPointerException("name == null");
        ConfigManager cfg = ConfigManager.getDefaultConfig();
        return cfg.getIntOrDefault(_$shadow$ns$prefix + name, -1);
    }

    public void setIntConfig(String name, int val) {
        if (name == null) throw new NullPointerException("name == null");
        ConfigManager cfg = ConfigManager.getDefaultConfig();
        cfg.putInt(_$shadow$ns$prefix + name, val);
    }

    public String getStringConfig(String name) {
        if (name == null) throw new NullPointerException("name == null");
        ConfigManager cfg = ConfigManager.getDefaultConfig();
        return cfg.getString(_$shadow$ns$prefix + name);
    }

    public void setStringConfig(String name, String val) {
        if (name == null) throw new NullPointerException("name == null");
        ConfigManager cfg = ConfigManager.getDefaultConfig();
        cfg.putString(_$shadow$ns$prefix + name, val);
    }

    @Override
    public boolean sync() {
        try {
            ConfigManager cfg = ConfigManager.getDefaultConfig();
            cfg.save();
            return true;
        } catch (IOException e) {
            Utils.log(e);
            return false;
        }
    }
}
