package turing.mods.configurator.api;

/**
 * <p>
 * config value that stores a byte.
 * </p>
 * same as ConfigValue of Byte
 */
public class ByteConfigValue extends ConfigValue<Byte> {
    public ByteConfigValue(String name, ConfigCategory parentCategory) {
        super(name, parentCategory);
    }
}
