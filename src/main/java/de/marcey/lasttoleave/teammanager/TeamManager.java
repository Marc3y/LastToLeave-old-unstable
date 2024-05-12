package de.marcey.lasttoleave.teammanager;

import de.marcey.lasttoleave.scoreboard.Rang;
import de.marcey.lasttoleave.scoreboard.ScoreboardManager;
import de.marcey.lasttoleave.utils.Strings;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TeamManager {
    private static List<Team> allTeams = new ArrayList<>();
    private static int teamSize = 1;
    private static int maxTeams = 32;
    public static void addTeam(Team t){
        if(!allTeams.contains(t)) {
            allTeams.add(t);
        }
    }

    public static void removeTeam(Team t){
        allTeams.remove(t);
    }

    public static void setTeamSize(int size){
        reloadTeams(teamSize, size);
        teamSize = size;
    }

    public static void setMaxTeams(int maxTeams) {
        reloadTeamsMaxTeams(TeamManager.maxTeams, maxTeams);
        TeamManager.maxTeams = maxTeams;
    }

    private static void reloadTeamsMaxTeams(int oldMaxTeams, int newMaxTeams){
        if(oldMaxTeams > newMaxTeams) {
            List<Player> playersToSendMessage = new ArrayList<>();
            int maxPlayersOnServer = teamSize*newMaxTeams;
            if(Bukkit.getOnlinePlayers().size() >= maxPlayersOnServer){
                for(int i = Bukkit.getOnlinePlayers().size(); i <= maxPlayersOnServer; i++){
                    Random rnd = new Random();
                    boolean found = false;
                    for(int d = 0; !found; d++){
                        int rndint = rnd.nextInt(Bukkit.getOnlinePlayers().size()-0)+0;
                        Player toKick = (Player) Bukkit.getOnlinePlayers().toArray()[rndint];
                        if(toKick == null) continue;
                        if(Rang.getRang(toKick).getPriority() <= 0) continue;
                        toKick.kickPlayer(Strings.prefix + " §cDu wurdest aufgrund von einer zu hohen Spielerzahl gekickt.");
                        found = true;
                    }
                }
                for (Player current : Bukkit.getOnlinePlayers()) {
                    current.sendMessage(Strings.prefix + " §7Die maximalen Teams wurden auf §c" + newMaxTeams + " §7gesetzt. Durch die Veränderung der maximalen Teams mussten ein paar Spieler gekickt werden.");
                }
            } else {
                for (Player current : Bukkit.getOnlinePlayers()) {
                    current.sendMessage(Strings.prefix + " §7Die maximalen Teams wurden auf §c" + newMaxTeams + " §7gesetzt.");
                }
            }
        } else {
            for (Player current : Bukkit.getOnlinePlayers()) {
                current.sendMessage(Strings.prefix + " §7Die maximalen Teams wurden auf §c" + newMaxTeams + " §7gesetzt.");
            }
        }
    }

    public static void joinRandomTeam(Player p){
        List<Team> teams = getActiveTeamsWithoutSelf(p);
        List<Team> teamsToJoin = new ArrayList<>();
        for(Team t : teams){
            if(t.getPlayers().size() < teamSize){
                teamsToJoin.add(t);
            }
        }
        if(!teamsToJoin.isEmpty()){
            Random rnd = new Random();
            int rndint = rnd.nextInt(teamsToJoin.size() - 1) + 1;
            joinTeam(p, teamsToJoin.get(rndint).getTeamNumber());
        } else {
            Random rnd = new Random();
            boolean finished = false;
            for(int i = 1; !finished && i <= maxTeams; i++){
                int rndint = rnd.nextInt((maxTeams) - 1) + 1;
                if(getTeam(rndint).getPlayers().size() < teamSize){
                    joinTeam(p, rndint);
                    finished = true;
                }
            }
        }
    }

    public static Team getTeam(int teamNumber){
        for(Team t : allTeams){
            if(t.getTeamNumber() == teamNumber) return t;
        }
        return new Team(teamNumber);
    }

    public static void leaveTeam(Player p, Team teamToLeave){
        if(teamToLeave.getPlayers().contains(p)){
            teamToLeave.getPlayers().remove(p);
            for(Player current : Bukkit.getOnlinePlayers()){
                if(!teamToLeave.getPlayers().contains(current)) continue;
                current.sendMessage(Strings.prefix + " §7Der Spieler §e" + p.getName() + " §7hat das Team §cverlassen§7.");
            }
            p.sendMessage(Strings.prefix + " §7Du hast das Team §e#" + teamToLeave.getTeamNumber() + " §cverlassen§7.");
        } else p.sendMessage(Strings.prefix + " §7Du befindest dich nicht im Team §e#" + teamToLeave.getTeamNumber() + "§7.");
    }

    public static void addPlayersWithNoTeam(){
        if(teamSize > 1){
            List<Team> teamsToFill = new ArrayList<>();
            for(Team team : getActiveTeams()){
                if(team.getPlayers().size() < teamSize){
                    teamsToFill.add(team);
                }
            }
            for(Player current : Bukkit.getOnlinePlayers()){
                Team team = getTeam(current);
                if(team == null){
                    if(!teamsToFill.isEmpty()){
                        joinTeam(current, teamsToFill.get(0).getTeamNumber());
                        if(teamsToFill.get(0).getPlayers().size() >= teamSize){
                            teamsToFill.remove(teamsToFill.get(0));
                        }
                    }
                } else if(team.getPlayers().size() <= 1){
                    if(!teamsToFill.isEmpty()) {
                        joinTeam(current, teamsToFill.get(0).getTeamNumber());
                        if (teamsToFill.get(0).getPlayers().size() >= teamSize) {
                            teamsToFill.remove(teamsToFill.get(0));
                        }
                    }
                }
            }
        }
    }

    public static List<Team> getTeams(){
        List<Team> list = new ArrayList<>();
        for(int i = 1; i <= getMaxTeams(); i++){
            list.add(getTeam(i));
        }
        return list;
    }

    public static void joinTeam(Player p, int teamNumber){
        Team previous = getTeam(p);
        Team team = getTeam(teamNumber);
        if(previous != null) {
            if (previous == team) return;
            leaveTeam(p, previous);
        }
        if(team.getPlayers().size() >= teamSize){
            p.sendMessage(Strings.prefix + " §cDas Team hat das Spieler-Limit erreicht.");
            return;
        }
        if(!team.getPlayers().contains(p)){
            for(Player current : Bukkit.getOnlinePlayers()){
                if(!team.getPlayers().contains(current)) continue;
                current.sendMessage(Strings.prefix + " §7Der Spieler §e" + p.getName() + " §7hat dein Team §ebetreten§7.");
            }
            team.getPlayers().add(p);
            p.sendMessage(Strings.prefix + " §7Du bist dem Team §e#" + teamNumber + " §ebeigetreten§7.");
        } else p.sendMessage(Strings.prefix + " §7Du bist bereits dem Team §e#" + teamNumber + " §ebeigetreten§7.");
    }

    private static void reloadTeams(int oldTeamSize, int newTeamSize){
        if(oldTeamSize > newTeamSize) {
            List<Player> playersToSendMessage = new ArrayList<>();
            for (Team team : getActiveTeams()) {
                if(!(team.getPlayers().size() <= newTeamSize)){
                    playersToSendMessage.addAll(team.getPlayers());
                    team.delete();
                }
            }
            for(Player current : Bukkit.getOnlinePlayers()){
                current.sendMessage(Strings.prefix + " §7Die Team-Größe wurde von §e" + oldTeamSize + " §7auf §e" + newTeamSize + " §7gesetzt." + (playersToSendMessage.contains(current) ? " §7Bitte suche dir ein neues Team aus." : ""));
            }
            ScoreboardManager.updateScoreboard();
        } else {
            for(Player current : Bukkit.getOnlinePlayers()){
                current.sendMessage(Strings.prefix + " §7Die Team-Größe wurde von §e" + oldTeamSize + " §7auf §e" + newTeamSize + " §7gesetzt.");
            }
        }
    }

    public static List<Team> getActiveTeams(){
        List<Team> teams = new ArrayList<>();
        for(Team t : allTeams){
            if(t == null || t.getPlayers().isEmpty()) continue;
            teams.add(t);
        }
        return teams;
    }

    public static List<Team> getActiveTeamsWithoutSelf(Player p){
        List<Team> teams = new ArrayList<>();
        for(Team t : allTeams){
            if(t == null || t.getPlayers().isEmpty() || t.getPlayers().contains(p)) continue;
            teams.add(t);
        }
        return teams;
    }

    public static Team getTeam(Player p){
        for(Team t : getActiveTeams()){
            if(t.getPlayers().contains(p)) return t;
        }
        return null;
    }

    public static int getMaxTeams() {
        return maxTeams;
    }

    public static int getTeamSize() {
        return teamSize;
    }
}
