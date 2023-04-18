package dev.lupluv.cb;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.driver.permission.IPermissionGroup;
import de.dytanic.cloudnet.driver.permission.Permission;
import de.dytanic.cloudnet.ext.bridge.BridgePlayerManager;
import dev.lupluv.cb.belohnung.FileMangerB;
import dev.lupluv.cb.belohnung.FileMangerC;
import dev.lupluv.cb.broadcast.BroadcastMessages;
import dev.lupluv.cb.casino.Casino;
import dev.lupluv.cb.commands.*;
import dev.lupluv.cb.economy.BankHandler;
import dev.lupluv.cb.events.ClickHandler;
import dev.lupluv.cb.events.PlayerHandler;
import dev.lupluv.cb.licence.LicenceManager;
import dev.lupluv.cb.listeners.CloudNetSimpleNameTagsListener;
import dev.lupluv.cb.mysql.MySQL;
import dev.lupluv.cb.namecolors.NamecolorManager;
import dev.lupluv.cb.particels.Particel;
import dev.lupluv.cb.stats.StatsNPC;
import dev.lupluv.cb.utils.*;
import dev.lupluv.cb.voting.VoteListener;
import me.neznamy.tab.api.TabAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;




public class Citybuild extends JavaPlugin {

    private static Citybuild plugin;
    private static FileManager fileManager;
    private static MoneyManager moneyManager;
    private static InventoryManager inventoryManager;
    private static Crafting crafting;
    private static StatsNPC statsNPC;
    private static BroadcastMessages broadcastMessages;
    private static BankHandler bankHandler;
    public static MySQL mySQL;

    private static Citybuild instance;

    public static Citybuild getInstance() {
        return instance;
    }

