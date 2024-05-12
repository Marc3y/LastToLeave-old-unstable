package de.marcey.lasttoleave.scoreboard;

import de.marcey.lasttoleave.LastToLeave;
import de.marcey.lasttoleave.teammanager.TeamManager;
import de.marcey.lasttoleave.utils.Strings;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class ScoreboardManager {

    private static Scoreboard sb;

    public static void setScoreboard(Player p){
        sb = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = sb.registerNewObjective("n", "dummy");


        for(Player current : Bukkit.getOnlinePlayers()){
            de.marcey.lasttoleave.teammanager.Team gameTeam = TeamManager.getTeam(p);
            String team = current.getUniqueId().toString();
            if(gameTeam != null) {
                team = "00" + Rang.getRang(current).getPriority() + gameTeam.getTeamNumber() + current.getUniqueId();
            } else {
                team = "00" + Rang.getRang(current).getPriority() + current.getUniqueId();
            }
            if(sb.getTeam(team) != null){
                sb.getTeam(team).unregister();
            }
            sb.registerNewTeam(team);
            Rang rang = Rang.getRang(current);
            if(gameTeam != null) {
                if (!rang.equals(Rang.SPIELER)) {
                    sb.getTeam(team).setPrefix("§r§f[§e" + gameTeam.getTeamNumber() + "§r§f] §r" + (gameTeam.getPlayers().contains(p) ? ChatColor.BOLD : "") + rang.getFormattedDisplayname());
                    sb.getTeam(team).setColor(rang.getColor());
                } else {
                    sb.getTeam(team).setPrefix("§r§f[§e" + gameTeam.getTeamNumber() + "§r§f] §r" + (gameTeam.getPlayers().contains(p) ? ChatColor.BOLD : ""));
                    sb.getTeam(team).setColor(rang.getColor());
                }
            } else {
                if (!rang.equals(Rang.SPIELER)) {
                    sb.getTeam(team).setPrefix(rang.getFormattedDisplayname());
                    sb.getTeam(team).setColor(rang.getColor());
                } else {
                    sb.getTeam(team).setPrefix(rang.getColor() + "");
                    sb.getTeam(team).setColor(rang.getColor());
                }
            }
        }
        for (Player current : Bukkit.getOnlinePlayers()) {
            setTeams(current);
        }
    }

    public static int taskID;
    public static void updater(){
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(LastToLeave.getInstance(), new Runnable() {
            @Override
            public void run() {
                if(!Bukkit.getOnlinePlayers().isEmpty()) {
                    for (Player current : Bukkit.getOnlinePlayers()) {
                        setTeams(current);
                    }
                }
            }
        }, 0, 200);
    }

    public static void updateScoreboard(){
        for(Player current : Bukkit.getOnlinePlayers()){
            setTeams(current);
        }
        setTablist();
    }

    private static void setTeams(Player p){
        try {
            Rang rang = Rang.getRang(p);
            de.marcey.lasttoleave.teammanager.Team gameTeam = TeamManager.getTeam(p);
            String team = "";
            if(gameTeam != null) {
                team = "00" + rang.getPriority() + gameTeam.getTeamNumber() + p.getUniqueId();
            } else team = "00" + rang.getPriority() + p.getUniqueId();
            Team t = sb.getTeam(team);
            if (t == null) {
                sb.registerNewTeam(team);
                if(gameTeam != null) {
                    if (!rang.equals(Rang.SPIELER)) {
                        sb.getTeam(team).setPrefix("§r§f[§e" + gameTeam.getTeamNumber() + "§r§f] §r" + (gameTeam.getPlayers().contains(p) ? ChatColor.BOLD : "") + rang.getFormattedDisplayname());
                        sb.getTeam(team).setColor(rang.getColor());
                    } else {
                        sb.getTeam(team).setPrefix("§r§f[§e" + gameTeam.getTeamNumber() + "§r§f] §r" + (gameTeam.getPlayers().contains(p) ? ChatColor.BOLD : ""));
                        sb.getTeam(team).setColor(rang.getColor());
                    }
                } else {
                    if (!rang.equals(Rang.SPIELER)) {
                        sb.getTeam(team).setPrefix(rang.getFormattedDisplayname());
                        sb.getTeam(team).setColor(rang.getColor());
                    } else {
                        sb.getTeam(team).setPrefix(rang.getColor() + "");
                        sb.getTeam(team).setColor(rang.getColor());
                    }
                }
            }
            sb.getTeam(team).addPlayer(p);
            p.setScoreboard(sb);
        } catch (Exception ignored){
        }
    }

    public static void setTablist(){
        for(Player current : Bukkit.getOnlinePlayers()){
            current.setPlayerListHeader("§7\n§7   " + " §r§7--- §r§7" + Strings.getGradient("LastToLeave", true) + " §r§7---    \n§7Spieler auf dem Event: §e" + Bukkit.getOnlinePlayers().size() + "\n§7\n");
            current.setPlayerListFooter("§7\n§ctwitch.tv/Kenjih\n§7\n§e/help §7um eine Hilfe zu bekommen\n§7");
        }
    }

}
