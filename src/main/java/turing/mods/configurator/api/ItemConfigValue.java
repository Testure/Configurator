package turing.mods.configurator.api;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

/**
 * <p>
 * stores an item as a string using it's registryName.
 * </p>
 * use {@link ItemConfigValue#getReal()} to get the actual item.
 */
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ItemConfigValue extends SerializedConfigValue<Item> {
    public ItemConfigValue(String name, ConfigCategory parentCategory) {
        super(name, parentCategory);
    }

    @Override
    public Item deserialize(String serializedValue) {
        String[] strings = serializedValue.split(":");
        return Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation(strings[0], strings[1])));
    }

    @Override
    public String serialize(Item value) {
        return value.getRegistryName().getNamespace() + ":" + value.getRegistryName().getPath();
    }
}
