package me.vynto.core.commands;

import me.vynto.core.misc.Utils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GamemodeCommand implements CommandExecutor {
    List<String> arguments = new ArrayList<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String prefix = Utils.getPrefix();

        if (!(sender instanceof Player) && args.length == 0) {
            sender.sendMessage(Utils.cc(prefix + "&cThe console cannot change its gamemode."));
            return true;
        }

        if ((label.equals("vyntocore:" + command.getName()) || label.equalsIgnoreCase("gm")) && args.length == 0) {
            sender.sendMessage(Utils.cc(prefix + "&cUsage: &e/gamemode [gamemode]"));
            sender.sendMessage(Utils.cc(prefix + "&cPossible gamemodes: &e" + Arrays.toString(GameMode.values())));
            return true;
        }

        GameMode gamemode;

        if (!label.equals("vyntocore:" + command.getName()) && !label.equalsIgnoreCase("gm")) {
            switch(label) {
                case "gmc":
                    gamemode = GameMode.CREATIVE;
                    break;
                case "gms":
                    gamemode = GameMode.SURVIVAL;
                    break;
                case "gma":
                    gamemode = GameMode.ADVENTURE;
                    break;
                case "gmsp":
                    gamemode = GameMode.SPECTATOR;
                    break;
                default:
                    sender.sendMessage(Utils.cc(prefix + "&cAn invalid gamemode was specified"));
                    sender.sendMessage(Utils.cc(prefix + "&cPossible gamemodes: &e" + Arrays.toString(GameMode.values())));
                    return true;
            }
        }
        else {
            try {
                gamemode = GameMode.valueOf(args[0].toUpperCase());
            }
            catch (Exception e) {
                sender.sendMessage(Utils.cc(prefix + "&cAn invalid gamemode was specified"));
                sender.sendMessage(Utils.cc(prefix + "&cPossible gamemodes: &e" + Arrays.toString(GameMode.values())));
                return true;
            }
        }

        if (!Utils.hasPermission(sender, "gamemode" + gamemode.name().toLowerCase())) return true;

        Player player;
        boolean onOthers;

        if ((!label.equals("vyntocore:" + command.getName()) && !label.equalsIgnoreCase("gm")) && args.length == 1) {
            // gmx [name]
            onOthers = true;
            player = Bukkit.getPlayer(args[0]);
        }
        else if ((label.equals("vyntocore:" + command.getName()) || label.equalsIgnoreCase("gm")) && args.length == 2) {
            // gamemode x [name] OR //gm x [name]
            onOthers = true;
            player = Bukkit.getPlayer(args[1]);
        }
        else {
            onOthers = false;
            player = (Player) sender;
        }

        if (player == null) {
            sender.sendMessage(Utils.cc(prefix + "&cAn invalid player was specified."));
            return true;
        }

        if (onOthers) {
            if (!Utils.hasPermission(sender, "gamemode.others")) return true;
        }

        // Change gamemode
        player.setGameMode(gamemode);

        if (onOthers) {
            sender.sendMessage(Utils.cc(prefix + "&e" + player.getDisplayName() + "'s &agamemode has been set to &e" + gamemode.name().toLowerCase()));
        }
        player.sendMessage(Utils.cc(prefix + "&aYou gamemode has been set to &e" + gamemode.name().toLowerCase()));
        return true;
    }
}
