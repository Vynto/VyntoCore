package me.vynto.core.misc;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Utils {
    private String prefix;

    public String cc(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public boolean hasPermission(CommandSender sender, String permission) {
        if (!sender.hasPermission("vynto.core." + permission)) {
            sender.sendMessage(cc(getPrefix() + "&cYou do not have permission to run that command!"));
            return false;
        }
        return true;
    }

    public boolean hasFullInventory(Player player) {
        if (player.getInventory().firstEmpty() == -1) {
            player.sendMessage(cc(prefix + "&cYou do not have enough inventory space"));
            return true;
        } else {
            return false;
        }
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
