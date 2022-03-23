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

    /**
     * <p>
     * Sets the value in this ConfigValue to the given value.
     * </p>
     * This method for internal use.
     * @param value the value to put into this ConfigValue
     */
    public void set(@Nullable T value) {
        this.value = value;
    }

    /**
     * Gets the {@link ConfigCategory} that this ConfigValue exists under.
     * @return the category this ConfigValue exists in
     */
    public ConfigCategory getParentCategory() {
        return parentCategory;
    }

    /**
     * Gets the name of this ConfigValue.
     * @return the name of this config
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the stored value from this ConfigValue.
     * @return the stored value
     */
    @Nullable
    public T get() {
        return value;
    }
}
