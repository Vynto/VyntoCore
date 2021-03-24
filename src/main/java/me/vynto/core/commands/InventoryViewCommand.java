package me.vynto.core.commands;

import me.vynto.core.misc.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InventoryViewCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String prefix = Utils.getPrefix();

        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.cc(prefix + "&cThe console cannot open player inventories."));
            return true;
        }
        Player player = (Player) sender;

        if (!Utils.hasPermission(player, "inventory")) return true;

        if (args.length == 0) {
            sender.sendMessage(Utils.cc(prefix + "&cInvalid Usage: &e/invsee <player>"));
            return true;
        }
        else {
            Player targetPlayer = Bukkit.getPlayer(args[0]);
            if (targetPlayer == null) {
                sender.sendMessage(Utils.cc(prefix + "&cInvalid Player"));
                return true;
            }

            player.sendMessage(Utils.cc(prefix + "&6You are now viewing &d" + targetPlayer.getName() + "'s &6Inventory"));
            player.openInventory(targetPlayer.getInventory());
        }
        return true;
    }
}
