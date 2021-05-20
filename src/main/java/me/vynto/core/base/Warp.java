package me.vynto.core.base;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Warp {
    private final String name;
    private final Location location;

    public Warp(String name, Location location) {
        this.name = name;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void warpPlayer(Player player) {
        player.teleport(this.location);
    }
}
