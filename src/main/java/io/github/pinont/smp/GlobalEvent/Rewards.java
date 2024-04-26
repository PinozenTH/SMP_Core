package io.github.pinont.smp.GlobalEvent;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

import static java.lang.Math.abs;

public class Rewards {


    public static ItemStack rewards(Integer position) {
        Random random = new Random();
        int rdm = random.nextInt(4);
        ItemStack item = null;

        if (rdm == 0) {
            item = new ItemStack(Material.DIAMOND, abs(6-position) + rdm);
        } else if (rdm == 1) {
            item = new ItemStack(Material.EMERALD, abs(6-position) + rdm);
        } else if (rdm == 2) {
            item = new ItemStack(Material.GOLD_INGOT, abs(6-position) + rdm);
        } else {
            item = new ItemStack(Material.IRON_INGOT, abs(6-position) + rdm);
        }

        return item;

    }

}
