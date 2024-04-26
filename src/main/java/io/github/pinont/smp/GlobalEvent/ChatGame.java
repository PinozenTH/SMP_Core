package io.github.pinont.smp.GlobalEvent;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatPreviewEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Random;

import static io.github.pinont.smp.Utils.GlobalEventUtils.*;

public class ChatGame implements Listener {

    public static String question;

    @EventHandler
    public void onChat(AsyncPlayerChatPreviewEvent event) {
        String message = event.getMessage();
        Player player = event.getPlayer();
        if (game) {
            if (event.getMessage().equals(answer)) {
                event.setCancelled(true);
                player.getInventory().addItem(new ItemStack(Material.EMERALD, answer.length()));
                correct(player);
                end();
            }
        }
    }

    public void sideGuess() {
        question = "How many side do Diamonds have?";
        Bukkit.broadcastMessage(question);
        answer = "8";
        setContestBar(question);
        // Expire in 5 Minutes
        expire(5L);
    }

    public void wordGuess() {
        String word = randomString();
        question = "How many letters does \"" + word + "\" have?";
        Bukkit.broadcastMessage(question);
        answer = String.valueOf(word.length());
        setContestBar(question);
        // Expire in 5 Minutes
        expire(5L);
    }

    public void math() {
        Random random = new Random();
        int randonInt1 = random.nextInt(20) + 5;
        int randonInt2 = random.nextInt(20) + 7;
        question = "What is " + randonInt1 + " + " + randonInt2 + "?";
        Bukkit.broadcastMessage(question);
        answer = String.valueOf(randonInt1 + randonInt2);
        setContestBar(question);
        expire(5L);
    }

    private static String randomString() {

        ArrayList<String> list = new ArrayList<>();
        list.add("Diamond");
        list.add("Gold");
        list.add("Iron");
        list.add("Coal");
        list.add("Redstone");
        list.add("Lapis");
        list.add("Wood");
        list.add("Log");
        list.add("Plank");
        list.add("Cobblestone");
        list.add("Stone");
        list.add("Dirt");
        list.add("Gravel");
        list.add("Sand");
        list.add("Sandstone");
        list.add("Netherrack");
        list.add("Netherbrick");
        list.add("Quartz");
        list.add("ExperienceOrbs");

        Random random = new Random();
        int index = random.nextInt(list.size());

        return list.get(index).toString();

    }
}