package turing.mods.configurator;

import mcp.MethodsReturnNonnullByDefault;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import turing.mods.configurator.api.BooleanConfigValue;
import turing.mods.configurator.api.Config;
import turing.mods.configurator.api.ConfigSerializer;

import javax.annotation.ParametersAreNonnullByDefault;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@Mod(Configurator.MOD_ID)
public class Configurator {
    public static final String MOD_ID = "configurator";
    public static final Logger LOGGER = LogManager.getLogger("Configurator");
    protected static final List<Config> configs = new ArrayList<>();
    public static final BooleanConfigValue CONTAINED;

    static {
        Config.Builder builder = Config.Builder.builder().ofType(Config.Type.UNCATEGORIZED).withName("Configurator").withFolder("");

        builder.push("general");
        CONTAINED = builder.define("contain_in_one_folder", false);
        builder.pop();

        register(builder::build);
    }

    public Configurator() {
        MinecraftForge.EVENT_BUS.register(this);

        for (Config config : configs) {
            File file = ConfigSerializer.getConfigFile(config);
            if (!file.exists()) ConfigSerializer.writeConfig(config);
            else ConfigSerializer.readConfig(config);
        }
    }

    public static Config register(Config config) {
        configs.add(config);
        return config;
    }

    public static Config register(Supplier<Config> config) {
        Config c = config.get();
        configs.add(c);
        return c;
    }
}
