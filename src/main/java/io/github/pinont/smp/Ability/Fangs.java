package io.github.pinont.smp.Ability;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.tags.CustomItemTagContainer;
import org.bukkit.inventory.meta.tags.ItemTagType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

import static io.github.pinont.smp.Core.plugin;

public class Fangs implements Listener {

    @EventHandler
    public void useItem(PlayerInteractEvent event) {
        if (event.getPlayer().getInventory().getItemInMainHand().getType().isAir() || event.getPlayer().getInventory().getItemInMainHand().getType().isEmpty()) return;

        Player player = event.getPlayer();
        Action action = event.getAction();

        if (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK) {
            if (player.isSneaking()) {
                if (player.getInventory().getItemInMainHand().getItemMeta().getCustomTagContainer().hasCustomTag(plugin.key("fangs"), ItemTagType.BYTE)) {
                    if (player.getCooldown(player.getInventory().getItemInMainHand().getType()) > 0) return;
                    spawnFangs(player);
                    player.setCooldown(Material.TRIDENT, 120);
                }
            }
        }

    }

    private void spawnFangs(Player player) {
        int min_radius = 1;
        int max_radius = 5;
        for (int i = 0; i < 360; i += 10) {
            int radius = min_radius + (int)(Math.random() * ((max_radius - min_radius) + 1));
            double x = Math.cos(i) * radius;
            double z = Math.sin(i) * radius;
            if (x == 0 && z == 0) continue;
            Location spawnLocation = player.getLocation().add(x, -0.2, z);
            Collection<Entity> entities = spawnLocation.getNearbyEntities(x, 1, z);
            for (Entity entity : entities) {
                if (!(entity instanceof Item)) {
                    if (entity == player) continue;
                    entity.eject();
                    entity.getLocation().getY();
                    spawnLocation.setY(entity.getLocation().getY() - 0.2);
                }
            }
            EvokerFangs fangs = player.getWorld().spawn(spawnLocation, EvokerFangs.class);
            fangs.setOwner(player);
        }
    }

    @EventHandler
    public void onFangsDamage(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            Entity damager = event.getDamager();
            if (damager instanceof EvokerFangs) {
                if (((EvokerFangs) damager).getOwner().equals(player)) {
                    EntityDamageEvent ev1 = new EntityDamageEvent(event.getEntity(), EntityDamageEvent.DamageCause.MAGIC, 10);
                    ev1.setDamage(10);
                }
            }
        }
    }

    @EventHandler
    public void fangsDrops(EntityDeathEvent event) {
        if (event.getEntity() instanceof Evoker) {
            if (event.getEntity().getKiller() != null) {
                if (event.getEntity().getKiller() instanceof Player) {
                    Player player = event.getEntity().getKiller();
                    if (player.getInventory().getItemInMainHand().getType().isAir() || player.getInventory().getItemInMainHand().getType().isEmpty()) return;
                    int lootingLevel = 0;
                    if (player.getInventory().getItemInMainHand().getItemMeta().hasEnchant(Enchantment.LOOT_BONUS_MOBS)) {
                        lootingLevel = player.getInventory().getItemInMainHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_MOBS);
                    }
                    Random random = new Random();
                    int amount = random.nextInt(3) + random.nextInt(lootingLevel);
                    event.getDrops().add(fangsShard(amount));
                }
            }
        }
    }

    private ItemStack fangsShard(int amount) {
        ItemStack item = new ItemStack(Material.PRISMARINE_SHARD);
        ItemMeta meta = item.getItemMeta();
        CustomItemTagContainer tagContainer = meta.getCustomTagContainer();
        tagContainer.setCustomTag(plugin.key("fangsShard"), ItemTagType.BYTE, (byte) 1);
        meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Fang Shards");
        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.LIGHT_PURPLE + "A shard of the fangs");
        meta.setLore(lore);
        item.setItemMeta(meta);
        item.setAmount(amount);
        return item;
    }

    private ItemStack trident() {
        ItemStack trident = new ItemStack(Material.TRIDENT);
        ItemMeta tridentMeta = trident.getItemMeta();
        CustomItemTagContainer tagContainer1 = tridentMeta.getCustomTagContainer();
        tagContainer1.hasCustomTag(plugin.key("fangs"), ItemTagType.BYTE);
        trident.setItemMeta(tridentMeta);
        return trident;
    }

    private ItemStack evokesTrident(int amount) {
        ItemStack item = new ItemStack(Material.TRIDENT);
        ItemMeta itemMeta = item.getItemMeta();
        CustomItemTagContainer tagContainer1 = itemMeta.getCustomTagContainer();
        tagContainer1.setCustomTag(plugin.key("fangs"), ItemTagType.BYTE, (byte) 1);
        itemMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Tridents of the Evokes");
        itemMeta.addEnchant(Enchantment.LOYALTY, 3, true);
        itemMeta.addEnchant(Enchantment.CHANNELING, 3, true);
        itemMeta.addEnchant(Enchantment.DURABILITY, 3, true);
        itemMeta.addEnchant(Enchantment.MENDING, 1, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        ArrayList<String> lore1 = new ArrayList<>();
        lore1.add(ChatColor.LIGHT_PURPLE + "Fangs");
        itemMeta.setLore(lore1);
        item.setItemMeta(itemMeta);
        item.setAmount(amount);
        return item;
    }


    // Crafting

    @EventHandler
    public void onAnvilUse(PrepareAnvilEvent event) {

//      Check for null items (air)
        if (event.getInventory().getFirstItem() == null || event.getInventory().getSecondItem() == null) return;
        if (event.getInventory().getFirstItem().getType().isAir() || event.getInventory().getSecondItem().getType().isAir()) return;
        if (event.getInventory().getFirstItem().getType().isEmpty() || event.getInventory().getSecondItem().getType().isEmpty()) return;

//      Define
        ItemStack item1 = event.getInventory().getFirstItem();
        ItemStack item2 = event.getInventory().getSecondItem();

        if (!isItemRecipe(item1) || !isItemRecipe(item2)) return;

        if ((item2.getAmount() >= 64 && item2.getItemMeta().getCustomTagContainer().hasCustomTag(plugin.key("fangsShard"), ItemTagType.BYTE)) && !trident().equals(item1)) {
            event.setResult(evokesTrident(1));
            event.getInventory().setRepairCost(60);
        } else if ((trident().equals(item1) || trident().equals(item2)) && (fangsShard(item1.getAmount()).equals(item1) || fangsShard(item2.getAmount()).equals(item2))) {
            event.getInventory().setRepairCost(100);
            event.setResult(evokesTrident(1));
        }
    }

    private Boolean isItemRecipe(ItemStack item) {
        ArrayList<Material> list = new ArrayList<>();

        list.add(Material.TRIDENT);
        list.add(Material.PRISMARINE_SHARD);

        return list.contains(item.getType());
    }

}
