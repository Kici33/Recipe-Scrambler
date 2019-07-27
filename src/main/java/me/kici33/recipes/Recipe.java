package me.kici33.recipes;

import me.kici33.recipes.commands.ShuffleCommand;
import me.kici33.recipes.mechanics.RecipeShuffler;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Recipe extends JavaPlugin {

    private static FileConfiguration config;

    public void onEnable() {
        saveDefaultConfig();
        config = getConfig();
        if(!config.isSet("seed")) {
            config.set("seed", Bukkit.getWorlds().get(0).getSeed());
        }
        getCommand("shuffle").setExecutor(new ShuffleCommand());
        RecipeShuffler.shuffleRecipes();
    }

    public void onDisable() {
        saveConfig();
    }


    public static FileConfiguration getConf() {
        return config;
    }

}
