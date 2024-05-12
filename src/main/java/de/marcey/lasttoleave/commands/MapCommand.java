package de.marcey.lasttoleave.commands;

import de.marcey.lasttoleave.LastToLeave;
import de.marcey.lasttoleave.utils.Strings;
import de.marcey.lasttoleave.utils.data.Config;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MapCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender s, Command command, String label, String[] args) {

        if(!(s instanceof Player)){
            return false;
        }
        Player p = (Player) s;
        if(!p.hasPermission("LastToLeave.FullAdmin")){
            p.sendMessage(Strings.nopermission);
            return false;
        }
        if(args.length == 0){
            sendDefault(p);
            return false;
        } else if(args.length >= 4){
            if(args[0].equalsIgnoreCase("add")){
                String worldname = args[1];
                String displayname = args[2];
                displayname = displayname.replace('&', '§');
                String builderSeperatedByCommas = args[3];
                Config config = new Config("worlds");
                config.set(worldname + ".Worldname", worldname);
                config.set(worldname + ".Displayname", displayname);
                config.set(worldname + ".Builder", builderSeperatedByCommas);
                config.save();
                LastToLeave.getMessageManager().sendMessage(p, "§7Die neue Map §e" + displayname + " §7wurde erfolgreich §agespeichert§7.");
            } else sendDefault(p);
        } else if(args.length == 2) {
            if(args[0].equalsIgnoreCase("remove")){
                String worldname = args[1];
                Config config = new Config("worlds");
                config.getConfig().set(worldname, null);
                config.save();
                LastToLeave.getMessageManager().sendMessage(p, "§7Die Map §e" + worldname + " §7wurde erfolgreich §centfernt§7.");
            } else sendDefault(p);
        }else sendDefault(p);

        return false;
    }

    public void sendDefault(Player p){
        p.sendMessage(Strings.prefix + " §7Bitte nutze §c/map add <World-Name> <Display-Name> <Builder,Builder2,...>");
        p.sendMessage(Strings.prefix + " §7Bitte nutze §c/map remove <World-Name>");
    }
}
