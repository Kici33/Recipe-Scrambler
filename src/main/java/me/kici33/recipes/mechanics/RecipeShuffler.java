package me.kici33.recipes.mechanics;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.*;

import java.util.*;

public class RecipeShuffler {

    private final static Random r = new Random(me.kici33.recipes.Recipe.getConf().getLong("seed"));

    public static void shuffleRecipes() {
        List<ItemStack> itemStacks = new ArrayList<ItemStack>();
        List<Recipe> stay = new ArrayList<Recipe>();
        List<ShapedRecipe> shaped = new ArrayList<ShapedRecipe>();
        List<FurnaceRecipe> furnace = new ArrayList<FurnaceRecipe>();
        List<ShapelessRecipe> shapeless = new ArrayList<ShapelessRecipe>();
        List<String> stayItems = new ArrayList<String>(me.kici33.recipes.Recipe.getConf().getStringList("stay-items"));
        for(Iterator<Recipe> recipeIterator = Bukkit.recipeIterator(); recipeIterator.hasNext(); ) {
            Recipe r = recipeIterator.next();
            if(stayItems.contains(r.getResult().getType().toString())){
                stay.add(r);
            }
            else {
                if (r instanceof FurnaceRecipe) {
                    furnace.add((FurnaceRecipe) r);
                }
                if (r instanceof ShapedRecipe) {
                    shaped.add((ShapedRecipe) r);
                }
                if (r instanceof ShapelessRecipe) {
                    shapeless.add((ShapelessRecipe) r);
                }
                itemStacks.add(r.getResult());
            }
        }
        String tag = "[RecipeScrambler] ";
        System.out.println(tag + "Cleaning Recipes");
        Bukkit.clearRecipes();

        System.out.println(tag + "Applying Default Recipes");
        for(Recipe r : stay) {
            Bukkit.addRecipe(r);
        }

        System.out.println(tag + "Applying Furnace Recipes");
        for(FurnaceRecipe recipe : furnace) {
            ItemStack itemStack = itemStacks.get(r.nextInt(itemStacks.size()));
            FurnaceRecipe recipeNew = new FurnaceRecipe(itemStack, itemStack.getType());
            itemStacks.remove(itemStack);
            recipeNew.setInput(recipe.getInput().getType());
            Bukkit.addRecipe(recipeNew);
        }
        System.out.println(tag + "Applying Shapeless Recipes");
        for(ShapelessRecipe shapelessRecipe : shapeless) {
            ItemStack itemStack = itemStacks.get(r.nextInt(itemStacks.size()));
            itemStacks.remove(itemStack);
            ShapelessRecipe newShapeless = new ShapelessRecipe(shapelessRecipe.getKey(), itemStack);
            for (ItemStack item : shapelessRecipe.getIngredientList()) {
                newShapeless.addIngredient(item.getType());
            }
            Bukkit.addRecipe(newShapeless);
        }
        System.out.println(tag + "Applying Shaped Recipes");
        for(ShapedRecipe recipe : shaped) {
            ItemStack itemStack = itemStacks.get(r.nextInt(itemStacks.size()));
            itemStacks.remove(itemStack);
            ShapedRecipe newShaped = new ShapedRecipe(recipe.getKey(), itemStack);
            newShaped.shape(recipe.getShape());
            Map<Character, ItemStack> ingredients = recipe.getIngredientMap();
            for (Map.Entry<Character, ItemStack> se : ingredients.entrySet()) {
                if (se.getValue() != null) {
                    newShaped.setIngredient(se.getKey(), se.getValue().getData());
                } else {
                    newShaped.setIngredient(se.getKey(), Material.AIR);
                }
            }
            Bukkit.addRecipe(newShaped);
        }
        System.out.println(tag + "Scrambled all recipes.");
    }
}
