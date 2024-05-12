package de.marcey.lasttoleave.teammanager;

import de.marcey.lasttoleave.apis.customheads.CustomHeads;
import de.marcey.lasttoleave.apis.gui.Gui;
import de.marcey.lasttoleave.apis.gui.ItemGui;
import net.md_5.bungee.api.chat.hover.content.Item;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TeamGUI {

    public static void openInv(Player p, int page){
        Inventory inv = Bukkit.createInventory(null, 9*6, "Team auswählen | Seite " + (page+1));
        for(Team t : getTeams(page)){
            List<String> lore = new ArrayList<>();
            lore.add("§7 ");
            for(Player u : t.getPlayers()){
                lore.add("§7- §6" + u.getName());
            }
            lore.add("§7 ");
            ItemStack stack = gM(Material.MAP, 1, "§fTeam #§e" + t.getTeamNumber(), t.getPlayers().contains(p), lore);
            inv.addItem(stack);
        }
        ItemStack glass = gM(Material.LIGHT_GRAY_STAINED_GLASS_PANE, 1, "§7", false, "§7");
        for(int i = 36; i < 45; i++){
            inv.setItem(i, glass);
        }
        ItemStack previous = CustomHeads.getCustomHeadFromValue("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjZkYWI3MjcxZjRmZjA0ZDU0NDAyMTkwNjdhMTA5YjVjMGMxZDFlMDFlYzYwMmMwMDIwNDc2ZjdlYjYxMjE4MCJ9fX0=");
        ItemMeta previousMeta = previous.getItemMeta();
        previousMeta.setDisplayName("§7Vorherige Seite");
        previousMeta.setLore(Arrays.asList("§7", "§7Klicke hier, um auf die vorherige Seite zu kommen.", "§7"));
        previous.setItemMeta(previousMeta);

        ItemStack next = CustomHeads.getCustomHeadFromValue("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGFhMTg3ZmVkZTg4ZGUwMDJjYmQ5MzA1NzVlYjdiYTQ4ZDNiMWEwNmQ5NjFiZGM1MzU4MDA3NTBhZjc2NDkyNiJ9fX0=");
        ItemMeta nextMeta = next.getItemMeta();
        nextMeta.setDisplayName("§7Nächste Seite");
        nextMeta.setLore(Arrays.asList("§7", "§7Klicke hier, um auf die nächste Seite zu kommen.", "§7"));
        next.setItemMeta(nextMeta);

        inv.setItem(45, previous);
        inv.setItem(53, next);
        Team team = TeamManager.getTeam(p);
        List<String> lore = new ArrayList<>();
        lore.add("§7 ");
        lore.add("§7Team: " + (team != null ? "§f#§e" + team.getTeamNumber() : "§8Kein Team"));
        lore.add("§7");
        if(team != null) {
            for (Player u : team.getPlayers()) {
                lore.add("§7- §6" + u.getName());
            }
        }
        lore.add("§7 ");
        ItemStack ownTeam = gM(Material.RED_BED, 1, "§7Dein Team", false, lore);
        inv.setItem(46, ownTeam);

        ItemStack randomJoin = gM(Material.FEATHER, 1, "§7Zufälliges Team beitreten", false, "§7Klicke hier, um ein", "§7zufälliges Team beizutreten.", "§7");
        inv.setItem(52, randomJoin);

        ItemStack close = gM(Material.OAK_DOOR, 1, "§7Schließen", false, "§7Klicke hier, um das Inventar", "§7zu schließen.", "§7");
        inv.setItem(49, close);

        p.openInventory(inv);
    }

    public static List<Team> getTeams(int page) {
        int maxPerInv = 36;
        int teams = TeamManager.getMaxTeams();
        page = page+1;

        if (teams <= maxPerInv) {
            return TeamManager.getTeams();
        }

        int totalPages = (int) Math.ceil((double) teams / maxPerInv);
        int teamNumberStartOnPage = (page - 1) * maxPerInv + 1;
        int teamNumberEndOnPage = Math.min(teamNumberStartOnPage + maxPerInv - 1, teams);

        List<Team> toReturn = new ArrayList<>();
        for (int i = teamNumberStartOnPage; i <= teamNumberEndOnPage; i++) {
            Team team = TeamManager.getTeam(i);
            toReturn.add(team);
        }

        return toReturn;
    }

    public static ItemStack gM(Material material, int amount, String displayname, boolean isEnchant, String... lore){

        ItemStack stack = new ItemStack(material, amount);
        ItemMeta meta = stack.getItemMeta();
        if(meta.getLore() != null && !meta.getLore().isEmpty()){
            meta.getLore().clear();
        }
        meta.setDisplayName(displayname);
        if(isEnchant){
            meta.addEnchant(Enchantment.DURABILITY, 3, true);
        }
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);

        List<String> lores = new ArrayList<>();
        for(String l : lore){
            lores.add(l);
        }
        meta.setLore(lores);
        stack.setItemMeta(meta);

        return stack;

    }

    public static ItemStack gM(Material material, int amount, String displayname, boolean isEnchant, List<String> lore){

        ItemStack stack = new ItemStack(material, amount);
        ItemMeta meta = stack.getItemMeta();
        if(meta.getLore() != null && !meta.getLore().isEmpty()){
            meta.getLore().clear();
        }
        meta.setDisplayName(displayname);
        if(isEnchant){
            meta.addEnchant(Enchantment.DURABILITY, 3, true);
        }
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addItemFlags(ItemFlag.HIDE_POTION_EFFECTS);

        List<String> lores = new ArrayList<>();
        for(String l : lore){
            lores.add(l);
        }
        meta.setLore(lores);
        stack.setItemMeta(meta);

        return stack;

    }
}
