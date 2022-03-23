package turing.mods.configurator.api;

import mcp.MethodsReturnNonnullByDefault;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * <p>
 * a config value that stores a non-json-compatible value by converting it to a string.
 * </p><p>
 * {@link SerializedConfigValue#get()} returns the serialized value that was stored.
 * </p><p>
 * {@link SerializedConfigValue#getReal()} returns the deserialized value.
 * </p>
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class SerializedConfigValue<T> extends ConfigValue<String> {
    /** The deserialized value */
    protected T realValue;

    public SerializedConfigValue(String name, ConfigCategory parentCategory) {
        super(name, parentCategory);
    }

    @Override
    public void set(String value) {
        this.realValue = deserialize(value);
    }

    /**
     * Gets the deserialized version of the stored value.
     * @return the deserialized value
     */
    public T getReal() {
        return this.realValue;
    }

    @Override
    public String get() {
        return serialize(realValue);
    }

    /**
     * Converts the stored string value into the real value.
     * @param serializedValue the serialized string value
     * @return the deserialized value
     */
    public abstract T deserialize(String serializedValue);

    /**
     * Converts a value into a string for storing in a .json file.
     * @param value the value to serialize
     * @return the serialized string
     */
    public abstract String serialize(T value);
}
