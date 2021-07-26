package nil.nadph.qnotified.config;

/**
 * Base interface for a config item
 */
public interface AbstractConfigItem {
    boolean isValid();

    boolean sync();
}
