package dev.lupluv.cb.commands;

import dev.lupluv.cb.annotations.RegisterCommand;
import dev.lupluv.cb.utils.Strings;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@RegisterCommand(name = "tpo", permission = "cb.tpo.here")
public class TpohereCmd implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if(commandSender instanceof Player){
            Player p = (Player) commandSender;
            if(!p.hasPermission("cb.tpo.here")){
                p.sendMessage(Strings.noPerms);
            }else{
                if(strings.length == 1){
                    Player t = Bukkit.getPlayer(strings[0]);
                    if(t != null){
                        t.teleport(p);
                        p.sendMessage(Strings.prefix + "Du hast " + t.getName() + " zu dir teleportiert");
                    }else{
                        p.sendMessage(Strings.playerNotFound);
                    }
                }else{
                    p.sendMessage(Strings.prefix + "Benutzung: /tpohere <spieler>");
                }
            }
        }

        return false;
    }
}
