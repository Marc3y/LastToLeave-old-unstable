package de.marcey.lasttoleave.maps;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;

import java.util.ArrayList;
import java.util.List;

public class Map {

    private String worldname;
    private World world;
    private String displayname;
    private List<String> builder;
    private String builderString;
    public Map(String worldname, String displayname, String builderSeperatedByCommas){
        this.worldname = worldname;
        this.displayname = displayname;
        this.builderString = builderSeperatedByCommas;
        this.builder = new ArrayList<>();
        for(String b : builderSeperatedByCommas.split(",")){
            builder.add(b);
        }
        this.world = Bukkit.getWorld(this.worldname);
        if(this.world == null){
            this.world = new WorldCreator(this.worldname).createWorld();
        }
    }

    public World getWorld() {
        return world;
    }

    public String getWorldname() {
        return worldname;
    }

    public List<String> getBuilder() {
        return builder;
    }

    public String getBuilderString() {
        return builderString;
    }

    public String getDisplayname() {
        return displayname;
    }

}
