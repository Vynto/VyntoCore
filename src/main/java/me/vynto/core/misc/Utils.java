package me.vynto.core.misc;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Utils {
    private static final String prefix = "&8[&6Vynto&8] ";

    public static String cc(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static boolean hasPermission(CommandSender sender, String permission) {
        if (!sender.hasPermission("vynto.core." + permission)) {
            sender.sendMessage(cc(getPrefix() + "&cYou do not have permission to run that command!"));
            return false;
        }
        return true;
    }

    public static boolean hasFullInventory(Player player) {
        if (player.getInventory().firstEmpty() == -1) {
            player.sendMessage(cc(prefix + "&cYou do not have enough inventory space"));
            return true;
        } else {
            return false;
        }
    }

    public static String getPrefix() {
        return prefix;
    }
}
