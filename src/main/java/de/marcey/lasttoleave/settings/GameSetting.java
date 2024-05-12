package de.marcey.lasttoleave.settings;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.util.Consumer;

public enum GameSetting {

    WORLD_BORDER_START("World-Border-Start", -1, 10, true, UnderSetting.WORLD_BORDER, Material.FEATHER, GameSetting::worldBorderStart, "§7Lege der Start-Zeitpunkt fest,", "§7wann nach dem Spiel-Start die", "§7World-Border starten soll.", "§7", "§7Zeit: §e{value}s", "§7"),
    WORLD_BORDER_STANDARD("World-Border-Standard", 500, 5, false, UnderSetting.WORLD_BORDER,  Material.BEACON,action -> {

    },"§7Lege die Standard World-Border-Grße,", "§7fest.", "§7", "§8Blöcke: §e{value}", "§7"),
    WORLD_BORDER_TIME("World-Border-Zeit", -1, 5, false, UnderSetting.WORLD_BORDER,  Material.CLOCK,action -> {

    },"§7Lege die Zeit (in Sekunden) fest,", "§7wie lange die Border zur Min-Size", "§7braucht.", "§7", "§7Zeit: §e{value}s", "§7"),
    WORLD_BORDER_MINSIZE("World-Border-Größe (min)", -1, 2, false, UnderSetting.WORLD_BORDER,  Material.LIGHTNING_ROD, action -> {

    },"§7Lege die fest, wie weit", "§7die World-Border laufen darf.", "§7", "§7Blöcke: §e{value}s", "§7"),

    //Player-Settings

    HUNGER("Hunger bekommen", false, UnderSetting.PLAYER_SETTINGS, Material.COOKED_BEEF, action -> {

    },"§7Lege fest, ob Spieler", "§7Hunger bekommen können.", "§7", "§7Status: §e{valueBool}", "§7"),
    PVP("PvP", true, UnderSetting.PLAYER_SETTINGS, Material.DIAMOND_SWORD, action -> {

    },"§7Lege fest, ob sich Spieler", "§7angreifen können", "§7", "§7Status: §e{valueBool}", "§7"),
    DROP_ITEMS("Items droppen", true, UnderSetting.PLAYER_SETTINGS, Material.TRIPWIRE_HOOK, action -> {

    },"§7Lege fest, ob Spieler", "§7Items droppen können.", "§7", "§7Status: §e{valueBool}", "§7"),
    FRIENDLY_FIRE("Friendly Fire (Teams)", false, UnderSetting.PLAYER_SETTINGS, Material.GOLDEN_SWORD, action -> {

    },"§7Lege fest, ob Spieler, die", "§7in einem Team sind, sich angreifen können.", "§7", "§7Status: §e{valueBool}", "§7"),
    PICKUP_ITEMS("Items aufheben", true, UnderSetting.PLAYER_SETTINGS, Material.WHEAT_SEEDS, action -> {

    }, "§7Lege fest, ob Spieler Items", "§7aufheben dürfen.", "§7", "§7Status: §e{valueBool}", "§7"),
    INTERACTION("Interaktion mit Türen/etc", true, UnderSetting.PLAYER_SETTINGS, Material.DARK_OAK_DOOR, action -> {

    },"§7Lege fest, ob Spieler", "§7Türen, Trapdoors, Chests, etc nutzen können.", "§7", "§7Status: §e{valueBool}", "§7"),
    BREAK_BLOCKS("Abbauen von Blöcken", false, UnderSetting.PLAYER_SETTINGS, Material.IRON_PICKAXE, action -> {

    },"§7Lege fest, ob Spieler Blöcke", "§7abbauen können.", "§7", "§7Status: §e{valueBool}", "§7"),
    BUILD("Blöcke platzieren", false, UnderSetting.PLAYER_SETTINGS, Material.GRASS_BLOCK, action -> {

    },"§7Lege fest, ob Spieler Blöcke", "§7platzieren können.", "§7", "§7Status: §e{valueBool}", "§7"),

    //Events

    MOVING_ZONE("Moving-Zone", false, UnderSetting.EVENTS, Material.HOPPER, action -> {

    },"§7Eine Zone wie in Fortnite,", "§7die sich jede paar Sekunden verkleinert und", "§7an sich an unterschiedliche", "§7Positionen bewegt.", "§7", "§7Status: §e{valueBool}", "§7");

    private String name;
    private String displayname;
    private boolean defaultBoolean;
    private int defaultInt;
    private double defaultDouble;
    private int differenceInt;
    private double differenceDouble;
    private boolean underZeroDeactivate;
    private UnderSetting underSetting;
    private Material material;
    private Consumer<InventoryClickEvent> action;
    private String[] lore;

