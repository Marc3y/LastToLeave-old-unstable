package de.marcey.lasttoleave.listener;

import de.marcey.lasttoleave.scoreboard.ScoreboardManager;
import de.marcey.lasttoleave.utils.Manager;
import de.marcey.lasttoleave.utils.Strings;
import de.marcey.lasttoleave.utils.data.Config;
import de.marcey.lasttoleave.utils.data.Locs;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        ScoreboardManager.setTablist();
        ScoreboardManager.setScoreboard(p);
        Location lobby = Locs.get(new Config("config"), "Lobby");
        if(!Manager.isGameStarted) {
            if (lobby != null) {
                p.teleport(lobby);
            }
        } else {

        }
    }

}
