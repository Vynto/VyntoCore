package me.vynto.core.misc;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Utils {
    private String prefix;

    public String cc(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public boolean hasPermission(Player player, String permission) {
        if (!player.hasPermission("vynto.core." + permission)) {
            player.sendMessage(cc(getPrefix() + "&cYou do not have permission to run that command!"));
            return false;
        }
        return true;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
