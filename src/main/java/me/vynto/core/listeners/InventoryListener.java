package me.vynto.core.listeners;

import me.vynto.core.VyntoCore;
import me.vynto.core.misc.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class InventoryListener implements Listener {
    private VyntoCore plugin;
    public InventoryListener(VyntoCore instance) {
        this.plugin = instance;
    }

    Scoreboard sb = Bukkit.getScoreboardManager().getMainScoreboard();

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack tagColour = event.getCurrentItem();
        String prefix = Utils.getPrefix();

        if (tagColour == null) {
            return;
        }

        if (event.getView().getTitle().contains("Player Tags")) {
            event.setCancelled(true);

            if (!tagColour.hasItemMeta()) return;
            ItemMeta tagColourMeta = tagColour.getItemMeta();

            NamespacedKey key = new NamespacedKey(plugin, "tag-colour");
            PersistentDataContainer container = tagColourMeta.getPersistentDataContainer();

            if (container.has(key, PersistentDataType.STRING)) {
                String itemColour = container.get(key, PersistentDataType.STRING);

                try {
                    ChatColor colour = ChatColor.valueOf(itemColour);

                    player.setPlayerListName(colour + player.getName());

                    Team team = sb.getTeam(itemColour);
                    if (team != null) team.addPlayer(player);

                    setPlayerDataValue(player, "playertag", itemColour);
                } catch (Exception e) {
                    player.sendMessage(Utils.cc(prefix + "&cInvalid Colour"));
                    return;
                }

                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1.25f);
                player.sendMessage(Utils.cc(prefix + "&aYour tag colour has been successfully changed!"));
                player.closeInventory();
                player.updateInventory();
            }
        }
    }

    private void setPlayerDataValue(Player player, String key, Object value) {
        FileConfiguration config = plugin.getData();

        config.set("players." + player.getUniqueId().toString() + "." + key, value);

        plugin.saveData();
        plugin.reloadData();
    }
}
