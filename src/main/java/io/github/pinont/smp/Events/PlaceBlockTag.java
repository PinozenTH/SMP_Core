package io.github.pinont.smp.Events;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.metadata.FixedMetadataValue;

import static io.github.pinont.smp.Core.plugin;

public class PlaceBlockTag implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        block.setMetadata("placed", new FixedMetadataValue(plugin, true));
        block.setMetadata("placedByPlayer", new FixedMetadataValue(plugin, player.getUniqueId()));
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Block block = event.getBlock();
        if (block.hasMetadata("placed")) {
            block.removeMetadata("placed", plugin);
            block.removeMetadata("placedByPlayer", plugin);
        }
    }

}
