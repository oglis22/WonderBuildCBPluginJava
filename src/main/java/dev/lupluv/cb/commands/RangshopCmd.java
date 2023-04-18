package dev.lupluv.cb.commands;


import dev.lupluv.cb.Citybuild;
import dev.lupluv.cb.annotations.RegisterCommand;
import dev.lupluv.cb.economy.Economy;
import dev.lupluv.cb.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.permissions.PermissionDefault;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.logging.Level;


@RegisterCommand(name = "rangshop", permissionDefault = PermissionDefault.TRUE)
public class RangshopCmd implements CommandExecutor, Listener {

    public static Inventory inv;
    public static String invname = "§6§lRangshop";

    public static void createInv(Player player){

        inv = Bukkit.createInventory(null, 9*3, invname);

        ItemStack nethscrape = new ItemStack(Material.NETHERITE_SCRAP);
        ItemMeta nethscrapemeta = nethscrape.getItemMeta();
        nethscrapemeta.setDisplayName("§6§lPremium");
        ArrayList<String> lore1 = new ArrayList<>();
        lore1.add(" ");
        lore1.add("§7Der §6Premium §7Rang kostet §a100.000 §7Coins");
        lore1.add("§7§8(§7Rechtsclick zum Kaufen§8)");
        lore1.add(" ");
        lore1.add("§7Um zu sehen was der Rang kann benutzte §a/ranginfo");
        lore1.add(" ");
        nethscrapemeta.setLore(lore1);
        nethscrape.setItemMeta(nethscrapemeta);

        ItemStack amtcluster = new ItemStack(Material.AMETHYST_CLUSTER);
        ItemMeta amtclustermeta = amtcluster.getItemMeta();
        amtclustermeta.setDisplayName("§9§lTitan");
        amtclustermeta.setLore(Lore.create(
                " ",
                "§7Der §9Titan §7Rang kostet §a600.000 §7Coins",
                "§7§8(§7Rechtsclick zum Kaufen§8)",
                " ",
                "§7Um zu sehen was der Rang kann benutzte §a/ranginfo",
                " "
        ));
        amtcluster.setItemMeta(amtclustermeta);

        ItemStack emerlad = new ItemStack(Material.EMERALD);
        ItemMeta emerladmeta = emerlad.getItemMeta();
        emerladmeta.setDisplayName("§2§lPlatin");
        emerladmeta.setLore(Lore.create(
                " ",
                "§7Der §2Platin §7Rang kostet §a225.000 §7Coins",
                "§7§8(§7Rechtsclick zum Kaufen§8)",
                " ",
                "§7Um zu sehen was der Rang kann benutzte §a/ranginfo",
                " "
//8,13,16
        ));
        emerlad.setItemMeta(emerladmeta);

        inv.setItem(10, nethscrape);
        inv.setItem(13, emerlad);
        inv.setItem(16, amtcluster);

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
        Player player = (Player) sender;
        if(args.length == 0){

            createInv(player);

        }else{
            player.sendMessage(Strings.prefix + "Benutzung /ranshop");
        }

        return false;
    }

    public static void giveRank(Player player, String group, double money){
        if(Economy.withdrawPlayer(player.getUniqueId(), money).transactionSuccess()) {
            String cmd = "hopecommander shop_manager purchase " + player.getUniqueId() + " " + group;
            Citybuild.getPlugin().getLogger().log(Level.INFO, cmd);
            System.out.println(cmd);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd);
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){

        if(!(e.getWhoClicked() instanceof Player player)) return;
        if(e.getCurrentItem() == null) return;

        if(e.getView().getTitle().equals(invname)) {
            e.setCancelled(true);
            if(!e.isRightClick()) return;
            Material mat = e.getCurrentItem().getType();
            if(mat == Material.NETHERITE_SCRAP){
                if (Economy.getBalance(player.getUniqueId()) >= 100000) {
                    giveRank(player, "premium", 100000);
                } else {
                    player.sendMessage(Strings.prefix + "Du hast leider nicht genügend Geld um dir diesen Rang zu kaufen");
                    player.closeInventory();
                }
            }else if(mat == Material.AMETHYST_CLUSTER){
                if (Economy.getBalance(player.getUniqueId()) >= 600000) {
                    giveRank(player, "titan", 600000);
                } else {
                    player.sendMessage(Strings.prefix + "Du hast leider nicht genügend Geld um dir diesen Rang zu kaufen");
                    player.closeInventory();
                }
            }else if(mat == Material.EMERALD){
                if (Economy.getBalance(player.getUniqueId()) >= 225000) {
                    giveRank(player, "platin", 225000);
                } else {
                    player.sendMessage(Strings.prefix + "Du hast leider nicht genügend Geld um dir diesen Rang zu kaufen");
                    player.closeInventory();
                }
            }
        }
    }

}