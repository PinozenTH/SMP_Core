package io.github.pinont.smp.Utils;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class InventoryUtils implements Listener {

    public static Boolean isInventoryFull(Player player) {
        return player.getInventory().firstEmpty() == -1;
    }

}
