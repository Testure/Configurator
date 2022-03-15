package turing.mods.configurator.api;

import mcp.MethodsReturnNonnullByDefault;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ConfigCategory {
    protected final List<ConfigValue<?>> values = new ArrayList<>();
    protected final List<ConfigCategory> subCategories = new ArrayList<>();
    protected final String name;
    protected final ConfigCategory parentCategory;

    public ConfigCategory(String name, @Nullable ConfigCategory parentCategory) {
        this.name = name;
        this.parentCategory = parentCategory;
    }

    public void addValue(ConfigValue<?> value) {
        values.add(value);
    }

    public String getName() {
        return name;
    }

    @Nullable
    public ConfigCategory getParentCategory() {
        return parentCategory;
    }

    public ConfigValue<?>[] getValues() {
        return values.toArray(new ConfigValue[0]);
    }

    public ConfigCategory[] getSubCategories() {
        return subCategories.toArray(new ConfigCategory[0]);
    }
}
