package de.marcey.lasttoleave.gui;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.Pane;
import de.marcey.lasttoleave.LastToLeave;
import de.marcey.lasttoleave.apis.gui.Gui;
import de.marcey.lasttoleave.apis.gui.ItemGui;
import de.marcey.lasttoleave.maps.Map;
import de.marcey.lasttoleave.maps.MapManager;
import de.marcey.lasttoleave.settings.GameSetting;
import de.marcey.lasttoleave.settings.UnderSetting;
import de.marcey.marceyapi.items.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class GuiManager {

    private final Player player;
    public GuiManager(Player p){
        this.player = p;
    }

    public Player getPlayer() {
        return player;
    }

    public void openMainMenu(){
        ChestGui gui = new ChestGui(3, "Einstellungen | Hauptmenü");
        gui.setOnGlobalClick(click -> click.setCancelled(true));
        gui.setOnGlobalDrag(click -> click.setCancelled(true));

        gui.setOnGlobalClick(inventoryClickEvent -> inventoryClickEvent.setCancelled(true));

        OutlinePane navigatorPane = new OutlinePane(0, 1, 9, 3, Pane.Priority.HIGH);

        navigatorPane.addItem(new GuiItem(new ItemBuilder(Material.MAP)
                .displayname("§eMap auswählen")
                .lore("§7", "§7Klicke hier, um eine", "§7Map auszuwählen.", "§7")
                .build(), click -> {
            openMapsGui();
        }));

        navigatorPane.addItem(new GuiItem(new ItemBuilder(Material.CLOCK)
                .displayname("§eEinstellungen")
                .lore("§7", "§7Klicke hier, um die", "§7Einstellungen des Spieles", "§7festzulegen.", "§7")
                .build(), click -> {
            openSettingsGui();
        }));

        navigatorPane.addItem(new GuiItem(new ItemBuilder(Material.IRON_SWORD)
                .displayname("§eKits")
                .lore("§7", "§7Klicke hier, um die", "§7Kits für das Spiel", "§7auszuwählen.", "§7")
                .build(), click -> {
            openKitsGui();
        }));

        navigatorPane.align(OutlinePane.Alignment.CENTER);
        navigatorPane.setGap(2);
        gui.addPane(navigatorPane);

        OutlinePane background = new OutlinePane(0, 0, 9, 3, Pane.Priority.LOWEST);
        background.addItem(new GuiItem(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).displayname("§7").build()));
        background.setRepeat(true);
        gui.addPane(background);
        gui.show(player);
    }

    public void openMapsGui(){
        ChestGui gui = new ChestGui(5, "Einstellungen | Map auswählen");
        gui.setOnGlobalClick(click -> click.setCancelled(true));
        gui.setOnGlobalDrag(click -> click.setCancelled(true));
        OutlinePane mapsPane = new OutlinePane(0, 0, 9, 3);

        for(Map map : MapManager.getAllMaps()){
            HashMap<Enchantment, Integer> enchantments = new HashMap<>();
            if(MapManager.getSelectedMap() != null && MapManager.getSelectedMap().getDisplayname().equalsIgnoreCase(map.getDisplayname())){
                enchantments.put(Enchantment.DURABILITY, 3);
            }
            String builder = "";
            for(String b : map.getBuilder()){
                if(builder.isEmpty()) {
                    builder = b;
                } else builder = builder + ", " + b;
            }
            mapsPane.addItem(new GuiItem(new ItemBuilder(Material.MAP)
                    .displayname("§e" + map.getDisplayname())
                    .lore("§7", "§7by " + builder, "§7")
                    .itemFlag(ItemFlag.HIDE_ATTRIBUTES)
                    .enchantments(enchantments)
                    .build(), click -> {
                MapManager.setSelectedMap(map);
                openMapsGui();
            }));
        }
        mapsPane.align(OutlinePane.Alignment.CENTER);
        mapsPane.setGap(1);

        gui.addPane(mapsPane);

        OutlinePane background = new OutlinePane(0, 3,  9, 1);
        background.addItem(new GuiItem(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).displayname("§7").build()));
        background.setRepeat(true);

        OutlinePane back = new OutlinePane(0, 4, 9, 1);
        back.align(OutlinePane.Alignment.CENTER);
        back.addItem(new GuiItem(new ItemBuilder(Material.OAK_DOOR)
                .displayname("§7Zurück")
                .lore("§7", "§7Klicke hier, um zum", "§7letzten GUI zurückzukehren.", "§7")
                .build(), click -> {
            openMainMenu();
        }));
        gui.addPane(background);
        gui.addPane(back);
        gui.show(player);
    }

    public void openSettingsGui(){
        ChestGui gui = new ChestGui(5, "Einstellungen");
        gui.setOnGlobalClick(click -> click.setCancelled(true));
        gui.setOnGlobalDrag(click -> click.setCancelled(true));
        OutlinePane pane = new OutlinePane(0, 1, 9, 3);
        pane.setGap(1);
        pane.addItem(new GuiItem(new ItemBuilder(Material.BEACON)
                .displayname("§bWorld-Border")
                .lore("§7", "§7Klicke hier, um die Einstellungen", "§7für die World-Border zu", "§7öffnen.", "§7")
                .build(), click -> {
            openWorldborderSettingsGui();
        }));
        pane.addItem(new GuiItem(new ItemBuilder(Material.DIAMOND_BOOTS)
                .displayname("§bSpieler-Einstellungen")
                .lore("§7", "§7Klicke hier, um die Einstellungen", "§7für die Spieler zu öffnen.", "§7")
                .build(), click -> {
            openPlayerSettings();
        }));
        pane.addItem(new GuiItem(new ItemBuilder(Material.NETHER_STAR)
                .displayname("§bEvents")
                .lore("§7", "§7Klicke hier, um die Einstellungen", "§7für die Events zu öffnen.", "§7")
                .build(), click -> {
            openEventsGui();
        }));
        pane.align(OutlinePane.Alignment.CENTER);
        gui.addPane(pane);

        OutlinePane glass = new OutlinePane(0, 3, 9, 1);
        glass.addItem(new GuiItem(new ItemBuilder(Material.LIGHT_GRAY_STAINED_GLASS_PANE)
                .displayname("§7")
                .build(), click -> {
            click.setCancelled(true);
        }));
        glass.setRepeat(true);
        gui.addPane(glass);
        OutlinePane downItems = new OutlinePane(0, 4, 9, 1);
        downItems.addItem(new GuiItem(new ItemBuilder(Material.OAK_DOOR)
                .displayname("§7Zurück")
                .lore("§7", "§7Klicke hier, um zum", "§7letzten GUI zurückzukehren.", "§7")
                .build(), click -> {
            openMainMenu();
        }));
        downItems.align(OutlinePane.Alignment.CENTER);
        gui.addPane(downItems);
        gui.show(player);
    }

    private void openWorldborderSettingsGui(){

        ChestGui gui = new ChestGui(5, "Einstellungen | World-Border");
        gui.setOnGlobalClick(click -> click.setCancelled(true));
        gui.setOnGlobalDrag(click -> click.setCancelled(true));

        OutlinePane settings = new OutlinePane(0, 1, 9, 3);
        settings.setGap(1);

        for(GameSetting setting : GameSetting.values()){
            if(!setting.getUnderSetting().equals(UnderSetting.WORLD_BORDER)) continue;
            List<String> lore = new ArrayList<>();
            List<String> tempLore = new ArrayList<>(Arrays.asList(setting.getLore()));
            for(String l : tempLore){
                lore.add(l.replaceAll("\\{valueBool}", setting.getDefaultBoolean() ? "§eAktiviert" : "§cDeaktiviert").replaceAll("\\{value}", setting.getDefaultValue() + ""));
            }
            settings.addItem(new GuiItem(new ItemBuilder(setting.getMaterial())
                    .displayname("§e" + setting.getDisplayname())
                    .lore(lore)
                    .build(), click -> {
                if(click.isRightClick()){
                    setting.next();
                    openWorldborderSettingsGui();
                } else  if(click.isLeftClick()){
                    setting.previous();
                    openWorldborderSettingsGui();
                }
            }));
        }
        settings.align(OutlinePane.Alignment.CENTER);
        gui.addPane(settings);

        OutlinePane glass = new OutlinePane(0, 3, 9, 1);
        glass.addItem(new GuiItem(new ItemBuilder(Material.LIGHT_GRAY_STAINED_GLASS_PANE)
                .displayname("§7")
                .build(), click -> {
            click.setCancelled(true);
        }));
        glass.setRepeat(true);
        glass.align(OutlinePane.Alignment.CENTER);
        OutlinePane downItems = new OutlinePane(0, 4, 9, 1);
        downItems.addItem(new GuiItem(new ItemBuilder(Material.OAK_DOOR)
                .displayname("§7Zurück")
                .lore("§7", "§7Klicke hier, um zum", "§7letzten GUI zurückzukehren.", "§7")
                .build(), click -> {
            openSettingsGui();
        }));
        downItems.align(OutlinePane.Alignment.CENTER);
        gui.addPane(glass);
        gui.addPane(downItems);

        gui.show(player);


    }

    private void openPlayerSettings(){
        ChestGui gui = new ChestGui(5, "Einstellungen | Spieler");
        gui.setOnGlobalClick(click -> click.setCancelled(true));
        gui.setOnGlobalDrag(click -> click.setCancelled(true));

        OutlinePane settings = new OutlinePane(0, 1, 9, 3);

        for(GameSetting setting : GameSetting.values()){
            if(!setting.getUnderSetting().equals(UnderSetting.PLAYER_SETTINGS)) continue;
            List<String> lore = new ArrayList<>();
            List<String> tempLore = new ArrayList<>(Arrays.asList(setting.getLore()));
            for(String l : tempLore){
                lore.add(l.replaceAll("\\{valueBool}", setting.getDefaultBoolean() ? "§eAktiviert" : "§cDeaktiviert").replaceAll("\\{value}", setting.getDefaultValue() + ""));
            }
            settings.addItem(new GuiItem(new ItemBuilder(setting.getMaterial())
                    .displayname("§e" + setting.getDisplayname())
                    .lore(lore)
                    .build(), click -> {
                if(click.isRightClick()){
                    setting.next();
                    openPlayerSettings();
                } else  if(click.isLeftClick()){
                    setting.previous();
                    openPlayerSettings();
                }
            }));
        }
        settings.align(OutlinePane.Alignment.CENTER);
        gui.addPane(settings);

        OutlinePane glass = new OutlinePane(0, 3, 9, 1);
        glass.addItem(new GuiItem(new ItemBuilder(Material.LIGHT_GRAY_STAINED_GLASS_PANE)
                .displayname("§7")
                .build(), click -> {
            click.setCancelled(true);
        }));
        glass.setRepeat(true);
        glass.align(OutlinePane.Alignment.CENTER);
        OutlinePane downItems = new OutlinePane(0, 4, 9, 1);
        downItems.addItem(new GuiItem(new ItemBuilder(Material.OAK_DOOR)
                .displayname("§7Zurück")
                .lore("§7", "§7Klicke hier, um zum", "§7letzten GUI zurückzukehren.", "§7")
                .build(), click -> {
            openSettingsGui();
        }));
        downItems.align(OutlinePane.Alignment.CENTER);
        gui.addPane(glass);
        gui.addPane(downItems);

        gui.show(player);

    }

    private void openEventsGui(){
        ChestGui gui = new ChestGui(5, "Einstellungen | Events");
        gui.setOnGlobalClick(click -> click.setCancelled(true));
        gui.setOnGlobalDrag(click -> click.setCancelled(true));

        OutlinePane settings = new OutlinePane(0, 1, 9, 3);

        for(GameSetting setting : GameSetting.values()){
            if(!setting.getUnderSetting().equals(UnderSetting.EVENTS)) continue;
            List<String> lore = new ArrayList<>();
            List<String> tempLore = new ArrayList<>(Arrays.asList(setting.getLore()));
            for(String l : tempLore){
                lore.add(l.replaceAll("\\{valueBool}", setting.getDefaultBoolean() ? "§eAktiviert" : "§cDeaktiviert").replaceAll("\\{value}", setting.getDefaultValue() + ""));
            }
            settings.addItem(new GuiItem(new ItemBuilder(setting.getMaterial())
                    .displayname("§d" + setting.getDisplayname())
                    .lore(lore)
                    .build(), click -> {
                if(click.isRightClick()){
                    setting.next();
                    openEventsGui();
                } else  if(click.isLeftClick()){
                    setting.previous();
                    openEventsGui();
                }
            }));
        }
        settings.align(OutlinePane.Alignment.CENTER);
        gui.addPane(settings);

        OutlinePane glass = new OutlinePane(0, 3, 9, 1);
        glass.addItem(new GuiItem(new ItemBuilder(Material.LIGHT_GRAY_STAINED_GLASS_PANE)
                .displayname("§7")
                .build(), click -> {
            click.setCancelled(true);
        }));
        glass.setRepeat(true);
        glass.align(OutlinePane.Alignment.CENTER);
        OutlinePane downItems = new OutlinePane(0, 4, 9, 1);
        downItems.addItem(new GuiItem(new ItemBuilder(Material.OAK_DOOR)
                .displayname("§7Zurück")
                .lore("§7", "§7Klicke hier, um zum", "§7letzten GUI zurückzukehren.", "§7")
                .build(), click -> {
            openSettingsGui();
        }));
        downItems.align(OutlinePane.Alignment.CENTER);
        gui.addPane(glass);
        gui.addPane(downItems);

        gui.show(player);
    }

    public void openKitsGui(){

    }


}
