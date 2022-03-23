package turing.mods.configurator.api;

/**
 * <p>
 * config value that stores an int.
 * </p>
 * same as ConfigValue of Integer
 */
public class IntegerConfigValue extends ConfigValue<Integer> {
    public IntegerConfigValue(String name, ConfigCategory parentCategory) {
        super(name, parentCategory);
    }
}
