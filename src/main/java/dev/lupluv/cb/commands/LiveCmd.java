package dev.lupluv.cb.commands;

import com.sun.jna.platform.unix.solaris.LibKstat;
import dev.lupluv.cb.annotations.RegisterCommand;
import dev.lupluv.cb.utils.Strings;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;
import org.jetbrains.annotations.NotNull;

@RegisterCommand(name = "live", permission = "cb.live")
public class LiveCmd implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(!(sender instanceof Player)) return true;
        Player player = (Player) sender;

        if(player.hasPermission("cb.live")){
            if(args.length == 2 && (args[0].equalsIgnoreCase("youtube") || args[0].equalsIgnoreCase("twitch"))){
                String platform = args[0];
                String url = args[1];
                if(!url.contains("https://") || !url.contains(".")){
                    player.sendMessage(Strings.prefix + "§cBitte gebe eine gültige URL ein.");
                    return true;
                }
                sendLiveMessage(player, platform, url);
            }else{
                player.sendMessage(Strings.prefix + "Benutzung /live <youtube/twitch> <url>");
            }


        }else{
            player.sendMessage(Strings.prefix + Strings.noPerms);
        }


        return false;
    }

    public void sendLiveMessage(Player player, String plat, String url) {

        Bukkit.getOnlinePlayers().forEach(all ->{
            all.sendMessage(" ");
            all.sendMessage(Strings.prefix + "§7---------------§c§lLIVE§7---------------");
            all.sendMessage(Strings.prefix);
            all.sendMessage(Strings.prefix + "§7Der Spieler §a" + player.getName() + " §7ist live auf");
            all.sendMessage(Strings.prefix + "§b§l" + plat);
            all.sendMessage(Strings.prefix);
            all.sendMessage(Strings.prefix + "§7" + url);
            all.sendMessage(Strings.prefix);
            all.sendMessage(Strings.prefix + "§7---------------§c§lLIVE§7---------------");
            all.sendMessage(" ");
        });

    }
}
