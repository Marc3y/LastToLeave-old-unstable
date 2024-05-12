package de.marcey.lasttoleave.utils;

import de.marcey.marceyapi.colors.ColorGradient;
import net.md_5.bungee.api.ChatColor;

public class Strings {

    public static String prefix = new ColorGradient("LastToLeave", "#eb9b19", "#ebc219").withBold().build() + " §r§8>>§r";
    public static String nopermission = prefix + " §cDu hast keine Rechte dafür§7.";

    public static String getGradient(String str, boolean bold){
        if(bold) {
            return new ColorGradient(str, "#eb9b19", "#ebc219").withBold().build();
        } else return new ColorGradient(str, "#eb9b19", "#ebc219").build();
    }

}
