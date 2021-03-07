package me.vynto.core.commands;

import me.vynto.core.misc.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class HealCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String prefix = Utils.getPrefix();

        if (!(sender instanceof Player) && args.length == 0) {
            sender.sendMessage(Utils.cc(prefix + "&cThe console cannot heal itself."));
            return true;
        }

        if (!Utils.hasPermission(sender, "heal")) return true;

        Player player;
        if (args.length == 0) {
            player = (Player) sender;
        }
        else {
            if (!Utils.hasPermission(sender, "heal.others")) return true;
            player = Bukkit.getPlayer(args[0]);
        }

        if (player == null) {
            sender.sendMessage(Utils.cc(prefix + "&cAn invalid player was specified."));
            return true;
        }

        // Heal player
        player.setHealth(20);
        player.setExhaustion(0);
        player.setSaturation(5);
        player.setFoodLevel(20);

        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }

        if (args.length > 0) {
            sender.sendMessage(Utils.cc(prefix + "&e" + player.getDisplayName() + " &ahas been healed"));
        }
        player.sendMessage(Utils.cc(prefix + "&aYou have been healed"));
        return true;
    }
}
