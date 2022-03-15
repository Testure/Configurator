package turing.mods.configurator.api;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraft.item.Item;
import turing.mods.configurator.Configurator;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class Config {
    public final String name;
    public final Type type;
    public final String folder;
    public final ConfigCategory[] categories;

    protected Config(String folder, String name, Type type, ConfigCategory[] categories) {
        this.folder = folder;
        this.name = name;
        this.type = type;
        this.categories = categories;
    }

    public static class Builder {
        protected String name;
        protected String folder;
        protected Type type;
        protected final List<ConfigCategory> categories = new ArrayList<>();
        protected ConfigCategory currentEdit;

        protected Builder() {}

        public static Builder builder() {
            return new Builder().withFolder(Configurator.CONTAINED != null && Boolean.TRUE.equals(Configurator.CONTAINED.get()) ? "Configurator" : "");
        }

        public static Builder builder(String folder) {
            return builder().withFolder(folder);
        }

        public Builder withName(String name) {
            this.name = name.replaceAll("/", "_").replaceAll(".\\.", "").replaceAll(" ", "_");
            return this;
        }

        public Builder withFolder(String folder) {
            folder = folder.replaceAll(".\\.", "").replaceAll(" ", "_");
            this.folder = this.folder == null ? folder : this.folder + "/" + folder;
            return this;
        }

        public Builder ofType(Type type) {
            this.type = type;
            return this;
        }

        public void push(String name) {
            ConfigCategory newCategory = new ConfigCategory(name, currentEdit);
            if (currentEdit == null) categories.add(newCategory);
            currentEdit = newCategory;
        }

        public void pop() {
            if (currentEdit != null && currentEdit.getParentCategory() != null) currentEdit = currentEdit.getParentCategory();
            else currentEdit = null;
        }

        public <T, C extends ConfigValue<T>> C defineCustom(String name, Class<C> configValueClass, @Nullable T defaultValue) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
            checkCurrentEdit();
            C value = configValueClass.getConstructor(String.class, ConfigCategory.class).newInstance(name, currentEdit);
            currentEdit.addValue(value);
            value.set(defaultValue);
            return value;
        }

        public <T, C extends SerializedConfigValue<T>> C defineCustomSerialized(String name, Class<C> configValueClass, @Nullable T defaultValue) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
            checkCurrentEdit();
            C value = configValueClass.getConstructor(String.class, ConfigCategory.class).newInstance(name, currentEdit);
            currentEdit.addValue(value);
            value.set(value.serialize(defaultValue));
            return value;
        }

        public <T> ConfigValue<T> define(String name, @Nullable T defaultValue) {
            checkCurrentEdit();
            ConfigValue<T> value = new ConfigValue<>(name, currentEdit);
            currentEdit.addValue(value);
            value.set(defaultValue);
            return value;
        }

        public BooleanConfigValue define(String name, boolean defaultValue) {
            checkCurrentEdit();
            BooleanConfigValue value = new BooleanConfigValue(name, currentEdit);
            currentEdit.addValue(value);
            value.set(defaultValue);
            return value;
        }

        public StringConfigValue define(String name, String defaultValue) {
            checkCurrentEdit();
            StringConfigValue value = new StringConfigValue(name, currentEdit);
            currentEdit.addValue(value);
            value.set(defaultValue);
            return value;
        }

        public IntegerConfigValue define(String name, int defaultValue) {
            checkCurrentEdit();
            IntegerConfigValue value = new IntegerConfigValue(name, currentEdit);
            currentEdit.addValue(value);
            value.set(defaultValue);
            return value;
        }

        public DoubleConfigValue define(String name, double defaultValue) {
            checkCurrentEdit();
            DoubleConfigValue value = new DoubleConfigValue(name, currentEdit);
            currentEdit.addValue(value);
            value.set(defaultValue);
            return value;
        }

        public LongConfigValue define(String name, long defaultValue) {
            checkCurrentEdit();
            LongConfigValue value = new LongConfigValue(name, currentEdit);
            currentEdit.addValue(value);
            value.set(defaultValue);
            return value;
        }

        public ShortConfigValue define(String name, short defaultValue) {
            checkCurrentEdit();
            ShortConfigValue value = new ShortConfigValue(name, currentEdit);
            currentEdit.addValue(value);
            value.set(defaultValue);
            return value;
        }

        public ByteConfigValue define(String name, byte defaultValue) {
            checkCurrentEdit();
            ByteConfigValue value = new ByteConfigValue(name, currentEdit);
            currentEdit.addValue(value);
            value.set(defaultValue);
            return value;
        }

        public FloatConfigValue define(String name, float defaultValue) {
            checkCurrentEdit();
            FloatConfigValue value = new FloatConfigValue(name, currentEdit);
            currentEdit.addValue(value);
            value.set(defaultValue);
            return value;
        }

        public ItemConfigValue defineItem(String name, Item defaultValue) {
            checkCurrentEdit();
            ItemConfigValue value = new ItemConfigValue(name, currentEdit);
            currentEdit.addValue(value);
            value.set(value.serialize(defaultValue));
            return value;
        }

        public <T extends Enum<T>> EnumConfigValue<T> defineEnum(String name, T defaultValue, Function<String, T> converter) {
            checkCurrentEdit();
            EnumConfigValue<T> value = new EnumConfigValue<>(name, converter, currentEdit);
            currentEdit.addValue(value);
            value.set(value.serialize(defaultValue));
            return value;
        }

        protected void checkCurrentEdit() {
            if (currentEdit == null) throw new NullPointerException("ConfigValue must have a category!");
        }

        protected void checkValid() {
            if (this.type == null) throw new NullPointerException("ConfigBuilder must have a Type!");
            if (this.name == null || this.name.isEmpty()) throw new IllegalArgumentException(String.format("ConfigBuilder must have a valid file name! got '%s'", this.name));
            if (this.folder == null) throw new IllegalArgumentException("ConfigBuilder must have a valid parent folder! got 'null'");
        }

        public Config build() {
            this.checkValid();
            return new Config(folder, name, type, categories.toArray(new ConfigCategory[0]));
        }
    }

    public enum Type {
        UNCATEGORIZED,
        COMMON,
        SERVER,
        CLIENT;

        public String getFolder(String parent) {
            if (this == Type.UNCATEGORIZED) return parent;
            return parent + "/" + this.name().toLowerCase();
        }
    }
}
