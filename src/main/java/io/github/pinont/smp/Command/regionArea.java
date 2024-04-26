package io.github.pinont.smp.Command;

import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.selector.ExtendingCuboidRegionSelector;
import io.github.pinont.smp.API.Worldedit.WorldeditAPI;
import io.github.pinont.smp.Utils.Msg;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static io.github.pinont.smp.Core.worldedit;

public class regionArea extends Command {
    protected regionArea(@NotNull String name) {
        super(name);
    }

    @Override
    public boolean execute(@NotNull CommandSender commandSender, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player) {
            if (worldedit != null) {
                Player player = (Player) commandSender;
                if (strings.length == 0) {
                    Msg.player(player, ChatColor.RED + "You need to specify a name first!");
                } else if (strings.length == 1) {
                    CuboidRegion region = CuboidRegion.makeCuboid(Objects.requireNonNull(WorldeditAPI.getRegion(player)));

                }
            }
        } else {
            commandSender.sendMessage("You must be a player to use this command!");
        }
        return true;
    }

    private void saveRegion(Player player, String name) {


        Msg.player(player, ChatColor.GREEN + "Region saved!");
    }


}
