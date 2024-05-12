package de.marcey.lasttoleave.utils;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class Serialization {
    public static String[] invToBase64(PlayerInventory inv){
        String content = toBase64(inv.getContents());
        String armor = toBase64(inv.getArmorContents());
        return new String[] {content, armor};
    }

    public static ItemStack[][] base64ToInv(String[] values){
        ItemStack[] content = fromBase64(values[0]);
        ItemStack[] armor = fromBase64(values[1]);
        return new ItemStack[][]{content, armor};
    }

    public static ItemStack[] fromBase64(String data) {
        try {
            ByteArrayInputStream in = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataIn = new BukkitObjectInputStream(in);

            ItemStack[] items = new ItemStack[dataIn.readInt()];

            for(int i = 0; i < items.length; i++){
                items[i] = (ItemStack) dataIn.readObject();
            }
            dataIn.close();
            return items;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static String toBase64(ItemStack[] items){
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOut = new BukkitObjectOutputStream(out);
            dataOut.writeInt(items.length);
            for(ItemStack item : items){
                dataOut.writeObject(item);
            }
            dataOut.close();
            return Base64Coder.encodeLines(out.toByteArray());
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}