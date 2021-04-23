package me.vynto.core.commands;

import me.vynto.core.misc.Utils;
import net.minecraft.server.v1_16_R3.PacketPlayOutWorldBorder;
import net.minecraft.server.v1_16_R3.WorldBorder;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class BorderCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String prefix = Utils.getPrefix();

        if (!(sender instanceof Player)) {
            sender.sendMessage(Utils.cc(prefix + "&cThe console cannot set borders."));
            return true;
        }

        if (!Utils.hasPermission(sender, "border")) return true;

        Player player = (Player) sender;

        double radius;
        if (args.length >= 1) {
            if (args[0].equalsIgnoreCase("reset")) {
                radius = 60000000;
            }
            else {
                radius = Double.parseDouble(args[0]);
            }
        }
        else {
            player.sendMessage(Utils.cc("&cInvalid Usage"));
            player.sendMessage(Utils.cc("&cUsage: &e/border <radius> <player|all>"));
            return true;
        }

        if (Double.isNaN(radius)) {
            player.sendMessage(Utils.cc("&cAn invalid radius was specified"));
            return true;
        }

        WorldBorder worldBorder = new WorldBorder();
        worldBorder.world = ((CraftWorld) player.getWorld()).getHandle();
        worldBorder.setCenter(player.getLocation().getX(), player.getLocation().getZ());
        worldBorder.setSize(radius);
        PacketPlayOutWorldBorder packetPlayOutWorldBorder = new PacketPlayOutWorldBorder(worldBorder, PacketPlayOutWorldBorder.EnumWorldBorderAction.INITIALIZE);

        if (args.length > 1 && args[1].equalsIgnoreCase("all")) {
            for (Player target : Bukkit.getOnlinePlayers()) {
                ((CraftPlayer) target).getHandle().playerConnection.sendPacket(packetPlayOutWorldBorder);
            }
            player.sendMessage(Utils.cc("&cA new &e" + radius + "x" + radius + " &cworld border has been created for all players!"));
        }
        else if (args.length > 1 && Bukkit.getPlayer(args[1]) != null) {
            CraftPlayer target = (CraftPlayer) Bukkit.getPlayer(args[1]);
            if (target == null) {
                player.sendMessage(Utils.cc("&cAn invalid option was specified"));
                return true;
            }
            target.getHandle().playerConnection.sendPacket(packetPlayOutWorldBorder);
            player.sendMessage(Utils.cc("&cA new &e" + radius + "x" + radius + " &cworld border has been created for &e" + target.getPlayer().getDisplayName() + "&c!"));
        }
        else {
            player.sendMessage(Utils.cc("&cInvalid Usage"));
            player.sendMessage(Utils.cc("&cUsage: &e/border <radius> <player|all>"));
        }
        return true;
    }
}
