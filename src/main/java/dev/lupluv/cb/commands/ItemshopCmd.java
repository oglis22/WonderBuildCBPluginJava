package dev.lupluv.cb.commands;

import dev.lupluv.cb.annotations.RegisterCommand;
import dev.lupluv.cb.namecolors.NameColorUI;
import dev.lupluv.cb.utils.Item;
import dev.lupluv.cb.utils.Strings;
import dev.lupluv.cb.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.PermissionDefault;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

@RegisterCommand(name = "itemshop", permissionDefault = PermissionDefault.TRUE, aliases = {"shop"})
public class ItemshopCmd implements CommandExecutor, Listener {

    public static Inventory inv;
    public static String inv_name = "§6§lItemshop";

    public static final String pets_value = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDY1NTE4NDA5" +
            "NTVmNTI0MzY3NTgwZjExYjM1MjI4OTM4YjY3ODYzOTdhOGYyZThjOGNjNmIwZWIwMWI1ZGIzZCJ9fX0=";

    public static final String crates_valeu = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjk4YmM2M2Y" +
            "wNWY2Mzc4YmYyOWVmMTBlM2Q4MmFjYjNjZWI3M2E3MjBiZjgwZjMwYmM1NzZkMGFkOGM0MGNmYiJ9fX0=";

    public static final String effects_vlaue = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzk1YTdhNjdmN2E0" +
            "YjM4ZjgyN2YxYmNhODBlNjFmNDg2MDZiNjljZTVmNjQzNDQxMmJiZmQ4YTU3NjU2YWQyZCJ9fX0=";

    public static final String namecolor_value = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWEwZWYyMDNh" +
            "M2MzODM3ZmQ0YTEwZTFjM2YxOWNjNDBlNjQ5N2NiOWI5NzgwNzRkNDcyYzY1ZDQ4MmY2YjY3MyJ9fX0=";

    public static final String ranks_value = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTZkMzI5MzU5ZWJlY" +
            "WM3ZGM1OWQzMDA0OTMyOTFmZmI3YTk5NWI4NWE3NzE4M2RjMDM5ZjdhZTgyZjYzNmRiYSJ9fX0=";

    public static final String booster_value = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzUyNzIzYWEwNjM3ZWE1MjA" +
            "0NDQ3YmU1MzYxNTFmNTI5ZmU0OTA2ODI2ZDhjMDY2YzRkY2M1MDA3OWE2NDQ2NSJ9fX0=";

public static void createAndOpenInventory(Player player){

    inv = Bukkit.createInventory(null, 9*5, inv_name);

        ItemStack pets = Util.createCustomSkull(pets_value, "§a§lHaustiere", null);
        ItemStack crates = Util.createCustomSkull(crates_valeu, "§a§lCrates", null);
        ItemStack effects = Util.createCustomSkull(effects_vlaue, "§a§lEffekte", null);
        ItemStack namecolor = Util.createCustomSkull(namecolor_value, "§a§lNamensfarben", null);
        ItemStack ranks = Util.createCustomSkull(ranks_value, "§a§lRänge", null);
        ItemStack booster = Util.createCustomSkull(booster_value, "§a§lBooster", null);



    inv.setItem(11, pets);
    inv.setItem(13, crates);
    inv.setItem(15, effects);
    inv.setItem(29, namecolor);
    inv.setItem(31, ranks);
    inv.setItem(33, booster);

    Item item = new Item(Material.GRAY_STAINED_GLASS_PANE);
    item.setDisplayName(" ");
    for(int i = 0; i < inv.getSize(); i++){
        if(inv.getItem(i) == null || inv.getItem(i).getType() == Material.AIR){
            inv.setItem(i, item.build());
        }
    }

    player.openInventory(inv);

}

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(!(sender instanceof Player)) return true;
        if(!(args.length == 0)) return true;

        Player player = (Player) sender;

        createAndOpenInventory(player);

        return false;
    }


    @EventHandler
    public void onClick(InventoryClickEvent e){

        if(!(e.getWhoClicked() instanceof Player)) return;
        if(e.getCurrentItem() == null) return;
        Player player = (Player) e.getWhoClicked();

        if(e.getView().getTitle().equals(inv_name)) {
            e.setCancelled(true);
            switch (e.getCurrentItem().getItemMeta().getDisplayName()){

                case "§a§lHaustiere" -> {

                    player.sendMessage(Strings.prefix  + "§cDiese Funktion ist in Arbeit!");

                    break;

                }
                case "§a§lCrates" -> {

                    player.performCommand("warp crates");

                    break;
                }
                case "§a§lEffekte" -> {

                    Effects.createAndOpenInventory(player);

                    break;
                }
                case "§a§lNamensfarben" -> {
                    new NameColorUI(player).setMainGUI().openGUI();
                    break;
                }
                    case "§a§lRänge" -> {
                        RangshopCmd.createInv(player);
                        break;
                    }
                    case "§a§lBooster" -> {

                    player.sendMessage(Strings.prefix + "§cDiese Funktion ist in Arbeit!");


                    break;
                }

            }
            switch (e.getCurrentItem().getType()){

                case BLACK_STAINED_GLASS:
                    player.sendMessage(" ");
                    break;

            }
        }

    }


}
