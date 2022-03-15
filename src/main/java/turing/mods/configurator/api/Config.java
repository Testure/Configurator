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

        /**
         * sets the file name of this config.
         * DO NOT PUT .json OR FILE PATH IN THE NAME.
         * @param name the name of the file
         */
        public Builder withName(String name) {
            this.name = name.replaceAll("/", "_").replaceAll(".\\.", "").replaceAll(" ", "_");
            return this;
        }

        /**
         * defines that this config will be in the given folder.
         * @apiNote chaining this function will allow nested folders
         * @param folder the folder name
         */
        public Builder withFolder(String folder) {
            folder = folder.replaceAll(".\\.", "").replaceAll(" ", "_");
            this.folder = this.folder == null ? folder : this.folder + "/" + folder;
            return this;
        }

        /**
         * defines a category for this config
         * @param type the category for this config
         */
        public Builder ofType(Type type) {
            this.type = type;
            return this;
        }

        /**
         * creates a new category inside the current one and sets the builder to edit it.
         * @param name the name of the category
         */
        public void push(String name) {
            ConfigCategory newCategory = new ConfigCategory(name, currentEdit);
            if (currentEdit == null) categories.add(newCategory);
            currentEdit = newCategory;
        }

        /**
         * if the builder is currently editing a category, goes up one category.
         */
        public void pop() {
            if (currentEdit != null && currentEdit.getParentCategory() != null) currentEdit = currentEdit.getParentCategory();
            else currentEdit = null;
        }

        /**
         * defines a config value of a custom class
         * @param name the name of the value
         * @param configValueClass the custom config value class
         * @param defaultValue the default value that is set on file generation
         * @param <T> type of value to be stored
         * @param <C> custom config value class
         */
        public <T, C extends ConfigValue<T>> C defineCustom(String name, Class<C> configValueClass, @Nullable T defaultValue) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
            checkCurrentEdit();
            C value = configValueClass.getConstructor(String.class, ConfigCategory.class).newInstance(name, currentEdit);
            currentEdit.addValue(value);
            value.set(defaultValue);
            return value;
        }

        /**
         * defines a serialized config value of a custom class
         * @param name the name of the value
         * @param configValueClass the custom config value class
         * @param defaultValue the default value that is set on file generation
         * @param <T> type of value to be stored
         * @param <C> custom config value class
         */
        public <T, C extends SerializedConfigValue<T>> C defineCustomSerialized(String name, Class<C> configValueClass, @Nullable T defaultValue) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
            checkCurrentEdit();
            C value = configValueClass.getConstructor(String.class, ConfigCategory.class).newInstance(name, currentEdit);
            currentEdit.addValue(value);
            value.set(value.serialize(defaultValue));
            return value;
        }

        /**
         * defines a boolean value in the current category.
         * @param name the name of the value
         * @param defaultValue the default value that is set on file generation
         */
        public BooleanConfigValue define(String name, boolean defaultValue) {
            checkCurrentEdit();
            BooleanConfigValue value = new BooleanConfigValue(name, currentEdit);
            currentEdit.addValue(value);
            value.set(defaultValue);
            return value;
        }

        /**
         * defines a string value in the current category.
         * @param name the name of the value
         * @param defaultValue the default value that is set on file generation
         */
        public StringConfigValue define(String name, String defaultValue) {
            checkCurrentEdit();
            StringConfigValue value = new StringConfigValue(name, currentEdit);
            currentEdit.addValue(value);
            value.set(defaultValue);
            return value;
        }

        /**
         * defines an int value in the current category.
         * @param name the name of the value
         * @param defaultValue the default value that is set on file generation
         */
        public IntegerConfigValue define(String name, int defaultValue) {
            checkCurrentEdit();
            IntegerConfigValue value = new IntegerConfigValue(name, currentEdit);
            currentEdit.addValue(value);
            value.set(defaultValue);
            return value;
        }

        /**
         * defines a double value in the current category.
         * @param name the name of the value
         * @param defaultValue the default value that is set on file generation
         */
        public DoubleConfigValue define(String name, double defaultValue) {
            checkCurrentEdit();
            DoubleConfigValue value = new DoubleConfigValue(name, currentEdit);
            currentEdit.addValue(value);
            value.set(defaultValue);
            return value;
        }

        /**
         * defines a long value in the current category.
         * @param name the name of the value
         * @param defaultValue the default value that is set on file generation
         */
        public LongConfigValue define(String name, long defaultValue) {
            checkCurrentEdit();
            LongConfigValue value = new LongConfigValue(name, currentEdit);
            currentEdit.addValue(value);
            value.set(defaultValue);
            return value;
        }

        /**
         * defines a short value in the current category.
         * @param name the name of the value
         * @param defaultValue the default value that is set on file generation
         */
        public ShortConfigValue define(String name, short defaultValue) {
            checkCurrentEdit();
            ShortConfigValue value = new ShortConfigValue(name, currentEdit);
            currentEdit.addValue(value);
            value.set(defaultValue);
            return value;
        }

        /**
         * defines a byte value in the current category.
         * @param name the name of the value
         * @param defaultValue the default value that is set on file generation
         */
        public ByteConfigValue define(String name, byte defaultValue) {
            checkCurrentEdit();
            ByteConfigValue value = new ByteConfigValue(name, currentEdit);
            currentEdit.addValue(value);
            value.set(defaultValue);
            return value;
        }

        /**
         * defines a float value in the current category.
         * @param name the name of the value
         * @param defaultValue the default value that is set on file generation
         */
        public FloatConfigValue define(String name, float defaultValue) {
            checkCurrentEdit();
            FloatConfigValue value = new FloatConfigValue(name, currentEdit);
            currentEdit.addValue(value);
            value.set(defaultValue);
            return value;
        }

        /**
         * defines an item value in the current category.
         * @param name the name of the value
         * @param defaultValue the default value that is set on file generation
         */
        public ItemConfigValue defineItem(String name, Item defaultValue) {
            checkCurrentEdit();
            ItemConfigValue value = new ItemConfigValue(name, currentEdit);
            currentEdit.addValue(value);
            value.set(value.serialize(defaultValue));
            return value;
        }

        /**
         * defines an enum value in the current category.
         * @param name the name of the value
         * @param defaultValue the default value that is set on file generation
         * @param converter the function that turns an enum name into the actual enum value. highly recommended to use Enum::valueOf
         */
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

        /**
         * builds a config with the current builder settings.
         * @throws NullPointerException if a Type category hasn't been defined
         * @throws IllegalArgumentException if no file name has been defined
         * @throws IllegalArgumentException if no folder name has been defined
         * @return the built config
         */
        public Config build() {
            this.checkValid();
            return new Config(folder, name, type, categories.toArray(new ConfigCategory[0]));
        }
    }

    /**
     * enum that defines a sided category folder.
     * {@link Type#UNCATEGORIZED} defines no folder.
     */
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
