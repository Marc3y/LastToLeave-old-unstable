package de.marcey.lasttoleave.teammanager;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Team {

    private List<Player> players;
    private int teamNumber;

    public Team(int teamNumber){
        this.teamNumber = teamNumber;
        this.players = new ArrayList<>();
        TeamManager.addTeam(this);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int getTeamNumber() {
        return teamNumber;
    }

    public void delete(){
        TeamManager.removeTeam(this);
    }
}
