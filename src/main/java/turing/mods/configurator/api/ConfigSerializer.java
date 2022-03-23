package turing.mods.configurator.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import mcp.MethodsReturnNonnullByDefault;
import turing.mods.configurator.Configurator;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class ConfigSerializer {
    /** The top-level config folder */
    public static final File CONFIG_DIR = new File("config");
    protected static final Gson GSON = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();

    /**
     * Converts a config into a {@link JsonObject}.
     * @param config the config to convert
     * @return the json version of the given config
     */
    public static JsonObject serialize(Config config) {
        JsonObject json = new JsonObject();

        for (ConfigCategory category : config.categories) writeCategory(json, category);

        return json;
    }

    private static void writeCategory(JsonObject parent, ConfigCategory category) {
        JsonObject categoryJSON = new JsonObject();

        for (ConfigValue<?> value : category.getValues()) {
            if (value instanceof IntegerConfigValue || value instanceof FloatConfigValue || value instanceof DoubleConfigValue || value instanceof LongConfigValue || value instanceof ShortConfigValue || value instanceof ByteConfigValue) categoryJSON.addProperty(value.name, (Number) value.get());
            else if (value instanceof BooleanConfigValue) categoryJSON.addProperty(value.name, (Boolean) value.get());
            else categoryJSON.addProperty(value.name, (String) value.get());
        }

        for (ConfigCategory category1 : category.subCategories) writeCategory(categoryJSON, category1);
        parent.add(category.name, categoryJSON);
    }

    /**
     * Writes a config to a .json file.
     * @param config the config to write
     */
    public static void writeConfig(Config config) {
        JsonObject json = serialize(config);
        String name = config.name + ".json";
        String folderName = getFolder(config);
        writeConfigJson(json, name, new File(folderName));
    }

    /**
     * Reads the given config's .json file and puts the read values into the config values.
     * @param config the config to read
     */
    public static void readConfig(Config config) {
        JsonObject json = readConfigJson(config.name + ".json", new File(getFolder(config)));
        if (json == null) throw new NullPointerException("Could not read config json!");

        for (ConfigCategory category : config.categories) readCategory(json, category);
    }

    private static void readCategory(JsonObject json, ConfigCategory category) {
        JsonObject categoryJSON = json.getAsJsonObject(category.name);
        if (categoryJSON == null) throw new NullPointerException();

        for (ConfigValue<?> value : category.getValues()) {
            if (value instanceof IntegerConfigValue) ((IntegerConfigValue) value).set(categoryJSON.get(value.name).getAsInt());
            else if (value instanceof FloatConfigValue) ((FloatConfigValue) value).set(categoryJSON.get(value.name).getAsFloat());
            else if (value instanceof DoubleConfigValue) ((DoubleConfigValue) value).set(categoryJSON.get(value.name).getAsDouble());
            else if (value instanceof LongConfigValue) ((LongConfigValue) value).set(categoryJSON.get(value.name).getAsLong());
            else if (value instanceof ShortConfigValue) ((ShortConfigValue) value).set(categoryJSON.get(value.name).getAsShort());
            else if (value instanceof ByteConfigValue) ((ByteConfigValue) value).set(categoryJSON.get(value.name).getAsByte());
            else if (value instanceof BooleanConfigValue) ((BooleanConfigValue) value).set(categoryJSON.get(value.name).getAsBoolean());
            else ((ConfigValue<String>) value).set(categoryJSON.get(value.name).getAsString());
        }

        for (ConfigCategory category1 : category.getSubCategories()) readCategory(categoryJSON, category1);
    }

    /**
     * Reads the raw {@link JsonObject} from a config file
     * @param name the name of the config file
     * @param folder the folder the config file is in
     * @return json that was read
     */
    @Nullable
    public static JsonObject readConfigJson(String name, File folder) {
        if (!folder.exists()) throw new NullPointerException("Attempt to read config from non-existent folder!");

        String fileName = folder.getPath() + "/" + name;
        Path path = Paths.get(fileName);

        if (!path.toFile().exists()) throw new NullPointerException("Attempt to read json from non-existent config file!");
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            return GSON.fromJson(reader, JsonObject.class);
        } catch (IOException e) {
            Configurator.LOGGER.error(e);
        }
        return null;
    }

    /**
     * Gets the config file for the given config.
     * @param config the config to get a file of
     * @return the config's {@link File}
     */
    public static File getConfigFile(Config config) {
        Path path = Paths.get(getFolder(config) + "/" + config.name + ".json");
        return path.toFile();
    }

    /**
     * Gets the folder path for this config to put into.
     * @param config the config
     * @return a string of the config's full folder path
     */
    public static String getFolder(Config config) {
        return config.type.getFolder(CONFIG_DIR.getPath() + (!config.folder.isEmpty() ? "/" + config.folder : ""));
    }

    private static void writeConfigJson(JsonObject json, String name, File folder) {
        if (!initFolder(folder)) throw new NullPointerException("Could not write config!");

        String fileName = folder.getPath() + "/" + name;
        Path path = Paths.get(fileName);

        if (path.toFile().exists()) throw new IllegalStateException(String.format("Config %s already exists!", fileName));
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(GSON.toJson(json));
        } catch (IOException e) {
            Configurator.LOGGER.error(e);
        }
    }

    private static boolean initFolder(File dir) {
        if (!dir.exists() && !dir.mkdirs()){
            Configurator.LOGGER.error(String.format("Could not make folder at %s", dir.getAbsolutePath()));
            return false;
        }
        return true;
    }

    /**
     * Directly writes a config file using a pre-built {@link JsonObject}
     * @param json the json object to write
     * @param fileName the name of the config file
     * @param folder the name of the folder to create the config file in
     * @param type the config's type category
     * @return if the write operation was successful
     */
    public static boolean writeConfigJson(JsonObject json, String fileName, String folder, Config.Type type) {
        String folderName = !folder.isEmpty() ? "/" + folder : "";
        if (!initFolder(new File(type.getFolder(CONFIG_DIR.getPath() + folderName)))) throw new NullPointerException();

        Path path = Paths.get(CONFIG_DIR.getPath() + folderName + "/" + fileName + ".json");

        if (path.toFile().exists()) throw new IllegalStateException(String.format("Config file %s already exists!", fileName));
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(GSON.toJson(json));
            return true;
        } catch (IOException e) {
            Configurator.LOGGER.error(e);
            return false;
        }
    }
}
