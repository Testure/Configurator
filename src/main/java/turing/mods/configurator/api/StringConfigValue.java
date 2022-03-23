package turing.mods.configurator.api;

/**
 * <p>
 * config value that stores a string.
 * </p>
 * same as ConfigValue of String
 */
public class StringConfigValue extends ConfigValue<String> {
    public StringConfigValue(String name, ConfigCategory parentCategory) {
        super(name, parentCategory);
    }
}
