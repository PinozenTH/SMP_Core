package io.github.pinont.smp.GlobalEvent;

import io.papermc.paper.event.player.PlayerTradeEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerExpChangeEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;
import java.util.Random;

import static io.github.pinont.smp.Utils.GlobalEventUtils.*;

public class Contest implements Listener {

    public static Boolean stone = false;
    public static Boolean wheat = false;
    public static Boolean carrot = false;
    public static Boolean potato = false;
    public static Boolean netherack = false;
    public static Boolean xp = false;
    public static Boolean buy = false;
    public static Boolean sugarCane = false;

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        if (contest) {
            blockBreak(block, player);
        }
    }

    @EventHandler
    public void onXpGain(PlayerExpChangeEvent event) {
        Player player = event.getPlayer();
        if (xp) {
            mineCount(player);
            if (Objects.equals(playerMine.get(player), mineGoal)) {
                Bukkit.broadcastMessage(ChatColor.YELLOW + player.getName() + ChatColor.GREEN + " has done the contest!");
            }
        }
    }

    @EventHandler
    public void playerTrading(PlayerTradeEvent event) {
        Player player = event.getPlayer();
        if (buy) {

            if (event.getTrade().getIngredients().contains(Material.EMERALD)) mineCount(player);

            if (Objects.equals(playerMine.get(player), mineGoal)) {
                player.getInventory().addItem(new ItemStack(Material.EMERALD, 64));
                Bukkit.broadcastMessage(ChatColor.YELLOW + player.getName() + ChatColor.GREEN + " has done the contest!");
            }
        }
    }

    @EventHandler
    public void onItemDamage(PlayerItemDamageEvent event) {
        if (buff) {
            event.setCancelled(true);
        }
    }

    private static void blockBreak(Block block, Player player) {

        Material material = block.getType();
        if (stone) {
            if (
                    material == Material.STONE ||
                            material == Material.COBBLESTONE ||
                            material == Material.DEEPSLATE ||
                            material == Material.COBBLED_DEEPSLATE ||
                            material == Material.BLACKSTONE
            ) {

                mineCount(player);

            }
        } else if (wheat) {
            if (material == Material.WHEAT) {

                mineCount(player);

                if (Objects.equals(playerMine.get(player), mineGoal)) {
                    Bukkit.broadcastMessage(ChatColor.YELLOW + player.getName() + ChatColor.GREEN + " has done the contest!");
                }

            }
        } else if (carrot) {
            if (material == Material.CARROTS) {

                mineCount(player);

                if (Objects.equals(playerMine.get(player), mineGoal)) {
                    Bukkit.broadcastMessage(ChatColor.YELLOW + player.getName() + ChatColor.GREEN + " has done the contest!");
                }

            }
        } else if (potato) {
            if (material == Material.POTATOES) {

                mineCount(player);

                if (Objects.equals(playerMine.get(player), mineGoal)) {
                    Bukkit.broadcastMessage(ChatColor.YELLOW + player.getName() + ChatColor.GREEN + " has done the contest!");
                }

            }
        } else if (netherack) {
            if (material == Material.NETHERRACK) {

                mineCount(player);

                if (Objects.equals(playerMine.get(player), mineGoal)) {
                    Bukkit.broadcastMessage(ChatColor.YELLOW + player.getName() + ChatColor.GREEN + " has done the contest!");
                }

            }
        } else if (sugarCane) {
            if (material == Material.SUGAR_CANE) {

                mineCount(player);

                if (Objects.equals(playerMine.get(player), mineGoal)) {
                    Bukkit.broadcastMessage(ChatColor.YELLOW + player.getName() + ChatColor.GREEN + " has done the contest!");
                }

            }
        }
    }

    public void stoneMining() {
        Random random = new Random();
        mineGoal = random.nextInt(1000) + 500;
        Bukkit.broadcastMessage(ChatColor.GREEN + "The contest has started! Mine " + ChatColor.YELLOW + mineGoal + ChatColor.GREEN + " stone!");
        stone = true;
        expire(15L);
    }

    public void wheatFarming() {
        Random random = new Random();
        mineGoal = random.nextInt(1000) + 500;
        Bukkit.broadcastMessage(ChatColor.GREEN + "The contest has started! Farm " + ChatColor.YELLOW + mineGoal + ChatColor.GREEN + " wheat!");
        wheat = true;
        expire(15L);
    }

    public void carrotFarming() {
        Random random = new Random();
        mineGoal = random.nextInt(1000) + 500;
        Bukkit.broadcastMessage(ChatColor.GREEN + "The contest has started! Farm " + ChatColor.YELLOW + mineGoal + ChatColor.GREEN + " carrots!");
        carrot = true;
        expire(15L);
    }

    public void potatoFarming() {
        Random random = new Random();
        mineGoal = random.nextInt(1000) + 500;
        Bukkit.broadcastMessage(ChatColor.GREEN + "The contest has started! Farm " + ChatColor.YELLOW + mineGoal + ChatColor.GREEN + " potatoes!");
        potato = true;
        expire(15L);
    }

    public void netherackMining() {
        Random random = new Random();
        mineGoal = random.nextInt(1000) + 500;
        Bukkit.broadcastMessage(ChatColor.GREEN + "The contest has started! Mine " + ChatColor.YELLOW + mineGoal + ChatColor.GREEN + " netherack!");
        netherack = true;
        expire(15L);
    }

    public void xpMining() {
        Random random = new Random();
        mineGoal = random.nextInt(1000) + 500;
        Bukkit.broadcastMessage(ChatColor.GREEN + "The contest has started! Mine " + ChatColor.YELLOW + mineGoal + ChatColor.GREEN + " xp!");
        xp = true;
        expire(15L);
    }

    public void buyTrading() {
        Random random = new Random();
        mineGoal = random.nextInt(1000) + 500;
        Bukkit.broadcastMessage(ChatColor.GREEN + "The contest has started! Spend " + ChatColor.YELLOW + mineGoal + ChatColor.GREEN + " emeralds!");
        buy = true;
        expire(15L);
    }

    public void sugarCaneFarming() {
        Random random = new Random();
        mineGoal = random.nextInt(1000) + 500;
        Bukkit.broadcastMessage(ChatColor.GREEN + "The contest has started! Farm " + ChatColor.YELLOW + mineGoal + ChatColor.GREEN + " sugar cane!");
        sugarCane = true;
        expire(15L);
    }

    private static void mineCount(Player player) {
        if (playerMine.containsKey(player)) {
            playerMine.replace(player, playerMine.get(player), playerMine.get(player) + 1);
        } else {
            playerMine.put(player, 1);
        }

        if (playerMine.get(player).equals(mineGoal)) {
            Bukkit.broadcastMessage(ChatColor.YELLOW + player.getName() + ChatColor.GREEN + " has done the contest!");
            stone = false;
        }
    }
}