package dev.lupluv.cb.commands;

import dev.lupluv.cb.annotations.RegisterCommand;
import dev.lupluv.cb.utils.Strings;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@RegisterCommand(name = "feed", description = "Feed yourself or another player", permission = "cb.feed.self")
public class FeedCmd implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if(commandSender instanceof Player){
            Player p = (Player) commandSender;
            if(!p.hasPermission("cb.feed.self") && !p.hasPermission("cb.feed.others")){
                p.sendMessage(Strings.noPerms);
            }else{
                if(strings.length == 0){
                    if(!p.hasPermission("cb.feed.self")){
                        p.sendMessage(Strings.prefix + "Nutze: /feed [spieler]");
                    }else{
                        p.setFoodLevel(20);
                        p.sendMessage(Strings.prefix + "Du hast nun keinen Hunger mehr");
                    }
                }else if(strings.length == 1){
                    if(!p.hasPermission("cb.feed.others")){
                        p.sendMessage(Strings.prefix + "Nutze: /feed");
                    }else{
                        Player t = Bukkit.getPlayer(strings[0]);
                        if(t != null){
                            t.setFoodLevel(20);
                            p.sendMessage(Strings.prefix + t.getName() + " hat nun keinen Hunger mehr");
                        }else{
                            p.sendMessage(Strings.playerNotFound);
                        }
                    }
                }else{
                    if(!p.hasPermission("cb.feed.others")){
                        p.sendMessage(Strings.prefix + "Nutze: /feed [spieler]");
                    }else{
                        p.sendMessage(Strings.prefix + "Nutze: /feed");
                    }
                }
            }
        }

        return false;
    }
}
