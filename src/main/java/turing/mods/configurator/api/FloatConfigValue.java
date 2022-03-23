package turing.mods.configurator.api;

/**
 * <p>
 * config value that stores a float.
 * </p>
 * same as ConfigValue of Float
 */
public class FloatConfigValue extends ConfigValue<Float> {
    public FloatConfigValue(String name, ConfigCategory parentCategory) {
        super(name, parentCategory);
    }
}
