package me.vynto.core;

import me.vynto.core.commands.*;
import org.bukkit.plugin.java.JavaPlugin;

public class VyntoCore extends JavaPlugin {
    @Override
    public void onEnable() {
        registerCommands();
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
    }

    private void registerTabCompleters() {
        getCommand("book").setTabCompleter(new BookCommand());
    }
}
