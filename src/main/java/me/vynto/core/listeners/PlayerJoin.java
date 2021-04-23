package me.vynto.core.listeners;

import me.vynto.core.VyntoCore;
import me.vynto.core.base.Party;
import me.vynto.core.misc.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Team;

public class PlayerJoin implements Listener {
    private VyntoCore plugin;
    public PlayerJoin(VyntoCore instance) {
        this.plugin = instance;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        // Player Tags
        String tagColour = getPlayerDataValue(event.getPlayer(), "nametag");
        if (tagColour != null) {
            try {
                ChatColor colour = ChatColor.valueOf(tagColour);

                event.getPlayer().setPlayerListName(colour + event.getPlayer().getName());

                Team team = Bukkit.getScoreboardManager().getMainScoreboard().getTeam(tagColour);
                if (team != null) team.addPlayer(event.getPlayer());
            } catch (Exception e) {
                return;
            }
        }

        // Parties
        Party party = plugin.getPartyManager().getPlayerParty(event.getPlayer().getUniqueId());
        if (party != null) {
            party.sendMessage(Utils.cc("&d" + event.getPlayer().getName() + " &6has come &aonline"));
        }
    }

    private String getPlayerDataValue(Player player, String key) {
        FileConfiguration config = plugin.getData();

        if (config.getConfigurationSection(player.getUniqueId().toString()) != null) {
            return config.getString("players." + player.getUniqueId().toString() + "." + key);
        }
        return null;
    }
}
