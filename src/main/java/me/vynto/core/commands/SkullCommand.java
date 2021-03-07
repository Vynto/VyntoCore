package me.vynto.core.commands;

import me.vynto.core.misc.Utils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class SkullCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String prefix = Utils.getPrefix();

        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.cc(prefix + "&cThe console cannot use this command."));
            return true;
        }

        if (!Utils.hasPermission(sender, "skull")) return true;

        Player player = (Player) sender;
        ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta meta = (SkullMeta) item.getItemMeta();

        if (Utils.hasFullInventory(player)) return true;

        if (args.length == 0) {
            meta.setOwningPlayer(player);
            player.sendMessage(Utils.cc(prefix + "&aYou have received &e" + player.getDisplayName() + "'s &askull"));
        }
        else {
            meta.setOwner(args[0]);
            player.sendMessage(Utils.cc(prefix + "&aYou have received &e" + args[0] + "'s &askull"));
        }
        item.setItemMeta(meta);

        player.getInventory().addItem(item);
        return true;
    }
}
