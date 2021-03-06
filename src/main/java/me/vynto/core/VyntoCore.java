package me.vynto.core;

import me.vynto.core.commands.*;
import me.vynto.core.misc.Utils;
import me.vynto.core.tabcompleters.BookTabCompleter;
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
        getCommand("heal").setExecutor(new HealCommand(this));
        getCommand("feed").setExecutor(new FeedCommand(this));
        getCommand("workbench").setExecutor(new WorkbenchCommand(this));
        getCommand("book").setExecutor(new BookCommand(this));
        getCommand("repair").setExecutor(new RepairCommand(this));
    }

    private void registerTabCompleters() {
        getCommand("book").setTabCompleter(new BookTabCompleter());
    }

    public Utils getUtils() {
        return utils;
    }

}
