package dev.lupluv.cb.commands;

import dev.lupluv.cb.annotations.RegisterCommand;
import dev.lupluv.cb.utils.Item;
import dev.lupluv.cb.utils.Lore;
import dev.lupluv.cb.utils.Strings;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Statistic;
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

@RegisterCommand(name = "statistiken", aliases = {"stats", "statistic", "stat"}, permissionDefault = PermissionDefault.TRUE)
public class StatistikenCmd implements CommandExecutor, Listener {



    public static Inventory inv;
    public static String invname = "§6§lStatistiken";

    public static void createAndOpenInventory(Player player){

        inv = Bukkit.createInventory(null, 9*5, invname);

        Item sheep = new Item(Material.SHEEP_SPAWN_EGG);
        sheep.setDisplayName("§6§lGetötete Lebewesen");
        try{
            int killed_entity = player.getStatistic(Statistic.MOB_KILLS) + player.getStatistic(Statistic.PLAYER_KILLS);

            sheep.setLore(Lore.create("§a" + killed_entity + " §7Lebewesen"));
        }catch (Exception e){
            sheep.setLore(Lore.create("§7Anzahl§8: §a"));
        }



        Item grass_block = new Item(Material.GRASS_BLOCK);
        grass_block.setDisplayName("§6§lBlöcke gelaufen");
        try{
            grass_block.setLore(Lore.create("§a" + player.getStatistic(Statistic.WALK_ONE_CM)  + " §7Blöcke"));

        }catch (Exception e){
            grass_block.setLore(Lore.create("§7Anzahl§8: §a"));
        }


        Item Stonesword = new Item(Material.STONE_SWORD);
        Stonesword.setDisplayName("§6§lGetötete Spieler");

        try{
            Stonesword.setLore(Lore.create("§a" + player.getStatistic(Statistic.PLAYER_KILLS) + " §7Spieler"));
        }catch (Exception e){
            Stonesword.setLore(Lore.create("§7Anzahl§8: §a"));
        }




        Item TotemOfUndiyng = new Item(Material.TOTEM_OF_UNDYING);
        TotemOfUndiyng.setDisplayName("§6§lTode");
        try {
            TotemOfUndiyng.setLore(Lore.create("§a" + player.getStatistic(Statistic.DEATHS) + " §7Tode"));

        }catch (Exception e){
            TotemOfUndiyng.setLore(Lore.create("§7Anzahl§8: §a"));
        }


        Item elytra = new Item(Material.ELYTRA);
        elytra.setDisplayName("§6§lGeflogene Blöcke");
        try{
            elytra.setLore(Lore.create("§a" + player.getStatistic(Statistic.FLY_ONE_CM) + " §7Blöcke"));
        }catch (Exception e){
            elytra.setLore(Lore.create("§7Anzahl§8: §a"));
        }



        Item enderperl = new Item(Material.ENDER_PEARL);
        enderperl.setDisplayName("§6§lJumps");
        try{
            enderperl.setLore(Lore.create("§a" + player.getStatistic(Statistic.JUMP) + " §7Jumps"));
        }catch (Exception e){
            enderperl.setLore(Lore.create("§7Anzahl§8: §a"));
        }



        Item clock = new Item(Material.CLOCK);
        clock.setDisplayName("§6§lSpielzeit");
        try {
            clock.setLore(Lore.create("§a" + player.getStatistic(Statistic.TOTAL_WORLD_TIME)/20/60 + " §7Minuten"));

        }catch (Exception e){
            clock.setLore(Lore.create("§7Anzahl§8: §a"));
        }



        Item mobs = new Item(Material.ZOMBIE_HEAD);
        mobs.setDisplayName("§6§lMobs getötet");
        try{
            mobs.setLore(Lore.create("§a" + player.getStatistic(Statistic.MOB_KILLS) + " §7Mobs"));
        }catch (Exception e){
           mobs.setLore(Lore.create("§7Anzahl§): §a"));
        }



        inv.setItem(31, mobs.build());
        inv.setItem(24, clock.build());
        inv.setItem(22, enderperl.build());
        inv.setItem(20, elytra.build());
        inv.setItem(16, TotemOfUndiyng.build());
        inv.setItem(14, Stonesword.build());
        inv.setItem(12, grass_block.build());
        inv.setItem(10, sheep.build());

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
        }

    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(!(sender instanceof Player player)) return true;
        if(!(args.length == 0)) return true;

        try{
            createAndOpenInventory(player);
        }catch (Exception e){
            player.sendMessage(Strings.prefix + "§cFehler beim Bilden der GUI");
        }

        return false;
    }
}