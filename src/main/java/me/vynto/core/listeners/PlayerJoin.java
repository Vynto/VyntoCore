package me.vynto.core.listeners;

import me.vynto.core.VyntoCore;
import me.vynto.core.base.Party;
import me.vynto.core.misc.Utils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoin implements Listener {
    private VyntoCore plugin;
    public PlayerJoin(VyntoCore instance) {
        this.plugin = instance;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Party party = plugin.getPartyManager().getPlayerParty(event.getPlayer().getUniqueId());
        if (party != null) {
            party.sendMessage(Utils.cc("&d" + event.getPlayer().getName() + " &6has come &aonline"));
        }
    }
}
