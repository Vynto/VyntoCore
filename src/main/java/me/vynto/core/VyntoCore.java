package me.vynto.core;

import me.vynto.core.commands.*;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class VyntoCore extends JavaPlugin {

    private Map<String, String> messageHistory;

    @Override
    public void onEnable() {
        registerCommands();
        registerTabCompleters();
        messageHistory = new HashMap<>();
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
    }

    private void registerTabCompleters() {
        getCommand("book").setTabCompleter(new BookCommand());
    }

    public Map<String, String> getMessageHistory() {
        return messageHistory;
    }
}
