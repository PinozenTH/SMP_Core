package io.github.pinont.smp.Events;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.HopperInventorySearchEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.metadata.FixedMetadataValue;

import static io.github.pinont.smp.Core.plugin;

public class ChestLock implements Listener {

    @EventHandler
    public void BlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        if (block.getType().isAir() || block.isEmpty()) return;
        if (block.hasMetadata("placed")) {
            if (block.getType().equals(Material.TRAPPED_CHEST)) {
                if (!block.getMetadata("placedByPlayer").get(0).asString().equals(player.getUniqueId().toString())) {
                    player.sendMessage("You are not allowed to break this chest");
                    event.setCancelled(true);
                }
            } else if (block.getType().equals(Material.HOPPER)) {
                if (!block.getMetadata("placedByPlayer").get(0).asString().equals(player.getUniqueId().toString())) {
                    player.sendMessage("You are not allowed to break this hopper");
                    event.setCancelled(true);
                }
            } else if (block.getType().equals(Material.HOPPER_MINECART)) {
                if (!block.getMetadata("placedByPlayer").get(0).asString().equals(player.getUniqueId().toString())) {
                    player.sendMessage("You are not allowed to break this hopper");
                    event.setCancelled(true);
                }
            } else if (block.getType().equals(Material.SHULKER_BOX)) {
                if (!block.getMetadata("placedByPlayer").get(0).asString().equals(player.getUniqueId().toString())) {
                    player.sendMessage("You are not allowed to break this shulker box");
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_AIR)) return;

        Block block = event.getClickedBlock();

        assert block != null;
        if (block.hasMetadata("placed")) {
            if (!block.hasMetadata("unlocked") && !block.getMetadata("placedByPlayer").get(0).asString().equals(player.getUniqueId().toString())) {
                if (block.getType().equals(Material.TRAPPED_CHEST)) {
                    if (!block.getMetadata("placedByPlayer").get(0).asString().equals(player.getUniqueId().toString())) {
                        player.sendMessage("You are not allowed to open this chest");
                        player.getInventory().close();
                        event.setCancelled(true);
                    }
                } else if (block.getType().equals(Material.HOPPER)) {
                    if (!block.getMetadata("placedByPlayer").get(0).asString().equals(player.getUniqueId().toString())) {
                        player.sendMessage("You are not allowed to open this hopper");
                        player.getInventory().close();
                        event.setCancelled(true);
                    }
                } else if (block.getType().equals(Material.HOPPER_MINECART)) {
                    if (!block.getMetadata("placedByPlayer").get(0).asString().equals(player.getUniqueId().toString())) {
                        player.sendMessage("You are not allowed to open this hopper");
                        player.getInventory().close();
                        event.setCancelled(true);
                    }
                } else if (block.getType().equals(Material.SHULKER_BOX)) {
                    if (!block.getMetadata("placedByPlayer").get(0).asString().equals(player.getUniqueId().toString())) {
                        player.sendMessage("You are not allowed to open this shulker box");
                        player.getInventory().close();
                        event.setCancelled(true);
                    }
                }
            } else if (player.getInventory().getItemInMainHand().getType().equals(Material.TRIPWIRE_HOOK) && player.isSneaking() && block.getMetadata("placedByPlayer").get(0).asString().equals(player.getUniqueId().toString())) {
                if (!block.hasMetadata("unlocked")) {
                    block.setMetadata("unlocked", new FixedMetadataValue(plugin, true));
                    player.sendMessage("You have unlocked this chest");
                } else if (block.hasMetadata("unlocked")) {
                    block.removeMetadata("unlocked", plugin);
                    player.sendMessage("You have locked this chest");
                }
            }
        }
    }

    @EventHandler
    public void onHopperGrabTrappedChestItem(HopperInventorySearchEvent event) {
        Block src = event.getSearchBlock();
        Block hopper = event.getBlock();
        if (hopper.hasMetadata("placed")) {
            if (src.getType().equals(Material.TRAPPED_CHEST)) {
                if (!src.getMetadata("placedByPlayer").get(0).asString().equals(hopper.getMetadata("placedByPlayer").get(0).asString())) {
                    hopper.breakNaturally();
                }
            }
        }
    }

}
