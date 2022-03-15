package turing.mods.configurator.api;

import mcp.MethodsReturnNonnullByDefault;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ConfigValue<T> {
    protected final String name;
    protected final ConfigCategory parentCategory;
    protected T value;

    public ConfigValue(String name, ConfigCategory parentCategory) {
        this.name = name;
        this.parentCategory = parentCategory;
    }

    public void set(@Nullable T value) {
        this.value = value;
    }

    public ConfigCategory getParentCategory() {
        return parentCategory;
    }

    public String getName() {
        return name;
    }

    @Nullable
    public T get() {
        return value;
    }
}
