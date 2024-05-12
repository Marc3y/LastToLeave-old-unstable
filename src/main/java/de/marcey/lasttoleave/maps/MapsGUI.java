package de.marcey.lasttoleave.maps;

import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import de.marcey.lasttoleave.apis.customheads.CustomHeads;
import de.marcey.lasttoleave.teammanager.Team;
import de.marcey.lasttoleave.teammanager.TeamManager;
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

public class MapsGUI {

    public static void openInv(Player p){
        ChestGui gui = new ChestGui(3, "Map auswählen");
        StaticPane pane = new StaticPane(0, 0, 9, 3);


        Inventory inv = Bukkit.createInventory(null, 9*6, "Map auswählen");
        for(Map map : MapManager.getAllMaps()){
            List<String> lore = new ArrayList<>();
            String builder = null;
            for(String b : map.getBuilder()){
                if(builder == null){
                    builder = "§8~ §7" + b;
                } else builder = builder + "§7, " + b;
            }
            lore.add("§7 ");
            ItemStack stack = gM(Material.MAP, 1, "§e" + map.getDisplayname(), MapManager.getSelectedMap() != null ? (MapManager.getSelectedMap().getWorldname().equalsIgnoreCase(map.getWorldname())) : false, lore);
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

        ItemStack selectedMap = null;
        if(MapManager.getSelectedMap() != null){
            String builder = null;
            for(String b : MapManager.getSelectedMap().getBuilder()){
                if(builder == null){
                    builder = "§8~ §7" + b;
                } else builder = builder + "§7, " + b;
            }
            selectedMap = gM(Material.MAP, 1, "§7Ausgewählte Map:", false, Arrays.asList("§7", "§7Map-Name: §e" + MapManager.getSelectedMap().getDisplayname(), "§7Builder: §e" + builder, "§7"));
        } else selectedMap = gM(Material.MAP, 1, "§7Ausgewählte Map:", false, Arrays.asList("§7", "§7Keine Map ausgewählt", "§7"));

        inv.setItem(46, selectedMap);

        ItemStack randomJoin = gM(Material.FEATHER, 1, "§7Zufällige Map auswählen", false, "§7", "§7Klicke hier, um eine", "§7zufällige Map auszuwählen.", "§7");
        inv.setItem(52, randomJoin);

        ItemStack close = gM(Material.OAK_DOOR, 1, "§7Schließen", false, "§7Klicke hier, um das Inventar", "§7zu schließen.", "§7");
        inv.setItem(49, close);

        p.openInventory(inv);
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
