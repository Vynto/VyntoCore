package me.vynto.core.base;

import me.vynto.core.VyntoCore;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class WarpManager {
    private VyntoCore plugin;
    private List<Warp> warps;

    public WarpManager(VyntoCore instance) {
        this.plugin = instance;
    }

    public List<Warp> getWarps() {
        return warps;
    }

    public void loadWarps() {
        if (warps == null) warps = new ArrayList<>();
        if (warps.size() > 0) warps.clear();

        plugin.getConfig().getConfigurationSection("warps").getKeys(false).forEach(key -> {
            String name = plugin.getConfig().getString("warps." + key + ".name");

            if (name == null) {
                System.out.println("Invalid Location Name for: " + key);
                return;
            }

            // Location Formatting
            World world = Bukkit.getWorld(plugin.getConfig().getString("warps." + key + ".world", "world"));
            double xVal = plugin.getConfig().getDouble("warps." + key + ".x", 0);
            double yVal = plugin.getConfig().getDouble("warps." + key + ".y", 0);
            double zVal = plugin.getConfig().getDouble("warps." + key + ".z", 0);

            Location location = new Location(world, xVal, yVal, zVal);

            Warp warp = new Warp(name, location);
            warps.add(warp);

            System.out.println("Loaded warp: " + warp.getName());
        });
    }

    public Warp getWarp(String name) {
        for (Warp warp : warps) {
            if (warp.getName().equalsIgnoreCase(name)) return warp;
        }
        return null;
    }

}
