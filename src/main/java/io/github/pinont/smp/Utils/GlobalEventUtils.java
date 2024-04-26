package io.github.pinont.smp.Utils;

import io.github.pinont.smp.GlobalEvent.ChatGame;
import io.github.pinont.smp.GlobalEvent.Contest;
import io.github.pinont.smp.GlobalEvent.Rewards;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

import static io.github.pinont.smp.Core.plugin;
import static io.github.pinont.smp.GlobalEvent.Contest.*;

public class GlobalEventUtils {
    public static Boolean game = false;
    public static Boolean contest = false;
    public static String answer;
    public static Boolean buff = false;
    public static Integer mineGoal = 0;
    public static HashMap<Player, Integer> playerMine = new HashMap<>();

    public static BossBar ContestBar = null;

    public static void correct(Player player) {
        Bukkit.broadcastMessage(ChatColor.YELLOW + player.getName() + ChatColor.GREEN + " guessed the answer correctly!");
        game = false;
    }

    public static void expire(Long x) {
        new BukkitRunnable() {
            @Override
            public void run() {
                game = false;

                if (contest) {
                    Bukkit.broadcastMessage(ChatColor.RED + "Contest has expired!");
                    if (ContestBar != null) ContestBar.removeAll();
                } else {
                    Bukkit.broadcastMessage(ChatColor.RED + "Game has expired!");
                }

                end();
            }
        }.runTaskLater(plugin, 20 * 60 * x);
    }

    public static void end() {
        if (contest) {
            Bukkit.broadcastMessage(ChatColor.GREEN + "The contest has ended!");
            stone = false;
            wheat = false;
            carrot = false;
            potato = false;
            netherack = false;
            xp = false;
            buff = false;
            mineGoal = 0;
            sugarCane = false;
            givePrize();
        } else {
            Bukkit.broadcastMessage(ChatColor.GREEN + "The game has ended!");
            game = false;
            answer = null;
        }
        if (ContestBar != null) ContestBar.removeAll();
    }

    public static void start() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                int rdm;
                Random random = new Random();
                rdm = random.nextInt(11);

                if (!game && isAnyoneIsOnline()) {
                    game = true;
                    if (rdm == 0) {
                        new ChatGame().sideGuess();
                    } else if (rdm == 1) {
                        new ChatGame().wordGuess();
                    } else if (rdm == 2) {
                        new ChatGame().math();
                    } else if (rdm == 3) {
                        new Contest().stoneMining();
                    } else if (rdm == 4) {
                        new Contest().wheatFarming();
                    } else if (rdm == 5) {
                        new Contest().xpMining();
                    } else if (rdm == 6) {
                        new Contest().potatoFarming();
                    } else if (rdm == 7) {
                        new Contest().carrotFarming();
                    } else if (rdm == 8) {
                        new Contest().sugarCaneFarming();
                    } else if (rdm == 9) {
                        new Contest().buyTrading();
                    } else {
                        Bukkit.broadcastMessage(ChatColor.YELLOW + "Mining Cost no Durability for 5 minutes!");
                        buff = true;
                        expire(5L);
                    }
                }
            }
        }, 0L, 20L * 60L * 30L);

    }

    private static Boolean isAnyoneIsOnline() {
        return Bukkit.getOnlinePlayers().size() > 0;
    }

    public static void setContestBar(int rdm) {
        ContestBar = Bukkit.createBossBar(getContest(rdm), BarColor.BLUE, BarStyle.SOLID);
        for (Player player : Bukkit.getOnlinePlayers()) {
            ContestBar.addPlayer(player);
        }
    }

    public static void setContestBar(String message) {
        ContestBar = Bukkit.createBossBar(message, BarColor.BLUE, BarStyle.SOLID);
        for (Player player : Bukkit.getOnlinePlayers()) {
            ContestBar.addPlayer(player);
        }
    }

    private static String getContest(int rdm) {
        String Contest = "Contest: ";
        if (rdm == 3){
            return Contest + "Stone Mining: " + mineGoal;
        } else if (rdm == 4) {
            return Contest + "Wheat Farming: " + mineGoal;
        } else if (rdm == 5) {
            return Contest + "XP Mining: " + mineGoal;
        } else if (rdm == 6) {
            return Contest + "Potato Farming: " + mineGoal;
        } else if (rdm == 7) {
            return Contest + "Carrot Farming: " + mineGoal;
        } else if (rdm == 8) {
            return Contest + "Sugar Cane Farming: " + mineGoal;
        } else if (rdm == 9) {
            return Contest + "Buy Trading: " + mineGoal;
        } else {
            return "5 Minutes No Item Durability fees";
        }
    }

    public static void givePrize() {
        if (playerMine.isEmpty()) {
            Bukkit.broadcastMessage(ChatColor.RED + "No one has participated in the contest!");
            return;
        }
        List<Map.Entry<Player, Integer>> playerMineList = new ArrayList<>(playerMine.entrySet());

        // Sort the List based on the mined values in descending order
        Collections.sort(playerMineList, (entry1, entry2) -> entry1.getValue().compareTo(entry2.getValue()));

        int count = 0;
        for (Map.Entry<Player, Integer> entry : playerMineList) {
            if (count >= 5) {
                break; // Only reward the top 5 players
            }

            Player player = entry.getKey();
            int minedValue = entry.getValue();

            // Give rewards to the player based on minedValue
            player.getInventory().addItem(Rewards.rewards(playerMineList.indexOf(player)));
            player.sendMessage("Congratulations! " + player.getName() + " Has won a " + position(playerMineList.indexOf(player)) + " place");

            count++;
        }
    }

    private static String position(Integer position) {
        if (position == 1) {
            return ChatColor.GREEN + "1st";
        } else if (position == 2) {
            return ChatColor.GOLD + "2nd";
        } else if (position == 3) {
            return ChatColor.YELLOW + "3rd";
        } else {
            return ChatColor.RED + position.toString() + "th";
        }
    }
}
