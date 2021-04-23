package me.vynto.core.guis;

import me.vynto.core.VyntoCore;
import me.vynto.core.misc.Utils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;

public class PlayerTagGUI {

    private VyntoCore plugin;
    public PlayerTagGUI(VyntoCore instance) {
        this.plugin = instance;
    }


    public void openTagsGUI(Player player) {
        Inventory inv = Bukkit.createInventory(player, 9, "Player Tags");

        ItemStack tag = new ItemStack(Material.RED_DYE);
        ItemMeta tagMeta = tag.getItemMeta();

        tagMeta.setDisplayName(Utils.cc("&cRed"));
        tagMeta.setUnbreakable(true);
        tagMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);

        NamespacedKey key = new NamespacedKey(plugin, "tag-colour");
        tagMeta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "DARK_RED");

        ArrayList<String> lore = new ArrayList<>();
        lore.add(ChatColor.GREEN + "Click to select this colour!");
        tagMeta.setLore(lore);

        tag.setItemMeta(tagMeta);
        inv.addItem(tag);

        // ORANGE
        tag.setType(Material.ORANGE_DYE);
        tagMeta.setDisplayName(Utils.cc("&6Orange"));
        tagMeta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "GOLD");
        tag.setItemMeta(tagMeta);
        inv.addItem(tag);

        // LIGHT GREEN
        tag.setType(Material.LIME_DYE);
        tagMeta.setDisplayName(Utils.cc("&aLight Green"));
        tagMeta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "GREEN");
        tag.setItemMeta(tagMeta);
        inv.addItem(tag);

        // GREEN
        tag.setType(Material.GREEN_DYE);
        tagMeta.setDisplayName(Utils.cc("&2Green"));
        tagMeta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "DARK_GREEN");
        tag.setItemMeta(tagMeta);
        inv.addItem(tag);

        // AQUA
        tag.setType(Material.CYAN_DYE);
        tagMeta.setDisplayName(Utils.cc("&3Aqua"));
        tagMeta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "DARK_AQUA");
        tag.setItemMeta(tagMeta);
        inv.addItem(tag);

        // BLUE
        tag.setType(Material.BLUE_DYE);
        tagMeta.setDisplayName(Utils.cc("&9Blue"));
        tagMeta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "BLUE");
        tag.setItemMeta(tagMeta);
        inv.addItem(tag);

        // PINK
        tag.setType(Material.PINK_DYE);
        tagMeta.setDisplayName(Utils.cc("&dPink"));
        tagMeta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "LIGHT_PURPLE");
        tag.setItemMeta(tagMeta);
        inv.addItem(tag);

        // PURPLE
        tag.setType(Material.PURPLE_DYE);
        tagMeta.setDisplayName(Utils.cc("&5Purple"));
        tagMeta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "DARK_PURPLE");
        tag.setItemMeta(tagMeta);
        inv.addItem(tag);

        // WHITE
        tag.setType(Material.WHITE_DYE);
        tagMeta.setDisplayName("White");
        tagMeta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "WHITE");
        tag.setItemMeta(tagMeta);
        inv.addItem(tag);

        player.playSound(player.getLocation(), Sound.ITEM_ARMOR_EQUIP_LEATHER, 1f, 1.25f);
        player.openInventory(inv);
    }
}
