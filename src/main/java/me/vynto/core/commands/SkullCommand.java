package me.vynto.core.commands;

import me.vynto.core.VyntoCore;
import me.vynto.core.misc.Utils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class SkullCommand implements CommandExecutor {
    private VyntoCore plugin;

    public SkullCommand(VyntoCore instance) {
        this.plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Utils utils = plugin.getUtils();
        String prefix = utils.getPrefix();

        if (!(sender instanceof Player)) {
            sender.sendMessage(utils.cc(prefix + "&cThe console cannot use this command."));
            return true;
        }

        if (!utils.hasPermission(sender, "skull")) return true;

        Player player = (Player) sender;
        ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta meta = (SkullMeta) item.getItemMeta();

        if (player.getInventory().firstEmpty() == -1) {
            player.sendMessage(utils.cc(prefix + "&cYou do not have enough inventory space"));
            return true;
        }

        if (args.length == 0) {
            meta.setOwningPlayer(player);
            player.sendMessage(utils.cc(prefix + "&aYou have received &e" + player.getDisplayName() + "'s &askull"));
        }
        else {
            meta.setOwner(args[0]);
            player.sendMessage(utils.cc(prefix + "&aYou have received &e" + args[0] + "'s &askull"));
        }
        item.setItemMeta(meta);

        player.getInventory().addItem(item);
        return true;
    }
}
