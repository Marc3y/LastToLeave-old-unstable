package de.marcey.lasttoleave.listener;

import de.marcey.lasttoleave.scoreboard.Rang;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        Player p = e.getPlayer();
        Rang rang = Rang.getRang(p);
        e.setFormat(rang.getFormattedDisplayname() + rang.getColor() + p.getName() + "§7: §r§f" + e.getMessage());
    }

}
