package me.vynto.core.commands;

import me.vynto.core.VyntoCore;
import me.vynto.core.misc.Utils;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerCommand implements CommandExecutor {

    private final VyntoCore plugin;
    private final String prefix;

    public PlayerCommand(VyntoCore instance) {
        this.plugin = instance;
        this.prefix = "&8[&6Vynto&8] ";
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.cc(prefix + "&cThe console cannot use this command."));
            return true;
        }

        Player player = (Player) sender;

        if (command.getName().equalsIgnoreCase("hat")) {
            if (!Utils.hasPermission(player, "hat")) return true;

            ItemStack hat = player.getInventory().getItemInMainHand().asOne();

            if (hat.getType().equals(Material.AIR)) {
                player.sendMessage(Utils.cc(prefix + "&cYou must be holding an item in order to wear it as your hat"));
                return true;
            }

            if (player.getEquipment() != null) {
                if (player.getEquipment().getHelmet() != null) {
                    ItemStack helmet = player.getEquipment().getHelmet();

                    // Check if player has room in their inventory for the current hat, if not drop it
                    if (player.getInventory().firstEmpty() == -1) {
                        player.getWorld().dropItem(player.getLocation(), helmet);
                    }
                    else {
                        player.getInventory().addItem(helmet);
                    }
                }
            }

            player.getInventory().removeItem(hat);
            player.getEquipment().setHelmet(hat);

            player.sendMessage(Utils.cc(prefix + "&aYou are now wearing a hat"));
            return true;
        }

        return true;
    }

    private boolean hasPermission(Player player, String permission) {
        if (!player.hasPermission("core.vynto." + permission)) {
            player.sendMessage(Utils.cc(prefix + "&cYou do not have permission to run that command!"));
            return false;
        }
        return true;
    }

}
