package io.github.pinont.smp.Events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static io.github.pinont.smp.Utils.GlobalEventUtils.ContestBar;

public class JoinEvents implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();

        if (player.hasPlayedBefore()) {
            Bukkit.broadcastMessage("Welcome back " + player.getName() + "!");
        } else {
            Bukkit.broadcastMessage("Welcome " + player.getName() + "!");
        }

        if (ContestBar != null) {
            ContestBar.addPlayer(player);
        }

    }

}
