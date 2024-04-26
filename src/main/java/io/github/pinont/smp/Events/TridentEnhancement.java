package io.github.pinont.smp.Events;

import io.github.pinont.smp.Utils.LoyaltyTridentTrackerTask;
import io.github.pinont.smp.Utils.ReflectionUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Trident;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.player.PlayerPickupArrowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import static io.github.pinont.smp.Core.plugin;
import static org.bukkit.Bukkit.getServer;

public class TridentEnhancement implements Listener {

    static ReflectionUtils reflectionUtils = new ReflectionUtils();

    @EventHandler(ignoreCancelled = true)
    private void onProjectileLaunch(ProjectileLaunchEvent event)
    {
        if(event.getEntityType() == EntityType.TRIDENT && (event.getEntity().getShooter() instanceof Player))
        {
            Player p = (Player) event.getEntity().getShooter();
            ItemStack tridentItem = null;
            boolean fromOffhand = false;
            // If the player is holding two tridents, only the one from the main hand will be thrown
            // (Unless the player starts charging in the offhand then equips a trident in the main hand)
            if(p.getInventory().getItemInMainHand().getType() == Material.TRIDENT)
            {
                tridentItem = p.getInventory().getItemInMainHand();
            }
            else if(p.getInventory().getItemInOffHand().getType() == Material.TRIDENT)
            {
                fromOffhand = true;
                tridentItem = p.getInventory().getItemInOffHand();
            }

            // The trident could be thrown "artificially"
            if(tridentItem != null)
            {
                // Check if it was thrown from the offhand
                if(fromOffhand)
                {
                    event.getEntity().setMetadata("offhand", new FixedMetadataValue(plugin, true));
                }
                if(tridentItem.getEnchantmentLevel(Enchantment.LOYALTY) != 0)
                {
                    event.getEntity().setMetadata("loyalty", new FixedMetadataValue(plugin,
                            tridentItem.getEnchantmentLevel(Enchantment.LOYALTY)));

                    LoyaltyTridentTrackerTask trackerTask = new LoyaltyTridentTrackerTask((Trident) event.getEntity(), reflectionUtils);
                    trackerTask.runTaskTimer(plugin, 0, 1);
                }
            }

        }
    }

    @EventHandler(ignoreCancelled = true)
    private void onPlayerPickupArrow(PlayerPickupArrowEvent event)
    {
        // Check if this is a trident that should be in the offhand
        if (event.getArrow() instanceof Trident) {
            Player p = event.getPlayer();
            if (
                    event.getArrow().hasMetadata("offhand") && p.getInventory().getItemInOffHand().getType() == Material.AIR) {
                // The itemstack gets modified after the event so it must be cloned for future comparison
                ItemStack item = event.getItem().getItemStack().clone();

                // The item isn't in the inventory yet so schedule a checker
                getServer().getScheduler().scheduleSyncDelayedTask(plugin, () ->
                {
                    // Double-check to ensure offhand item is still empty
                    if (event.getPlayer().getInventory().getItemInOffHand().getType() != Material.AIR) {
                        return;
                    }

                    // Start from end of inventory to get the most recently added trident in case duplicates exist
                    ItemStack[] contents = p.getInventory().getContents();
                    for (int i = contents.length - 1; i >= 0; i--) {
                        ItemStack current = contents[i];
                        if (current != null && current.equals(item)) {
                            // If we find the trident and the offhand is clear, put it in the offhand
                            p.getInventory().setItemInOffHand(current.clone());
                            current.setAmount(current.getAmount() - 1);
                            break;
                        }
                    }
                    p.updateInventory();
                });
            }
            p.setCooldown(Material.TRIDENT, 50);
        }

    }

}
