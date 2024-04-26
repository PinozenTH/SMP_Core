package io.github.pinont.smp.Utils;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class CropsUtils {

    public static Boolean isCrop(Material material) {

        return !material.isAir() && getMaterial().contains(material);
    }

    private static ArrayList<Material> getMaterial() {
        ArrayList<Material> list = new ArrayList<>();

        list.add(Material.WHEAT);
        list.add(Material.POTATOES);
        list.add(Material.CARROTS);
        list.add(Material.BEETROOTS);
        list.add(Material.NETHER_WART);

        return list;
    }

    public static void plantCrop(Block block, Player player) {

        Material material = block.getType();

        if (isCrop(material)) {
            if (isFullyGrow(block)) {
                player.getInventory().addItem(block.getDrops().toArray(new ItemStack[0]));
                block.setType(block.getType());
            }
        }
    }

    public static Boolean isFullyGrow(Block block) {
        return block.getBlockData() instanceof Ageable && ((Ageable) block.getBlockData()).getAge() == ((Ageable) block.getBlockData()).getMaximumAge();
    }
}
