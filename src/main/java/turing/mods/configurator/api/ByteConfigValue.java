package turing.mods.configurator.api;

/**
 * config value that stores a byte.
 * same as ConfigValue of Byte
 */
public class ByteConfigValue extends ConfigValue<Byte> {
    public ByteConfigValue(String name, ConfigCategory parentCategory) {
        super(name, parentCategory);
    }
}
