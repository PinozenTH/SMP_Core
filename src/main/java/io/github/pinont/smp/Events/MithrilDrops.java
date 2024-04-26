package io.github.pinont.smp.Events;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Random;

public class MithrilDrops implements Listener {

    @EventHandler
    public void onIronDrop(BlockBreakEvent event) {
        if (event.getBlock().isEmpty() || event.getBlock().isEmpty()) return;
        if (event.getBlock().getType().equals(Material.IRON_ORE) || event.getBlock().getType().equals(Material.DEEPSLATE_IRON_ORE)) {
            Block block = event.getBlock();
            Random random = new Random();
            int chance = random.nextInt(600);
            if (chance == 1) {
                ItemStack mithril = new ItemStack(Material.PRISMARINE_CRYSTALS, 1);
                ItemMeta mithrilMeta = mithril.getItemMeta();
                mithrilMeta.setDisplayName(ChatColor.RESET + "Mithril");
                mithril.setItemMeta(mithrilMeta);
                block.getWorld().dropItemNaturally(block.getLocation(), mithril);
                block.setType(Material.AIR);
            }
        }
    }
}
