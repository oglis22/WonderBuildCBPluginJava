package dev.lupluv.cb.casino.coinflip;

import dev.lupluv.cb.casino.Casino;
import dev.lupluv.cb.utils.Item;
import dev.lupluv.cb.utils.Lore;
import dev.lupluv.cb.utils.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CoinflipUI {

    public static final String plus_value = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5" +
            "taW5lY3JhZnQubmV0L3RleHR1cmUvNWZmMzE0MzFkNjQ1ODdmZjZlZjk4YzA2NzU4MTA2ODFmOGMxM2JmOTZmNTFkOWNiMDdlZDc4NTJiMmZmZDEifX19",
            minus_value = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3" +
                    "RleHR1cmUvNGU0YjhiOGQyMzYyYzg2NGUwNjIzMDE0ODdkOTRkMzI3MmE2YjU3MGFmYmY4MGMyYzViMTQ4Yzk1NDU3OWQ0NiJ9fX0=";

    public static final Component inv_name_main = Component.text("§6§lCoinflip Spiele");
    public static final Component inv_name_create = Component.text("§6§lErstelle ein Coinflip Spiel");
    public static final Component inv_name_flipping = Component.text("§6§lFlipping Coin...");

    public static final String
            plus_100_name = "§a§l+100",
            plus_500_name = "§a§l+500",
            plus_1000_name = "§a§l+1000",
            minus_100_name = "§c§l-100",
            minus_500_name = "§c§l-500",
            minus_1000_name = "§c§l-1000";

    Player player;
    Inventory inventory;

    public Inventory getInventory() {
        return inventory;
    }

    public Player getPlayer() {
        return player;
    }

    public CoinflipUI(Player player) {
        this.player = player;
        Casino.getInstance().getCoinflipManager().openedUIs.put(player, this);
    }

    public CoinflipUI setMainGUI(){
        inventory = Bukkit.createInventory(null, 9*4, inv_name_main);

        Item gi = new Item(Material.GRAY_STAINED_GLASS_PANE);
        gi.setDisplayName(" ");
        ItemStack g = gi.build();
        inventory.setItem(27, g);
        inventory.setItem(28, g);
        inventory.setItem(29, g);
        inventory.setItem(30, g);
        inventory.setItem(31, g);
        inventory.setItem(32, g);
        inventory.setItem(33, g);
        inventory.setItem(34, g);

        Item create = new Item(Material.NETHER_STAR);
        create.setDisplayName("§a§lErstelle ein Spiel");
        create.setLore(Lore.create(
                "§7Klicke hier um ein neues Spiel zu erstellen."
        ));
        inventory.setItem(35, create.build());

        if(Casino.getInstance().getCoinflipManager().getRunningGames().isEmpty()){
            Item barrier = new Item(Material.BARRIER);
            barrier.setDisplayName("§cKeine aktiven Spiele.");
            inventory.setItem(13, barrier.build());
        }else{
            for(Coinflip coinflip : Casino.getInstance().getCoinflipManager().getWaitingGames().values()){
                Item item = new Item(Material.PLAYER_HEAD);
                item.setSkullOwner(coinflip.getP1().getName());
                item.setLore(Lore.create(
                        " ",
                        "§7Wettbetrag: §6" + coinflip.getBet() + " Coins",
                        " "
                ));
                inventory.addItem(item.build());
            }
        }
        return this;
    }

    public CoinflipUI setCreateGUI(){
        inventory = Bukkit.createInventory(null, 9*3, inv_name_create);

        ItemStack plus100 = Util.createCustomSkull(plus_value, plus_100_name, null);
        ItemStack plus500 = Util.createCustomSkull(plus_value, plus_500_name, null);
        ItemStack plus1000 = Util.createCustomSkull(plus_value, plus_1000_name, null);
        inventory.setItem(10, plus100);
        inventory.setItem(11, plus500);
        inventory.setItem(12, plus1000);

        ItemStack minus100 = Util.createCustomSkull(minus_value, minus_100_name, null);
        ItemStack minus500 = Util.createCustomSkull(minus_value, minus_500_name, null);
        ItemStack minus1000 = Util.createCustomSkull(minus_value, minus_1000_name, null);
        inventory.setItem(14, minus1000);
        inventory.setItem(15, minus500);
        inventory.setItem(16, minus100);

        Item sign = new Item(Material.OAK_SIGN);
        sign.setDisplayName("§f§lWährung");
        sign.setLore(Lore.create(
                " ",
                "§7Wettbetrag: §f" + 0,
                " "
        ));
        inventory.setItem(13, sign.build());

        Item create = new Item(Material.GREEN_DYE);
        create.setDisplayName("§a§lSpiel erstellen");
        create.setLore(Lore.create(
                "§7Klicke um das Spiel zu erstellen"
        ));
        inventory.setItem(26, create.build());

        for(int i = 0; i < inventory.getSize(); i++){
            if(inventory.getItem(i) == null || inventory.getItem(i).getType() == Material.AIR){
                Item item = new Item(Material.GRAY_STAINED_GLASS_PANE);
                item.setDisplayName(" ");
                inventory.setItem(i, item.build());
            }
        }

        return this;

    }

    public CoinflipUI updateCreateGUI(double value){

        Item sign = new Item(Material.OAK_SIGN);
        sign.setDisplayName("§f§lWährung");
        sign.setLore(Lore.create(
                " ",
                "§7Wettbetrag: §f" + value,
                " "
        ));

        player.getOpenInventory().setItem(13, sign.build());

        return this;
    }

    public void clickChangeBet(String item_name){
        Coinflip coinflip = Casino.getInstance().getCoinflipManager().getCreations().get(player);
        if(item_name.equalsIgnoreCase(plus_100_name)){
            coinflip.higherBet(100);
        }else if(item_name.equalsIgnoreCase(plus_500_name)){
            coinflip.higherBet(500);
        }else if(item_name.equalsIgnoreCase(plus_1000_name)){
            coinflip.higherBet(1000);
        }else if(item_name.equalsIgnoreCase(minus_100_name)){
            coinflip.lowerBet(100);
        }else if(item_name.equalsIgnoreCase(minus_500_name)){
            coinflip.lowerBet(500);
        }else if(item_name.equalsIgnoreCase(minus_1000_name)){
            coinflip.lowerBet(1000);
        }
        updateCreateGUI(coinflip.getBet());
    }

    public void openGUI(){
        player.openInventory(inventory);
    }

}
