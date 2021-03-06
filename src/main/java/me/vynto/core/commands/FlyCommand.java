package me.vynto.core.commands;

import me.vynto.core.VyntoCore;
import me.vynto.core.misc.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class FlyCommand implements CommandExecutor {
    private VyntoCore plugin;

    public FlyCommand(VyntoCore instance) {
        this.plugin = instance;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Utils utils = plugin.getUtils();
        String prefix = utils.getPrefix();

        if (!(sender instanceof Player) && args.length == 0) {
            sender.sendMessage(utils.cc(prefix + "&cThe console cannot fly."));
            return true;
        }

        if (!utils.hasPermission(sender, "fly")) return true;

        Player player;
        if (args.length == 0) {
            player = (Player) sender;
        }
        else {
            if (!utils.hasPermission(sender, "fly.others")) return true;
            player = Bukkit.getPlayer(args[0]);
        }

        if (player == null) {
            sender.sendMessage(utils.cc(prefix + "&cAn invalid player was specified."));
            return true;
        }

        // Toggle flying
        boolean flyMode = !player.getAllowFlight();
        player.setAllowFlight(flyMode);

        if (args.length > 0) {
            sender.sendMessage(utils.cc(prefix + "&aFlying for &e" + player.getDisplayName() + " &ahas been " + (flyMode ? "&2enabled" : "&cdisabled")));
        }
        player.sendMessage(utils.cc(prefix + "&aFlying has been " + (flyMode ? "&2enabled" : "&cdisabled")));
        return true;
    }
}
