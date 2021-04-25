package me.vynto.core.commands;

import me.vynto.core.VyntoCore;
import me.vynto.core.misc.Utils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Date;
import java.util.UUID;

public class OnlineCommand implements CommandExecutor {
    private VyntoCore plugin;
    public OnlineCommand(VyntoCore instance) {
        this.plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String prefix = Utils.getPrefix();

        if (!Utils.hasPermission(sender, "online")) return true;

        if (args.length == 0) {
            sender.sendMessage(Utils.cc(prefix + "&6There are currently &d" + Bukkit.getServer().getOnlinePlayers().size() + " &6players online!"));
            return true;
        }
        else {
            if (!Utils.hasPermission(sender, "online.others")) return true;

            OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
            Long lastOnlineMS = getPlayerDataValue(player.getUniqueId(), "lastOnline");

            if (player.getLastPlayed() == 0 && !player.isOnline()) {
                sender.sendMessage(Utils.cc(prefix + "&cInvalid Player"));
                return true;
            }

            sender.sendMessage(Utils.cc(prefix + "&d" + player.getName() + " &6is currently " + (player.isOnline() ? "&aonline" : "&coffline") ));
            if (!player.isOnline() && lastOnlineMS != null) {
                sender.sendMessage(Utils.cc(prefix + "&d" + player.getName() + " &6was last online on &c" + new Date(lastOnlineMS)));
            }
        }
        return true;
    }

    private Long getPlayerDataValue(UUID uuid, String key) {
        FileConfiguration config = plugin.getData();

        if (config.getConfigurationSection("players") != null) {
            return config.getLong("players." + uuid.toString() + "." + key);
        }
        return null;
    }
}
