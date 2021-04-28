package me.vynto.core;

import me.vynto.core.base.PartyManager;
import me.vynto.core.commands.*;
import me.vynto.core.listeners.*;
import me.vynto.core.misc.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class VyntoCore extends JavaPlugin {

    private Map<String, String> recipientHistory;
    private PartyManager partyManager;
    private String chatPrefix;

    private File data;
    private FileConfiguration dataConfig;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        setChatPrefix();

        partyManager = new PartyManager();
        recipientHistory = new HashMap<>();

        if (!getDataFolder().exists()) getDataFolder().mkdir();

        data = new File(getDataFolder(), "data.yml");
        setupDataConfig();

        registerCommands();
        registerEvents();
        registerTabCompleters();

        if (Bukkit.getVersion().contains("1.16.5")) registerPlayerTags();
    }

    @Override
    public void onDisable() {
        if (Bukkit.getVersion().contains("1.16.5")) unregisterPlayerTags();
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
        getCommand("online").setExecutor(new OnlineCommand(this));
        getCommand("tp").setExecutor(new TeleportCommand());
        getCommand("tppos").setExecutor(new TeleportPositionCommand());
        getCommand("party").setExecutor(new PartyCommand(this));
        getCommand("enderchest").setExecutor(new EnderChestCommand());
        getCommand("invsee").setExecutor(new InventoryViewCommand());
//        getCommand("border").setExecutor(new BorderCommand());
        getCommand("playertag").setExecutor(new TagCommand(this));
        getCommand("nickname").setExecutor(new NicknameCommand(this));
        getCommand("history").setExecutor(new BookHistoryCommand(this));
    }

    private void registerEvents() {
        getServer().getPluginManager().registerEvents(new PlayerJoin(this), this);
        getServer().getPluginManager().registerEvents(new PlayerLeave(this), this);
        getServer().getPluginManager().registerEvents(new InventoryListener(this), this);
        getServer().getPluginManager().registerEvents(new ChatHandler(this), this);
        getServer().getPluginManager().registerEvents(new BookEditListener(this), this);
    }

    private void registerTabCompleters() {
        getCommand("book").setTabCompleter(new BookCommand());
        // TODO add tab completers for party
        // TODO add tab completers for gamemode
    }

    private void registerPlayerTags() {
        Scoreboard sb = Bukkit.getScoreboardManager().getMainScoreboard();

        Team redTeam = sb.registerNewTeam("DARK_RED");
        redTeam.setColor(ChatColor.DARK_RED);

        Team goldTeam = sb.registerNewTeam("GOLD");
        goldTeam.setColor(ChatColor.GOLD);

        Team greenTeam = sb.registerNewTeam("GREEN");
        greenTeam.setColor(ChatColor.GREEN);

        Team darkGreenTeam = sb.registerNewTeam("DARK_GREEN");
        darkGreenTeam.setColor(ChatColor.DARK_GREEN);

        Team darkAquaTeam = sb.registerNewTeam("DARK_AQUA");
        darkAquaTeam.setColor(ChatColor.DARK_AQUA);

        Team blueTeam = sb.registerNewTeam("BLUE");
        blueTeam.setColor(ChatColor.BLUE);

        Team lightPurpleTeam = sb.registerNewTeam("LIGHT_PURPLE");
        lightPurpleTeam.setColor(ChatColor.LIGHT_PURPLE);

        Team darkPurpleTeam = sb.registerNewTeam("DARK_PURPLE");
        darkPurpleTeam.setColor(ChatColor.DARK_PURPLE);

        Team whiteTeam = sb.registerNewTeam("WHITE");
        whiteTeam.setColor(ChatColor.WHITE);
    }

    private void unregisterPlayerTags() {
        Scoreboard sb = Bukkit.getScoreboardManager().getMainScoreboard();

        sb.getTeams().forEach(Team::unregister);
    }

    public FileConfiguration getData() {
        return dataConfig;
    }

    private void setupDataConfig() {
        if (!data.exists()) {
            try {
                data.createNewFile();
                this.saveResource("data.yml", true);
            } catch (IOException e) {
                getLogger().severe("Could not create data.yml!");
            }
        }
        dataConfig = YamlConfiguration.loadConfiguration(data);

        if(dataConfig.getConfigurationSection("players") == null) {
            dataConfig.createSection("players");
        }
    }

    public void saveData() {
        try {
            dataConfig.save(data);
        } catch (IOException e) {
            getLogger().severe("Could not save data.yml!");
        }
    }

    public void reloadData() {
        dataConfig = YamlConfiguration.loadConfiguration(data);
    }

    public Map<String, String> getRecipientHistory() {
        return recipientHistory;
    }

    public PartyManager getPartyManager() {
        return partyManager;
    }

    private void setChatPrefix() {
        chatPrefix = Utils.cc(getConfig().getString("chatPrefix", "&f[&2Global&f] "));
    }

    public String getChatPrefix() {
        return chatPrefix;
    }
}
