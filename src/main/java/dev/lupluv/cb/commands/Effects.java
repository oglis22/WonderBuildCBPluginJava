package dev.lupluv.cb.commands;

import dev.lupluv.cb.annotations.RegisterCommand;
import dev.lupluv.cb.particels.Particel;
import dev.lupluv.cb.utils.Item;
import dev.lupluv.cb.utils.Lore;
import dev.lupluv.cb.utils.Strings;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.data.type.Bed;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.permissions.PermissionDefault;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;


@RegisterCommand(name = "effects", aliases = {"effekt", "effect"}, description = "Opens the effects menu", permissionDefault = PermissionDefault.TRUE)
public class Effects implements CommandExecutor, Listener {

    public static Inventory inv;
    public static String invname = "§6§lEffekte";
    public static String lore = "§7Rechtsklick um den Effekt auszuwählen.";
    public static String lore2 = "§cDu hast diesen Effekt nicht!";

    public static void createAndOpenInventory(Player player){

        inv = Bukkit.createInventory(null, 9*5, invname);

        Item mangrove = new Item(Material.MANGROVE_PLANKS);
        mangrove.setDisplayName("§c§lHerz Effekt");
        if(!player.hasPermission("cb.effekt.herzen")){
            mangrove.setLore(Lore.create(lore2));
        }else{

            if(Particel.haseffect1.contains(player)){
                mangrove.setLore(Lore.create(lore, " ", "§aAusgewählt"));
            }else{
                mangrove.setLore(Lore.create(lore, " ", "§cnicht Ausgewählt"));
            }

        }
        inv.setItem(11, mangrove.build());

        Item oak = new Item(Material.OAK_PLANKS);
        oak.setDisplayName("§a§lNoten Effekt");
        if(!player.hasPermission("cb.effekt.noten")){
            oak.setLore(Lore.create(lore2));
        }else{
            if(Particel.haseffect2.contains(player)){
                oak.setLore(Lore.create(lore, " ", "§aAusgewählt"));
            }else{
                oak.setLore(Lore.create(lore, " ", "§cnicht Ausgewählt"));
            }
        }
        inv.setItem(20, oak.build());



        Item jungel = new Item(Material.JUNGLE_PLANKS);
        jungel.setDisplayName("§2§lVilliager Effekt");
        if(!player.hasPermission("cb.effekt.villigar.happy")){
            jungel.setLore(Lore.create(lore2));
        }else{
            if(Particel.haseffect3.contains(player)){
                jungel.setLore(Lore.create(lore, " ", "§aAusgewählt"));
            }else{
                jungel.setLore(Lore.create(lore, " ", "§cnicht Ausgewählt"));
            }
        }
        inv.setItem(29, jungel.build());





        Item acacia = new Item(Material.ACACIA_PLANKS);
        acacia.setDisplayName("§8§lRauch Effekt");
        if(!player.hasPermission("cb.effekt.rauch")){
            acacia.setLore(Lore.create(lore2));
        }else{
            if(Particel.haseffect4.contains(player)){
                acacia.setLore(Lore.create(lore, " ", "§aAusgewählt"));
            }else{
                acacia.setLore(Lore.create(lore, " ", "§cnicht Ausgewählt"));
            }
        }
        inv.setItem(13, acacia.build());


        Item darkoak = new Item(Material.DARK_OAK_PLANKS);
        darkoak.setDisplayName("§4§lLava Effekt");
        if(!player.hasPermission("cb.effekt.lava")){
            darkoak.setLore(Lore.create(lore2));
        }else{
            if(Particel.haseffect5.contains(player)){
                darkoak.setLore(Lore.create(lore, " ", "§aAusgewählt"));
            }else{
                darkoak.setLore(Lore.create(lore, " ", "§cnicht Ausgewählt"));
            }
        }
        inv.setItem(22, darkoak.build());







        Item warpedplank = new Item(Material.WARPED_PLANKS);
        warpedplank.setDisplayName("§6§lSchaden effekt");
        if(!player.hasPermission("cb.effekt.schaden")){
            warpedplank.setLore(Lore.create(lore2));
        }else{
            if(Particel.haseffect6.contains(player)){
                warpedplank.setLore(Lore.create(lore, " ", "§aAusgewählt"));
            }else{
                warpedplank.setLore(Lore.create(lore, " ", "§cnicht Ausgewählt"));
            }
        }
        inv.setItem(31, warpedplank.build());



        Item crimson = new Item(Material.CRIMSON_PLANKS);
        crimson.setDisplayName("§9§lRedstone Effekt");
        if(!player.hasPermission("cb.effekt.redstone")){
            crimson.setLore(Lore.create(lore2));
        }else{
            if(Particel.haseffect7.contains(player)){
                crimson.setLore(Lore.create(lore, " ", "§aAusgewählt"));
            }else{
                crimson.setLore(Lore.create(lore, " ", "§cnicht Ausgewählt"));
            }
        }
        inv.setItem(15, crimson.build());


        Item spruce = new Item(Material.SPRUCE_PLANKS);
        spruce.setDisplayName("§5§lDrachen Effekt");
        if(!player.hasPermission("cb.effekt.drachen")){
            spruce.setLore(Lore.create(lore2));
        }else{
            if(Particel.haseffect8.contains(player)){
                spruce.setLore(Lore.create(lore, " ", "§aAusgewählt"));
            }else{
                spruce.setLore(Lore.create(lore, " ", "§cnicht Ausgewählt"));
            }
        }
        inv.setItem(24, spruce.build());


        Item birch = new Item(Material.BIRCH_PLANKS);
        birch.setDisplayName("§3§lMonster Effekt");
        if(!player.hasPermission("cb.effekt.monster")){
            birch.setLore(Lore.create(lore2));
        }else{
            if(Particel.haseffect9.contains(player)){
                birch.setLore(Lore.create(lore, " ", "§aAusgewählt"));
            }else{
                birch.setLore(Lore.create(lore, " ", "§cnicht Ausgewählt"));
            }
        }

        Item barrier = new Item(Material.BARRIER);
        barrier.setDisplayName("§c§lZurücksetzten");
        inv.setItem(40, barrier.build());

        inv.setItem(33, birch.build());

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

        if(!(sender instanceof Player player)) return true;

        if(args.length == 0){

        createAndOpenInventory(player);
        }else{
            player.sendMessage(Strings.prefix + "§aBenutzung /effekt");
        }

        return false;
    }


