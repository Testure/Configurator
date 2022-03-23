package turing.mods.configurator.api;

/**
 * <p>
 * config value that stores a boolean.
 * </p>
 * same as ConfigValue of Boolean
 */
public class BooleanConfigValue extends ConfigValue<Boolean> {
    public BooleanConfigValue(String name, ConfigCategory parentCategory) {
        super(name, parentCategory);
    }
}
