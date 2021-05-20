package me.vynto.core.commands;

import me.vynto.core.VyntoCore;
import me.vynto.core.misc.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand  implements CommandExecutor {
    private VyntoCore plugin;
    public ReloadCommand(VyntoCore instance) {
        this.plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload") && Utils.hasPermission(sender, "reload")) {
                plugin.reloadConfig();
                plugin.getWarpManager().loadWarps();
                sender.sendMessage(Utils.cc(Utils.getPrefix() + "&cThe Vynto Core config has been reloaded."));
            }
        }
        return true;
    }
}
