package turing.mods.configurator.api;

/**
 * <p>
 * config value that stores a double.
 * </p>
 * same as ConfigValue of Double
 */
public class DoubleConfigValue extends ConfigValue<Double> {
    public DoubleConfigValue(String name, ConfigCategory parentCategory) {
        super(name, parentCategory);
    }
}
