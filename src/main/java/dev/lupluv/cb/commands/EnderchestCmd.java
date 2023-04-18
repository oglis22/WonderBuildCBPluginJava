package dev.lupluv.cb.commands;

import dev.lupluv.cb.annotations.RegisterCommand;
import dev.lupluv.cb.utils.Strings;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@RegisterCommand(name = "enderchest", permission = "cb.ec.self", description = "Open your enderchest", aliases = {"ec"})
public class EnderchestCmd implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if(commandSender instanceof Player){
            Player p = (Player) commandSender;
            if(!p.hasPermission("cb.ec.self") && !p.hasPermission("cb.ec.others")){
                p.sendMessage(Strings.noPerms);
            }else{
                if(strings.length == 0){
                    if(!p.hasPermission("cb.ec.self")){
                        p.sendMessage(Strings.prefix + "Nutze: /ec [spieler]");
                    }else{
                        p.openInventory(p.getEnderChest());
                    }
                }else if(strings.length == 1){
                    if(!p.hasPermission("cb.ec.others")){
                        p.sendMessage(Strings.prefix + "Nutze: /ec");
                    }else{
                        Player t = Bukkit.getPlayer(strings[0]);
                        if(t != null){
                            p.openInventory(t.getEnderChest());
                        }else{
                            p.sendMessage(Strings.playerNotFound);
                        }
                    }
                }else{
                    if(!p.hasPermission("cb.ec.others")){
                        p.sendMessage(Strings.prefix + "Nutze: /ec");
                    }else{
                        p.sendMessage(Strings.prefix + "Nutze: /ec [spieler]");
                    }
                }
            }
        }

        return false;
    }
}
