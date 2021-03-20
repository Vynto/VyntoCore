package me.vynto.core.commands;

import me.vynto.core.misc.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportPositionCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String prefix = Utils.getPrefix();

        if (!Utils.hasPermission(sender, "teleport.position")) return true;

        Player targetPlayer;
        float x;
        float y;
        float z;
        Location destination;

        if (args.length == 0) {
            sender.sendMessage(Utils.cc(prefix + "&cUsage: &e/tppos <x> <y> <z> OR /tppos <player> <x> <y> <z>"));
            return true;
        }
        else if (args.length == 3) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Utils.cc(prefix + "&cThe console cannot use this command."));
                return true;
            }
            targetPlayer = (Player) sender;

            try {
                x = Float.parseFloat(args[0]);
                y = Float.parseFloat(args[1]);
                z = Float.parseFloat(args[2]);
            } catch (Exception e) {
                sender.sendMessage(Utils.cc(prefix + "&cUsage: &e/tppos <x> <y> <z> OR /tppos <player> <x> <y> <z>"));
                return true;
            }
        } else if (args.length == 4) {
            if (!Utils.hasPermission(sender, "teleport.position.others")) return true;

            targetPlayer = Bukkit.getPlayer(args[0]);

            try {
                x = Float.parseFloat(args[1]);
                y = Float.parseFloat(args[2]);
                z = Float.parseFloat(args[3]);
            } catch (Exception e) {
                sender.sendMessage(Utils.cc(prefix + "&cUsage: &e/tppos <x> <y> <z> OR /tppos <player> <x> <y> <z>"));
                return true;
            }
        } else {
            sender.sendMessage(Utils.cc(prefix + "&cUsage: &e/tppos <x> <y> <z> OR /tppos <player> <x> <y> <z>"));
            return true;
        }

        if (targetPlayer == null) {
            sender.sendMessage(Utils.cc(prefix + "&cAn invalid player was specified."));
            return true;
        }
        destination = new Location(targetPlayer.getWorld(), x, y, z);

        targetPlayer.teleport(destination);
        targetPlayer.sendMessage(Utils.cc(prefix + "&6You have been teleported"));
        return true;
    }

}
