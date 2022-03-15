package turing.mods.configurator.api;

/**
 * config value that stores a double.
 * same as ConfigValue of Double
 */
public class DoubleConfigValue extends ConfigValue<Double> {
    public DoubleConfigValue(String name, ConfigCategory parentCategory) {
        super(name, parentCategory);
    }
}
