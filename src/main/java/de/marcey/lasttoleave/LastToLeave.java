package de.marcey.lasttoleave;

import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.marcey.eventsystemapi.EventSystemAPI;
import de.marcey.lasttoleave.commands.*;
import de.marcey.lasttoleave.listener.JoinListener;
import de.marcey.lasttoleave.listener.TeamListener;
import de.marcey.lasttoleave.scoreboard.Rang;
import de.marcey.lasttoleave.scoreboard.ScoreboardManager;
import de.marcey.marceyapi.MarceyMinecraftAPI;
import de.marcey.marceyapi.colors.ColorGradient;
import de.marcey.marceyapi.utils.MessageManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class LastToLeave extends JavaPlugin {

    private static LastToLeave instance;
    private static final CloudNetDriver DRIVER = CloudNetDriver.getInstance();

    public static CloudNetDriver getDriver() {
        return DRIVER;
    }
    private static MessageManager messageManager;
    @Override
    public void onEnable() {
        instance = this;
        EventSystemAPI.init(this);
        MarceyMinecraftAPI.init(this);
        Rang.init();
        registerEvents(getServer().getPluginManager());
        registerCommands();
        registerOther();
    }

    public void registerEvents(PluginManager pl){
        pl.registerEvents(new JoinListener(), this);
        pl.registerEvents(new TeamListener(), this);
    }

    public void registerCommands(){
        getCommand("team").setExecutor(new TeamCommand());
        getCommand("whitelist").setExecutor(new WhitelistCommand());
        getCommand("rang").setExecutor(new RangCommand());
        getCommand("map").setExecutor(new MapCommand());
        getCommand("setspawn").setExecutor(new SetSpawnCommand());
        getCommand("worldtp").setExecutor(new WorldTpCommand());
    }
    public void registerOther(){
        messageManager = new MessageManager(new ColorGradient("LastToLeave", "#eb9b19", "#ebc219").withBold().build() + " §r§8>>§r");
        ScoreboardManager.updater();
    }

    @Override
    public void onDisable() {
        EventSystemAPI.disable();
        MarceyMinecraftAPI.disable();
    }

    public static LastToLeave getInstance() {
        return instance;
    }

    public static MessageManager getMessageManager() {
        return messageManager;
    }
}