    @EventHandler
    public void onClick(InventoryClickEvent e){

        if(!(e.getWhoClicked() instanceof Player)) return;
        if(e.getCurrentItem() == null) return;
        Player pl = (Player) e.getWhoClicked();

        if(e.getView().getTitle().equals(invname)) {
            e.setCancelled(true);
            switch (e.getCurrentItem().getItemMeta().getDisplayName()){

         case "§c§lHerz Effekt" -> {

             if(!pl.hasPermission("cb.effekt.herzen")) return;



            if(Particel.has_one_effect.contains(pl)){

                if(Particel.haseffect1.contains(pl)){
                    Particel.haseffect1.remove(pl);
                    Particel.has_one_effect.remove(pl);
                    pl.sendMessage(" ");
                    pl.sendMessage(Strings.prefix + "§cDer Effekt wurde dir erfolgreich entfernt.");
                    pl.sendMessage(" ");
                    pl.closeInventory();
                }else{
                    pl.sendMessage(" ");
                    pl.sendMessage(Strings.prefix + "§cDu hast bereits ein Effekt ausgewählt!");
                    pl.sendMessage(Strings.prefix + "§cDu must den Effekt erst Entfernen!");
                    pl.sendMessage(" ");
                }


            }else{
                Particel.haseffect1.add(pl);
                Particel.has_one_effect.add(pl);
                pl.sendMessage(" ");
                pl.sendMessage(Strings.prefix + "§aDu hast den Effekt erfolgreich ausgewählt!");
                pl.sendMessage(" ");
                pl.closeInventory();
            }

             break;

         }
        case "§a§lNoten Effekt" -> {
            if(!pl.hasPermission("cb.effekt.noten")) return;


            if(Particel.has_one_effect.contains(pl)){

                if(Particel.haseffect2.contains(pl)){
                    Particel.haseffect2.remove(pl);
                    Particel.has_one_effect.remove(pl);
                    pl.sendMessage(" ");
                    pl.sendMessage(Strings.prefix + "§cDer Effekt wurde dir erfolgreich entfernt.");
                    pl.sendMessage(" ");
                    pl.closeInventory();
                }else{
                    pl.sendMessage(" ");
                    pl.sendMessage(Strings.prefix + "§cDu hast bereits ein Effekt ausgewählt!");
                    pl.sendMessage(Strings.prefix + "§cDu must den Effekt erst Entfernen!");
                    pl.sendMessage(" ");
                }


            }else{
                Particel.haseffect2.add(pl);
                Particel.has_one_effect.add(pl);
                pl.sendMessage(" ");
                pl.sendMessage(Strings.prefix + "§aDu hast den Effekt erfolgreich ausgewählt!");
                pl.sendMessage(" ");
                pl.closeInventory();
            }



             break;
        }
        case "§2§lVilliager Effekt" -> {
            if(!pl.hasPermission("cb.effekt.villigar.happy")) return;

            if(Particel.has_one_effect.contains(pl)){

                if(Particel.haseffect3.contains(pl)){
                    Particel.haseffect3.remove(pl);
                    Particel.has_one_effect.remove(pl);
                    pl.sendMessage(" ");
                    pl.sendMessage(Strings.prefix + "§cDer Effekt wurde dir erfolgreich entfernt.");
                    pl.sendMessage(" ");
                    pl.closeInventory();
                }else{
                    pl.sendMessage(" ");
                    pl.sendMessage(Strings.prefix + "§cDu hast bereits ein Effekt ausgewählt!");
                    pl.sendMessage(Strings.prefix + "§cDu must den Effekt erst Entfernen!");
                    pl.sendMessage(" ");
                }


            }else{
                Particel.haseffect3.add(pl);
                Particel.has_one_effect.add(pl);
                pl.sendMessage(" ");
                pl.sendMessage(Strings.prefix + "§aDu hast den Effekt erfolgreich ausgewählt!");
                pl.sendMessage(" ");
                pl.closeInventory();
            }




             break;
        }

        case "§8§lRauch Effekt" -> {

            if(!pl.hasPermission("cb.effekt.rauch")) return;
            if(Particel.has_one_effect.contains(pl)){

                if(Particel.haseffect4.contains(pl)){
                    Particel.haseffect4.remove(pl);
                    Particel.has_one_effect.remove(pl);
                    pl.sendMessage(" ");
                    pl.sendMessage(Strings.prefix + "§cDer Effekt wurde dir erfolgreich entfernt.");
                    pl.sendMessage(" ");
                    pl.closeInventory();
                }else{
                    pl.sendMessage(" ");
                    pl.sendMessage(Strings.prefix + "§cDu hast bereits ein Effekt ausgewählt!");
                    pl.sendMessage(Strings.prefix + "§cDu must den Effekt erst Entfernen!");
                    pl.sendMessage(" ");
                }


            }else{
                Particel.haseffect4.add(pl);
                Particel.has_one_effect.add(pl);
                pl.sendMessage(" ");
                pl.sendMessage(Strings.prefix + "§aDu hast den Effekt erfolgreich ausgewählt!");
                pl.sendMessage(" ");
                pl.closeInventory();
            }


             break;
        }
        case "§4§lLava Effekt" -> {

            if(!pl.hasPermission("cb.effekt.lava")) return;


            if(Particel.has_one_effect.contains(pl)){

                if(Particel.haseffect5.contains(pl)){
                    Particel.haseffect5.remove(pl);
                    Particel.has_one_effect.remove(pl);
                    pl.sendMessage(" ");
                    pl.sendMessage(Strings.prefix + "§cDer Effekt wurde dir erfolgreich entfernt.");
                    pl.sendMessage(" ");
                    pl.closeInventory();
                }else{
                    pl.sendMessage(" ");
                    pl.sendMessage(Strings.prefix + "§cDu hast bereits ein Effekt ausgewählt!");
                    pl.sendMessage(Strings.prefix + "§cDu must den Effekt erst Entfernen!");
                    pl.sendMessage(" ");
                }


            }else{
                Particel.haseffect5.add(pl);
                Particel.has_one_effect.add(pl);
                pl.sendMessage(" ");
                pl.sendMessage(Strings.prefix + "§aDu hast den Effekt erfolgreich ausgewählt!");
                pl.sendMessage(" ");
                pl.closeInventory();
            }




             break;
        }
        case "§6§lSchaden effekt" -> {

            if(!pl.hasPermission("cb.effekt.schaden")) return;

            if(Particel.has_one_effect.contains(pl)){

                if(Particel.haseffect6.contains(pl)){
                    Particel.haseffect6.remove(pl);
                    Particel.has_one_effect.remove(pl);
                    pl.sendMessage(" ");
                    pl.sendMessage(Strings.prefix + "§cDer Effekt wurde dir erfolgreich entfernt.");
                    pl.sendMessage(" ");
                    pl.closeInventory();
                }else{
                    pl.sendMessage(" ");
                    pl.sendMessage(Strings.prefix + "§cDu hast bereits ein Effekt ausgewählt!");
                    pl.sendMessage(Strings.prefix + "§cDu must den Effekt erst Entfernen!");
                    pl.sendMessage(" ");
                }


            }else{
                Particel.haseffect6.add(pl);
                Particel.has_one_effect.add(pl);
                pl.sendMessage(" ");
                pl.sendMessage(Strings.prefix + "§aDu hast den Effekt erfolgreich ausgewählt!");
                pl.sendMessage(" ");
                pl.closeInventory();
            }



             break;
        }
        case "§9§lRedstone Effekt" -> {

            if(!pl.hasPermission("cb.effekt.redstone")) return;


            if(Particel.has_one_effect.contains(pl)){

                if(Particel.haseffect7.contains(pl)){
                    Particel.haseffect7.remove(pl);
                    Particel.has_one_effect.remove(pl);
                    pl.sendMessage(" ");
                    pl.sendMessage(Strings.prefix + "§cDer Effekt wurde dir erfolgreich entfernt.");
                    pl.sendMessage(" ");
                    pl.closeInventory();
                }else{
                    pl.sendMessage(" ");
                    pl.sendMessage(Strings.prefix + "§cDu hast bereits ein Effekt ausgewählt!");
                    pl.sendMessage(Strings.prefix + "§cDu must den Effekt erst Entfernen!");
                    pl.sendMessage(" ");
                }


            }else{
                Particel.haseffect7.add(pl);
                Particel.has_one_effect.add(pl);
                pl.sendMessage(" ");
                pl.sendMessage(Strings.prefix + "§aDu hast den Effekt erfolgreich ausgewählt!");
                pl.sendMessage(" ");
                pl.closeInventory();
            }



             break;
        }
        case "§5§lDrachen Effekt" -> {

            if(!pl.hasPermission("cb.effekt.drachen")) return;

            if(Particel.has_one_effect.contains(pl)){

                if(Particel.haseffect8.contains(pl)){
                    Particel.haseffect8.remove(pl);
                    Particel.has_one_effect.remove(pl);
                    pl.sendMessage(" ");
                    pl.sendMessage(Strings.prefix + "§cDer Effekt wurde dir erfolgreich entfernt.");
                    pl.sendMessage(" ");
                    pl.closeInventory();
                }else{
                    pl.sendMessage(" ");
                    pl.sendMessage(Strings.prefix + "§cDu hast bereits ein Effekt ausgewählt!");
                    pl.sendMessage(Strings.prefix + "§cDu must den Effekt erst Entfernen!");
                    pl.sendMessage(" ");
                }


            }else{
                Particel.haseffect8.add(pl);
                Particel.has_one_effect.add(pl);
                pl.sendMessage(" ");
                pl.sendMessage(Strings.prefix + "§aDu hast den Effekt erfolgreich ausgewählt!");
                pl.sendMessage(" ");
                pl.closeInventory();
            }



             break;
        }
        case "§3§lMonster Effekt" -> {

            if(!pl.hasPermission("cb.effekt.monster")) return;
            if(Particel.has_one_effect.contains(pl)){

                if(Particel.haseffect9.contains(pl)){
                    Particel.haseffect9.remove(pl);
                    Particel.has_one_effect.remove(pl);
                    pl.sendMessage(" ");
                    pl.sendMessage(Strings.prefix + "§cDer Effekt wurde dir erfolgreich entfernt.");
                    pl.sendMessage(" ");
                    pl.closeInventory();
                }else{
                    pl.sendMessage(" ");
                    pl.sendMessage(Strings.prefix + "§cDu hast bereits ein Effekt ausgewählt!");
                    pl.sendMessage(Strings.prefix + "§cDu must den Effekt erst Entfernen!");
                    pl.sendMessage(" ");
                }


            }else{
                Particel.haseffect9.add(pl);
                Particel.has_one_effect.add(pl);
                pl.sendMessage(" ");
                pl.sendMessage(Strings.prefix + "§aDu hast den Effekt erfolgreich ausgewählt!");
                pl.sendMessage(" ");
                pl.closeInventory();
            }



             break;
        }
        case "§c§lZurücksetzten" -> {

             Particel.has_one_effect.remove(pl);
             Particel.haseffect1.remove(pl);
            Particel.haseffect2.remove(pl);
            Particel.haseffect3.remove(pl);
            Particel.haseffect4.remove(pl);
            Particel.haseffect5.remove(pl);
            Particel.haseffect6.remove(pl);
            Particel.haseffect7.remove(pl);
            Particel.haseffect8.remove(pl);
            Particel.haseffect9.remove(pl);


            pl.sendMessage(" ");
            pl.sendMessage(Strings.prefix + "§cAlle deine Effekte wurde zurückgesetzt!");
            pl.sendMessage(" ");

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