    @Override
    public void onLoad() {
        plugin = this;
        instance = this;
        // Files

        try {
            fileManager = new FileManager();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // MySQL Con
        reloadMysql();

    }



    @Override
    public void onEnable() {



        //Belohnung
        FileMangerB.loadFile();
        FileMangerC.loadFile();


        if(getServer().getPluginManager().isPluginEnabled("Vault")) {
            try {
                Plugin vault = getServer().getPluginManager().getPlugin("Vault");
                plugin.getServer().getServicesManager().register(net.milkbowl.vault.economy.Economy.class, new EconomyHolder(), vault, ServicePriority.High);
                plugin.getLogger().log(Level.INFO, "Hooking into Vault");
            } catch (Exception exception) {
                plugin.getLogger().log(Level.SEVERE, "Exception occurred while hooking into vault", exception);
            }
        }

        if(!getServer().getPluginManager().isPluginEnabled("Citizens")) {
            getLogger().log(Level.SEVERE, "Citizens 2.0 not found or not enabled");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }


        LicenceManager.getInstance().doOnEnable();

        // Money

        moneyManager = new MoneyManager();
        inventoryManager = new InventoryManager();
        crafting = new Crafting();
        crafting.loadRecipes();
        bankHandler = new BankHandler();
        new Casino();


        // Commands & Events
        RegisterManager.registerAll();


        broadcastMessages = new BroadcastMessages(fileManager.getBroadcast().getStringList("Messages"), 0);

        statsNPC = new StatsNPC();


        Bukkit.getScheduler().runTaskLater(this, Citybuild::startNPCScheduler, 20*10);

        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");

        Listener listener = new CloudNetSimpleNameTagsListener();

        this.getServer().getPluginManager().registerEvents(new CloudNetSimpleNameTagsListener(), this);
        CloudNetDriver.getInstance().getEventManager().registerListener(listener);

        // ScoreboardManager.getInstance().startScoreboardTask();

        TabAPI.getInstance().getPlaceholderManager().registerPlayerPlaceholder("%player_rank_color%", 1000*3, player -> ChatColor.GRAY + NamecolorManager.getNameColor(Bukkit.getPlayer(player.getUniqueId())).format(player.getName()));
        TabAPI.getInstance().getPlaceholderManager().registerServerPlaceholder("%proxy_players_online%", 1000, () -> BridgePlayerManager.getInstance().getOnlinePlayers().size());

        fixCloudPermsGroupsSortIDs();

    }

    public void fixCloudPermsGroupsSortIDs(){
        for(IPermissionGroup iPermissionGroup : CloudNetDriver.getInstance().getPermissionManagement().getGroups()){
            int sortID = iPermissionGroup.getSortId();
            boolean hasCurrent = false;
            boolean changed = false;
            for(Permission permission : iPermissionGroup.getPermissions()){
                if(permission.getName().startsWith("sort.id.")){
                    int thatID = Integer.valueOf(permission.getName().replace("sort.id.", ""));
                    if(thatID != sortID){
                        iPermissionGroup.removePermission(permission.getName());
                        changed = true;
                    }else{
                        hasCurrent = true;
                    }
                }
            }
            if(!hasCurrent){
                iPermissionGroup.addPermission("sort.id." + sortID);
                changed = true;
            }
            if(changed) CloudNetDriver.getInstance().getPermissionManagement().updateGroup(iPermissionGroup);
        }
    }

    //BelohungsSystem

    @Override
    public void onDisable() {

    }

    public static Citybuild getPlugin() {
        return plugin;
    }

    public static FileManager getFileManager() {
        return fileManager;
    }

    public static MoneyManager getMoneyManager() {
        return moneyManager;
    }

    public static InventoryManager getInventoryManager() {
        return inventoryManager;
    }

    public static Crafting getCrafting() {
        return crafting;
    }

    public static BankHandler getBankHandler() {
        return bankHandler;
    }

    public static BroadcastMessages getBroadcastMessages() {
        return broadcastMessages;
    }

    public static MySQL getMySQL() {
        return mySQL;
    }

    public static void reloadMysql(){
        if(getFileManager().getMysqlFile().exists()){
            FileConfiguration cfg = getFileManager().getMysql();
            if(mySQL != null){
                mySQL.disconnect();
            }
            mySQL = new MySQL(cfg.getString("Host"), cfg.getString("Port"), cfg.getString("Database"), cfg.getString("Username"), cfg.getString("Password"));
            mySQL.connect();
            System.out.println("Trying to connect");
            if(mySQL.isConnected()){
                System.out.println("Successfull");
                mySQL.update("CREATE TABLE IF NOT EXISTS cb_economy (uuid VARCHAR(255),name VARCHAR(255),money DOUBLE(255,20));");
                mySQL.update("CREATE TABLE IF NOT EXISTS cb_economy_banks (name VARCHAR(255),money DOUBLE(255,20));");
                mySQL.update("CREATE TABLE IF NOT EXISTS cb_jobs (uuid VARCHAR(255),name VARCHAR(255),job VARCHAR(255))");
                mySQL.update("CREATE TABLE IF NOT EXISTS cb_clans_clan (id BIGINT(255),name VARCHAR(255),tag VARCHAR(255),color VARCHAR(255)" +
                        ",open VARCHAR(255),chat VARCHAR(255),date BIGINT(255));");
                mySQL.update("CREATE TABLE IF NOT EXISTS cb_clans_user (id BIGINT(255),uuid VARCHAR(255),name VARCHAR(255));");
                mySQL.update("CREATE TABLE IF NOT EXISTS cb_clans_member (user_id BIGINT(255),clan_id BIGINT(255),role VARCHAR(255));");
                mySQL.update("CREATE TABLE IF NOT EXISTS cb_clans_requests (user_id BIGINT(255),clan_id BIGINT(255),type VARCHAR(255),date BIGINT(255));");
                mySQL.update("CREATE TABLE IF NOT EXISTS cb_namecolors (uuid VARCHAR(255),name VARCHAR(255),namecolor VARCHAR(255));");

            }
        }
    }

    public static void sendColoredConsoleMessage(String msg){
        Bukkit.getConsoleSender().sendMessage(msg);
    }

    public static void replaceNPCS(){
        System.out.println("Replace NPCs");
        if(statsNPC == null) statsNPC = new StatsNPC();
        updateNpcs();
        Bukkit.broadcast(Strings.prefix + "§7Alle Stats NPCs wurden §aerfolgreich §7aktualisiert.", "cb.stats.set");
    }

    public static void startNPCScheduler(){
        System.out.println("Started Scheduler");
        Bukkit.getScheduler().scheduleSyncRepeatingTask(getPlugin(), Citybuild::replaceNPCS, 0, 20*60);
    }

    public static void updateNpcs(){
        System.out.println("Place NPCs");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(new File("plugins//Citybuild//stats.yml"));

        dev.lupluv.cb.economy.Economy eco1 = dev.lupluv.cb.economy.Economy.getRank(1);
        dev.lupluv.cb.economy.Economy eco2 = dev.lupluv.cb.economy.Economy.getRank(2);
        dev.lupluv.cb.economy.Economy eco3 = dev.lupluv.cb.economy.Economy.getRank(3);

        System.out.println("UUID1: " + eco1.getPlayerName());
        System.out.println("UUID2: " + eco2.getPlayerName());
        System.out.println("UUID3: " + eco3.getPlayerName());

        // NPC 1
        if(cfg.getString("NPC.0.World") != null){
            Location loc = Util.getLocation(cfg, "NPC.0");
            if(eco1 != null) {
                statsNPC.updateNpc(1, "§6§lPlatz 1\n§e§l" + eco1.getPlayerName() + "\n§6§l" + eco1.getMoney() + " Coins", eco1.getPlayerName());
                System.out.println("Appending 1");
            }
        }

        // NPC 2
        if(cfg.getString("NPC.1.World") != null){
            Location loc = Util.getLocation(cfg, "NPC.1");
            if(eco2 != null) {
                statsNPC.updateNpc(2, "§6§lPlatz 2\n§e§l" + eco2.getPlayerName() + "\n§6§l" + eco2.getMoney() + " Coins", eco2.getPlayerName());
                System.out.println("Appending 2");
            }
        }

        // NPC 3
        if(cfg.getString("NPC.2.World") != null){
            Location loc = Util.getLocation(cfg, "NPC.2");
            if(eco3 != null) {
                statsNPC.updateNpc(3, "§6§lPlatz 3\n§e§l" + eco3.getPlayerName() + "\n§6§l" + eco3.getMoney() + " Coins", eco3.getPlayerName());
                System.out.println("Appending 3");
            }
        }
    }

    public static void sendPlayerToLobby(Player player){
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF("lobby-1");

        player.sendPluginMessage(getPlugin(), "BungeeCord", out.toByteArray());
    }

}
