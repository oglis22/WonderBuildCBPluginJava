package dev.lupluv.cb.namecolors;

import dev.lupluv.cb.scoreboard.ScoreboardManager;
import dev.lupluv.cb.utils.Item;
import dev.lupluv.cb.utils.Lore;
import dev.lupluv.cb.utils.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.List;

public class NameColorUI {


    public static final Component invName1 = Component.text("§6§lNamensfarben §7Seite 1");
    public static final Component invName2 = Component.text("§6§lNamensfarben §7Seite 2");

    Player player;

    Inventory inventory;

    public NameColorUI(Player player) {
        this.player = player;
    }

    public NameColorUI setMainGUI(){
        inventory = Bukkit.createInventory(null, 9*6, invName1);
        Item glassItem = new Item(Material.GRAY_STAINED_GLASS_PANE);
        glassItem.setDisplayName(" ");
        ItemStack glass = glassItem.build();

        NameColorSelector ncs = new NameColorSelector(player.getUniqueId());
        if(!ncs.existsByUuid()) return this;
        ncs.loadByUuid();
        NColor sel = NColor.valueOf(ncs.getName_color());

        inventory.setItem(0, glass);
        inventory.setItem(1, glass);
        inventory.setItem(2, glass);
        inventory.setItem(3, glass);
        inventory.setItem(4, glass);
        inventory.setItem(5, glass);
        inventory.setItem(6, glass);
        inventory.setItem(7, glass);
        inventory.setItem(8, glass);
        inventory.setItem(9, glass);
        inventory.setItem(11, glass);
        inventory.setItem(13, glass);
        inventory.setItem(15, glass);
        inventory.setItem(17, glass);
        inventory.setItem(18, glass);
        inventory.setItem(19, glass);
        inventory.setItem(20, glass);
        inventory.setItem(21, glass);
        inventory.setItem(22, glass);
        inventory.setItem(23, glass);
        inventory.setItem(24, glass);
        inventory.setItem(25, glass);
        inventory.setItem(26, glass);
        inventory.setItem(27, glass);
        inventory.setItem(29, glass);
        inventory.setItem(31, glass);
        inventory.setItem(33, glass);
        inventory.setItem(35, glass);
        inventory.setItem(36, glass);
        inventory.setItem(37, glass);
        inventory.setItem(38, glass);
        inventory.setItem(39, glass);
        inventory.setItem(40, glass);
        inventory.setItem(41, glass);
        inventory.setItem(42, glass);
        inventory.setItem(43, glass);
        inventory.setItem(44, glass);
        Item none = new Item(Material.RED_STAINED_GLASS_PANE);
        none.setDisplayName("§4Keine Namensfarbe");
        if(sel == NColor.NONE){
            none.setLore(Lore.create("§aAusgewählt."));
        }else {
            none.setLore(Lore.create("§7Klicke zum auswählen."));
        }
        inventory.setItem(45, none.build());
        inventory.setItem(46, glass);
        inventory.setItem(47, glass);
        inventory.setItem(48, glass);
        inventory.setItem(51, glass);
        inventory.setItem(52, glass);
        inventory.setItem(53, glass);

        Item current = new Item(Material.BOOK);
        current.setDisplayName("§7Du bist auf Seite 1");
        inventory.setItem(49, current.build());
        Item next = new Item(Material.PAPER);
        next.setDisplayName("§7Seite 2");
        inventory.setItem(50, next.build());

        List<NColor> nColors = List.of(NColor.values());
        int i = 0;
        for(NColor nColor : nColors){
            if(i > 7){
                break;
            }

            // Set item
            ItemStack is;
            if(player.hasPermission("cb.namecolor.color." + nColor.toString())) {
                if(sel == nColor) {
                    is = Util.createCustomSkull(nColor.getValue(), ScoreboardManager.format2(nColor.format(nColor.getName())), Lore.create(
                            " ",
                            "§aAusgewählt.",
                            " "
                    ));
                    ItemMeta im = is.getItemMeta();
                    im.addEnchant(Enchantment.CHANNELING, 0, true);
                    im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                    im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    is.setItemMeta(im);
                    inventory.addItem(is);
                }else{
                    is = Util.createCustomSkull(nColor.getValue(), ScoreboardManager.format2(nColor.format(nColor.getName())), Lore.create(
                            " ",
                            "§7Klicke zum auswählen.",
                            " "
                    ));
                    inventory.addItem(is);
                }
            }else{
                is = Util.createCustomSkull(nColor.getValue(), ScoreboardManager.format2(nColor.format(nColor.getName())), Lore.create(
                        " ",
                        "§7Nicht in deinem Inventar.",
                        " "
                ));
                inventory.addItem(is);
            }
            i++;
        }
        return this;
    }

