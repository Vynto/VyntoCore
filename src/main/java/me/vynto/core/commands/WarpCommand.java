package me.vynto.core.commands;

import me.vynto.core.VyntoCore;
import me.vynto.core.base.Warp;
import me.vynto.core.misc.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WarpCommand implements CommandExecutor {
    private VyntoCore plugin;
    public WarpCommand(VyntoCore instance) {
        this.plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String prefix = Utils.getPrefix();

        if (!Utils.hasPermission(sender, "warps")) return true;

        Player targetPlayer;
        Warp warp;

        if (args.length == 0) {
            sender.sendMessage(Utils.cc(prefix + "&cUsage: &e/warp <warp>"));
            return true;
        }
        else if (args.length == 1) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(Utils.cc(prefix + "&cThe console cannot use this command."));
                return true;
            }
            targetPlayer = (Player) sender;

            warp = plugin.getWarpManager().getWarp(args[0]);
        } else {
            if (!Utils.hasPermission(sender, "warps.others")) return true;

            targetPlayer = Bukkit.getPlayer(args[0]);
            warp = plugin.getWarpManager().getWarp(args[0]);
        }

        if (targetPlayer == null) {
            sender.sendMessage(Utils.cc(prefix + "&cAn invalid player was specified."));
            return true;
        }

        if (warp == null) {
            sender.sendMessage(Utils.cc(prefix + "&cAn invalid warp was specified."));
            return true;
        }

        if (!Utils.hasPermission(sender, "warps." + warp.getName())) return true;

        warp.warpPlayer(targetPlayer);
        targetPlayer.sendMessage(Utils.cc(prefix + "&6You have warped to &d" + warp.getName()));
        return true;
    }
}
