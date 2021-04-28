package me.vynto.core.commands;

import me.vynto.core.VyntoCore;
import me.vynto.core.misc.Utils;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Date;

public class BookHistoryCommand implements CommandExecutor {
    private VyntoCore plugin;
    public BookHistoryCommand(VyntoCore instance) {
        this.plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String prefix = Utils.getPrefix();

        if (!Utils.hasPermission(sender, "history")) return true;

        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.cc(prefix + "&cThe console cannot use this command."));
            return true;
        }
        Player player = (Player) sender;

       if (!player.getInventory().getItemInMainHand().getType().equals(Material.WRITTEN_BOOK) && !player.getInventory().getItemInMainHand().getType().equals(Material.WRITABLE_BOOK)) {
           player.sendMessage(Utils.cc(prefix + "&cYou must be holding either a &eWritten Book &cor &eBook and Quill &cto use this command!"));
           return true;
       }

        NamespacedKey lastEditedAuthor = new NamespacedKey(plugin, "lastEditedAuthor");
        NamespacedKey lastEditedTime = new NamespacedKey(plugin, "lastEditedTime");
        PersistentDataContainer container = player.getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer();

        if (!container.has(lastEditedAuthor, PersistentDataType.STRING) && !container.has(lastEditedTime, PersistentDataType.LONG)) {
            player.sendMessage(Utils.cc(prefix + "&cThere is no history of edits for this book!"));
            return true;
        }
        String editAuthor = container.get(lastEditedAuthor, PersistentDataType.STRING);
        Long editTime = container.get(lastEditedTime, PersistentDataType.LONG);

        player.sendMessage(Utils.cc(prefix + "&6This book was last edited by &d" + editAuthor + "&6 on &d" + new Date(editTime)));
        return true;
    }

}
