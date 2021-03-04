package me.vynto.core.misc;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Utils {
    // In future move this to the main command class and access it via a injection
    private static final String prefix = "&8[&6Vynto&8] ";

    public static String cc(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static boolean hasPermission(Player player, String permission) {
        if (!player.hasPermission("core.vynto." + permission)) {
            player.sendMessage(Utils.cc(prefix + "&cYou do not have permission to run that command!"));
            return false;
        }
        return true;
    }
}
