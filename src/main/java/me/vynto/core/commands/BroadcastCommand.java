package me.vynto.core.commands;

import me.vynto.core.misc.Utils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class BroadcastCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String prefix = Utils.getPrefix();

        if (!Utils.hasPermission(sender, "broadcast")) return true;

        if (args.length == 0) {
            sender.sendMessage(Utils.cc(prefix + "&cUsage: &e/broadcast <message ...>"));
            return true;
        }

        Bukkit.getServer().broadcastMessage(Utils.cc("&8[&dBroadcast&8] &7" + StringUtils.join(args, " ")));
        return true;
    }
}
