package de.marcey.lasttoleave.commands;

import com.google.common.escape.Escaper;
import de.marcey.lasttoleave.apis.gui.Gui;
import de.marcey.lasttoleave.gui.GuiManager;
import de.marcey.lasttoleave.scoreboard.Rang;
import de.marcey.lasttoleave.teammanager.Team;
import de.marcey.lasttoleave.teammanager.TeamGUI;
import de.marcey.lasttoleave.teammanager.TeamManager;
import de.marcey.lasttoleave.utils.Strings;
import de.marcey.lasttoleave.utils.TabComplete;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TeamCommand implements TabExecutor {
    @Override
    public boolean onCommand(CommandSender s, Command command, String label, String[] args) {

        if(!(s instanceof Player)){
            return false;
        }
        Player p = (Player) s;
        if(Rang.getRang(p).getPriority() > 0){
            p.sendMessage(Strings.nopermission);
            return false;
        }
        if(args.length == 0){
            sendDefault(p);
            return false;
        } else if(args.length == 1){
            if(args[0].equalsIgnoreCase("gui")){
                TeamGUI.openInv(p, 0);
            } else  if(args[0].equalsIgnoreCase("test")){
                GuiManager manager = new GuiManager(p);
                manager.openMainMenu();
            }
        } else if(args.length == 2){
            if(args[0].equalsIgnoreCase("size") || args[0].equalsIgnoreCase("setsize")){
                int teamsize = -1;
                try {
                    teamsize = Integer.parseInt(args[1]);
                } catch (Exception e){
                    sendDefault(p);
                    return false;
                }
                if(teamsize <= 0){
                    p.sendMessage(Strings.prefix + " §cDie Team-Größe darf nicht unter 1 sein§7.");
                    return false;
                }
                if(TeamManager.getTeamSize() == teamsize){
                    p.sendMessage(Strings.prefix + " §cDie Team-Größe ist bereits auf §e" + teamsize + "§7.");
                    return false;
                }
                TeamManager.setTeamSize(teamsize);
            } else if(args[0].equalsIgnoreCase("maxteams")){
                int maxteams = -1;
                try {
                    maxteams = Integer.parseInt(args[1]);
                } catch (Exception e){
                    sendDefault(p);
                    return false;
                }
                if(maxteams <= 2){
                    p.sendMessage(Strings.prefix + " §cDie maximalen Teams müssen mindestens 2 sein§7.");
                    return false;
                }
                if(TeamManager.getTeamSize() == maxteams){
                    p.sendMessage(Strings.prefix + " §cDie maximalen Teams sind bereits auf §e" + maxteams + " §cgesetzt§7.");
                    return false;
                }
                TeamManager.setMaxTeams(maxteams);
            } else sendDefault(p);
        } else if(args.length >= 3){
            if(args[0].equalsIgnoreCase("set")){
                String name = args[1];
                int teamnumber = -1;
                try {
                    teamnumber = Integer.parseInt(args[2]);
                } catch (Exception e){
                    sendDefault(p);
                    return false;
                }
                if(teamnumber < 1 || teamnumber > TeamManager.getMaxTeams()){
                    p.sendMessage(Strings.prefix + " §cDie Team-Nummer muss zwischen 1-" + TeamManager.getMaxTeams() + " liegen§7.");
                    return false;
                }
                Player target = Bukkit.getPlayer(name);
                if(target == null){
                    p.sendMessage(Strings.prefix + " §cDer angegebene Spieler ist nicht online.");
                    return false;
                }
                Team toJoin = TeamManager.getTeam(teamnumber);
                if(toJoin.getPlayers().size() >= TeamManager.getTeamSize()){
                    p.sendMessage(Strings.prefix + " §cDas Team ist voll§7.");
                    return false;
                }
                TeamManager.joinTeam(target, teamnumber);
                p.sendMessage(Strings.prefix + " §7Du hast §e" + target.getName() + " §7in das Team §c" + teamnumber + " §7verschoben.");
            } else sendDefault(p);
        } else sendDefault(p);

        return false;
    }

    public void sendDefault(Player p){
        p.sendMessage(Strings.prefix + " §7Bitte nutze folgende Commands:");
        p.sendMessage(Strings.prefix + " §e/team size <Team-Größe> §7- Setze die Team-Größe");
        p.sendMessage(Strings.prefix + " §e/team maxteams <Anzahl> §7- Setze die maximalen Teams");
        p.sendMessage(Strings.prefix + " §e/team set <Name> <Team> §7- Setze ein Spieler in ein Team");
    }

    @Override
    public List<String> onTabComplete(CommandSender s, Command command, String l, String[] args) {
        List<String> list = new ArrayList<>();
        if(!(s instanceof Player)){
            return list;
        }
        String input = "";
        Player p = (Player) s;
        if(args.length == 1){
            input = args[0];
            list.add("size");
            list.add("maxteams");
            list.add("set");
        } else if(args.length == 2){
            input = args[1];
            if(args[0].equalsIgnoreCase("size")){
                list.add("<Team-Größe>");
            } else if(args[0].equalsIgnoreCase("maxteams")){
                list.add("<Anzahl>");
            } else if(args[0].equalsIgnoreCase("set")){
                for(Player current : Bukkit.getOnlinePlayers()){
                    list.add(current.getName());
                }
            }
        } else if(args.length == 3){
            input = args[2];
            if(args[0].equalsIgnoreCase("set")){
                boolean found = false;
                for(Player current : Bukkit.getOnlinePlayers()){
                    if(current.getName().equalsIgnoreCase(args[1])) found = true;
                }
                if(found){
                    for(int i = 1; i <= TeamManager.getMaxTeams(); i++){
                        list.add(i + "");
                    }
                }
            }
        }

        return TabComplete.sort(input, list);
    }
}
