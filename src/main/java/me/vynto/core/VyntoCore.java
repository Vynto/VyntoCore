package me.vynto.core;

import me.vynto.core.commands.FlyCommand;
import me.vynto.core.commands.HatCommand;
import me.vynto.core.commands.SkullCommand;
import me.vynto.core.misc.Utils;
import org.bukkit.plugin.java.JavaPlugin;

public class VyntoCore extends JavaPlugin {

    private Utils utils;

    @Override
    public void onEnable() {
        utils = new Utils();
        utils.setPrefix("&8[&6Vynto&8] ");

        registerCommands();
        registerTabCompleters();
    }

    private void registerCommands() {
        getCommand("hat").setExecutor(new HatCommand(this));
        getCommand("skull").setExecutor(new SkullCommand(this));
        getCommand("fly").setExecutor(new FlyCommand(this));
    }

    private void registerTabCompleters() {
        // register tab completers with:
        // getCommand("COMMANDNAME").setTabCompleter(new COMMANDNAMETab());
        // I believe a new one has to be made for every command otherwise it gets messy
    }

    public Utils getUtils() {
        return utils;
    }

}
