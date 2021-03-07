package me.vynto.core.commands;

import me.vynto.core.misc.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class RepairCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String prefix = Utils.getPrefix();

        if (!(sender instanceof Player) && args.length == 0) {
            sender.sendMessage(Utils.cc(prefix + "&cThe console has no tools to repair."));
            return true;
        }

        if (!Utils.hasPermission(sender, "repair")) return true;

        Player player;
        if (args.length == 0) {
            player = (Player) sender;
        }
        else {
            if (!Utils.hasPermission(sender, "repair.others")) return true;
            player = Bukkit.getPlayer(args[0]);
        }

        if (player == null) {
            sender.sendMessage(Utils.cc(prefix + "&cAn invalid player was specified."));
            return true;
        }

        ItemStack hand = player.getInventory().getItemInMainHand();

        Material tool = hand.getType();
        if (tool.getMaxDurability() == 0) {
            sender.sendMessage(Utils.cc(prefix + "&cThat item has no durability and therefore cannot be repaired."));
            return true;
        }

        Damageable toolMeta = (Damageable) hand.getItemMeta();
        toolMeta.setDamage(0);
        hand.setItemMeta((ItemMeta) toolMeta);

        if (args.length > 0) {
            sender.sendMessage(Utils.cc(prefix + "&aYou have repaired the &e" + hand.getI18NDisplayName() + " &abelonging to &e" + player.getDisplayName()));
        }
        player.sendMessage(Utils.cc(prefix + "&aYour &e" + hand.getI18NDisplayName() + "&a has been repaired"));
        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_USE, 0.5f, 1f);
        return true;
    }
}
