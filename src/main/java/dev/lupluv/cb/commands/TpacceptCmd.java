package dev.lupluv.cb.commands;

import dev.lupluv.cb.annotations.RegisterCommand;
import dev.lupluv.cb.utils.Strings;
import dev.lupluv.cb.utils.TpRequest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@RegisterCommand(name = "tpaccept", permission = "cb.tpa.accept")
public class TpacceptCmd implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if(commandSender instanceof Player){
            Player p = (Player) commandSender;
            if(!p.hasPermission("cb.tpa.accept")){
                p.sendMessage(Strings.noPerms);
            }else{
                if(strings.length == 0){
                    if(TpRequest.getRequest(p) != null){
                        if(TpRequest.getRequest(p).getSender() != p){
                            TpRequest.getRequest(p).accept();
                        }else{
                            p.sendMessage(Strings.prefix + "Du hast keine Teleport Anfrage bekommen");
                        }
                    }else{
                        p.sendMessage(Strings.prefix + "Du hast keine Teleport Anfrage bekommen");
                    }
                }else{
                    p.sendMessage(Strings.prefix + "Benutzung: /tpaccept");
                }
            }
        }

        return false;
    }
}
