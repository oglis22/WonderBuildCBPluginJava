package dev.lupluv.cb.shop;

import dev.lupluv.cb.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Adminshop {

    public Adminshop(Player p) {
        this.p = p;
    }

    Player p;

    public void open(){
        Inventory inv = Bukkit.createInventory(null, 9*6, "§6§lAdminshop");

        Item grayItem = new Item(Material.GRAY_STAINED_GLASS_PANE);
        grayItem.setDisplayName(" ");
        ItemStack gray = grayItem.build();
        for(int i = 0; i < 10; i++){
            inv.setItem(i, gray);
        }
        inv.setItem(9+8, gray);
        inv.setItem(9+9, gray);
        inv.setItem(9+9+8, gray);
        inv.setItem(9+9+9, gray);
        inv.setItem(9+9+9+8, gray);
        inv.setItem(9+9+9+9, gray);
        inv.setItem(9+9+9+9+8, gray);
        for(int i = 9+9+9+9+9; i < inv.getSize(); i++){
            inv.setItem(i, gray);
        }

        for(CbItem cbItem : CbItem.values()){
            // Item
            Item item = new Item(cbItem.getMaterial());
            item.setDisplayName("§7" + cbItem.getDisplayName());
            if(Worth.getWorth(cbItem).getSell() >= 1) {
                item.setLore(Lore.create(
                        " ",
                        "§7Kaufen für §a" + Worth.getWorth(cbItem).getBuy() + "§7 pro Item",
                        "§7Verkaufen für §a" + Worth.getWorth(cbItem).getSell() + "§7 pro Item",
                        " ",
                        "§7Linksklick um zu kaufen",
                        "§7Rechtsklick um zu verkaufen",
                        " "
                ));
            }else{
                item.setLore(Lore.create(
                        " ",
                        "§7Kaufen für §a" + Worth.getWorth(cbItem).getBuy() + "§7 pro Item",
                        " ",
                        "§7Linksklick um zu kaufen",
                        " "
                ));
            }
            inv.addItem(item.build());
        }

        p.openInventory(inv);
    }

    public static final String amount_1 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3Jh" +
            "ZnQubmV0L3RleHR1cmUvNGI1OTljNjE4ZTkxNGMyNWEzN2Q2OWY1NDFhMjJiZWJiZjc1MTYxNTI2Mzc1NmYyNTYxZmFiNGNmYTM5ZSJ9fX0=",
            amount_32 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmNhNWJm" +
                    "ZjMyNWVkNzFkOTdhMmRkZmM4M2FjZjA1ZmU3ZmQ5Y2I3Y2JkYjE1ZWJiNGYwNTYyMTkwN2U5ZjJiIn19fQ==",
            amount_64 = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjMzYjUx" +
                    "ZmVmMWQ3ZmRmMTkyNzRiYjc2ZmNlZGVjZWM3YTc3ZDAxMGNiMzRmZTAyOWZiNzk0Y2M1OWFiYSJ9fX0=",
            backVal = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmQ2OWUwNmU" +
                    "1ZGFkZmQ4NGU1ZjNkMWMyMTA2M2YyNTUzYjJmYTk0NWVlMWQ0ZDcxNTJmZGM1NDI1YmMxMmE5In19fQ==";

    public void amountBuy(CbItem cbItem){
        Inventory inv = Bukkit.createInventory(null, 9*3, "§7" + cbItem.getDisplayName() + " §8» §7Kaufen");

        ItemStack buy_1 = Util.createCustomSkull(amount_1, "§7Kaufe 1 " + cbItem.getDisplayName(),
                Lore.create(" ", "§7Gesamt Preis §e" + Worth.getWorth(cbItem).getBuy() + " Coins"));
        inv.setItem(11, buy_1);

        ItemStack buy_32 = Util.createCustomSkull(amount_32, "§7Kaufe 32 " + cbItem.getDisplayName(),
                Lore.create(" ", "§7Gesamt Preis §e" + Worth.getWorth(cbItem).getBuy()*32 + " Coins"));
        buy_32.setAmount(32);
        inv.setItem(13, buy_32);

        ItemStack buy_64 = Util.createCustomSkull(amount_64, "§7Kaufe 64 " + cbItem.getDisplayName(),
                Lore.create(" ", "§7Gesamt Preis §e" + Worth.getWorth(cbItem).getBuy()*64 + " Coins"));
        buy_64.setAmount(64);
        inv.setItem(15, buy_64);

        ItemStack back = Util.createCustomSkull(backVal, "§7➥ Zurück", Lore.create("§7Gehe zurück ins Hauptmenü"));
        inv.setItem(26, back);

        Item item = new Item(Material.GRAY_STAINED_GLASS_PANE);
        item.setDisplayName(" ");
        for(int i = 0; i < inv.getSize(); i++){
            if(inv.getItem(i) == null || inv.getItem(i).getType() == Material.AIR){
                inv.setItem(i, item.build());
            }
        }

        p.openInventory(inv);
    }

    public void amountSell(CbItem cbItem){
        Inventory inv = Bukkit.createInventory(null, 9*3, "§7" + cbItem.getDisplayName() + " §8» §7Verkaufen");

        ItemStack buy_1 = Util.createCustomSkull(amount_1, "§7Verkaufe 1 " + cbItem.getDisplayName(),
                Lore.create(" ", "§7Du erhälst §e" + Worth.getWorth(cbItem).getSell() + " Coins"));
        inv.setItem(11, buy_1);

        ItemStack buy_32 = Util.createCustomSkull(amount_32, "§7Verkaufe 32 " + cbItem.getDisplayName(),
                Lore.create(" ", "§7Du erhälst §e" + Worth.getWorth(cbItem).getSell()*32 + " Coins"));
        buy_32.setAmount(32);
        inv.setItem(13, buy_32);

        ItemStack buy_64 = Util.createCustomSkull(amount_64, "§7Verkaufe 64 " + cbItem.getDisplayName(),
                Lore.create(" ", "§7Du erhälst §e" + Worth.getWorth(cbItem).getSell()*64 + " Coins"));
        buy_64.setAmount(64);
        inv.setItem(15, buy_64);

        ItemStack back = Util.createCustomSkull(backVal, "§7➥ Zurück", Lore.create("§7Gehe zurück ins Hauptmenü"));
        inv.setItem(26, back);

        Item item = new Item(Material.GRAY_STAINED_GLASS_PANE);
        item.setDisplayName(" ");
        for(int i = 0; i < inv.getSize(); i++){
            if(inv.getItem(i) == null || inv.getItem(i).getType() == Material.AIR){
                inv.setItem(i, item.build());
            }
        }

        p.openInventory(inv);
    }

}
