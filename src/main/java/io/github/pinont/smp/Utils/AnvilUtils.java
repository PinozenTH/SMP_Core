package io.github.pinont.smp.Utils;

import org.bukkit.Material;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;

public class AnvilUtils {

    public static void CraftItem(AnvilInventory anvilInventory, ItemStack base, ItemStack addition, ItemStack result) {
        if (anvilInventory.getItem(0) == base && anvilInventory.getItem(1) == addition) {
            anvilInventory.setItem(0, result);
            anvilInventory.setItem(1, new ItemStack(Material.AIR));
        }
    }

}
