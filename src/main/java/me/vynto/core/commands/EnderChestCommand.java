package me.vynto.core.commands;

import me.vynto.core.misc.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EnderChestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String prefix = Utils.getPrefix();

        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.cc(prefix + "&cThe console cannot open player ender chests."));
            return true;
        }
        Player player = (Player) sender;

        if (!Utils.hasPermission(player, "enderchest")) return true;

        if (args.length == 0) {
            player.openInventory(player.getEnderChest());
        }
        else {
            if (!Utils.hasPermission(sender, "enderchest.others")) return true;

            Player targetPlayer = Bukkit.getPlayer(args[0]);
            if (targetPlayer == null) {
                sender.sendMessage(Utils.cc(prefix + "&cInvalid Player"));
                return true;
            }

            player.sendMessage(Utils.cc(prefix + "&6You are now viewing &d" + targetPlayer.getName() + "'s &6Ender Chest"));
            player.openInventory(targetPlayer.getEnderChest());
        }
        return true;
    }

}
