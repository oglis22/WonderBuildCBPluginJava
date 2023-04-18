package dev.lupluv.cb.commands;

import dev.lupluv.cb.annotations.RegisterCommand;
import dev.lupluv.cb.utils.Strings;
import dev.lupluv.cb.utils.Warp;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@RegisterCommand(name = "delwarp", permission = "cb.warp.del", description = "Deletes a warp")
public class DelwarpCmd implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if(commandSender instanceof Player){
            Player p = (Player) commandSender;
            if(!p.hasPermission("cb.warp.del")){
                p.sendMessage(Strings.noPerms);
            }else{
                if(strings.length == 1){
                    String name = strings[0];
                    Warp warp = new Warp(name);
                    if(warp.exists()){
                        warp.load();
                        warp.delete();
                        p.sendMessage(Strings.prefix + "Du hast den Warp '" + name + "' gelöscht");
                    }else{
                        p.sendMessage(Strings.prefix + "Der Warp '" + name + "' existiert nicht");
                    }
                }else{
                    p.sendMessage(Strings.prefix + "Benutzung: /delwarp <name>");
                }
            }
        }

        return false;
    }
}
