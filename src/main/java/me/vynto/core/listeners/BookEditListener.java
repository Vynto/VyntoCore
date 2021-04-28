package me.vynto.core.listeners;

import me.vynto.core.VyntoCore;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEditBookEvent;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.persistence.PersistentDataType;

public class BookEditListener implements Listener {
    private VyntoCore plugin;
    public BookEditListener(VyntoCore instance) {
        this.plugin = instance;
    }

    @EventHandler
    public void onBookEdit(PlayerEditBookEvent event) {
        BookMeta bookMeta = event.getNewBookMeta();

        NamespacedKey lastEditedAuthor = new NamespacedKey(plugin, "lastEditedAuthor");
        bookMeta.getPersistentDataContainer().set(lastEditedAuthor, PersistentDataType.STRING, event.getPlayer().getName());

        NamespacedKey lastEditedTime = new NamespacedKey(plugin, "lastEditedTime");
        bookMeta.getPersistentDataContainer().set(lastEditedTime, PersistentDataType.LONG, System.currentTimeMillis());

        event.setNewBookMeta(bookMeta);
    }
}
