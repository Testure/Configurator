package turing.mods.configurator.api;

/**
 * <p>
 * config value that stores a short.
 * </p>
 * same as ConfigValue of Short
 */
public class ShortConfigValue extends ConfigValue<Short> {
    public ShortConfigValue(String name, ConfigCategory parentCategory) {
        super(name, parentCategory);
    }
}
