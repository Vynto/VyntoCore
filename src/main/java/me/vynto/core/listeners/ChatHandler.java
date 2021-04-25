package me.vynto.core.listeners;

import me.vynto.core.VyntoCore;
import me.vynto.core.misc.Utils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatHandler implements Listener {
    private VyntoCore plugin;
    public ChatHandler(VyntoCore instance) {
        this.plugin = instance;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        boolean useColour = player.hasPermission("vynto.core.colour");
        event.setFormat(Utils.cc(plugin.getChatPrefix() + player.getDisplayName() + ": &7") + (useColour ? Utils.cc(event.getMessage()) : event.getMessage()));
    }

}
