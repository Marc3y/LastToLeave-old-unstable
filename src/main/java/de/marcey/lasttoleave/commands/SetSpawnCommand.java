package de.marcey.lasttoleave.commands;

import de.marcey.lasttoleave.utils.Strings;
import de.marcey.lasttoleave.utils.data.Config;
import de.marcey.lasttoleave.utils.data.Locs;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetSpawnCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender s, Command command, String l, String[] args) {

        if(!(s instanceof Player)){
            return false;
        }
        Player p = (Player) s;
        if(!p.hasPermission("LastToLeave.FullAdmin")){
            p.sendMessage(Strings.nopermission);
            return false;
        }
        Locs.save(new Config("config"), "Lobby", p.getLocation());
        p.sendMessage(Strings.prefix + " §7Du hast erfolgreich den Spawn der Lobby gesetzt.");
        return false;
    }
}
