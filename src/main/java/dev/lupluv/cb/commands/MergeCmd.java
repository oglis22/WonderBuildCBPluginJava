package dev.lupluv.cb.commands;

import dev.lupluv.cb.annotations.RegisterCommand;
import dev.lupluv.cb.economy.Economy;
import dev.lupluv.cb.utils.Item;
import dev.lupluv.cb.utils.Lore;
import dev.lupluv.cb.utils.Strings;
import dev.lupluv.cb.utils.Util;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.PermissionDefault;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.ArrayList;

@RegisterCommand(name = "merge", permissionDefault = PermissionDefault.TRUE)
public class MergeCmd implements CommandExecutor, Listener {

    public static Inventory inv;
    public static String invname = "§6§lMerge";

    public static Inventory confirm_inv;
    public static String confirm_invname = "§6§lConfirm";

    public static final String home_skull_value = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3Rl" +
            "eHR1cmUvNmQwNjM2ZmE3YjdhODEyYTFjNzYyMWUyMzk5ZWQwMzhkODViM2VhYjAzY2E4M2MxOTliNzcxYWY3MGUyYjhjMyJ9fX0=";

    public static ArrayList<Player> ingui = new ArrayList<>();

    public void createAndOpenInventory(Player player){

        inv = Bukkit.createInventory(null, 9*1, invname);

        Item greenwool = new Item(Material.GREEN_WOOL);
        greenwool.setDisplayName("§aBestätigen");
        inv.setItem(2, greenwool.build());

        //HEAD -> 4
        ItemStack home_skull = Util.createCustomSkull(home_skull_value, "§aMöchtest du das Plot für 7500 Mergen?", Lore.create("§c§lAchtung:", "§cSei sicher das dir die Plots gehören die du mergen willst!", "§cWir erstsatten kein Geld!"));
        inv.setItem(4, home_skull);


        Item redwool = new Item(Material.RED_WOOL);
        redwool.setDisplayName("§cAbrechen");
        inv.setItem(6, redwool.build());


        Item item = new Item(Material.GRAY_STAINED_GLASS_PANE);
        item.setDisplayName(" ");
        for(int i = 0; i < inv.getSize(); i++){
            if(inv.getItem(i) == null || inv.getItem(i).getType() == Material.AIR){
                inv.setItem(i, item.build());
            }
        }


        player.openInventory(inv);


    }

    public void createAndOpenConfirmInventory(Player player){

        confirm_inv = Bukkit.createInventory(null, 9*1, confirm_invname);

        Item greenwool = new Item(Material.GREEN_WOOL);
        greenwool.setDisplayName("§aBestätigen");
        confirm_inv.setItem(3, greenwool.build());


        Item redwool = new Item(Material.RED_WOOL);
        redwool.setDisplayName("§cAbrechen");
        confirm_inv.setItem(5, redwool.build());


        Item item = new Item(Material.GRAY_STAINED_GLASS_PANE);
        item.setDisplayName(" ");
        for(int i = 0; i < confirm_inv.getSize(); i++){
            if(confirm_inv.getItem(i) == null || confirm_inv.getItem(i).getType() == Material.AIR){
                confirm_inv.setItem(i, item.build());
            }
        }

        player.openInventory(confirm_inv);

    }



    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(!(sender instanceof Player player)) return true;
        if(args.length == 0){
    createAndOpenInventory(player);
        }else{
            player.sendMessage(Strings.prefix + "§cBenutzung /merge");
        }

        return false;
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event){

        String message = event.getMessage();
        String arguments = "";
        String command = "";
        if (!message.contains(" ")) {
            command = message.replace("/", "");
        } else {
            command = message.substring(0, message.indexOf(" ")).replace("/", "");
            arguments = message.substring(message.indexOf(" ") + 1, message.length());
        }
        if (command.equalsIgnoreCase("p") && (arguments.contains("merge"))) {



                createAndOpenInventory(event.getPlayer());
                event.setCancelled(true);



        }

    }


    @EventHandler
    public void onCommand2(PlayerCommandPreprocessEvent event){

        String message = event.getMessage();
        String arguments = "";
        String arguments2 = "";
        String command = "";
        if (!message.contains(" ")) {
            command = message.replace("/", "");
        } else {
            command = message.substring(0, message.indexOf(" ")).replace("/", "");
            arguments = message.substring(message.indexOf(" ") + 1, message.length());
            arguments2 = message.substring(message.indexOf(" ") + 2, message.length());
        }
        if (command.equalsIgnoreCase("p") && (arguments.contains("merge")) && (arguments2.contains("all"))) {

            if(ingui.contains(event.getPlayer())){

                event.setCancelled(false);
                ingui.remove(event.getPlayer());

            }else{
                createAndOpenInventory(event.getPlayer());
                event.setCancelled(true);
            }



        }

    }

    @EventHandler
    public void onClick(InventoryClickEvent e){

        if(!(e.getWhoClicked() instanceof Player)) return;
        if(e.getCurrentItem() == null) return;
        Player pl = (Player) e.getWhoClicked();

        if(e.getView().getTitle().equals(invname)) {
            e.setCancelled(true);
            switch (e.getCurrentItem().getItemMeta().getDisplayName()){

                case "§aBestätigen" -> {
                    pl.closeInventory();
                    createAndOpenConfirmInventory(pl);
                    break;
                }
                case "§cAbrechen" -> {
                    pl.closeInventory();
                    break;
                }



            }
            switch (e.getCurrentItem().getType()){

                case BLACK_STAINED_GLASS:
                    pl.sendMessage(" ");
                    break;
            }
        }

    }



    //---------------------Confirm Inventory---------------------------


    @EventHandler
    public void onClickConfirm(InventoryClickEvent e){

        if(!(e.getWhoClicked() instanceof Player)) return;
        if(e.getCurrentItem() == null) return;
        Player pl = (Player) e.getWhoClicked();

        if(e.getView().getTitle().equals(confirm_invname)) {
            e.setCancelled(true);
            switch (e.getCurrentItem().getItemMeta().getDisplayName()){

                case "§aBestätigen" -> {

                    if(Economy.getBalance(pl.getUniqueId()) >= 7500){

                    Economy.withdrawPlayer(pl.getUniqueId(), 7500);
                    ingui.add(pl);
                    pl.performCommand("p merge all");

                    }else{
                        pl.sendMessage(" ");
                        pl.sendMessage(Strings.prefix + "§cDu hast zu wenig Geld");
                        pl.sendMessage(" ");
                    }

                    break;
                }


                case "§cAbrechen" -> {
                    pl.closeInventory();
                    break;
                }



            }
            switch (e.getCurrentItem().getType()){

                case BLACK_STAINED_GLASS:
                    pl.sendMessage(" ");
                    break;
            }
        }

    }

}
