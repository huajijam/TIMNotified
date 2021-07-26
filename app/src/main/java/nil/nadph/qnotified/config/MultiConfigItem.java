package nil.nadph.qnotified.config;

public interface MultiConfigItem extends AbstractConfigItem {
    boolean isValid();

    boolean hasConfig(String name);

    boolean getBooleanConfig(String name);

    void setBooleanConfig(String name, boolean val);

    int getIntConfig(String name);

    void setIntConfig(String name, int val);

    String getStringConfig(String name);

    void setStringConfig(String name, String val);

    @Override
    boolean sync();
}
