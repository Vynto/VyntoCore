package me.vynto.core.commands;

import me.vynto.core.misc.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class AnvilCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String prefix = Utils.getPrefix();

        if (!(sender instanceof Player) && args.length == 0) {
            sender.sendMessage(Utils.cc(prefix + "&cThe console cannot open an inventory."));
            return true;
        }

        if (!Utils.hasPermission(sender, "anvil")) return true;

        Player player;
        if (args.length == 0) {
            player = (Player) sender;
        }
        else {
            if (!Utils.hasPermission(sender, "anvil.others")) return true;
            player = Bukkit.getPlayer(args[0]);
        }

        if (player == null) {
            sender.sendMessage(Utils.cc(prefix + "&cAn invalid player was specified."));
            return true;
        }

        if ((player.getOpenInventory().getType() == InventoryType.ANVIL)) {
            sender.sendMessage(Utils.cc(prefix + "&cAnvil already open for &e" + player.getDisplayName()));
            return true;
        }

        Inventory anvil = Bukkit.createInventory(player, InventoryType.ANVIL);
        player.closeInventory();
        player.openInventory(anvil);

        if (args.length > 0) {
            sender.sendMessage(Utils.cc(prefix + "&aOpened anvil for &e" + player.getDisplayName()));
        }
        return true;
    }
}
