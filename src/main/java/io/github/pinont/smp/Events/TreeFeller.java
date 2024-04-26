package io.github.pinont.smp.Events;

import io.github.pinont.smp.Utils.Msg;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import static io.github.pinont.smp.Core.plugin;

public class TreeFeller implements Listener {

    HashMap<Player, Long> sneakTime = new HashMap<>();
    HashMap<Player, Integer> sneakCount = new HashMap<>();
    HashMap<Player, Boolean> treeFeller = new HashMap<>();

    @EventHandler
    public void onTreeFeller(PlayerToggleSneakEvent event) {

        Player player = event.getPlayer();

        if (player.getInventory().getItemInMainHand().getType().isAir()) return; // check if player is holding an item

        if (axe().contains(player.getInventory().getItemInMainHand().getType())) {
            sneakTime.putIfAbsent(player, TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
            if (sneakTime.get(player) <= TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()) + 2) {
                sneakCount.putIfAbsent(player, 0);
                sneakCount.put(player, sneakCount.get(player) + 1);
                if (sneakCount.get(player) >= 5 && (!treeFeller.containsKey(player) || !treeFeller.get(player))) {
                    player.setSneaking(false);
                    // minecraft tick sound
                    player.playSound(player.getLocation(), "minecraft:block.wood.break", 1, 1);
                    Msg.player(player, ChatColor.YELLOW + "Tree Feller " + ChatColor.GREEN + "Activated");
                    treeFeller.put(player, true);
                    AbilityExpire(player, 1L);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerMineTree(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        if (block.getType().isAir() || block.isEmpty()) return;
        if (block.hasMetadata("placed")) return;

        if (treeFeller.containsKey(player)) {
            if (treeFeller.get(player)) {
                breakTree(block, player);
            }
        }
    }

    private void breakTree(Block block, Player player) {
        if (wood().contains(block.getType())) {
            checkBlock(block, player);
        }
    }

    private void checkBlock(Block block, Player player) {
        Location location = block.getLocation();
        for (int x = -1; x <= 1; x++) {
            for (int y = 0; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    if (!location.clone().add(x, y, z).getBlock().getType().isAir()) {
                        Block block1 = location.clone().add(x, y, z).getBlock();
                        if (wood().contains(block1.getType()) && !block1.hasMetadata("placed")) {
                            if (player.getInventory().getItemInMainHand().getType().isAir()) return;
                            if (player.getInventory().getItemInMainHand().getDurability() < player.getInventory().getItemInMainHand().getType().getMaxDurability()) {
                                block1.breakNaturally();
                                player.getInventory().getItemInMainHand().setDurability((short) (player.getInventory().getItemInMainHand().getDurability() + 1));
                            } else {
                                return;
                            }
                            checkBlock(block1, player);
                        }
                    }
                }
            }
        }
    }

    private static ArrayList<Material> axe() {

        ArrayList<Material> axes = new ArrayList<>();
        axes.add(Material.WOODEN_AXE);
        axes.add(Material.STONE_AXE);
        axes.add(Material.IRON_AXE);
        axes.add(Material.GOLDEN_AXE);
        axes.add(Material.DIAMOND_AXE);
        axes.add(Material.NETHERITE_AXE);

        return axes;

    }

    private ArrayList<Material> wood() {
        ArrayList<Material> wood = new ArrayList<>();
        wood.add(Material.ACACIA_LOG);
        wood.add(Material.BIRCH_LOG);
        wood.add(Material.DARK_OAK_LOG);
        wood.add(Material.JUNGLE_LOG);
        wood.add(Material.OAK_LOG);
        wood.add(Material.SPRUCE_LOG);
        wood.add(Material.STRIPPED_ACACIA_LOG);
        wood.add(Material.STRIPPED_BIRCH_LOG);
        wood.add(Material.STRIPPED_DARK_OAK_LOG);
        wood.add(Material.STRIPPED_JUNGLE_LOG);
        wood.add(Material.STRIPPED_OAK_LOG);
        wood.add(Material.STRIPPED_SPRUCE_LOG);
        return wood;
    }

    private void AbilityExpire(Player player, Long x) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            treeFeller.remove(player);
            Msg.player(player, ChatColor.YELLOW + "Tree Feller " + ChatColor.RED + "Deactivate");
        }, 20 * 60 * x);
    }

}
