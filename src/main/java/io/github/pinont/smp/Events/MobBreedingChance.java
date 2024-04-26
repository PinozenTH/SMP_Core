package io.github.pinont.smp.Events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityBreedEvent;

import java.util.Random;

import static io.github.pinont.smp.Core.plugin;

public class MobBreedingChance implements Listener {

    private int chance;

    private int successRate = 100 - plugin.getConfig().getInt("mobBreedingSuccessPercentage");

    @EventHandler
    public void onEntitySpawn(CreatureSpawnEvent event) {
        Entity entity = event.getEntity();
        if (successRate == 0) successRate = 1;
        if (entity.getEntitySpawnReason().equals(CreatureSpawnEvent.SpawnReason.BREEDING)) {
            if (chance < successRate) { // if number < 60 -> remove entity
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onEntityBreeding(EntityBreedEvent event) {
        Random random = new Random(); // random new number
        chance = random.nextInt(100); // random number between 0 and 100
        if (chance < successRate) { // if number < successRate -> breeding failed
            event.getBreeder().sendMessage(ChatColor.RED + "Breeding Failed!");
        } else { // if > 60 -> success
            event.getBreeder().sendMessage(ChatColor.GREEN + "Breeding Successful!");
        }
    }
}
