package me.vynto.core.listeners;

import me.vynto.core.VyntoCore;
import me.vynto.core.base.Party;
import me.vynto.core.misc.Utils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeave implements Listener {
    private VyntoCore plugin;
    public PlayerLeave(VyntoCore instance) {
        this.plugin = instance;
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        Party party = plugin.getPartyManager().getPlayerParty(player.getUniqueId());
        if (party != null) {
            party.sendMessage(Utils.cc("&d" + player.getName() + " &6has gone &coffline"));

            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                if (!plugin.getPartyManager().getPartyList().contains(party)) return;

                OfflinePlayer offlinePlayer = player;
                if (!offlinePlayer.isOnline()) {
                    if (event.getPlayer().getUniqueId().equals(party.getHost())) {
                        party.sendMessage(Utils.cc("&dThis party has been disbanded due to inactivity from the party host."));
                        party.crashParty();
                        plugin.getPartyManager().deleteParty(party);
                    }
                    else {
                        party.removeMember(player.getUniqueId());
                        party.sendMessage("&d" + player.getName() + " &6has been removed from the party for inactivity.");
                    }
                }
            }, 6000);
        }
    }
}
