package me.vynto.core.commands;

import me.vynto.core.VyntoCore;
import me.vynto.core.misc.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class BookCommand implements CommandExecutor {
    private final VyntoCore plugin;

    public BookCommand(VyntoCore instance) {
        this.plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Utils utils = plugin.getUtils();
        String prefix = utils.getPrefix();

        if (!(sender instanceof Player)) {
            sender.sendMessage(utils.cc(prefix + "&cThe console cannot use this command."));
            return true;
        }
        Player player = (Player) sender;

        if (!utils.hasPermission(player, "book")) return true;

        if (args.length == 0) {
            player.sendMessage(utils.cc("&7-- -- -- &6Book Editor &7-- -- --"));
            player.sendMessage(utils.cc("&e/book title [title]: &6Set the title of your book"));
            player.sendMessage(utils.cc("&e/book author [author]: &6Set the author of your book"));
            player.sendMessage(utils.cc("&e/book copy: &6Create a copy of your book"));
            player.sendMessage(utils.cc("&e/book copy [IGN]: &6Send a copy of your book to the specified player"));
        } else {
            ItemStack book = player.getInventory().getItemInMainHand();
            if (!book.getType().equals(Material.WRITTEN_BOOK)) {
                player.sendMessage(utils.cc(prefix + "&cYou must be holding a &6Signed Book &cto use this command"));
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
                    if (!utils.hasPermission(player, "book." + args[0])) return true;

                    if (args[0].equals("title")) {
                        meta.setTitle(utils.cc(content.toString()));
                    }
                    else if (args[0].equals("author")) {
                        meta.setAuthor(utils.cc(content.toString()));
                    }

                    book.setItemMeta(meta);
                    player.sendMessage(utils.cc("&aYour book's " + args[0] + " has been updated to " + content.toString()));
                    break;
                case "copy":
                    if (!utils.hasPermission(player, "book.copy")) return true;

                    Player recipient;
                    if (args.length > 1 && args[1] != null) {
                        if (!utils.hasPermission(player, "book.copy.share")) return true;
                        recipient = Bukkit.getPlayer(args[1]);
                    } else {
                        recipient = player;
                    }

                    if (recipient == null) {
                        sender.sendMessage(utils.cc(prefix + "&cAn invalid player was specified."));
                        return true;
                    }

                    recipient.getInventory().addItem(book.asOne());
                    player.sendMessage(utils.cc(prefix + "&aYou have been given a copy of &e" + meta.getTitle()));
                    if (args.length > 1) {
                        player.sendMessage(utils.cc(prefix + "&aYou have given a copy of &e" + meta.getTitle() + " &ato &e" + recipient.getDisplayName()));
                    }
                    break;
                default:
                    player.sendMessage(utils.cc(prefix + "&cInvalid book option specified"));
            }
        }
        return true;
    }

}
