package me.vynto.core;

import me.vynto.core.commands.PlayerCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class VyntoCore extends JavaPlugin {

    @Override
    public void onEnable() {
        registerCommands();
        registerTabCompleters();
    }

    private void registerCommands() {
        getCommand("hat").setExecutor(new PlayerCommand(this));
    }

    private void registerTabCompleters() {
        // register tab completers with:
        // getCommand("COMMANDNAME").setTabCompleter(new COMMANDNAMETab());
        // I believe a new one has to be made for every command otherwise it gets messy
    }

}
