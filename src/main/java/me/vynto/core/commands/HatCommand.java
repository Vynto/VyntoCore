package me.vynto.core.commands;

import me.vynto.core.VyntoCore;
import me.vynto.core.misc.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class HatCommand implements CommandExecutor {
    private VyntoCore plugin;

    public HatCommand(VyntoCore instance) {
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
        Player player;

        if (args.length == 0) {
            if (!utils.hasPermission(sender, "hat")) return true;
            player = (Player) sender;
        }
        else {
            if (!utils.hasPermission((Player) sender, "hat.others")) return true;
            player = Bukkit.getPlayer(args[0]);
            if (player == null) {
                sender.sendMessage(utils.cc(prefix + "&cAn invalid player was specified."));
                return true;
            }
        }

        ItemStack hat = ((Player) sender).getInventory().getItemInMainHand().asOne();

        if (hat.getType().equals(Material.AIR)) {
            sender.sendMessage(utils.cc(prefix + "&cYou must be holding an item in order to wear it as a hat"));
            return true;
        }

        if (player.getEquipment() != null) {
            if (player.getEquipment().getHelmet() != null) {
                ItemStack helmet = player.getEquipment().getHelmet();

                // Check if player has room in their inventory for the current hat, if not drop it
                if (player.getInventory().firstEmpty() == -1) {
                    player.getWorld().dropItem(player.getLocation(), helmet);
                } else {
                    player.getInventory().addItem(helmet);
                }
            }
        }

        player.getInventory().removeItem(hat);
        player.getEquipment().setHelmet(hat);

        if (!((Player) sender).getUniqueId().equals(player.getUniqueId())) {
            sender.sendMessage(utils.cc(prefix + "&e" + player.getDisplayName() + " &ais now wearing &e" + hat.getI18NDisplayName() + "&a as a hat"));
        }
        player.sendMessage(utils.cc(prefix + "&aYou are now wearing &e" + hat.getI18NDisplayName() + "&a as a hat"));
        return true;
    }
}
