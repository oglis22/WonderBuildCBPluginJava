package dev.lupluv.cb.events;

import com.plotsquared.core.PlotSquared;
import com.plotsquared.core.plot.Plot;
import com.plotsquared.core.plot.PlotId;
import dev.lupluv.cb.Citybuild;
import dev.lupluv.cb.casino.Casino;
import dev.lupluv.cb.casino.coinflip.Coinflip;
import dev.lupluv.cb.casino.coinflip.CoinflipUI;
import dev.lupluv.cb.economy.BankHandler;
import dev.lupluv.cb.economy.BankUI;
import dev.lupluv.cb.economy.Economy;
import dev.lupluv.cb.namecolors.NColor;
import dev.lupluv.cb.namecolors.NameColorSelector;
import dev.lupluv.cb.namecolors.NameColorUI;
import dev.lupluv.cb.scoreboard.ScoreboardManager;
import dev.lupluv.cb.shop.Adminshop;
import dev.lupluv.cb.utils.*;
import dev.lupluv.cb.voting.VoteAPI;
import dev.lupluv.cb.voting.VoteFetcher;
import dev.lupluv.cb.voting.VoteSite;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.units.qual.C;

import java.util.function.Consumer;

public class ClickHandler implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(e.getCurrentItem() == null) return;
        if(e.getCurrentItem().getType() == Material.AIR) return;
        Player p = (Player) e.getWhoClicked();
        String title = e.getView().getTitle();
        Inventory inv = e.getClickedInventory();
        ItemStack item = e.getCurrentItem();
        Material mat = item.getType();

        if(title.equalsIgnoreCase("§6§lWarps")){
            e.setCancelled(true);
            String w = "fehler";
            if(item.getItemMeta().getDisplayName().equalsIgnoreCase("§6§lBank")){
                w = "bank";
            }else if(item.getItemMeta().getDisplayName().equalsIgnoreCase("§6§lLaufbursche")){
                w = "laufbursche";
            }else if(item.getItemMeta().getDisplayName().equalsIgnoreCase("§6§lFarmwelten")){
                w = "farmwelt";
            }else if(item.getItemMeta().getDisplayName().equalsIgnoreCase("§6§lCasino")){
                w = "casino";
            }else if(item.getItemMeta().getDisplayName().equalsIgnoreCase("§6§lCrates")){
                w = "crates";
            }else if(item.getItemMeta().getDisplayName().equalsIgnoreCase("§6§lIndustriehaus")){
                w = "industriehaus";
            }else if(item.getItemMeta().getDisplayName().equalsIgnoreCase("§6§lItem Shop")){
                w = "item-shop";
            }
            p.performCommand("warp " + w);
        }else if(title.equalsIgnoreCase("§6§lWarps Admin")){
            if(mat == Material.PAPER){
                String name = item.getItemMeta().getDisplayName().replace("§c§l", "");
                Warp w = new Warp(name);
                if(w.exists()){
                    w.load();
                    w.teleport(p);
                }else{
                    p.sendMessage(Strings.prefix + "§cDer Warp '" + name + "' existiert nicht.");
                }
            }
        }else if(title.equalsIgnoreCase("§6Deine Homes")){
            e.setCancelled(true);
            if(mat == Material.WHITE_BED){
                Home h = new Home(p.getUniqueId(), item.getItemMeta().getDisplayName().replace("§c§l", ""));
                if(h.exists()){
                    p.performCommand("home " + h.getName());
                }
            }else if(mat == Material.GREEN_WOOL){
                p.openInventory(Citybuild.getInventoryManager().getHomeCreationInventory(p));
            }
        }else if(title.equalsIgnoreCase("§6§lPlots")){
            e.setCancelled(true);
            if(item.getItemMeta().getDisplayName().equalsIgnoreCase("§6Plot erstellen")){
                p.closeInventory();
                p.performCommand("plot auto");
            }else if(item.getItemMeta().getDisplayName().equalsIgnoreCase("§6Home")){
                p.openInventory(Citybuild.getInventoryManager().getPlotsHomeInventory(p));
            }
        }else if(title.equalsIgnoreCase("§6§lHome")){
            e.setCancelled(true);
            if(mat == Material.SUNFLOWER){
                String[] parts = item.getLore().get(0).replace("§7ID §8• §7", "").split(":");
                p.performCommand("plot visit " + parts[0] + "," + parts[1]);
            }
        }else if(title.equalsIgnoreCase("§6§lVote Belohnungen")){
            e.setCancelled(true);
            if(item.getItemMeta().getDisplayName().contains("Vote 1")){
                int hasVoted;
                VoteFetcher vf1 = new VoteFetcher(VoteSite.vote1, p.getName());
                if(!vf1.exists()) {
                    hasVoted = 0;
                }else{
                    vf1.load();
                    hasVoted = vf1.getResult();
                }
                if(hasVoted == 0) {
                    // Has not voted and not claimed
                    p.closeInventory();
                    p.sendMessage(Strings.prefix + "§7Vote jetzt unter: §6https://vote.wonderbuild.net");
                }else if(hasVoted == 1){
                    // Has voted but not claimed
                    vf1.setClaimed(true);
                    vf1.update();
                    p.closeInventory();
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "crate giveKey vote " + p.getName() + " 1");
                    p.sendMessage(Strings.prefix + "§7Du hast deine §6Belohnung §7erhalten.");
                }else if(hasVoted == 2){
                    // Has voted and claimed
                    p.closeInventory();
                    p.sendMessage(Strings.prefix + "§7Vote jetzt unter: §6https://vote.wonderbuild.net");
                }
            }
        }else if(title.equalsIgnoreCase("§6§lAdminshop")){
            e.setCancelled(true);
            if(mat == Material.GRAY_STAINED_GLASS_PANE) return;

            for(CbItem cbItem : CbItem.values()){
                if(cbItem.getMaterial() == mat){
                    if(e.isLeftClick()){
                        new Adminshop(p).amountBuy(cbItem);
                    }else if(e.isRightClick()){
                        if(Worth.getWorth(cbItem).getSell() >= 1) {
                            new Adminshop(p).amountSell(cbItem);
                        }
                    }
                }
            }

        }else if(title.endsWith(" §8» §7Kaufen")){
            e.setCancelled(true);
            if(mat == Material.GRAY_STAINED_GLASS_PANE) return;

            String name = title.replace(" §8» §7Kaufen", "").replace("§7", "");
            CbItem cbItem = null;
            for(CbItem c : CbItem.values()){
                if(c.getDisplayName().equalsIgnoreCase(name)){
                    cbItem = c;
                }
            }
            if(item.getItemMeta().getDisplayName().startsWith("§7Kaufe 1 ")){
                // Buy 1
                double price = Worth.getWorth(cbItem).getBuy();
                if(Economy.getBalance(p.getUniqueId()) < price){
                    p.sendMessage(Strings.prefix + "§cDazu hast du nicht genug Coins!");
                    return;
                }
                if(!hasFree(p)){
                    p.closeInventory();
                    p.sendMessage(Strings.prefix + "§cDu hast keinen Platz mehr in deinem Inventar!");
                    return;
                }
                ItemStack is = new ItemStack(cbItem.getMaterial());
                if(!Economy.withdrawPlayer(p.getUniqueId(), price).transactionSuccess()){
                    p.closeInventory();
                    p.sendMessage(Strings.prefix + "§cEs ist etwas schiefgelaufen. Bitte versuche es erneut.");
                    return;
                }
                p.getInventory().addItem(is);
                p.sendMessage(Strings.prefix + "§7Du hast §a" + is.getAmount() + " " + cbItem.getDisplayName() + " §7gekauft.");
            }else if(item.getItemMeta().getDisplayName().startsWith("§7Kaufe 32 ")){
                // Buy 2
                double price = Worth.getWorth(cbItem).getBuy()*32;
                if(Economy.getBalance(p.getUniqueId()) < price){
                    p.sendMessage(Strings.prefix + "§cDazu hast du nicht genug Coins!");
                    return;
                }
                if(!hasFree(p)){
                    p.closeInventory();
                    p.sendMessage(Strings.prefix + "§cDu hast keinen Platz mehr in deinem Inventar!");
                    return;
                }
                ItemStack is = new ItemStack(cbItem.getMaterial(), 32);
                if(!Economy.withdrawPlayer(p.getUniqueId(), price).transactionSuccess()){
                    p.closeInventory();
                    p.sendMessage(Strings.prefix + "§cEs ist etwas schiefgelaufen. Bitte versuche es erneut.");
                    return;
                }
                p.getInventory().addItem(is);
                p.sendMessage(Strings.prefix + "§7Du hast §a" + is.getAmount() + " " + cbItem.getDisplayName() + " §7gekauft.");
            }else if(item.getItemMeta().getDisplayName().startsWith("§7Kaufe 64 ")){
                // Buy 3
                double price = Worth.getWorth(cbItem).getBuy()*64;
                if(Economy.getBalance(p.getUniqueId()) < price){
                    p.sendMessage(Strings.prefix + "§cDazu hast du nicht genug Coins!");
                    return;
                }
                if(!hasFree(p)){
                    p.closeInventory();
                    p.sendMessage(Strings.prefix + "§cDu hast keinen Platz mehr in deinem Inventar!");
                    return;
                }
                ItemStack is = new ItemStack(cbItem.getMaterial(), 64);
                if(!Economy.withdrawPlayer(p.getUniqueId(), price).transactionSuccess()){
                    p.closeInventory();
                    p.sendMessage(Strings.prefix + "§cEs ist etwas schiefgelaufen. Bitte versuche es erneut.");
                    return;
                }
                p.getInventory().addItem(is);
                p.sendMessage(Strings.prefix + "§7Du hast §a" + is.getAmount() + " " + cbItem.getDisplayName() + " §7gekauft.");
            }else if(item.getItemMeta().getDisplayName().equalsIgnoreCase("§7➥ Zurück")){
                new Adminshop(p).open();
            }
        }else if(title.endsWith(" §8» §7Verkaufen")){
            e.setCancelled(true);
            if(mat == Material.GRAY_STAINED_GLASS_PANE) return;

            String name = title.replace(" §8» §7Verkaufen", "").replace("§7", "");
            CbItem cbItem = null;
            for(CbItem c : CbItem.values()){
                if(c.getDisplayName().equalsIgnoreCase(name)){
                    cbItem = c;
                }
            }
            if(item.getItemMeta().getDisplayName().startsWith("§7Verkaufe 1 ")){
                // Sell 1
                double price = Worth.getWorth(cbItem).getSell();
                if(!hasItems(p, cbItem.getMaterial(), 1)){
                    p.sendMessage(Strings.prefix + "§cDu hast nicht genug Items in deinem Inventar!");
                    return;
                }
                if(!Economy.depositPlayer(p.getUniqueId(), price).transactionSuccess()){
                    p.closeInventory();
                    p.sendMessage(Strings.prefix + "§cEs ist etwas schiefgelaufen. Bitte versuche es erneut.");
                    return;
                }
                removeItems(p, cbItem.getMaterial(), 1);
                p.sendMessage(Strings.prefix + "§7Du hast §a1 " + cbItem.getDisplayName() + " §7verkauft.");
            }else if(item.getItemMeta().getDisplayName().startsWith("§7Verkaufe 32 ")){
                // Sell 2
                double price = Worth.getWorth(cbItem).getSell()*32;
                if(!hasItems(p, cbItem.getMaterial(), 32)){
                    p.sendMessage(Strings.prefix + "§cDu hast nicht genug Items in deinem Inventar!");
                    return;
                }
                if(!Economy.depositPlayer(p.getUniqueId(), price).transactionSuccess()){
                    p.closeInventory();
                    p.sendMessage(Strings.prefix + "§cEs ist etwas schiefgelaufen. Bitte versuche es erneut.");
                    return;
                }
                removeItems(p, cbItem.getMaterial(), 32);
                p.sendMessage(Strings.prefix + "§7Du hast §a32 " + cbItem.getDisplayName() + " §7verkauft.");
            }else if(item.getItemMeta().getDisplayName().startsWith("§7Verkaufe 64 ")){
                // Sell 3
                double price = Worth.getWorth(cbItem).getSell()*64;
                if(!hasItems(p, cbItem.getMaterial(), 64)){
                    p.sendMessage(Strings.prefix + "§cDu hast nicht genug Items in deinem Inventar!");
                    return;
                }
                if(!Economy.depositPlayer(p.getUniqueId(), price).transactionSuccess()){
                    p.closeInventory();
                    p.sendMessage(Strings.prefix + "§cEs ist etwas schiefgelaufen. Bitte versuche es erneut.");
                    return;
                }
                removeItems(p, cbItem.getMaterial(), 64);

                p.sendMessage(Strings.prefix + "§7Du hast §a64 " + cbItem.getDisplayName() + " §7verkauft.");
            }else if(item.getItemMeta().getDisplayName().equalsIgnoreCase("§7➥ Zurück")){
                new Adminshop(p).open();
            }
        }else if(e.getView().title().equals(NameColorUI.invName1)){
            e.setCancelled(true);
            if(mat == Material.PLAYER_HEAD){
                NColor nColor = NColor.NONE;
                for(NColor all : NColor.values()){
                    if(item.getItemMeta().getDisplayName().equalsIgnoreCase(all.format(all.getName()))){
                        nColor = all;
                        break;
                    }
                }

                if(!p.hasPermission("cb.namecolor.color." + nColor.toString())) return;

                NameColorSelector ncs = new NameColorSelector(p.getUniqueId());
                if(!ncs.existsByUuid()) return;
                ncs.loadByUuid();
                ncs.setName_color(nColor.toString());
                ncs.update();

                p.sendMessage(Strings.prefix + "§7Namensfarbe wurde auf " + nColor.format(nColor.getName()) + " §7gesetzt.");

                new NameColorUI(p).setMainGUI().openGUI();

            }else if(mat == Material.RED_STAINED_GLASS_PANE){
                NameColorSelector ncs = new NameColorSelector(p.getUniqueId());
                if(!ncs.existsByUuid()) return;
                ncs.loadByUuid();
                ncs.setName_color(NColor.NONE.toString());
                ncs.update();

                p.sendMessage(Strings.prefix + "§7Namensfarbe wurde entfernt.");

                new NameColorUI(p).setMainGUI().openGUI();

            }else if(mat == Material.PAPER){

                new NameColorUI(p).setMainGUI2().openGUI();

            }
        }else if(e.getView().title().equals(NameColorUI.invName2)){
            e.setCancelled(true);
            if(mat == Material.PLAYER_HEAD){
                NColor nColor = NColor.NONE;
                for(NColor all : NColor.values()){
                    if(item.getItemMeta().getDisplayName().equalsIgnoreCase(ScoreboardManager.format2(all.format(all.getName())))){
                        nColor = all;
                        break;
                    }
                }

                if(!p.hasPermission("cb.namecolor.color." + nColor.toString())) return;

                NameColorSelector ncs = new NameColorSelector(p.getUniqueId());
                if(!ncs.existsByUuid()) return;
                ncs.loadByUuid();
                ncs.setName_color(nColor.toString());
                ncs.update();

                p.sendMessage(Strings.prefix + "§7Namensfarbe wurde auf " + ScoreboardManager.format2(nColor.format(nColor.getName())) + " §7gesetzt.");

                new NameColorUI(p).setMainGUI2().openGUI();

            }else if(mat == Material.RED_STAINED_GLASS_PANE){
                NameColorSelector ncs = new NameColorSelector(p.getUniqueId());
                if(!ncs.existsByUuid()) return;
                ncs.loadByUuid();
                ncs.setName_color(NColor.NONE.toString());
                ncs.update();

                p.sendMessage(Strings.prefix + "§7Namensfarbe wurde entfernt.");

                new NameColorUI(p).setMainGUI2().openGUI();

            }else if(mat == Material.PAPER){

                new NameColorUI(p).setMainGUI().openGUI();

            }
        }else if(e.getView().title().equals(BankUI.invName)){
            e.setCancelled(true);
            if(mat == Material.PLAYER_HEAD){
                if(item.getItemMeta().getDisplayName().equalsIgnoreCase(BankUI.out_name)){
                    Citybuild.getBankHandler().putPayOut(p);
                }else if(item.getItemMeta().getDisplayName().equalsIgnoreCase(BankUI.in_name)){
                    Citybuild.getBankHandler().putPayIn(p);
                }
            }
        }else if(e.getView().title().equals(CoinflipUI.inv_name_main)){
            e.setCancelled(true);
            if(mat == Material.NETHER_STAR){
                Casino.getInstance().getCoinflipManager().openedUIs.get(p).setCreateGUI().openGUI();
                Casino.getInstance().getCoinflipManager().addCreation(p, new Coinflip(p));
            }
        }else if(e.getView().title().equals(CoinflipUI.inv_name_create)){
            e.setCancelled(true);
            if(mat == Material.PLAYER_HEAD){
                for(CoinflipUI c : Casino.getInstance().getCoinflipManager().openedUIs.values()){
                    p.sendMessage("Player: " + c.getPlayer() + " | Inventar: " + c.getPlayer().getOpenInventory().getTitle());
                }
                Casino.getInstance().getCoinflipManager().openedUIs.get(p).clickChangeBet(item.getItemMeta().getDisplayName());
            }else if(mat == Material.GREEN_DYE){

            }
        }

    }

    public static boolean hasFree(Player p){
        boolean hasFree = false;
        for(int i = 0; i < 6*5+3; i++){
            ItemStack is = p.getInventory().getItem(i);
            if(is == null || is.getType() == Material.AIR){
                hasFree = true;
                break;
            }
        }
        return hasFree;
    }

    public static void removeItems(Player p, Material mat, int amount){

        ItemStack iss = new ItemStack(mat, amount);
       p.getInventory().removeItem(iss);

        //int am = 0;
        //for(int i = 0; i < 6*5+3; i++){
        //  ItemStack is = p.getInventory().getItem(i);
        //    if(is != null && is.getType() != Material.AIR){
        //        if(is.getType() == mat){
        //            if(is.getAmount() > amount-am){
        //                am=amount;
        //                is.setAmount(is.getAmount()-amount-am);
        //                break;
        //            }else{
        //                am+=is.getAmount();
        //                p.getInventory().remove(is);
        //            }
        //            if(am >= amount){
        //                break;
        //            }
        //        }
        //    }
        //}
    }

    public static boolean hasItems(Player p, Material mat, int amount){
        int am = 0;
        for(int i = 0; i < 6*5+3; i++){
            ItemStack is = p.getInventory().getItem(i);
            if(is != null && is.getType() != Material.AIR){
                if(is.getType() == mat){
                    am += is.getAmount();
                }
            }
        }
        if(am >= amount){
            return true;
        }else{
            return false;
        }
    }

}
