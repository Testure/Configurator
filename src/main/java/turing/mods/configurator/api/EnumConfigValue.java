package turing.mods.configurator.api;

import mcp.MethodsReturnNonnullByDefault;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Function;

/**
 * stores an Enum as a string.
 * you must provide EnumClass::valueOf.
 * use {@link EnumConfigValue#getReal()} to get the actual Enum.
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class EnumConfigValue<T extends Enum<T>> extends SerializedConfigValue<T> {
    protected final Function<String, T> toReal;

    public EnumConfigValue(String name, Function<String, T> toReal, ConfigCategory parentCategory) {
        super(name, parentCategory);
        this.toReal = toReal;
    }

    @Override
    public T deserialize(String serializedValue) {
        return toReal.apply(serializedValue.toUpperCase());
    }

    @Override
    public String serialize(T value) {
        return value.name().toLowerCase();
    }
}
