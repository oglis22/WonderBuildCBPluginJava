package dev.lupluv.cb.commands;

import dev.lupluv.cb.annotations.RegisterCommand;
import dev.lupluv.cb.utils.Strings;
import dev.lupluv.cb.utils.TpRequest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@RegisterCommand(name = "tpdeny", permission = "cb.tpa.deny")
public class TpdenyCmd implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if(commandSender instanceof Player){
            Player p = (Player) commandSender;
            if(!p.hasPermission("cb.tpa.deny")){
                p.sendMessage(Strings.noPerms);
            }else{
                if(strings.length == 0){
                    if(TpRequest.getRequest(p) != null){
                        if(TpRequest.getRequest(p).getReceiver() != p){
                            TpRequest.getRequest(p).deny();
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
