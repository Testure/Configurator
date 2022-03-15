## About
Configurator is a 1.16+ Forge library mod that allows developers to define .json config files in the same style as Forge's own config system.

## Usage

### Mods

A typical config class goes as such:

    public static final BooleanConfigValue boolValue;
    public static final StringConfigValue stringValue;
    
    static {
        Config.Builder builder = Config.Builder.builder().withName("cool_config").withFolder("i_am_config").ofType(Config.Type.COMMON);
        
        builder.push("cool_booleans");
        boolValue = builder.define("i_am_cool", true);
        builder.pop();
        builder.push("cool_strings");
        stringValue = builder.define("my_name_is", "Dan");
        builder.pop();
        
        Configurator.register(builder);
    }

This will create a json file called **cool_config.json** in **config/i_am_config/common** with the following contents:

    {
        "cool_booleans": {
            "i_am_cool": true
        },
        "cool_strings": {
            "my_name_is": "Dan"
        }
    }

You will then be able to get these values by doing:

> boolValue.get();

and 

> stringValue.get();


You can use **Config.Type.UNCATEGORIZED** to have config files generate directly in their defined folder.

### CT

Coming soon!