    GameSetting(String displayname, boolean defaultBoolean, UnderSetting underSetting, Material material, Consumer<InventoryClickEvent> action, String... lore) {
        this.name = this.toString().toLowerCase();
        this.displayname = displayname;
        this.defaultBoolean = defaultBoolean;
        this.underSetting = underSetting;
        this.material = material;
        this.action = action;
        this.lore = lore;
    }

    GameSetting(String displayname, int defaultInt, int difference, boolean underZeroDeactivate, UnderSetting underSetting, Material material, Consumer<InventoryClickEvent> action, String... lore) {
        this.displayname = displayname;
        this.defaultInt = defaultInt;
        this.underSetting = underSetting;
        this.differenceInt = difference;
        this.underZeroDeactivate = underZeroDeactivate;
        this.material = material;
        this.action = action;
        this.lore = lore;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    GameSetting(String displayname, double defaultDouble, double difference, boolean underZeroDeactivate, UnderSetting underSetting, Material material, Consumer<InventoryClickEvent> action, String... lore) {
        this.displayname = displayname;
        this.defaultDouble = defaultDouble;
        this.differenceDouble = difference;
        this.underZeroDeactivate = underZeroDeactivate;
        this.underSetting = underSetting;
        this.material = material;
        this.action = action;
        this.lore = lore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public boolean getDefaultBoolean() {
        return defaultBoolean;
    }

    public int getDefaultInt() {
        return defaultInt;
    }

    public void setDefaultInt(int defaultInt) {
        this.defaultInt = defaultInt;
    }

    public double getDefaultDouble() {
        return defaultDouble;
    }

    public void setDefaultDouble(double defaultDouble) {
        this.defaultDouble = defaultDouble;
    }

    public UnderSetting getUnderSetting() {
        return underSetting;
    }

    public void setUnderSetting(UnderSetting underSetting) {
        this.underSetting = underSetting;
    }

    public int getDifferenceInt() {
        return differenceInt;
    }

    public void setDifferenceInt(int differenceInt) {
        this.differenceInt = differenceInt;
    }

    public double getDifferenceDouble() {
        return differenceDouble;
    }

    public void setDifferenceDouble(double differenceDouble) {
        this.differenceDouble = differenceDouble;
    }

    public void setDefaultBoolean(boolean defaultBoolean) {
        this.defaultBoolean = defaultBoolean;
    }

    public String[] getLore() {
        return lore;
    }

    public void setLore(String[] lore) {
        this.lore = lore;
    }

    public Consumer<InventoryClickEvent> getAction() {
        return action;
    }
    public void setAction(Consumer<InventoryClickEvent> action) {
        this.action = action;
    }

    public Object getDefaultValue(){
        Double d = defaultDouble;
        Integer i = defaultInt;
        Boolean b = defaultBoolean;
        if(d != null) return defaultDouble;
        if(i != null) return defaultInt;
        return b;
    }

    public boolean isInt(){
        Integer i = defaultInt;
        return i != null;
    }
    public boolean isDouble(){
        Double i = defaultDouble;
        return i != null;
    }
    public boolean isBoolean(){
        Boolean i = defaultBoolean;
        return i != null;
    }

    public void next() {
        if (isInt()) {
            int value = getDefaultInt() + differenceInt;
            setDefaultInt(value);
        } else if (isDouble()) {
            double value = getDefaultDouble() + differenceDouble;
            if (value <= 0 && underZeroDeactivate) {
                setDefaultDouble(-1);
            } else {
                setDefaultDouble(value <= 0 ? 0 : value);
            }
        } else {
            setDefaultBoolean(!getDefaultBoolean());
        }
    }

    public void previous() {
        if (isInt()) {
            int value = getDefaultInt() - differenceInt;
            if (value <= 0 && underZeroDeactivate) {
                setDefaultInt(-1);
            } else {
                setDefaultInt(value <= 0 ? 0 : value);
            }
        } else if (isDouble()) {
            double value = getDefaultDouble() - differenceDouble;
            if (value <= 0 && underZeroDeactivate) {
                setDefaultDouble(-1);
            } else {
                setDefaultDouble(value <= 0 ? 0 : value);
            }
        } else {
            setDefaultBoolean(!getDefaultBoolean());
        }
    }

    private static void worldBorderStart(GameSetting setting, InventoryClickEvent action) {
        if (action.isRightClick()) {

        }
    }
}
