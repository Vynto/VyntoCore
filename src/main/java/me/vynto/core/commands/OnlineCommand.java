package me.vynto.core.commands;

import me.vynto.core.misc.Utils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class OnlineCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String prefix = Utils.getPrefix();

        if (!Utils.hasPermission(sender, "online")) return true;

        if (args.length == 0) {
            sender.sendMessage(Utils.cc(prefix + "&6There are currently &d" + Bukkit.getServer().getOnlinePlayers().size() + " &6players online!"));
            return true;
        }
        else {
            if (!Utils.hasPermission(sender, "online.others")) return true;

            OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
            if (Bukkit.getServer().getOfflinePlayer(args[0]).getLastPlayed() == 0) {
                sender.sendMessage(Utils.cc(prefix + "&cInvalid Player"));
                return true;
            }
            sender.sendMessage(Utils.cc(prefix + "&d" + player.getName() + " &6is currently " + (Bukkit.getServer().getOfflinePlayer(args[0]).isOnline() ? "&aonline" : "&coffline") ));
        }
        return true;
    }

}
