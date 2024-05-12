package de.marcey.lasttoleave.commands;

import de.marcey.lasttoleave.utils.Strings;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class WorldTpCommand implements TabExecutor {

    @Override
    public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {

        if(s instanceof Player){
            Player p = (Player) s;
            if(p.hasPermission("owner")){
                if(args.length == 0){
                    p.sendMessage(Strings.prefix + " §cBitte nutze §b/worldtp <World/setspawn/§7import§b> §7<World>");
                }
                if(args.length == 1){
                    if(args[0].equalsIgnoreCase("setspawn")){
                        World w = p.getWorld();
                        w.setSpawnLocation(p.getLocation());
                        p.sendMessage(Strings.prefix + " §aDu hast erfolgreich den Spawn von §6" + w.getName() + " §agesetzt!");
                        return false;
                    }
                    World target = Bukkit.getWorld(args[0]);
                    if(target != null){
                        p.teleport(target.getSpawnLocation());
                        p.sendMessage(Strings.prefix + " §aDu wurdest teleportiert!");
                    } else {
                        p.sendMessage(Strings.prefix + " §cDie Welt wurde noch nicht importiert! Bitte nutze §b/worldtp import <World>");
                        return false;
                    }
                }
                if(args.length == 2){
                    if(args[0].equalsIgnoreCase("import")){
                        World w = Bukkit.getWorld(args[1]);
                        if(w == null){
                            Bukkit.createWorld(new WorldCreator(args[1]));
                            if(!Bukkit.getWorlds().contains(w)){
                                Bukkit.getWorlds().add(w);
                            }
                            p.sendMessage(Strings.prefix + " §aDie Welt wurde erfolgreich importiert!");
                            return false;
                        } else {
                            p.sendMessage(Strings.prefix + " §cDie Welt wurde bereits importiert!");
                            return false;
                        }
                    }
                    if(args[0].equalsIgnoreCase("tp")){
                        World w = Bukkit.getWorld(args[1]);
                        if(w == null){
                            Bukkit.createWorld(new WorldCreator(args[1]));
                            p.teleport(Bukkit.getWorld(args[1]).getSpawnLocation());
                            return false;
                        } else {
                            p.teleport(Bukkit.getWorld(args[1]).getSpawnLocation());
                            return false;
                        }
                    }
                }

            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender s, Command cmd, String label, String[] args) {

        List<String> list = new ArrayList<>();
        if(args.length == 1){
            list.add("setspawn");
            list.add("import");
            for(World w : Bukkit.getWorlds()){
                list.add(w.getName());
            }
        }
        if(args.length == 2){
            if(args[0].equalsIgnoreCase("import")){
                for(World w : Bukkit.getWorlds()){
                    list.add(w.getName());
                }
            }
        }

        return list;
    }

}
