package me.vynto.core.commands;

import me.vynto.core.misc.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import java.util.ArrayList;
import java.util.List;

public class BookCommand implements CommandExecutor, TabCompleter {
    List<String> arguments = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String prefix = Utils.getPrefix();

        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.cc(prefix + "&cThe console cannot use this command."));
            return true;
        }
        Player player = (Player) sender;

        if (!Utils.hasPermission(player, "book")) return true;

        if (args.length == 0) {
            sendCommandHelp(player);
        } else {
            ItemStack book = player.getInventory().getItemInMainHand();
            if (!book.getType().equals(Material.WRITTEN_BOOK)) {
                player.sendMessage(Utils.cc(prefix + "&cYou must be holding a &6Signed Book &cto use this command"));
                player.sendMessage(Utils.cc(prefix + "&cUse &6/book &cfor more information"));
                return true;
            }
            BookMeta meta = (BookMeta) book.getItemMeta();

            StringBuilder content = new StringBuilder();
            for (int i = 1; i < args.length; i++) {
                if (i != 1) content.append(" ");
                content.append(args[i]);
            }

            switch (args[0]) {
                case "title":
                case "author":
                    if (!Utils.hasPermission(player, "book." + args[0])) return true;

                    if (args[0].equals("title")) {
                        meta.setTitle(Utils.cc(content.toString()));
                    }
                    else if (args[0].equals("author")) {
                        meta.setAuthor(Utils.cc(content.toString()));
                    }

                    book.setItemMeta(meta);
                    player.sendMessage(Utils.cc(prefix + "&aYour book's &e" + args[0] + " &ahas been updated to &e" + content.toString()));
                    break;
                case "copy":
                    if (!Utils.hasPermission(player, "book.copy")) return true;

                    Player recipient;
                    if (args.length > 1 && args[1] != null) {
                        if (!Utils.hasPermission(player, "book.copy.share")) return true;
                        recipient = Bukkit.getPlayer(args[1]);
                    } else {
                        recipient = player;
                    }

                    if (recipient == null) {
                        sender.sendMessage(Utils.cc(prefix + "&cAn invalid player was specified."));
                        return true;
                    }

                    recipient.getInventory().addItem(book.asOne());
                    recipient.sendMessage(Utils.cc(prefix + "&aYou have been given a copy of &e" + meta.getTitle()));
                    if (args.length > 1) {
                        player.sendMessage(Utils.cc(prefix + "&aYou have given a copy of &e" + meta.getTitle() + " &ato &e" + recipient.getDisplayName()));
                    }
                    break;
                default:
                    sendCommandHelp(player);
            }
        }
        return true;
    }

    public void sendCommandHelp(Player player) {
        player.sendMessage(Utils.cc("&7-- -- -- &6Book Editor &7-- -- --"));
        player.sendMessage(Utils.cc("&e/book title [title]: &6Set the title of your book"));
        player.sendMessage(Utils.cc("&e/book author [author]: &6Set the author of your book"));
        player.sendMessage(Utils.cc("&e/book copy: &6Create a copy of your book"));
        player.sendMessage(Utils.cc("&e/book copy [IGN]: &6Send a copy of your book to the specified player"));
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (arguments.isEmpty()) {
            arguments.add("title");
            arguments.add("author");
            arguments.add("copy");
        }

        List<String> result = new ArrayList<>();
        if (args.length == 1) {
            for (String argument: arguments) {
                if (argument.toLowerCase().startsWith(args[0].toLowerCase())) {
                    result.add(argument);
                }
            }
            return result;
        }
        return null;
    }
}
