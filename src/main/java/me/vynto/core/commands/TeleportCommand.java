package me.vynto.core.commands;

import me.vynto.core.misc.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String prefix = Utils.getPrefix();

        if (!Utils.hasPermission(sender, "teleport.players")) return true;

        Player targetPlayer;
        Player destinationPlayer;

        if (args.length == 0) {
            sender.sendMessage(Utils.cc(prefix + "&cUsage: &e/tp <destination player> OR /tp <target player> <destination player>"));
            return true;
        }
        else if (args.length == 1) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Utils.cc(prefix + "&cThe console cannot use this command."));
                return true;
            }
            targetPlayer = (Player) sender;
            destinationPlayer = Bukkit.getPlayer(args[0]);
        } else {
            if (!Utils.hasPermission(sender, "teleport.players.others")) return true;

            targetPlayer = Bukkit.getPlayer(args[0]);
            destinationPlayer = Bukkit.getPlayer(args[1]);
        }

        if (targetPlayer == null || destinationPlayer == null) {
            sender.sendMessage(Utils.cc(prefix + "&cAn invalid player was specified."));
            return true;
        }

        if (targetPlayer == destinationPlayer) {
            sender.sendMessage(Utils.cc(prefix + "&cYou cannot teleport to yourself!"));
            return true;
        }

        targetPlayer.teleport(destinationPlayer);
        targetPlayer.sendMessage(Utils.cc(prefix + "&6You have been teleported to &d" + destinationPlayer.getName()));
        return true;
    }
}
