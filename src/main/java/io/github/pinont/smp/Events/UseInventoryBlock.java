package io.github.pinont.smp.Events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class UseInventoryBlock implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Block block = event.getBlock();

        if (block.isSolid() && isChestBlock(block)) {
            Player player = event.getPlayer();
            if (block instanceof InventoryHolder) {
                InventoryHolder chest = (InventoryHolder) block.getState();
                Inventory inventory = chest.getInventory();
                ItemStack[] items = inventory.getContents();
                Inventory chestInv = Bukkit.createInventory(chest, chest.getInventory().getSize());
                chestInv.setContents(items);
                player.openInventory(chestInv);
            }
            event.setCancelled(true);
        }
    }

    private Boolean isChestBlock(Block block) {
        return block.getType().equals(Material.ENDER_CHEST) || block.getType().equals(Material.SHULKER_BOX);
    }

}
