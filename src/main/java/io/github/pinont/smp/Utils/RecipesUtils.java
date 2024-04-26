package io.github.pinont.smp.Utils;

import io.github.pinont.smp.Core;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;

import java.util.Map;

public class RecipesUtils {

    public static void addShapelessRecipe(NamespacedKey key, ItemStack item, String[] ingredients) {
        ShapelessRecipe recipe = new ShapelessRecipe(key, item);

        // Convert ingredients strings into material types
        for (String ingredient : ingredients) {
            Material ingredientMaterial = Material.matchMaterial(ingredient);
            if (ingredientMaterial != null) {
                recipe.addIngredient(ingredientMaterial);
            }
        }

        // Add the recipe to the server
        Bukkit.addRecipe(recipe);
    }

    // Update your function implementation
    public static void addShapedRecipe(Core plugin, String name, ItemStack recipeResult, String number, String number1, String number2, Map<Character, Material> ingredientMapping) {
        NamespacedKey recipeKey = new NamespacedKey(plugin, name);

        // Create the recipe item
        ItemStack recipeItem = recipeResult;

        // Create the recipe instance
        ShapedRecipe recipe = new ShapedRecipe(recipeKey, recipeItem);

        // Set the shape of the recipe
        recipe.shape(number, number1, number2);

        // Set the ingredients using the ingredient mapping
        for (Map.Entry<Character, Material> entry : ingredientMapping.entrySet()) {
            Character key = entry.getKey();
            Material material = entry.getValue();
            recipe.setIngredient(key, material);
        }

        // Add the recipe to the server
        Bukkit.addRecipe(recipe);
    }


}
