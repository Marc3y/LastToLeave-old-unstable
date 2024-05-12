package de.marcey.lasttoleave.scoreboard;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class Rang {

    private static HashMap<UUID, Rang> playerRangs = new HashMap<>();
    public static Rang SPIELER;
    public static Rang OP;
    private String name;
    private String displayname;
    private String formattedDisplayname;
    private ChatColor color;
    private boolean bold;
    private int priority;
    public static List<Rang> rangs = new ArrayList<>();

    //Init nur weil ansonsten die Class nicht initialized wird
    public static void init(){
        SPIELER = new Rang("Spieler", ChatColor.GRAY, 1).withEmpty();
        OP = new Rang("OP", ChatColor.DARK_RED, 0);
    }

    public Rang(String displayname, ChatColor color, int priority){
        this.displayname = displayname;
        this.name = displayname;
        this.color = color;
        this.bold = false;
        this.priority = priority;
        this.formattedDisplayname = color + this.displayname + " §r§7| §r" + color;
        rangs.add(this);
    }

    public Rang withEmpty(){
        this.formattedDisplayname = "";
        return this;
    }

    public Rang withBold(){
        this.bold = true;
        this.formattedDisplayname = color + "" + ChatColor.BOLD + this.displayname + " §r§8| §r" + color + ChatColor.BOLD + " ";
        return this;
    }

    public String getName() {
        return name;
    }

    public String getDisplayname() {
        return displayname;
    }

    public int getPriority() {
        return priority;
    }

    public ChatColor getColor() {
        return color;
    }

    public static List<Rang> getRangs() {
        return rangs;
    }

    public String getFormattedDisplayname() {
        return formattedDisplayname;
    }

    public static void setRang(Player p, Rang rang){
        playerRangs.put(p.getUniqueId(), rang);
    }

    private static Rang getRangByName(String name){
        for(Rang r : rangs){
            if(r.getDisplayname().equalsIgnoreCase(name) || r.getName().equalsIgnoreCase(name)){
                return r;
            }
        }
        return null;
    }

    public static Rang getRang(Player p){
        return playerRangs.get(p.getUniqueId()) != null ? playerRangs.get(p.getUniqueId()) : Rang.SPIELER;
    }
    public static Rang getRang(UUID uuid){
        return playerRangs.get(uuid) != null ? playerRangs.get(uuid) : Rang.SPIELER;
    }

}
