package dev.lupluv.cb.commands;

import dev.lupluv.cb.annotations.RegisterCommand;
import dev.lupluv.cb.utils.Strings;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@RegisterCommand(name = "chatclear", aliases = {"cc"}, permission = "cb.chat.clear", description = "Clears the chat")
public class ChatclearCmd implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if(commandSender instanceof Player){
            Player p = (Player) commandSender;
            if(!p.hasPermission("cb.chat.clear")){
                p.sendMessage(Strings.noPerms);
            }else{
                if(strings.length == 0){
                    for(int i = 0; i < 500; i++){
                        Bukkit.getOnlinePlayers().forEach(all ->{
                            if(!all.hasPermission("cb.chat.clear.bypass")){
                                all.sendMessage(" ");
                            }
                        });
                    }
                    Bukkit.broadcastMessage("§b-----------------------------------------------");
                    Bukkit.broadcastMessage(" ");
                    Bukkit.broadcastMessage("     §7Der Chat wurde von §e" + p.getName() + " §7geleert");
                    Bukkit.broadcastMessage("             §7Grund: §cKeine Angabe");
                    Bukkit.broadcastMessage(" ");
                    Bukkit.broadcastMessage("§b-----------------------------------------------");
                    Bukkit.broadcastMessage(" ");
                }else if(strings.length == 1){
                    for(int i = 0; i < 500; i++){
                        Bukkit.getOnlinePlayers().forEach(all ->{
                            if(!all.hasPermission("cb.chat.clear.bypass")){
                                all.sendMessage(" ");
                            }
                        });
                    }
                    Bukkit.broadcastMessage("§b-----------------------------------------------");
                    Bukkit.broadcastMessage(" ");
                    Bukkit.broadcastMessage("     §7Der Chat wurde von §e" + p.getName() + " §7geleert");
                    Bukkit.broadcastMessage("             §7Grund: §c" + strings[0]);
                    Bukkit.broadcastMessage(" ");
                    Bukkit.broadcastMessage("§b-----------------------------------------------");
                    Bukkit.broadcastMessage(" ");
                }else{
                    p.sendMessage(Strings.prefix + "Nutze: /cc [grund]");
                }
            }
        }

        return false;
    }
}
