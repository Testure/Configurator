package turing.mods.configurator.api;

/**
 * config value that stores an int.
 * same as ConfigValue of Integer
 */
public class IntegerConfigValue extends ConfigValue<Integer> {
    public IntegerConfigValue(String name, ConfigCategory parentCategory) {
        super(name, parentCategory);
    }
}
