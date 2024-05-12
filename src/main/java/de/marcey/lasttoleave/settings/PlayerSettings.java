package de.marcey.lasttoleave.settings;

import org.bukkit.entity.Player;

import java.util.UUID;

public class PlayerSettings {

    private Player player;
    private UUID uuid;

    private boolean pvp;

    public PlayerSettings(Player p){
        this.uuid = p.getUniqueId();
        this.player = p;
    }

    public PlayerSettings(UUID uuid){
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Player getPlayer() {
        return player;
    }
}
