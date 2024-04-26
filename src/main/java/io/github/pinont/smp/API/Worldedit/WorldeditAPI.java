package io.github.pinont.smp.API.Worldedit;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.world.World;
import io.github.pinont.smp.Utils.Msg;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static io.github.pinont.smp.Core.worldedit;

public class WorldeditAPI {

    public static WorldEditPlugin worldEditPlugin = new WorldEditPlugin();

    public static Region getRegion(Player player) {
        if (worldedit != null) {
            try {
                return worldEditPlugin.getSession(player).getSelection((World) player.getWorld());
            } catch (IncompleteRegionException e) {
                Msg.player(player, ChatColor.RED + "You need to select an area first!");
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public static BlockVector3 minimumRegion(Player player) {
        if (worldedit != null) {
            try {
                return worldEditPlugin.getSession(player).getSelection((World) player.getWorld()).getMinimumPoint();
            } catch (IncompleteRegionException e) {
                Msg.player(player, ChatColor.RED + "You need to select an area first!");
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public static BlockVector3 maximumRegion(Player player) {
        if (worldedit != null) {
            try {
                return worldEditPlugin.getSession(player).getSelection().getMaximumPoint();
            } catch (IncompleteRegionException e) {
                Msg.player(player, ChatColor.RED + "You need to select an area first!");
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public static void saveRegionArea(Region region, String name) {
        // Create a YAML object
        Yaml yaml = new Yaml();

        // Create a map to hold the region coordinates
        Map<String, Map<String, Integer>> regionData = new LinkedHashMap<>();

        // Add the minimum and maximum coordinates to the map
        Map<String, Integer> minCoordinates = new LinkedHashMap<>();
        minCoordinates.put("x", region.getMinimumPoint().getX());
        minCoordinates.put("y", region.getMinimumPoint().getY());
        minCoordinates.put("z", region.getMinimumPoint().getZ());

        Map<String, Integer> maxCoordinates = new LinkedHashMap<>();
        maxCoordinates.put("x", region.getMinimumPoint().getX());
        maxCoordinates.put("y", region.getMinimumPoint().getY());
        maxCoordinates.put("z", region.getMinimumPoint().getZ());

        regionData.put("min", minCoordinates);
        regionData.put("max", maxCoordinates);

        // Serialize the data to YAML and save it to a file
        try {
            FileWriter writer = new FileWriter("/plugins/SMP/region/" + name + ".yml", true);
            yaml.dump(regionData, writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public static void loadRegions() {
//        File regionFolder = new File("/plugins/SMP/region/");
//        Yaml yaml = new Yaml();
//
//        // Check if the region folder exists
//        if (!regionFolder.exists() || !regionFolder.isDirectory()) {
//            System.out.println("Region folder does not exist.");
//            return;
//        }
//
//        // List all files in the region folder
//        File[] regionFiles = regionFolder.listFiles();
//
//        if (regionFiles == null) {
//            System.out.println("No region files found.");
//            return;
//        }
//
//        for (File regionFile : regionFiles) {
//            if (regionFile.isFile() && regionFile.getName().endsWith(".yml")) {
//                try {
//                    FileInputStream inputStream = new FileInputStream(regionFile);
//                    Map<String, Map<String, Integer>> regionData = yaml.load(inputStream);
//
//                    // Extract minimum and maximum coordinates from regionData map
//                    Map<String, Integer> minCoordinates = regionData.get("min");
//                    Map<String, Integer> maxCoordinates = regionData.get("max");
//
//                    // Create Region objects or use them as needed
//                    int minX = minCoordinates.get("x");
//                    int minY = minCoordinates.get("y");
//                    int minZ = minCoordinates.get("z");
//
//                    int maxX = maxCoordinates.get("x");
//                    int maxY = maxCoordinates.get("y");
//                    int maxZ = maxCoordinates.get("z");
//
//                    //
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//    }

}
