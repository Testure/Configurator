package turing.mods.configurator.api;

/**
 * config value that stores a boolean.
 * same as ConfigValue of Boolean
 */
public class BooleanConfigValue extends ConfigValue<Boolean> {
    public BooleanConfigValue(String name, ConfigCategory parentCategory) {
        super(name, parentCategory);
    }
}
