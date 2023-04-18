package dev.lupluv.cb.utils;

import com.plotsquared.core.PlotSquared;
import com.plotsquared.core.plot.Plot;
import dev.lupluv.cb.voting.VoteAPI;
import dev.lupluv.cb.voting.VoteFetcher;
import dev.lupluv.cb.voting.VoteSite;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class InventoryManager {

    public static final String oak_wood_plus = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubm" +
            "V0L3RleHR1cmUvM2VkZDIwYmU5MzUyMDk0OWU2Y2U3ODlkYzRmNDNlZmFlYjI4YzcxN2VlNmJmY2JiZTAyNzgwMTQyZjcxNiJ9fX0=",
            home_icon = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTJkN2E3NTFlYj" +
                    "A3MWUwOGRiYmM5NWJjNWQ5ZDY2ZTVmNTFkYzY3MTI2NDBhZDJkZmEwM2RlZmJiNjhhN2YzYSJ9fX0=",
            empty_skull = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cm" +
                    "UvNmJkMmY5MzQ3NmFiNjlmYWY1YTUxOWViNTgzMmRiODQxYzg1MjY2ZTAwMWRlNWIyNmU0MjdmNDFkOThlNWM3ZSJ9fX0=",
            dollar_skull = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cm" +
                    "UvYmYxYWM3MTgyZjkxZWZjYzI3NGQ2ZGQzODdlNzVkMjEyMjdmMWFkMGEwNmIwNzI1M2Q0M2NjYzlkZWEyOWZmIn19fQ==",
            the_flash_skull = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR" +
                    "1cmUvMzEzZjg3NjhiM2Y1YjYxNGQ3NTg5ZmIyNzNjNmQzYjliMDIwODMxMGEwMjViYjk5YTI3MWM3M2M0YjM2ZWI3MCJ9fX0=",
            farmer_skull = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cm" +
                    "UvN2E4YWIyMjY3YWU0NDNlODNiMDVlM2E4ZDY3YjhiNzYwYWRmYWFkMzM1YzkwNDczMzhjMGUxNzc0YTY1YzM3MiJ9fX0=",
            lucky_block_skull = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleH" +
                    "R1cmUvMWYyMzY4NWM2OTdiYTU1ZWRkYTQyNWVjZjFmZWM3MmZlZWI3YmI5ODVjNjUwNmIyY2VmMDcwNTE1ZTRlNTQ5MiJ9fX0=",
            crates_skull = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmU" +
                    "vMTNkZDMxYTE3ZDI0NGRmYmYyNTQzNGRmYjZhN2RmMjg5MjVkN2YwYmVlNmY0ZjljYWM2ZjY4Y2U5OGQ5NWY1NyJ9fX0=",
            toy_house_skull = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1" +
                    "cmUvOWY2YmIzYWQ4ZGFmMGMxNDk5YjVlNDZkY2Y0MTc2YzgzNDU0MzU1M2ExYTgxODAwOWU3Njc1ZTg5NjI5NWUxYSJ9fX0=",
            shop_green_skull = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1" +
                    "cmUvZWY2MmZiNTAwODhiNjVlOThmY2M5MWExODE2MjNlZjlhMGQ4MmM4OGZjMGUwZjdmNTdmZWVkZWEzNDg3MWFiNSJ9fX0=";

    public Inventory getWarpsInventory(Player p){
        Inventory inv = Bukkit.createInventory(null, 9*4, "§6§lWarps Admin");



        for(Warp w : Warp.getAllWarps()){
            if(w.exists()){
                Item item = new Item(Material.PAPER);
                item.setDisplayName("§c§l" + w.getName());
                item.setLore(Lore.create("§7Klicke um dich zu teleportieren"));
                inv.addItem(item.build());
            }
        }

        if(p.hasPermission("cb.warp.set")) {
            Item create = new Item(Material.GREEN_WOOL);
            create.setDisplayName("§a§lErstelle einen Warp");
            Item g = new Item(Material.WHITE_STAINED_GLASS_PANE);
            g.setDisplayName(" ");
            inv.setItem(49-9-9, create.build());
            inv.setItem(45-9-9, g.build());
            inv.setItem(46-9-9, g.build());
            inv.setItem(47-9-9, g.build());
            inv.setItem(48-9-9, g.build());
            inv.setItem(50-9-9, g.build());
            inv.setItem(51-9-9, g.build());
            inv.setItem(52-9-9, g.build());
            inv.setItem(53-9-9, g.build());
        }

        for(int i = 0; i < inv.getSize(); i++){
            if(inv.getItem(i) == null || inv.getItem(i).getType() == Material.AIR){
                Item item = new Item(Material.GRAY_STAINED_GLASS_PANE);
                item.setDisplayName(" ");
                inv.setItem(i, item.build());
            }
        }

        return inv;

    }
    public Inventory getHomesInventory(Player p){
        Inventory inv = Bukkit.createInventory(null, 9*6, "§6Deine Homes");

        for(Home h : Home.getHomeList(p.getUniqueId())){
            if(h.exists()){
                Item item = new Item(Material.WHITE_BED);
                item.setDisplayName("§c§l" + h.getName());
                item.setLore(Lore.create("§7Klicke um dich zu teleportieren"));
                inv.addItem(item.build());
            }
        }

        if(p.hasPermission("fb.home.set")) {
            Item create = new Item(Material.GREEN_WOOL);
            create.setDisplayName("§a§lErstelle einen Home");
            Item g = new Item(Material.WHITE_STAINED_GLASS_PANE);
            g.setDisplayName(" ");
            inv.setItem(49, create.build());
            inv.setItem(45, g.build());
            inv.setItem(46, g.build());
            inv.setItem(47, g.build());
            inv.setItem(48, g.build());
            inv.setItem(50, g.build());
            inv.setItem(51, g.build());
            inv.setItem(52, g.build());
            inv.setItem(53, g.build());
        }

        for(int i = 0; i < inv.getSize(); i++){
            if(inv.getItem(i) == null || inv.getItem(i).getType() == Material.AIR){
                Item item = new Item(Material.GRAY_STAINED_GLASS_PANE);
                item.setDisplayName(" ");
                inv.setItem(i, item.build());
            }
        }

        return inv;

    }
    public Inventory getWarpCreationInventory(Player p){
        AnvilInventory inv = (AnvilInventory) Bukkit.createInventory(null, InventoryType.ANVIL, "§a§lErstell einen Warp");

        Item before = new Item(Material.PAPER);
        before.setDisplayName("§c§lGebe einen Namen ein");
        inv.setItem(0, before.build());

        return inv;

    }
    public Inventory getHomeCreationInventory(Player p){
        AnvilInventory inv = (AnvilInventory) Bukkit.createInventory(null, InventoryType.ANVIL, "§a§lErstell einen Home");

        Item before = new Item(Material.WHITE_BED);
        before.setDisplayName("§c§lGebe einen Namen ein");
        inv.setItem(0, before.build());

        return inv;

    }
    public Inventory getPlotsCreateInventory(Player p){
        Inventory inv = Bukkit.createInventory(null, 9*3, "§6§lPlots");

        ItemStack create = Util.createCustomSkull(oak_wood_plus, "§6Plot erstellen", Lore.create("§7Erstelle dir ein Grundstück"));
        inv.setItem(12, create);

        ItemStack home = Util.createCustomSkull(home_icon, "§6Home", Lore.create("§7Gehe zum Home Menü"));
        inv.setItem(14, home);

        for(int i = 0; i < inv.getSize(); i++){
            if(inv.getItem(i) == null || inv.getItem(i).getType() == Material.AIR){
                Item item = new Item(Material.GRAY_STAINED_GLASS_PANE);
                item.setDisplayName(" ");
                inv.setItem(i, item.build());
            }
        }

        return inv;
    }
    public Inventory getPlotsHomeInventory(@NotNull Player p){

        Inventory inv = Bukkit.createInventory(null, 9*3, "§6§lHome");

        // Plots
        int x = 10;
        int y = 1;
        List<Plot> pa = PlotSquared.get().getPlotAreaManager().getPlotAreaByString("cb").getPlots(p.getUniqueId()).stream().toList();
        if(!pa.isEmpty()) {
            for (Plot plot : pa) {
                Item item = new Item(Material.SUNFLOWER);
                item.setDisplayName("§6Plot " + y);
                item.setLore(Lore.create("§7ID §8• §7" + plot.getId().toSeparatedString(":")));
                inv.setItem(x, item.build());
                x++;
                y++;
            }
        }else{
            inv.setItem(13, Util.createCustomSkull(empty_skull, "§cDu hast keine Plots", Lore.create("§7Klicke um eines zu erstellen.")));
        }

        for(int i = 0; i < inv.getSize(); i++){
            if(inv.getItem(i) == null || inv.getItem(i).getType() == Material.AIR){
                Item item = new Item(Material.GRAY_STAINED_GLASS_PANE);
                item.setDisplayName(" ");
                inv.setItem(i, item.build());
            }
        }

        return inv;
    }
    public Inventory getVotingInventory(Player p){
        Inventory inv = Bukkit.createInventory(null, 9*3, "§6§lVote Belohnungen");

        // minecraft-server.eu
        int hasVoted;
        VoteFetcher vf1 = new VoteFetcher(VoteSite.vote1, p.getName());
        if(!vf1.exists()) {
            hasVoted = 0;
        }else{
            vf1.load();
            hasVoted = vf1.getResult();
        }
        Item vote1 = new Item(Material.CHEST);
        if(hasVoted == 0){
            // Has not voted and not claimed
            vote1.setDisplayName("§cVote 1");
            vote1.setLore(Lore.create("§cNoch nicht gevotet.", "§7Klicke um den vote link zu erhalten.", "§7Serverliste: §7minecraft-server.eu"));
        }else if(hasVoted == 1){
            // Has voted but not claimed
            vote1.setDisplayName("§aVote 1");
            vote1.setLore(Lore.create("§aDu hast gevotet.", "§7Klicke um deine Belohnung zu erhalten.", "§7Serverliste: §7minecraft-server.eu"));
        }else if(hasVoted == 2){
            // Has voted and claimed
            vote1.setDisplayName("§eVote 1");
            vote1.setLore(Lore.create("§cNoch nicht gevotet.", "§7Klicke um den vote link zu erhalten.", "§7Serverliste: §7minecraft-server.eu"));
        }
        inv.setItem(13, vote1.build());

        for(int i = 0; i < inv.getSize(); i++){
            if(inv.getItem(i) == null || inv.getItem(i).getType() == Material.AIR){
                Item item = new Item(Material.GRAY_STAINED_GLASS_PANE);
                item.setDisplayName(" ");
                inv.setItem(i, item.build());
            }
        }

        return inv;
    }
    public Inventory getBeautifulWarpMenuInventory(Player p){
        Inventory inv = Bukkit.createInventory(null, 9*4, "§6§lWarps");

        /*
        for(Warp w : Warp.getAllWarps()){
            if(w.exists()){
                Item item = new Item(Material.PAPER);
                item.setDisplayName("§c§l" + w.getName());
                item.setLore(Lore.create("§7Klicke um dich zu teleportieren"));
                inv.addItem(item.build());
            }
        }

         */

        ItemStack bank = Util.createCustomSkull(dollar_skull, "§6§lBank", Lore.create("§7Sichere dein Geld indem du es einzahlst."));
        inv.setItem(10, bank);
        ItemStack runner = Util.createCustomSkull(the_flash_skull, "§6§lLaufbursche", Lore.create("§7Lasse den Laufburschen für dich Farmen."));
        inv.setItem(12, runner);
        ItemStack farming = Util.createCustomSkull(farmer_skull, "§6§lFarmwelten", Lore.create("§7Farme alle Materialien und Erze."));
        inv.setItem(14, farming);
        ItemStack casino = Util.createCustomSkull(lucky_block_skull, "§6§lCasino", Lore.create("§7Verspiele dein Geld und gewinne immense Summen."));
        inv.setItem(16, casino);
        ItemStack crates = Util.createCustomSkull(crates_skull, "§6§lCrates", Lore.create("§7Öffne Truhen und gewinne tolle Preise."));
        inv.setItem(20, crates);
        ItemStack industrial_house = Util.createCustomSkull(toy_house_skull, "§6§lIndustriehaus", Lore.create("§7Keine Ahnung."));
        inv.setItem(22, industrial_house);
        ItemStack shop = Util.createCustomSkull(shop_green_skull, "§6§lItem Shop", Lore.create("§7Kaufe Items und Bau Materialien."));
        inv.setItem(24, shop);

        for(int i = 0; i < inv.getSize(); i++){
            if(inv.getItem(i) == null || inv.getItem(i).getType() == Material.AIR){
                Item item = new Item(Material.GRAY_STAINED_GLASS_PANE);
                item.setDisplayName(" ");
                inv.setItem(i, item.build());
            }
        }

        return inv;
    }
    public Inventory getRandInventory(Player p){
        Inventory inv = Bukkit.createInventory(null, 9*5, "§6§lPlot Ränder");

        Item none = new Item(Material.WHITE_STAINED_GLASS_PANE);
        none.setDisplayName("§c§lKein Rand ausgewählt");

        // Beacon
        Item beacon = new Item(Material.BEACON);
        beacon.setDisplayName("§6§lBeacon Rand");
        beacon.setLore(Lore.create("§7Klicke um diesen Rand zu benutzen."));

        // Command block
        Item command_block = new Item(Material.COMMAND_BLOCK);
        command_block.setDisplayName("§6§lCommand Block Rand");
        command_block.setLore(Lore.create("§7Klicke um diesen Rand zu benutzen."));

        // End portal frame
        Item end_portal_frame = new Item(Material.END_PORTAL_FRAME);
        end_portal_frame.setDisplayName("§6§lEnd Portal Frame Rand");
        end_portal_frame.setLore(Lore.create("§7Klicke um diesen Rand zu benutzen."));

        // Bedrock
        Item bedrock = new Item(Material.BEDROCK);
        bedrock.setDisplayName("§6§lBedrock Rand");
        bedrock.setLore(Lore.create("§7Klicke um diesen Rand zu benutzen."));

        // Enchanting Table

        // Gray Wool

        // Glass

        // Cactus

        // Emerald Block

        // Sculk

        // Dragon Egg

        // Sea Lantern

        // Tnt

        // Glowstone

        return inv;
    }






















}
