package me.vynto.core.commands;

import me.vynto.core.VyntoCore;
import me.vynto.core.guis.PlayerTagGUI;
import me.vynto.core.misc.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TagCommand implements CommandExecutor {

    private VyntoCore plugin;
    public TagCommand(VyntoCore instance) {
        this.plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String prefix = Utils.getPrefix();

        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.cc(prefix + "&cThe console cannot access player tags."));
            return true;
        }

        if (!Utils.hasPermission(sender, "playertag")) return true;

        Player player = (Player) sender;
        PlayerTagGUI gui = new PlayerTagGUI(plugin);
        gui.openTagsGUI(player);
        return true;
    }

}
