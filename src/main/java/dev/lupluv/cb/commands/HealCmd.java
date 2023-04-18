package dev.lupluv.cb.commands;

import dev.lupluv.cb.annotations.RegisterCommand;
import dev.lupluv.cb.utils.Strings;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@RegisterCommand(name = "heal", permission = "cb.heal.self")
public class HealCmd implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if(commandSender instanceof Player){
            Player p = (Player) commandSender;
            if(!p.hasPermission("cb.heal.self") && !p.hasPermission("cb.heal.others")){
                p.sendMessage(Strings.noPerms);
            }else{
                if(strings.length == 0){
                    if(!p.hasPermission("cb.heal.self")){
                        p.sendMessage(Strings.prefix + "Nutze: /heal [spieler]");
                    }else{
                        p.setHealth(20);
                        p.sendMessage(Strings.prefix + "Du wurdest geheilt");
                    }
                }else if(strings.length == 1){
                    if(!p.hasPermission("cb.heal.others")){
                        p.sendMessage(Strings.prefix + "Nutze: /heal");
                    }else{
                        Player t = Bukkit.getPlayer(strings[0]);
                        if(t != null){
                            t.setHealth(20);
                            p.sendMessage(Strings.prefix + t.getName() + " wurde geheilt");
                        }else{
                            p.sendMessage(Strings.playerNotFound);
                        }
                    }
                }else{
                    if(!p.hasPermission("cb.heal.others")){
                        p.sendMessage(Strings.prefix + "Nutze: /heal [spieler]");
                    }else{
                        p.sendMessage(Strings.prefix + "Nutze: /heal");
                    }
                }
            }
        }

        return false;
    }
}