    public NameColorUI setMainGUI2(){
        inventory = Bukkit.createInventory(null, 9*6, invName2);
        Item glassItem = new Item(Material.GRAY_STAINED_GLASS_PANE);
        glassItem.setDisplayName(" ");
        ItemStack glass = glassItem.build();

        NameColorSelector ncs = new NameColorSelector(player.getUniqueId());
        if (!ncs.existsByUuid()) return this;
        ncs.loadByUuid();
        NColor sel = NColor.valueOf(ncs.getName_color());

        inventory.setItem(0, glass);
        inventory.setItem(1, glass);
        inventory.setItem(2, glass);
        inventory.setItem(3, glass);
        inventory.setItem(4, glass);
        inventory.setItem(5, glass);
        inventory.setItem(6, glass);
        inventory.setItem(7, glass);
        inventory.setItem(8, glass);
        inventory.setItem(9, glass);
        inventory.setItem(11, glass);
        inventory.setItem(13, glass);
        inventory.setItem(15, glass);
        inventory.setItem(17, glass);
        inventory.setItem(18, glass);
        inventory.setItem(19, glass);
        inventory.setItem(20, glass);
        inventory.setItem(21, glass);
        inventory.setItem(22, glass);
        inventory.setItem(23, glass);
        inventory.setItem(24, glass);
        inventory.setItem(25, glass);
        inventory.setItem(26, glass);
        inventory.setItem(27, glass);
        inventory.setItem(29, glass);
        inventory.setItem(31, glass);
        inventory.setItem(33, glass);
        inventory.setItem(35, glass);
        inventory.setItem(36, glass);
        inventory.setItem(37, glass);
        inventory.setItem(38, glass);
        inventory.setItem(39, glass);
        inventory.setItem(40, glass);
        inventory.setItem(41, glass);
        inventory.setItem(42, glass);
        inventory.setItem(43, glass);
        inventory.setItem(44, glass);
        Item none = new Item(Material.RED_STAINED_GLASS_PANE);
        none.setDisplayName("§4Keine Namensfarbe");
        if(sel == NColor.NONE){
            none.setLore(Lore.create("§aAusgewählt."));
        }else {
            none.setLore(Lore.create("§7Klicke zum auswählen."));
        }
        inventory.setItem(45, none.build());
        inventory.setItem(46, glass);
        inventory.setItem(47, glass);
        inventory.setItem(50, glass);
        inventory.setItem(51, glass);
        inventory.setItem(52, glass);
        inventory.setItem(53, glass);

        Item current = new Item(Material.BOOK);
        current.setDisplayName("§7Du bist auf Seite 2");
        inventory.setItem(49, current.build());
        Item next = new Item(Material.PAPER);
        next.setDisplayName("§7Seite 1");
        inventory.setItem(48, next.build());

        List<NColor> nColors = List.of(NColor.values());
        int i = 0;
        for(NColor nColor : nColors){
            if(i > 7) {
                if (i > 7+8) {
                    break;
                }

                // Set item
                ItemStack is;
                if (player.hasPermission("cb.namecolor.color." + nColor.toString())) {
                    if (sel == nColor) {
                        is = Util.createCustomSkull(nColor.getValue(), ScoreboardManager.format2(nColor.format(nColor.getName())), Lore.create(
                                " ",
                                "§aAusgewählt.",
                                " "
                        ));
                        ItemMeta im = is.getItemMeta();
                        im.addEnchant(Enchantment.CHANNELING, 0, true);
                        im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                        is.setItemMeta(im);
                        inventory.addItem(is);
                    } else {
                        is = Util.createCustomSkull(nColor.getValue(), ScoreboardManager.format2(nColor.format(nColor.getName())), Lore.create(
                                " ",
                                "§eKlicke zum auswählen.",
                                " "
                        ));
                        inventory.addItem(is);
                    }
                } else {
                    is = Util.createCustomSkull(nColor.getValue(), ScoreboardManager.format2(nColor.format(nColor.getName())), Lore.create(
                            " ",
                            "§cNicht in deinem Inventar.",
                            " "
                    ));
                    inventory.addItem(is);
                }
            }
            i++;
        }
        return this;
    }

    public NameColorUI openGUI(){
        player.openInventory(this.inventory);
        return this;
    }

}
