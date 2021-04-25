package me.vynto.core.commands;

import me.vynto.core.VyntoCore;
import me.vynto.core.misc.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class NicknameCommand implements CommandExecutor {
    private VyntoCore plugin;
    public NicknameCommand(VyntoCore instance) {
        this.plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String prefix = Utils.getPrefix();

        if (!Utils.hasPermission(sender, "nickname")) return true;

        if (args.length < 2) {
            sendInvalidUsage(sender);
            return true;
        }
        else if (args[1].equalsIgnoreCase("clear")) {
            Player player = Bukkit.getPlayer(args[0]);
            if (player == null) {
                sendInvalidUsage(sender);
                return true;
            }

            player.setDisplayName(player.getName());
            setPlayerDataValue(player, "nickname", null);

            sender.sendMessage(Utils.cc(prefix + "&d" + player.getName() + "'s &6nickname has been &ccleared"));
            player.sendMessage(Utils.cc(prefix + "&6Your nickname has been &ccleared"));
        }
        else {
            Player player = Bukkit.getPlayer(args[0]);
            if (player == null) {
                sendInvalidUsage(sender);
                return true;
            }
            String nickname = args[1];

            player.setDisplayName(Utils.cc(nickname));
            setPlayerDataValue(player, "nickname", nickname);

            sender.sendMessage(Utils.cc(prefix + "&d" + player.getName() + "'s &6nickname has been set to &d" + nickname));
            player.sendMessage(Utils.cc(prefix + "&6Your nickname has been updated to &d" + nickname));
        }
        return true;
    }

    private void sendInvalidUsage(CommandSender sender) {
        String prefix = Utils.getPrefix();
        sender.sendMessage(Utils.cc(prefix + "&cInvalid Usage"));
        sender.sendMessage(Utils.cc(prefix + "&cUsage: &e/nickname <player> <nickname>"));
    }

    private void setPlayerDataValue(Player player, String key, Object value) {
        FileConfiguration config = plugin.getData();

        config.set("players." + player.getUniqueId().toString() + "." + key, value);

        plugin.saveData();
        plugin.reloadData();
    }
}
