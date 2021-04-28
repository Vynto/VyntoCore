package me.vynto.core.commands;

import me.vynto.core.VyntoCore;
import me.vynto.core.misc.Utils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReplyCommand implements CommandExecutor {
    private VyntoCore plugin;
    public ReplyCommand(VyntoCore instance) {
        this.plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String prefix = Utils.getPrefix();

        if (args.length == 0) {
            sender.sendMessage(Utils.cc(prefix + "&cUsage: &e/r <message ...>"));
            return true;
        }

        if (!Utils.hasPermission(sender, "reply")) return true;

        if (!plugin.getRecipientHistory().containsKey(sender.getName())) {
            sender.sendMessage(Utils.cc(prefix + "&cThere is nobody to reply to!"));
            return true;
        }

        Bukkit.getServer().dispatchCommand(sender, "msg " + plugin.getRecipientHistory().get(sender.getName()) + " " + StringUtils.join(args, " "));
        return true;
    }
}
