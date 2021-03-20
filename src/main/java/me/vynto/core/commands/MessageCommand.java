package me.vynto.core.commands;

import me.vynto.core.VyntoCore;
import me.vynto.core.misc.Utils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MessageCommand implements CommandExecutor {

    private VyntoCore plugin;
    public MessageCommand(VyntoCore instance) {
        this.plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String prefix = Utils.getPrefix();

        if (args.length <= 1) {
            sender.sendMessage(Utils.cc(prefix + "&cUsage: &e/msg <player> <message ...>"));
            return true;
        }

        if (!Utils.hasPermission(sender, "message")) return true;

        StringBuilder messageContent = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            if (i != 1) messageContent.append(" ");
            messageContent.append(args[i]);
        }

        String toTemplate = "&8[&aPM&8] &bTo {{RECIPIENT}}: &d" + messageContent;
        String fromTemplate = "&8[&aPM&8] &bFrom {{SENDER}}: &d" + messageContent;

        Player recipient = Bukkit.getPlayer(args[0]);
        if (args[0].equalsIgnoreCase("console") && sender.hasPermission("vynto.core.message.console")) {
            sender.sendMessage(Utils.cc(toTemplate.replace("{{RECIPIENT}}", "CONSOLE")));
            Bukkit.getConsoleSender().sendMessage(Utils.cc(fromTemplate.replace("{{SENDER}}", "CONSOLE")));

            plugin.getMessageHistory().put("CONSOLE", sender.getName());
            return true;
        }
        else if (Bukkit.getServer().getOfflinePlayer(args[0]).getLastPlayed() != 0 && !Bukkit.getServer().getOfflinePlayer(args[0]).isOnline()) {
            sender.sendMessage(Utils.cc(prefix + "&e" + args[0] + " &cis currently offline."));
            return true;
        }
        else if (recipient == null) {
            sender.sendMessage(Utils.cc(prefix + "&cAn invalid player was specified."));
            return true;
        }
        else if (recipient.getName().equals(sender.getName())) {
            sender.sendMessage(Utils.cc(prefix + "&cYou cannot message yourself!"));
            return true;
        }

        sender.sendMessage(Utils.cc(toTemplate.replace("{{RECIPIENT}}", recipient.getName())));
        recipient.sendMessage(Utils.cc(fromTemplate.replace("{{SENDER}}", sender.getName())));

        plugin.getMessageHistory().put(recipient.getName(), sender.getName());
        return true;
    }
}
