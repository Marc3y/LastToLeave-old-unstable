package de.marcey.lasttoleave.kits;

import de.marcey.lasttoleave.utils.Serialization;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.List;

public class Kit {

    private static List<Kit> kits = new ArrayList<>();
    private String base64Inv;
    private String base64Armor;

    public Kit(String base64Inv, String base64Armor){
        this.base64Inv = base64Inv;
        this.base64Armor = base64Armor;
        kits.add(this);
    }

    public ItemStack[] getContent(){
        String[] values = new String[] {base64Inv, base64Armor};
        ItemStack[][] items = Serialization.base64ToInv(values);
        return items[0];
    }

    public ItemStack[] getArmor(){
        String[] values = new String[] {base64Inv, base64Armor};
        ItemStack[][] items = Serialization.base64ToInv(values);
        return items[1];
    }

    public void setKit(Player p){
        p.getInventory().clear();
        p.getInventory().setContents(getContent());
        p.getInventory().setArmorContents(getArmor());
    }

    public static Kit createKit(PlayerInventory inv){
        String[] values = Serialization.invToBase64(inv);
        Kit kit = new Kit(values[0], values[1]);
        return kit;
    }

}
