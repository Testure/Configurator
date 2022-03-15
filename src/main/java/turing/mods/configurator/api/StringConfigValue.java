package turing.mods.configurator.api;

/**
 * config value that stores a string.
 * same as ConfigValue of String
 */
public class StringConfigValue extends ConfigValue<String> {
    public StringConfigValue(String name, ConfigCategory parentCategory) {
        super(name, parentCategory);
    }
}
