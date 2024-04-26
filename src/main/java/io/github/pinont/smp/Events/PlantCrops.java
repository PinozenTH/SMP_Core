package io.github.pinont.smp.Events;

import io.github.pinont.smp.Utils.CropsUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;


public class PlantCrops implements Listener {

    @EventHandler
    public void playerInteractCrops(PlayerInteractEvent event) {

        Player player = event.getPlayer();
        Action action = event.getAction();

        if (event.getAction().isRightClick() && action != Action.RIGHT_CLICK_AIR) {
            Block block = event.getClickedBlock();
            if (!player.getInventory().getItemInMainHand().getType().isAir() && player.getInventory().getItemInMainHand().getType().equals(Material.BONE_MEAL)) return;
            if (CropsUtils.isCrop(block.getType())) {
                CropsUtils.plantCrop(block, player);
            }
        }
    }

}
