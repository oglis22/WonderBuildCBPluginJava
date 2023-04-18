package dev.lupluv.cb.events;

import com.destroystokyo.paper.event.player.PlayerAdvancementCriterionGrantEvent;
import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import com.plotsquared.core.PlotSquared;
import com.plotsquared.core.player.PlotPlayer;
import com.plotsquared.core.plot.Plot;
import dev.lupluv.cb.Citybuild;
import dev.lupluv.cb.casino.Casino;
import dev.lupluv.cb.casino.coinflip.Coinflip;
import dev.lupluv.cb.casino.coinflip.CoinflipUI;
import dev.lupluv.cb.clans.User;
import dev.lupluv.cb.commands.VanishCmd;
import dev.lupluv.cb.economy.Bank;
import dev.lupluv.cb.economy.BankHandler;
import dev.lupluv.cb.economy.Economy;
import dev.lupluv.cb.elevators.ElevatorBlock;
import dev.lupluv.cb.namecolors.NameColorSelector;
import dev.lupluv.cb.namecolors.NamecolorManager;
import dev.lupluv.cb.scoreboard.ScoreboardManager;
import dev.lupluv.cb.utils.*;
import net.kyori.adventure.text.Component;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityTargetEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class PlayerHandler implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();

        // Economy Registration
        Economy.correctName(p);
        Bank.loadPlayerBank(p);

        // Clans Registration
        User.onJoin(e);

        // Namecolor Registration
        NameColorSelector.correctName(p);

        // Editing Join Message
        e.joinMessage(Component.text("§8[§2+§8] §7" + p.getName()));

        // Registering Home File
        File file = new File("plugins//Citybuild//homes.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        if(cfg.get(p.getUniqueId() + ".List") == null){
            List<String> l = new ArrayList<>();
            cfg.set(p.getUniqueId() + ".List", l);
            try {
                cfg.save(file);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        // Teleporting to Spawn if first Spawn or in Farming world
        if(!p.hasPlayedBefore() || !p.getLocation().getWorld().getName().equalsIgnoreCase("cb")){
            Warp spawn = new Warp("spawn");
            if(spawn.exists()) {
                spawn.load();
                p.teleport(spawn.getLoc());
            }else{
                p.sendMessage(Strings.prefix + "Du konntest nicht zum Spawn teleportiert werden da dieser nicht existiert.");
            }
        }

        if(!p.hasPlayedBefore()){
            p.getInventory().addItem(new ItemStack(Material.STONE_AXE));
            p.getInventory().addItem(new ItemStack(Material.STONE_PICKAXE));
            p.getInventory().addItem(new ItemStack(Material.STONE_SHOVEL));
            p.getInventory().addItem(new ItemStack(Material.BREAD, 64));
        }

        // Updating Vanished players to not see him
        VanishCmd.updateVanished();
    }

    //@EventHandler
    //public void onChat(AsyncPlayerChatEvent e){
      //  if(e.getPlayer().hasPermission("cb.can.hex")) {
      //      e.setFormat(ScoreboardManager.format2(ScoreboardManager.getPrefix(e.getPlayer()) + ScoreboardManager.getColor(e.getPlayer()))
      //              + e.getPlayer().getName() + " §8: §r"
      //              + ScoreboardManager.format2(e.getMessage()));
      //  }else if(e.getPlayer().hasPermission("cb.can.color")){
      //      e.setFormat(ScoreboardManager.format2(
      //              ScoreboardManager.getPrefix(e.getPlayer()) + ScoreboardManager.getColor(e.getPlayer()))
      //              + e.getPlayer().getName() + " §8: §r"
      //              + ChatColor.translateAlternateColorCodes('&', e.getMessage()));
      //  }else{
      //      e.setFormat(ScoreboardManager.format2(
      //              ScoreboardManager.getPrefix(e.getPlayer()) + ScoreboardManager.getColor(e.getPlayer()))
      //              + e.getPlayer().getName() + " §8: §r"
      //              + e.getMessage());
      //  }
    //}

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        if(Citybuild.getBankHandler().bankActions.containsKey(e.getPlayer())){
            e.setCancelled(true);
            Citybuild.getBankHandler().chatEvent(e.getPlayer(), e.getMessage());
            return;
        }
        if(e.getPlayer().hasPermission("cb.can.hex")) {
            send(" ", e.getPlayer());
            e.setFormat(ScoreboardManager.format2(ScoreboardManager.getPrefix(e.getPlayer()) + ScoreboardManager.getColor(e.getPlayer()))
                    + ScoreboardManager.format2(NamecolorManager.getNameColor(e.getPlayer()).format(e.getPlayer().getName())) + "§8: §r"
                    + ScoreboardManager.format2(e.getMessage()));
            Bukkit.getScheduler().runTaskLater(Citybuild.getInstance(), () ->{
                send(" ", e.getPlayer());
            }, 1);
        }else if(e.getPlayer().hasPermission("cb.can.color")){
            send(" ", e.getPlayer());
            e.setFormat(ScoreboardManager.format2(
                    ScoreboardManager.getPrefix(e.getPlayer()) + ScoreboardManager.getColor(e.getPlayer()))
                    + ScoreboardManager.format2(NamecolorManager.getNameColor(e.getPlayer()).format(e.getPlayer().getName())) + "§8: §r"
                    + ChatColor.translateAlternateColorCodes('&', e.getMessage()));
            Bukkit.getScheduler().runTaskLater(Citybuild.getInstance(), () ->{
                send(" ", e.getPlayer());
            }, 1);
        }else{
            send(" ", e.getPlayer());
            e.setFormat(ScoreboardManager.format2(
                    ScoreboardManager.getPrefix(e.getPlayer()) + ScoreboardManager.getColor(e.getPlayer()))
                    + ScoreboardManager.format2(NamecolorManager.getNameColor(e.getPlayer()).format(e.getPlayer().getName())) + "§8: §r"
                    + e.getMessage());
            Bukkit.getScheduler().runTaskLater(Citybuild.getInstance(), () ->{
                send(" ", e.getPlayer());
            }, 1);
        }
    }
    public static void send(String s, Player player){
        if(player.hasPermission("chat.big")) {
            Bukkit.getOnlinePlayers().forEach(all -> all.sendMessage(s));
        }
    }

    @EventHandler
    public void onInvClose(InventoryCloseEvent event){
        Player player = (Player) event.getPlayer();
        Inventory inventory = event.getInventory();
        Component title = event.getView().title();
        if(title.equals(CoinflipUI.inv_name_main) || title.equals(CoinflipUI.inv_name_create)){
            Casino.getInstance().getCoinflipManager().openedUIs.remove(player);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Player p = e.getPlayer();
        e.quitMessage(Component.text("§8[§4-§8] §7" + p.getName()));
    }
    @EventHandler
    public void onDeath(PlayerDeathEvent e){
        e.deathMessage(null);
        e.setKeepInventory(false);
        e.setShouldDropExperience(false);
        e.getPlayer().sendMessage(Strings.prefix + "Du bist gestorben.");
        Citybuild.getPlugin().getLogger().log(Level.INFO, "THE PLAYER " + e.getPlayer().getName() + " HAS DIED.");
    }
    @EventHandler
    public void onRespawn(PlayerRespawnEvent e){
        Warp spawn = new Warp("spawn");
        if(spawn.exists()) {
            spawn.load();
            e.setRespawnLocation(spawn.getLoc());
        }else{
            e.getPlayer().sendMessage(Strings.prefix + "Du konntest nicht zum Spawn teleportiert werden da dieser nicht existiert.");
        }
    }
    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        Block b = e.getBlock();
        Material mat = b.getType();
            if(mat == Material.BLACK_CONCRETE || mat == Material.WHITE_CONCRETE){
                ElevatorBlock eb1 = ElevatorBlock.getByLocation(b.getLocation());
                if(eb1 != null) {
                    if (eb1.exists()) eb1.load();
                    eb1.delete();
                    p.sendActionBar(Component.text("§6Du hast einen Teleporter abgebaut."));
                    e.setDropItems(false);
                    Item item = new Item(Material.BLACK_CONCRETE);
                    item.setDisplayName("§6§lAufzug");
                    item.setLore(Lore.create("§7Nutze diesen Block als Aufzug."));
                    p.getWorld().dropItem(b.getLocation(), item.build());
                }
            }

    }
    @EventHandler
    public void onCriteriaAdvancementGrant(PlayerAdvancementCriterionGrantEvent e){
        e.setCancelled(true);
    }
    @EventHandler
    public void onDamage(EntityDamageEvent e){
        if(e.getEntityType() == EntityType.PLAYER){
            Player p = (Player) e.getEntity();
            if(p.getLocation().getWorld().getName().equalsIgnoreCase("cb")) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onDamageE(EntityDamageByEntityEvent e){
        if(e.getDamager().getType() == EntityType.PLAYER && e.getEntity().getType() == EntityType.PLAYER){
            Player p = (Player) e.getDamager();
            if(!p.hasPermission("cb.can.damage")) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onTarget(EntityTargetEvent e){
        if(e.getEntity().getLocation().getWorld().getName().equalsIgnoreCase("cb")){
            e.setCancelled(true);
        }
    }
    @EventHandler
    public void onPing(ServerListPingEvent e){
        e.setMotd("§afür dein Citybuild Erlebnis");
    }
    @EventHandler
    public void onJump(PlayerJumpEvent e){
        Player p = e.getPlayer();
        Block blockUnderPlayer = e.getFrom().subtract(0, 1, 0).getBlock();
        if(blockUnderPlayer.getType() == Material.WHITE_CONCRETE){
            ElevatorBlock eb = ElevatorBlock.getByLocation(blockUnderPlayer.getLocation());
            if (eb != null) {
                    // Is teleporter
                    // Check for upper blocks
                    Block firstUpperBlock = null;
                    boolean priv = false;
                    for(int i = 2; i < 200; i++){
                        Block b = blockUnderPlayer.getLocation().add(0, i, 0).getBlock();
                        Material mat = b.getType();
                        if(mat == Material.WHITE_CONCRETE && ElevatorBlock.getByLocation(b.getLocation()) != null){
                            firstUpperBlock = b;
                            break;
                        }else if(mat == Material.BLACK_CONCRETE && ElevatorBlock.getByLocation(b.getLocation()) != null){
                            firstUpperBlock = b;
                            priv = true;
                            break;
                        }
                    }
                    if(firstUpperBlock != null){
                        if(!priv){
                            Location tel = firstUpperBlock.getLocation().add(0.5, 1, 0.5);
                            tel.setYaw(p.getLocation().getYaw());
                            tel.setPitch(p.getLocation().getPitch());
                            p.teleport(tel);
                            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 3.0F, 1.0F);
                        }else{
                            // Check if plot member
                            PlotPlayer<Player> plotPlayer = PlotPlayer.from(p);
                            Plot plot = PlotSquared.get().getPlotAreaManager().getPlotAreaByString("cb").getPlot(plotPlayer.getLocation());
                            if(plot.getOwner().toString().equalsIgnoreCase(p.getUniqueId().toString())){
                                Location tel = firstUpperBlock.getLocation().add(0.5, 1, 0.5);
                                tel.setYaw(p.getLocation().getYaw());
                                tel.setPitch(p.getLocation().getPitch());
                                p.teleport(tel);
                                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 3.0F, 1.0F);
                            }else if(plot.getTrusted().contains(p.getUniqueId())){
                                Location tel = firstUpperBlock.getLocation().add(0.5, 1, 0.5);
                                tel.setYaw(p.getLocation().getYaw());
                                tel.setPitch(p.getLocation().getPitch());
                                p.teleport(tel);
                                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 3.0F, 1.0F);
                            }else if(plot.getMembers().contains(p.getUniqueId())){
                                Location tel = firstUpperBlock.getLocation().add(0.5, 1, 0.5);
                                tel.setYaw(p.getLocation().getYaw());
                                tel.setPitch(p.getLocation().getPitch());
                                p.teleport(tel);
                                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 3.0F, 1.0F);
                            }else if(p.hasPermission("cb.teleport.admin")){
                                Location tel = firstUpperBlock.getLocation().add(0.5, 1, 0.5);
                                tel.setYaw(p.getLocation().getYaw());
                                tel.setPitch(p.getLocation().getPitch());
                                p.teleport(tel);
                                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 3.0F, 1.0F);
                            }
                        }
                    }
            }
        }else if(blockUnderPlayer.getType() == Material.BLACK_CONCRETE){
            ElevatorBlock eb = ElevatorBlock.getByLocation(blockUnderPlayer.getLocation());
            if (eb != null) {
                    // Is teleporter
                    // Check for upper blocks
                    PlotPlayer<Player> plotPlayer = PlotPlayer.from(p);
                    Plot plot = PlotSquared.get().getPlotAreaManager().getPlotAreaByString("cb").getPlot(plotPlayer.getLocation());
                    if(!plot.getOwner().toString().equalsIgnoreCase(p.getUniqueId().toString())){
                        if(!plot.getTrusted().contains(p.getUniqueId())){
                            if(!plot.getMembers().contains(p.getUniqueId())){
                                if(!p.hasPermission("cb.teleport.admin")) {
                                    return;
                                }
                            }
                        }
                    }
                    Block firstUpperBlock = null;
                    for(int i = 2; i < 200; i++){
                        Block b = blockUnderPlayer.getLocation().add(0, i, 0).getBlock();
                        Material mat = b.getType();
                        if(mat == Material.WHITE_CONCRETE && ElevatorBlock.getByLocation(b.getLocation()) != null){
                            firstUpperBlock = b;
                            break;
                        }else if(mat == Material.BLACK_CONCRETE && ElevatorBlock.getByLocation(b.getLocation()) != null){
                            firstUpperBlock = b;
                            break;
                        }
                    }
                    if(firstUpperBlock != null){
                        Location tel = firstUpperBlock.getLocation().add(0.5, 1, 0.5);
                        tel.setYaw(p.getLocation().getYaw());
                        tel.setPitch(p.getLocation().getPitch());
                        p.teleport(tel);
                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 3.0F, 1.0F);
                    }
            }
        }
    }
    @EventHandler
    public void onSneak(PlayerToggleSneakEvent e){
        if(e.isSneaking()) {
            Player p = e.getPlayer();
            Block blockUnderPlayer = p.getLocation().subtract(0, 1, 0).getBlock();
            if (blockUnderPlayer.getType() == Material.WHITE_CONCRETE) {
                ElevatorBlock eb = ElevatorBlock.getByLocation(blockUnderPlayer.getLocation());
                if (eb != null) {
                        // Is teleporter
                        // Check for lower blocks
                        Block firstLowerBlock = null;
                        boolean priv = false;
                        for (int i = 2; i < 200; i++) {
                            Block b = blockUnderPlayer.getLocation().subtract(0, i, 0).getBlock();
                            Material mat = b.getType();
                            if (mat == Material.WHITE_CONCRETE && ElevatorBlock.getByLocation(b.getLocation()) != null) {
                                firstLowerBlock = b;
                                break;
                            } else if (mat == Material.BLACK_CONCRETE && ElevatorBlock.getByLocation(b.getLocation()) != null) {
                                firstLowerBlock = b;
                                priv = true;
                                break;
                            }
                        }
                        if (firstLowerBlock != null) {
                            if (!priv) {
                                Location tel = firstLowerBlock.getLocation().add(0.5, 1, 0.5);
                                tel.setYaw(p.getLocation().getYaw());
                                tel.setPitch(p.getLocation().getPitch());
                                p.teleport(tel);
                                p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 3.0F, 1.0F);
                            } else {
                                // Check if plot member
                                PlotPlayer<Player> plotPlayer = PlotPlayer.from(p);
                                Plot plot = PlotSquared.get().getPlotAreaManager().getPlotAreaByString("cb").getPlot(plotPlayer.getLocation());
                                if (plot.getOwner().toString().equalsIgnoreCase(p.getUniqueId().toString())) {
                                    Location tel = firstLowerBlock.getLocation().add(0.5, 1, 0.5);
                                    tel.setYaw(p.getLocation().getYaw());
                                    tel.setPitch(p.getLocation().getPitch());
                                    p.teleport(tel);
                                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 3.0F, 1.0F);
                                } else if (plot.getTrusted().contains(p.getUniqueId())) {
                                    Location tel = firstLowerBlock.getLocation().add(0.5, 1, 0.5);
                                    tel.setYaw(p.getLocation().getYaw());
                                    tel.setPitch(p.getLocation().getPitch());
                                    p.teleport(tel);
                                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 3.0F, 1.0F);
                                } else if (plot.getMembers().contains(p.getUniqueId())) {
                                    Location tel = firstLowerBlock.getLocation().add(0.5, 1, 0.5);
                                    tel.setYaw(p.getLocation().getYaw());
                                    tel.setPitch(p.getLocation().getPitch());
                                    p.teleport(tel);
                                    p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 3.0F, 1.0F);
                                }
                            }
                        }
                }
            } else if (blockUnderPlayer.getType() == Material.BLACK_CONCRETE) {
                ElevatorBlock eb = ElevatorBlock.getByLocation(blockUnderPlayer.getLocation());
                if (eb != null) {
                        // Is teleporter
                        // Check for lower blocks
                        PlotPlayer<Player> plotPlayer = PlotPlayer.from(p);
                        Plot plot = PlotSquared.get().getPlotAreaManager().getPlotAreaByString("cb").getPlot(plotPlayer.getLocation());
                        if (!plot.getOwner().toString().equalsIgnoreCase(p.getUniqueId().toString())) {
                            if (!plot.getTrusted().contains(p.getUniqueId())) {
                                if (!plot.getMembers().contains(p.getUniqueId())) {
                                    return;
                                }
                            }
                        }
                        Block firstLowerBlock = null;
                        for (int i = 2; i < 200; i++) {
                            Block b = blockUnderPlayer.getLocation().subtract(0, i, 0).getBlock();
                            Material mat = b.getType();
                            if (mat == Material.WHITE_CONCRETE && ElevatorBlock.getByLocation(b.getLocation()) != null) {
                                firstLowerBlock = b;
                                break;
                            } else if (mat == Material.BLACK_CONCRETE && ElevatorBlock.getByLocation(b.getLocation()) != null) {
                                firstLowerBlock = b;
                                break;
                            }
                        }
                        if (firstLowerBlock != null) {
                            Location tel = firstLowerBlock.getLocation().add(0.5, 1, 0.5);
                            tel.setYaw(p.getLocation().getYaw());
                            tel.setPitch(p.getLocation().getPitch());
                            p.teleport(tel);
                            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 3.0F, 1.0F);
                        }
                }
            }
        }
    }
    @EventHandler
    public void onPlace(BlockPlaceEvent e){
        Player p = e.getPlayer();
        Block block = e.getBlock();
        Material mat = block.getType();
        if(mat == Material.BLACK_CONCRETE || mat == Material.WHITE_CONCRETE){
            if(e.getItemInHand().getItemMeta().getDisplayName().equalsIgnoreCase("§6§lAufzug")){
                if(e.getItemInHand().getItemMeta().getLore().isEmpty()) return;
                if(e.getItemInHand().getItemMeta().getLore().get(0).equalsIgnoreCase("§7Nutze diesen Block als Aufzug.")){
                    ElevatorBlock eb1 = ElevatorBlock.getByLocation(block.getLocation());
                    if(eb1 != null) {
                        if (eb1.exists()) eb1.load();
                        eb1.delete();
                    }
                    eb1 = new ElevatorBlock(block);
                    eb1.giveFreeId();
                    eb1.create();
                    p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§6Du hast einen Teleporter platziert."));
                }
            }
        }
    }
    @EventHandler
    public void onWorldEnter(PlayerChangedWorldEvent e){
        Player p = e.getPlayer();
        if(!TpRequest.inTeleport.contains(p)) {
            if (p.getLocation().getWorld().getName().equalsIgnoreCase("farmwelt_normal")) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "rtp " + p.getName() + " " + p.getLocation().getWorld().getName());
            } else if (p.getLocation().getWorld().getName().equalsIgnoreCase("farmwelt_nether")) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "rtp " + p.getName() + " " + p.getLocation().getWorld().getName());
            } else if (p.getLocation().getWorld().getName().equalsIgnoreCase("farmwelt_end")) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "rtp " + p.getName() + " " + p.getLocation().getWorld().getName());
            }
        }
    }

    @EventHandler
    public void onRename(PrepareAnvilEvent e){
        ItemStack res = e.getResult();
        Player player = (Player) e.getView().getPlayer();
        if(!player.hasPermission("cb.colors.rename")) return;

        ItemMeta im = res.getItemMeta();
        im.setDisplayName(ScoreboardManager.format2(im.getDisplayName()));
        res.setItemMeta(im);

        e.setResult(res);
    }

    @EventHandler
    public void onSign(SignChangeEvent e){
        Player player = e.getPlayer();
        if(!player.hasPermission("cb.colors.signs")) return;

        int i = 0;
        for(String s : e.getLines()){
            e.setLine(i, ScoreboardManager.format2(s));
            i++;
        }
    }

}
