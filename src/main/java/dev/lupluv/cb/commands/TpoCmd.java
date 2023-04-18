package dev.lupluv.cb.commands;

import dev.lupluv.cb.annotations.RegisterCommand;
import dev.lupluv.cb.utils.Strings;
import dev.lupluv.cb.utils.TpRequest;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@RegisterCommand(name = "tpo", permission = "cb.tpo.normal")
public class TpoCmd implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if(commandSender instanceof Player){
            Player p = (Player) commandSender;
            if(!p.hasPermission("cb.tpo.normal")){
                p.sendMessage(Strings.noPerms);
            }else{
                if(strings.length == 1){
                    Player t = Bukkit.getPlayer(strings[0]);
                    if(t != null){
                        TpRequest.inTeleport.add(p);
                        p.teleport(t);
                        TpRequest.inTeleport.remove(p);
                        p.sendMessage(Strings.prefix + "Du hast dich zu " + t.getName() + " teleportiert");
                    }else{
                        p.sendMessage(Strings.playerNotFound);
                    }
                }else if(strings.length == 2){
                    Player t1 = Bukkit.getPlayer(strings[0]);
                    Player t2 = Bukkit.getPlayer(strings[1]);
                    if(t1 != null && t2 != null){
                        TpRequest.inTeleport.add(t1);
                        t1.teleport(t2);
                        TpRequest.inTeleport.remove(t1);
                        p.sendMessage(Strings.prefix + "Du hast " + t1.getName() + " zu " + t2.getName() + " teleportiert");
                    }else{
                        p.sendMessage(Strings.playerNotFound);
                    }
                }else{
                    p.sendMessage(Strings.prefix + "Benutzung: /tpo <spieler> [spieler]");
                }
            }
        }

        return false;
    }
}
