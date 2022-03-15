package turing.mods.configurator.api;

/**
 * config value that stores a short.
 * same as ConfigValue of Short
 */
public class ShortConfigValue extends ConfigValue<Short> {
    public ShortConfigValue(String name, ConfigCategory parentCategory) {
        super(name, parentCategory);
    }
}
