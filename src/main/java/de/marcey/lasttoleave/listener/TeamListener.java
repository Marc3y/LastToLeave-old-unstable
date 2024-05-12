package de.marcey.lasttoleave.listener;

import de.marcey.lasttoleave.teammanager.Team;
import de.marcey.lasttoleave.teammanager.TeamGUI;
import de.marcey.lasttoleave.teammanager.TeamManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;

public class TeamListener implements Listener {

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent e){
        if(!(e.getEntity() instanceof Player) || !(e.getDamager() instanceof Player)) return;
        Player p = (Player) e.getEntity();
        Player damager = (Player) e.getDamager();
        Team team = TeamManager.getTeam(p);
        Team damagerTeam = TeamManager.getTeam(damager);
        if(team == null || damagerTeam == null) return;
        if(team.getTeamNumber() == damagerTeam.getTeamNumber()){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent e){
        if(!(e.getWhoClicked() instanceof Player)){
            return;
        }
        if(e.getCurrentItem() == null) return;
        Player p = (Player) e.getWhoClicked();
        if(!e.getView().getTitle().toLowerCase().contains("team auswählen")) return;
        e.setCancelled(true);
        int page = Integer.parseInt(e.getView().getTitle().replace("Team auswählen | Seite ", ""));
        if(e.getCurrentItem().getType().equals(Material.OAK_DOOR)){
            p.closeInventory();
        } else if(e.getCurrentItem().getType().equals(Material.FEATHER)){
            TeamManager.joinRandomTeam(p);
            TeamGUI.openInv(p, page);
        } else if(e.getCurrentItem().getType().equals(Material.MAP)){
            int teamNumber = Integer.parseInt(e.getCurrentItem().getItemMeta().getDisplayName().replace("§fTeam #§e", ""));
            TeamManager.joinTeam(p, teamNumber);
            TeamGUI.openInv(p, (page-1));
        } else if(e.getCurrentItem().getItemMeta().getDisplayName().toLowerCase().contains("vorherige seite")){
            int newpage = (page-1)-1;
            if(newpage < 0){
                newpage = 0;
            }
            TeamGUI.openInv(p, newpage);
        } else if(e.getCurrentItem().getItemMeta().getDisplayName().toLowerCase().contains("nächste seite")){
            int newpage = page;
            if(newpage < 0){
                newpage = 0;
            }
            TeamGUI.openInv(p, newpage);
        }
    }

}
