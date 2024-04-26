package io.github.pinont.smp;

import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import io.github.pinont.smp.Ability.Fangs;
import io.github.pinont.smp.Database.MySQL;
import io.github.pinont.smp.Database.SQLite;
import io.github.pinont.smp.Events.*;
import io.github.pinont.smp.GlobalEvent.ChatGame;
import io.github.pinont.smp.GlobalEvent.Contest;
import io.github.pinont.smp.Utils.DiscordWebhook;
import io.github.pinont.smp.Utils.GlobalEventUtils;
import io.github.pinont.smp.Utils.Msg;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.Yaml;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public final class Core extends JavaPlugin {

    public static Core plugin;

    public Core() {
        plugin = this;
    }

    @Override
    public void onEnable() {
        registerConfig();
        registerEvents();
        new GlobalEventUtils();
        GlobalEventUtils.start();
        try {
            initDatabase();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        try {
//            registerRecipes();
//        } catch (Exception e) {
//            Msg.console("Error while registering recipes: " + e.getMessage());
//            Msg.console(e.getMessage());
//            try {
//                DiscordWebhook.sendEmbedMessage("Shutting Down Server", e.getMessage(), "990000");
//            } catch (IOException ex) {
//                throw new RuntimeException(ex);
//            }
//            Bukkit.getServer().shutdown();
//        }

        getLogger().info("Plugin enabled");
    }

    @Override
    public void onDisable() {
        getLogger().info("Plugin disabled");
    }

    private void registerConfig() {
        // get Default config and lines
        if (this.getConfig().get("config.yml") == null) {
            ArrayList<String> lines = new ArrayList<>();
            getConfig().addDefault("config.yml", lines);
            getConfig().options().copyDefaults();
            saveDefaultConfig();
        }
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new ChestLock(), this);
        getServer().getPluginManager().registerEvents(new PlaceBlockTag(), this);
        getServer().getPluginManager().registerEvents(new MobBreedingChance(), this);
        getServer().getPluginManager().registerEvents(new MithrilDrops(), this);
        getServer().getPluginManager().registerEvents(new ChatGame(), this);
        getServer().getPluginManager().registerEvents(new Contest(), this);
        getServer().getPluginManager().registerEvents(new PlantCrops(), this);
        getServer().getPluginManager().registerEvents(new InventoryFull(), this);
        getServer().getPluginManager().registerEvents(new JoinEvents(), this);
        getServer().getPluginManager().registerEvents(new TreeFeller(), this);
        getServer().getPluginManager().registerEvents(new Fangs(), this);
        getServer().getPluginManager().registerEvents(new TridentEnhancement(), this);
    }

//    private void registerRecipes() {
//        Fangs.Recipe();
//    }

    private void initDatabase() throws IOException {
        try {
            if (getConfig().getString("database_type").equalsIgnoreCase("sqlite")) {
                SQLite.init();
            }
            else if (getConfig().getString("database_type").equalsIgnoreCase("mysql")) {
                MySQL.init();
            }
            else {
                Msg.console("Invalid database type. Please check your config.yml");
                DiscordWebhook.sendEmbedMessage("Shutting Down Server", "Invalid database type. Please check your config.yml", "990000");
                Bukkit.getServer().shutdown();
            }
        } catch (SQLException e) {
            Msg.console("Error while connecting to database: " + e.getMessage());
            Msg.console(e.getMessage());
            DiscordWebhook.sendEmbedMessage("Shutting Down Server", e.getMessage(), "990000");
            Bukkit.getServer().shutdown();
        }
    }

    public static Plugin worldedit = null;

    private void initWorldeditPlugin() {
        if (Bukkit.getPluginManager().getPlugin("WorldEdit") != null) {
            Msg.console("WorldEdit plugin found");
            worldedit = new WorldEditPlugin();
        }
        else {
            Msg.console(ChatColor.YELLOW + "WorldEdit plugin not found\n" +
                    "Please install WorldEdit plugin to use full feature of this plugin\n" +
                    "https://dev.bukkit.org/projects/worldedit");
        }
    }

    private void worldEditImplementFeature() throws FileNotFoundException {
        // Command

        // Events

    }

    private void loadAreaFile() throws IOException {
        File areaFile = new File(plugin.getDataFolder(), "selectedArea.yml");
        if (!areaFile.exists()) {
            areaFile.createNewFile();
        }

        YamlConfiguration config = YamlConfiguration.loadConfiguration(areaFile);
        config.save(areaFile);
    }

    public @NotNull NamespacedKey key(String string) {
        return string == null ? null : new NamespacedKey(this, string);
    }


    public MetadataValue metadata(int i) {
        return new MetadataValue() {
            @Override
            public Object value() {
                return i;
            }

            @Override
            public int asInt() {
                return i;
            }

            @Override
            public float asFloat() {
                return i;
            }

            @Override
            public double asDouble() {
                return i;
            }

            @Override
            public long asLong() {
                return i;
            }

            @Override
            public short asShort() {
                return (short) i;
            }

            @Override
            public byte asByte() {
                return (byte) i;
            }

            @Override
            public boolean asBoolean() {
                return i != 0;
            }

            @Override
            public String asString() {
                return String.valueOf(i);
            }

            @Override
            public Plugin getOwningPlugin() {
                return plugin;
            }

            @Override
            public void invalidate() {

            }
        };
    }
}
