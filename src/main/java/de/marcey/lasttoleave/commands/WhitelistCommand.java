package de.marcey.lasttoleave.commands;

import de.marcey.eventsystemapi.EventSystemAPI;
import de.marcey.eventsystemapi.utils.Server;
import de.marcey.eventsystemapi.utils.WhitelistType;
import de.marcey.lasttoleave.hosting.WhitelistProvider;
import de.marcey.lasttoleave.scoreboard.Rang;
import de.marcey.lasttoleave.utils.Strings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WhitelistCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender s, @NotNull Command command, @NotNull String l, @NotNull String[] args) {

        if(!(s instanceof Player)){
            return false;
        }
        Player p = (Player) s;
        if(Rang.getRang(p).getPriority() > 0){
            p.sendMessage(Strings.nopermission);
            return false;
        }
        if(args.length == 0){
            WhitelistType type = WhitelistProvider.switchToNextWhitelist();
            p.sendMessage(Strings.prefix + " §7" + (type.equals(WhitelistType.WHITELIST_ON) ? "Es können nun §ckeine §7Spieler mehr joinen." : "Es können nun Spieler den Server betreten."));
        } else {
            String name = args[0];
            Server server = EventSystemAPI.getServerObject();
            List<String> currentPlayers = server.getWhitelistedPlayers();
            if(!currentPlayers.contains(name)) currentPlayers.add(name);
            server.setWhitelistedPlayers(currentPlayers);
            p.sendMessage(Strings.prefix + " §7Der Spieler §e" + name + " §7kann das Event nun beitreten.");
        }
        return false;
    }

}
