package me.vynto.core.commands;

import me.vynto.core.misc.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class WorkbenchCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String prefix = Utils.getPrefix();

        if (!(sender instanceof Player) && args.length == 0) {
            sender.sendMessage(Utils.cc(prefix + "&cThe console cannot open an inventory."));
            return true;
        }

        if (!Utils.hasPermission(sender, "workbench")) return true;

        Player player;
        if (args.length == 0) {
            player = (Player) sender;
        }
        else {
            if (!Utils.hasPermission(sender, "workbench.others")) return true;
            player = Bukkit.getPlayer(args[0]);
        }

        if (player == null) {
            sender.sendMessage(Utils.cc(prefix + "&cAn invalid player was specified."));
            return true;
        }

        if ((player.getOpenInventory().getType() == InventoryType.WORKBENCH)) {
            sender.sendMessage(Utils.cc(prefix + "&cWorkbench already open for &e" + player.getDisplayName()));
            return true;
        }

        Inventory workbench = Bukkit.createInventory(player, InventoryType.WORKBENCH);
        player.closeInventory();
        player.openInventory(workbench);

        if (args.length > 0) {
            sender.sendMessage(Utils.cc(prefix + "&aOpened workbench for &e" + player.getDisplayName()));
        }
        return true;

    }
}
