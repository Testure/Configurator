package turing.mods.configurator.api;

import mcp.MethodsReturnNonnullByDefault;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * a config value that stores a non-json-compatible value by converting it to a string.
 * {@link SerializedConfigValue#get()} returns the serialized value that was stored.
 * {@link SerializedConfigValue#getReal()} returns the deserialized value.
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public abstract class SerializedConfigValue<T> extends ConfigValue<String> {
    protected T realValue;

    public SerializedConfigValue(String name, ConfigCategory parentCategory) {
        super(name, parentCategory);
    }

    @Override
    public void set(String value) {
        this.realValue = deserialize(value);
    }

    public T getReal() {
        return this.realValue;
    }

    @Override
    public String get() {
        return serialize(realValue);
    }

    public abstract T deserialize(String serializedValue);

    public abstract String serialize(T value);
}
