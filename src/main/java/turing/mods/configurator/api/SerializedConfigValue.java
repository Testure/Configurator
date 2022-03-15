package turing.mods.configurator.api;

import mcp.MethodsReturnNonnullByDefault;

import javax.annotation.ParametersAreNonnullByDefault;

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
