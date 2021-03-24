package me.vynto.core;

import me.vynto.core.base.PartyManager;
import me.vynto.core.commands.*;
import me.vynto.core.listeners.PlayerJoin;
import me.vynto.core.listeners.PlayerLeave;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class VyntoCore extends JavaPlugin {

    private Map<String, String> recipientHistory;
    private PartyManager partyManager;

    @Override
    public void onEnable() {
        partyManager = new PartyManager();
        recipientHistory = new HashMap<>();

        registerCommands();
        registerEvents();
        registerTabCompleters();
    }

    private void registerCommands() {
        getCommand("hat").setExecutor(new HatCommand());
        getCommand("skull").setExecutor(new SkullCommand());
        getCommand("fly").setExecutor(new FlyCommand());
        getCommand("heal").setExecutor(new HealCommand());
        getCommand("feed").setExecutor(new FeedCommand());
        getCommand("workbench").setExecutor(new WorkbenchCommand());
        getCommand("book").setExecutor(new BookCommand());
        getCommand("repair").setExecutor(new RepairCommand());
        getCommand("anvil").setExecutor(new AnvilCommand());
        getCommand("gamemode").setExecutor(new GamemodeCommand());
        getCommand("msg").setExecutor(new MessageCommand(this));
        getCommand("reply").setExecutor(new ReplyCommand(this));
        getCommand("broadcast").setExecutor(new BroadcastCommand());
        getCommand("online").setExecutor(new OnlineCommand());
        getCommand("tp").setExecutor(new TeleportCommand());
        getCommand("tppos").setExecutor(new TeleportPositionCommand());
        getCommand("party").setExecutor(new PartyCommand(this));
        getCommand("enderchest").setExecutor(new EnderChestCommand());
        getCommand("invsee").setExecutor(new InventoryViewCommand());
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new PlayerJoin(this), this);
        getServer().getPluginManager().registerEvents(new PlayerLeave(this), this);
    }

    private void registerTabCompleters() {
        getCommand("book").setTabCompleter(new BookCommand());
        // TODO add tab completers for party
        // TODO add tab completers for gamemode
    }

    public Map<String, String> getRecipientHistory() {
        return recipientHistory;
    }

    public PartyManager getPartyManager() {
        return partyManager;
    }
}
