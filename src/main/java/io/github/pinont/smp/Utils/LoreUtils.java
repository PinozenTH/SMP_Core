package io.github.pinont.smp.Utils;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class LoreUtils {

    public static void addLore(ItemStack item, String lore) {
        ArrayList<String> loreList = new ArrayList<>();
        loreList.add(lore);
        ItemMeta meta = item.getItemMeta();
        meta.setLore(loreList);
        item.setItemMeta(meta);
    }

    public static void removeLore(ItemStack item, String lore) {
        ItemMeta meta = item.getItemMeta();
        for (String line : meta.getLore()) {
            if (line.equals(lore)) {
                int index = meta.getLore().indexOf(line);
                meta.getLore().remove(index);
                item.setItemMeta(meta);
            }
        }
    }

    public enum Rarity {
        COMMON(ChatColor.WHITE),
        RARE(ChatColor.BLUE),
        EPIC(ChatColor.DARK_PURPLE),
        LEGENDARY(ChatColor.GOLD),
        MYSTIC(ChatColor.LIGHT_PURPLE);
        private final ChatColor color;

        Rarity(ChatColor color) {
            this.color = color;
        }

        public ChatColor getColor() {
            return color;
        }
    }

}
