package me.kici33.recipes.commands;

import me.kici33.recipes.Recipe;
import me.kici33.recipes.mechanics.RecipeShuffler;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ShuffleCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!sender.hasPermission("recipe.shuffle")) {
            sender.sendMessage("§cYou don't have permission to use this command.");
            return false;
        }
        Recipe.getConf().set("save", Math.random() * (1000L - 1L));
        RecipeShuffler.shuffleRecipes();
        sender.sendMessage("§cYou have successfully shuffled all recipes. Enjoy!");
        return false;
    }
}
