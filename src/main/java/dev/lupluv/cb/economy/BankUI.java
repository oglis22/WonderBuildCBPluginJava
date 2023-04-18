package dev.lupluv.cb.economy;

import dev.lupluv.cb.utils.Item;
import dev.lupluv.cb.utils.Lore;
import dev.lupluv.cb.utils.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class BankUI {

    public static final String in_value = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5" +
            "lY3JhZnQubmV0L3RleHR1cmUvNWRhMDI3NDc3MTk3YzZmZDdhZDMzMDE0NTQ2ZGUzOTJiNGE1MWM2MzRlYTY4YzhiN2JjYzAxMzFjODNlM2YifX19",
            out_value = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3Rle" +
                    "HR1cmUvYTM4NTJiZjYxNmYzMWVkNjdjMzdkZTRiMGJhYTJjNWY4ZDhmY2E4MmU3MmRiY2FmY2JhNjY5NTZhODFjNCJ9fX0=",
            balance_value = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3" +
                    "RleHR1cmUvYzhlYTc5MzM1ODFlZTlmYjQwMGYzOTA0NGQzMDE1Y2EwZDQzYmI2ZTcyZmM5MjY3YzdmZDEzNjFmNjhmZjEyYiJ9fX0=";

    public static final Component invName = Component.text("§6§lBank");

    public static final String in_name = "§6§lEinzahlen", out_name = "§6§lAuszahlen", balance_name = "§6§lKontostand";

    private Player player;
    private Inventory inventory;

    public BankUI(Player player) {
        this.player = player;
    }

    public BankUI setMainGUI(){
        this.inventory = Bukkit.createInventory(null, 9*3, invName);

        ItemStack in = Util.createCustomSkull(in_value, in_name, Lore.create(
                " ",
                "§7Klicke um Coins einzuzahlen.",
                " "
        ));
        inventory.setItem(10, in);

        ItemStack balance = Util.createCustomSkull(balance_value, balance_name, Lore.create(
                " ",
                "§7Dein aktueller Kontostand beträgt:", "§6" + Bank.getBalance(player.getUniqueId().toString()) + " Coins",
                " "
        ));
        inventory.setItem(13, balance);

        ItemStack out = Util.createCustomSkull(out_value, out_name, Lore.create(
                " ",
                "§7Klicke um Coins auszuzahlen.",
                " "
        ));
        inventory.setItem(16, out);

        for(int i = 0; i < inventory.getSize(); i++){
            if(inventory.getItem(i) == null || inventory.getItem(i).getType() == Material.AIR){
                dev.lupluv.cb.utils.Item item = new Item(Material.GRAY_STAINED_GLASS_PANE);
                item.setDisplayName(" ");
                inventory.setItem(i, item.build());
            }
        }
        return this;

    }

    public void openGUI(){
        player.openInventory(inventory);
    }

}
