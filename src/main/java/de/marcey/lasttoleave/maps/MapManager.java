package de.marcey.lasttoleave.maps;

import de.marcey.lasttoleave.utils.data.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MapManager {

    public static Map selectedMap = null;

    public static List<Map> getAllMaps(){
        List<Map> toReturn = new ArrayList<>();
        Config config = new Config("worlds");
        for(String s : config.getAll()){
            if(config.getString(s + ".Worldname") == null) continue;
            String displayname = config.getString(s + ".Displayname");
            String builder = config.getString(s + ".Builder");
            toReturn.add(new Map(s, displayname, builder));
        }
        return toReturn;
    }

    public static Map getRandomMap(){
        List<Map> maps = getAllMaps();
        Random rnd = new Random();
        int rndint = rnd.nextInt(maps.size()-0)+0;
        return maps.get(rndint);
    }

    public static Map getMapByDisplayname(String displayname){
        for(Map m : getAllMaps()){
            if(m.getDisplayname().equalsIgnoreCase(displayname)){
                return m;
            }
        }
        return null;
    }

    public static Map getSelectedMap(){
        return selectedMap;
    }

    public static void setSelectedMap(Map selectedMap) {
        MapManager.selectedMap = selectedMap;
    }
}
