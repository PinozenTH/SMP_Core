package io.github.pinont.smp.Events;

import io.papermc.paper.event.player.PlayerInventorySlotChangeEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class InventoryFull implements Listener {

    @EventHandler
    public void inventoryEvent(PlayerInventorySlotChangeEvent event) {

        Player player = event.getPlayer();

        if (player.getInventory().firstEmpty() == -1) {
            player.sendTitle("Inventory Full", "", 10, 70, 20);
        }

    }

}
