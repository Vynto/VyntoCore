package me.vynto.core.commands;

import me.vynto.core.misc.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String prefix = Utils.getPrefix();

        if (!(sender instanceof Player) && args.length == 0) {
            sender.sendMessage(Utils.cc(prefix + "&cThe console cannot fly."));
            return true;
        }

        if (!Utils.hasPermission(sender, "fly")) return true;

        Player player;
        if (args.length == 0) {
            player = (Player) sender;
        }
        else {
            if (!Utils.hasPermission(sender, "fly.others")) return true;
            player = Bukkit.getPlayer(args[0]);
        }

        if (player == null) {
            sender.sendMessage(Utils.cc(prefix + "&cAn invalid player was specified."));
            return true;
        }

        // Toggle flying
        boolean flyMode = !player.getAllowFlight();
        player.setAllowFlight(flyMode);

        if (args.length > 0) {
            sender.sendMessage(Utils.cc(prefix + "&aFlying for &e" + player.getDisplayName() + " &ahas been " + (flyMode ? "&2enabled" : "&cdisabled")));
        }
        player.sendMessage(Utils.cc(prefix + "&aFlying has been " + (flyMode ? "&2enabled" : "&cdisabled")));
        return true;
    }
}
