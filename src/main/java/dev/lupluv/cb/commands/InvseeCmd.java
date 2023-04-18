package dev.lupluv.cb.commands;

import dev.lupluv.cb.annotations.RegisterCommand;
import dev.lupluv.cb.utils.Strings;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@RegisterCommand(name = "invsee", permission = "cb.invsee")
public class InvseeCmd implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(sender instanceof Player){
            Player p = (Player) sender;
            if(!p.hasPermission("cb.invsee")){
                p.sendMessage(Strings.noPerms);
            }else{
                if(args.length == 1){
                    Player t = Bukkit.getPlayer(args[0]);
                    if(t != null){
                        p.openInventory(t.getInventory());
                    }else{
                        p.sendMessage(Strings.playerNotFound);
                    }
                }else{
                    p.sendMessage(Strings.prefix + "§7Benutzung: /invsee §a<Spieler>");
                }
            }
        }

        return false;
    }
}
