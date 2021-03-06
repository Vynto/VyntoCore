package me.vynto.core.commands;

import me.vynto.core.VyntoCore;
import me.vynto.core.misc.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class AnvilCommand implements CommandExecutor {

    private VyntoCore plugin;

    public AnvilCommand(VyntoCore instance) {
        this.plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Utils utils = plugin.getUtils();
        String prefix = utils.getPrefix();

        if (!(sender instanceof Player) && args.length == 0) {
            sender.sendMessage(utils.cc(prefix + "&cThe console cannot open an inventory."));
            return true;
        }

        if (!utils.hasPermission(sender, "anvil")) return true;

        Player player;
        if (args.length == 0) {
            player = (Player) sender;
        }
        else {
            if (!utils.hasPermission(sender, "anvil.others")) return true;
            player = Bukkit.getPlayer(args[0]);
        }

        if (player == null) {
            sender.sendMessage(utils.cc(prefix + "&cAn invalid player was specified."));
            return true;
        }

        if ((player.getOpenInventory().getType() == InventoryType.ANVIL)) {
            sender.sendMessage(utils.cc(prefix + "&cAnvil already open for &e" + player.getDisplayName()));
            return true;
        }

        Inventory anvil = Bukkit.createInventory(player, InventoryType.ANVIL);
        player.closeInventory();
        player.openInventory(anvil);

        if (args.length > 0) {
            sender.sendMessage(utils.cc(prefix + "&aOpened anvil for &e" + player.getDisplayName()));
        }
        return true;

    }

}
