package turing.mods.configurator.api;

/**
 * <p>
 * config value that stores a long.
 * </p>
 * same as ConfigValue of Long
 */
public class LongConfigValue extends ConfigValue<Long> {
    public LongConfigValue(String name, ConfigCategory parentCategory) {
        super(name, parentCategory);
    }
}
