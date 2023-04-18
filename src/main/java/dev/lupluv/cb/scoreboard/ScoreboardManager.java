package dev.lupluv.cb.scoreboard;
import com.google.common.base.Preconditions;
import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.driver.permission.IPermissionGroup;
import de.dytanic.cloudnet.driver.permission.IPermissionUser;
import de.dytanic.cloudnet.ext.bridge.BridgePlayerManager;
import de.dytanic.cloudnet.ext.bridge.player.ICloudPlayer;
import de.dytanic.cloudnet.ext.bridge.player.IPlayerManager;
import dev.lupluv.cb.Citybuild;
import dev.lupluv.cb.economy.Economy;
//import net.melion.rgbchat.api.RGBApi;
import dev.lupluv.cb.namecolors.NColor;
import dev.lupluv.cb.namecolors.NamecolorManager;
import net.melion.rgbchat.api.RGBApi;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScoreboardManager {
    private static ScoreboardManager instance;
    public static ScoreboardManager getInstance() {
        if(instance == null){
            instance = new ScoreboardManager();
        }
        return instance;
    }

    private static final Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");

    public static String format(String s){
        Matcher match = pattern.matcher(s);
        while(match.find()){
            String color = s.substring(match.start(), match.end());
            s = s.replace(color, net.md_5.bungee.api.ChatColor.of(color) + "");
            match = pattern.matcher(s);
        }
        return net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', s);
    }

    public static String format2(String s){
        return net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', RGBApi.INSTANCE.toColoredMessage(s));
    }

    /*
    public static void updateScoreboard(Player p){
        IPlayerManager pm = BridgePlayerManager.getInstance();
        ICloudPlayer cp = pm.getOnlinePlayer(p.getUniqueId());
        IPermissionUser u = CloudNetDriver.getInstance().getPermissionManagement().getUser(p.getUniqueId());
        String serverName = cp.getConnectedService().getServerName();
        Scoreboard scoreboard = p.getScoreboard();
        Team profile;
        Team server;
        Team coins;
        Team online;
        if(scoreboard.getTeam("profile") != null) profile = scoreboard.getTeam("profile"); else profile = scoreboard.registerNewTeam("profile");
        if(scoreboard.getTeam("server") != null) server = scoreboard.getTeam("server"); else server = scoreboard.registerNewTeam("server");
        if(scoreboard.getTeam("coins") != null) coins = scoreboard.getTeam("coins"); else coins = scoreboard.registerNewTeam("coins");
        if(scoreboard.getTeam("online") != null) online = scoreboard.getTeam("online"); else online = scoreboard.registerNewTeam("online");

        profile.setPrefix("§e" + format2(getPrefix(p) + getColor(p) + p.getName()));
        server.setPrefix("§8 » §6" + serverName);
        coins.setPrefix("§8 » §6" + Economy.getBalance(p.getUniqueId()) + " §6Coins");
        online.setPrefix("§8 » §6" + Bukkit.getOnlinePlayers().size() + "§8/§6" + Bukkit.getMaxPlayers());

        updateTagline(p);
    }
    */

    public static String getPrefix(Player player){
        return CloudNetDriver.getInstance().getPermissionManagement()
                .getHighestPermissionGroup(
                        CloudNetDriver.getInstance().getPermissionManagement()
                                .getUser(
                                        player.getUniqueId()
                                )
                )
                .getPrefix();
    }

    public static String getColor(Player player){
        return CloudNetDriver.getInstance().getPermissionManagement()
                .getHighestPermissionGroup(
                        CloudNetDriver.getInstance().getPermissionManagement()
                                .getUser(
                                        player.getUniqueId()
                                )
                )
                .getColor();
    }

    /*
    public void startScoreboardTask(){
        Bukkit.getScheduler().scheduleSyncRepeatingTask(Citybuild.getPlugin(), new Runnable() {
            @Override
            public void run() {
                Bukkit.getOnlinePlayers().forEach(all ->{
                    updateScoreboard(all);
                });
            }
        }, 0, 20);
    }

    public void updateNameTags(Player player) {
        this.updateNameTags(player, null);
    }

    public void updateNameTags(Player player, Function<Player, IPermissionGroup> playerIPermissionGroupFunction) {
        this.updateNameTags(player, playerIPermissionGroupFunction, null);
    }

    public void updateNameTags(Player player, Function<Player, IPermissionGroup> playerIPermissionGroupFunction,
                               Function<Player, IPermissionGroup> allOtherPlayerPermissionGroupFunction) {
        Preconditions.checkNotNull(player);

        IPermissionUser playerPermissionUser = CloudNetDriver.getInstance().getPermissionManagement()
                .getUser(player.getUniqueId());
        AtomicReference<IPermissionGroup> playerPermissionGroup = new AtomicReference<>(
                playerIPermissionGroupFunction != null ? playerIPermissionGroupFunction.apply(player) : null);

        if (playerPermissionUser != null && playerPermissionGroup.get() == null) {
            playerPermissionGroup
                    .set(CloudNetDriver.getInstance().getPermissionManagement().getHighestPermissionGroup(playerPermissionUser));

            if (playerPermissionGroup.get() == null) {
                playerPermissionGroup.set(CloudNetDriver.getInstance().getPermissionManagement().getDefaultPermissionGroup());
            }
        }

        int sortIdLength = CloudNetDriver.getInstance().getPermissionManagement().getGroups().stream()
                .map(IPermissionGroup::getSortId)
                .map(String::valueOf)
                .mapToInt(String::length)
                .max()
                .orElse(0);

        this.initScoreboard(player);

        Bukkit.getOnlinePlayers().forEach(all -> {
            this.initScoreboard(all);

            if (playerPermissionGroup.get() != null) {
                this.addTeamEntry(player, all, playerPermissionGroup.get(), sortIdLength);
            }

            IPermissionUser targetPermissionUser = CloudNetDriver.getInstance().getPermissionManagement()
                    .getUser(all.getUniqueId());
            IPermissionGroup targetPermissionGroup =
                    allOtherPlayerPermissionGroupFunction != null ? allOtherPlayerPermissionGroupFunction.apply(all) : null;

            if (targetPermissionUser != null && targetPermissionGroup == null) {
                targetPermissionGroup = CloudNetDriver.getInstance().getPermissionManagement()
                        .getHighestPermissionGroup(targetPermissionUser);

                if (targetPermissionGroup == null) {
                    targetPermissionGroup = CloudNetDriver.getInstance().getPermissionManagement().getDefaultPermissionGroup();
                }
            }

            if (targetPermissionGroup != null) {
                this.addTeamEntry(all, player, targetPermissionGroup, sortIdLength);
            }
        });
    }

    private void addTeamEntry(Player target, Player all, IPermissionGroup permissionGroup, int highestSortIdLength) {
        int sortIdLength = String.valueOf(permissionGroup.getSortId()).length();
        String teamName = (
                highestSortIdLength == sortIdLength ?
                        permissionGroup.getSortId() :
                        String.format("%0" + highestSortIdLength + "d", permissionGroup.getSortId())
        ) + permissionGroup.getName();

        if (teamName.length() > 16) {
            teamName = teamName.substring(0, 16);
        }

        Team team = all.getScoreboard().getTeam(teamName);
        if (team == null) {
            team = all.getScoreboard().registerNewTeam(teamName);
        }

        String prefix = permissionGroup.getPrefix();
        String groupColor = permissionGroup.getColor();
        ChatColor finalColor = ChatColor.GRAY;
        if(NamecolorManager.getNameColor(target) != NColor.NONE){
            finalColor = NamecolorManager.getNameColor(target).asColor();
        }else{
            if (groupColor != null && !groupColor.isEmpty()) {
                ChatColor chatColor = ChatColor.getByChar(groupColor.replaceAll("&", "").replaceAll("§", ""));
                if (chatColor != null) {
                    finalColor = chatColor;
                }
            } else {
                groupColor = ChatColor.getLastColors(prefix.replace('&', '§'));
                if (!groupColor.isEmpty()) {
                    ChatColor chatColor = ChatColor.getByChar(groupColor.replaceAll("&", "").replaceAll("§", ""));
                    if (chatColor != null) {
                        permissionGroup.setColor(groupColor);
                        CloudNetDriver.getInstance().getPermissionManagement().updateGroup(permissionGroup);
                    }
                }
            }
        }
        String suffix = permissionGroup.getSuffix();



        try {
            Method method = team.getClass().getDeclaredMethod("setColor", ChatColor.class);
            method.setAccessible(true);
            method.invoke(team, finalColor);
        } catch (NoSuchMethodException ignored) {
        } catch (IllegalAccessException | InvocationTargetException exception) {
            exception.printStackTrace();
        }

        team.setPrefix(format2(prefix));

        team.setSuffix(format2(suffix));

        team.addEntry(target.getName());

        target.setDisplayName(permissionGroup.getDisplay() + target.getName());
    }

    public static void updateTagline(Player player){
       // Scoreboard scoreboard = player.getScoreboard();
        //Objective objective = scoreboard.registerNewObjective("citybuild_belowname", "air");
        //if(objective == null){
        //    objective.setDisplayName(ChatColor.RED + "Test");
        //    objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
        //    objective.setDisplayName("test123");
        //}


       // String tagline = "test";



  
    }

    private void initScoreboard(Player all) {
        if (all.getScoreboard().equals(all.getServer().getScoreboardManager().getMainScoreboard())) {
            Scoreboard scoreboard = all.getServer().getScoreboardManager().getNewScoreboard();
            Objective objective = scoreboard.registerNewObjective("citybuild", "dummy");
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            objective.setDisplayName("§6§lWONDERBUILD§8§l");
            Team profile;
            Team server;
            Team coins;
            Team online;
            if(scoreboard.getTeam("profile") != null) profile = scoreboard.getTeam("profile"); else profile = scoreboard.registerNewTeam("profile");
            if(scoreboard.getTeam("server") != null) server = scoreboard.getTeam("server"); else server = scoreboard.registerNewTeam("server");
            if(scoreboard.getTeam("coins") != null) coins = scoreboard.getTeam("coins"); else coins = scoreboard.registerNewTeam("coins");
            if(scoreboard.getTeam("online") != null) online = scoreboard.getTeam("online"); else online = scoreboard.registerNewTeam("online");
            objective.getScore("§7§o    wonderbuild.net").setScore(13);
            objective.getScore(" ").setScore(12);
            objective.getScore("§7⭐ §8• §7Bargeld§8:").setScore(11);
            objective.getScore("§6").setScore(10);
            objective.getScore("  ").setScore(9);
            objective.getScore("§7✪ §8• §7Server§8:").setScore(8);
            objective.getScore("§5").setScore(7);
            objective.getScore("   ").setScore(6);
            objective.getScore("§7⛏ §8• §7Job§8:").setScore(5);
            objective.getScore("§8 » §6Wartungen").setScore(4);
            objective.getScore("    ").setScore(3);
            //objective.getScore("§fProfil§7:").setScore(10);
            //objective.getScore("§1").setScore(9);
            objective.getScore("§7❤ §8• §7Spieler§8:").setScore(2);
            objective.getScore("§4").setScore(1);
            objective.getScore("           ").setScore(0);
            //profile.addEntry("§1");
            server.addEntry("§5");
            coins.addEntry("§6");
            online.addEntry("§4");
//8-0
            all.setScoreboard(scoreboard);
            updateTagline(all);
        }
    }

     */

}
