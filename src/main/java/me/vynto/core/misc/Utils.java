package me.vynto.core.misc;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

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

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
