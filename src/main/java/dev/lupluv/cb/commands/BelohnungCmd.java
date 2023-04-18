package dev.lupluv.cb.commands;

import dev.lupluv.cb.annotations.RegisterCommand;
import dev.lupluv.cb.belohnung.FileMangerB;
import dev.lupluv.cb.belohnung.FileMangerC;
import dev.lupluv.cb.economy.Economy;
import dev.lupluv.cb.utils.FileManager;
import dev.lupluv.cb.utils.Item;
import dev.lupluv.cb.utils.Strings;
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
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.permissions.PermissionDefault;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

@RegisterCommand(name = "belohnung", description = "Öffnet das Belohnungsmenü", permissionDefault = PermissionDefault.TRUE)
public class BelohnungCmd implements CommandExecutor, Listener {

    public Inventory inv;
    public String invname = "§6§lBelohnung";

    public static long millis;



    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(!(sender instanceof Player)) return true;
        Player player = (Player) sender;
        if(args.length == 0){

            createInv(player);

        }else{
            player.sendMessage(Strings.prefix + "Benutzung: /belohnung");
        }

        return false;
    }

    public void createInv(Player player){

        inv = Bukkit.createInventory(null, 9*3, invname);

        ItemStack goldblock = new ItemStack(Material.GOLD_BLOCK);
        ItemMeta goldblockmeta = goldblock.getItemMeta();
        goldblockmeta.setDisplayName("§c§lTages Belohnung");
        ArrayList<String> lore1 = new ArrayList<>();
        lore1.add("§7Hole dir deine Tagesbelohnung");
        goldblockmeta.setLore(lore1);
        goldblock.setItemMeta(goldblockmeta);

        ItemStack diamond = new ItemStack(Material.DIAMOND_BLOCK);
        ItemMeta diamondmeta = diamond.getItemMeta();
        diamondmeta.setDisplayName("§c§lWochen Belohnung");
        ArrayList<String> lore2 = new ArrayList<>();
        lore2.add("§7Hole dir deine Wochenbelohnung");
        diamondmeta.setLore(lore2);
        diamond.setItemMeta(diamondmeta);

        inv.setItem(12, goldblock);
        inv.setItem(14, diamond);

        Item item = new Item(Material.GRAY_STAINED_GLASS_PANE);
        item.setDisplayName(" ");
        for(int i = 0; i < inv.getSize(); i++){
            if(inv.getItem(i) == null || inv.getItem(i).getType() == Material.AIR){
                inv.setItem(i, item.build());
            }
        }

        player.openInventory(inv);

    }

    @EventHandler
    public void onClick(InventoryClickEvent e){

        if(!(e.getWhoClicked() instanceof Player)) return;
        if(e.getCurrentItem() == null) return;
        Player pl = (Player) e.getWhoClicked();

        if(e.getView().getTitle().equals(invname)) {
            e.setCancelled(true);
            switch (e.getCurrentItem().getItemMeta().getDisplayName()){

                case "§c§lTages Belohnung":

                    //TAGESBELOHNUNG BEKOMMEN--------------------------------------------------

                   // pl.sendMessage(Strings.prefix + "Funktion in bearbeitung!");

                    long currentTime = System.currentTimeMillis();



                    if(FileMangerB.yamlConfiguration.get(pl.getName()) != null){
                        if(FileMangerB.yamlConfiguration.getLong(pl.getName()) + 86400000 <= currentTime){

                            FileMangerB.yamlConfiguration.set(pl.getName(), currentTime);
                            FileMangerB.loadFile();
                            sendAllDailyReward(pl);
                            pl.sendMessage(Strings.prefix + "Du hast deine Tägliche Belohnung abgeholt§8,§7 250 Coins§8!");
                            Economy.depositPlayer(pl.getUniqueId(), 250);
                            FileMangerB.loadFile();
                        }else{
                            pl.sendMessage(Strings.prefix + "§cDu hast deine Tägliche Belohnung schon abgeholt!");

                        }

                    }else{
                        FileMangerB.yamlConfiguration.set(pl.getName(), currentTime);
                        FileMangerB.loadFile();
                        sendAllDailyReward(pl);
                        pl.sendMessage(Strings.prefix + "Du hast deine Tägliche Belohnung abgeholt§8,§7 250 Coins§8!");
                        Economy.depositPlayer(pl.getUniqueId(), 250);
                        FileMangerB.loadFile();
                    }

                    break;
                case "§c§lWochen Belohnung":

                    //WOCHENBELOHNUNG BEKOMMEN




                    long currentTime2 = System.currentTimeMillis();



                    if(FileMangerC.yamlConfiguration.get(pl.getName()) != null){
                        if(FileMangerC.yamlConfiguration.getLong(pl.getName()) + 604800000 <= currentTime2){

                            FileMangerC.yamlConfiguration.set(pl.getName(), currentTime2);
                            FileMangerC.loadFile();
                            sendAllWeeklyReward(pl);
                            pl.sendMessage(Strings.prefix + "Du hast deine Wöchentliche Belohnung abgeholt§8,§7 500 Coins§8!");
                            Economy.depositPlayer(pl.getUniqueId(), 500);
                            FileMangerC.loadFile();
                        }else{
                            pl.sendMessage(Strings.prefix + "§cDu hast deine Wöchentliche Belohnung schon abgeholt!");

                        }

                    }else{
                        FileMangerC.yamlConfiguration.set(pl.getName(), currentTime2);
                        FileMangerC.loadFile();
                        sendAllWeeklyReward(pl);
                        pl.sendMessage(Strings.prefix + "Du hast deine Wöchentliche Belohnung abgeholt§8,§7 500 Coins§8!");
                        Economy.depositPlayer(pl.getUniqueId(), 500);
                        FileMangerC.loadFile();
                    }




                    break;

            }
            switch (e.getCurrentItem().getType()){

                case BLACK_STAINED_GLASS:
                    pl.sendMessage(" ");
                    break;

            }
        }

    }

    public void getTagesBelohnung(Player player){
//500


    }

    public void getWochenBelohnung(Player player){
//3000
        player.sendMessage(Strings.prefix + "Diese Fuktion ist noch nicht fertig!");

    }

    public void sendAllDailyReward(Player player) {

        Bukkit.getOnlinePlayers().forEach(all ->{
            all.sendMessage(" ");
            all.sendMessage(Strings.prefix + "Der Spieler §a" + player.getName() + " §7hat gerade seine Tägliche Belohnung abgeholt§8! (§7/belohnung§8)");
            all.sendMessage(" ");
        });

    }
    public void sendAllWeeklyReward(Player player) {

        Bukkit.getOnlinePlayers().forEach(all ->{
            all.sendMessage(" ");
            all.sendMessage(Strings.prefix + "Der Spieler §a" + player.getName() + " §7hat gerade seine Wöchentliche Belohnung abgeholt§8! (§7/belohnung§8)");
            all.sendMessage(" ");
        });

    }


}
