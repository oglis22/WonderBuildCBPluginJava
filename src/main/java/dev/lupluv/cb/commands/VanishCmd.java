package dev.lupluv.cb.commands;

import dev.lupluv.cb.annotations.RegisterCommand;
import dev.lupluv.cb.utils.Strings;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@RegisterCommand(name = "vanish", permission = "cb.vanish.self", aliases = {"v"})
public class VanishCmd implements CommandExecutor {

    public static List<Player> vanishedPlayer = new ArrayList<>();

    public static void updateVanished(){
        for(Player all : Bukkit.getOnlinePlayers()){
                Bukkit.getOnlinePlayers().forEach(all2 ->{
                    if(!all.hasPermission("cb.vanish.bypass")) {
                        if (vanishedPlayer.contains(all2)) {
                            all.hidePlayer(all2);
                        } else {
                            all.showPlayer(all2);
                        }
                    }else{
                        all.showPlayer(all2);
                    }
                });
        }
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if(commandSender instanceof Player){
            Player p = (Player) commandSender;
            if(!p.hasPermission("cb.vanish.self") && !p.hasPermission("cb.vanish.others")){
                p.sendMessage(Strings.noPerms);
            }else{
                if(strings.length == 0){
                    if(!p.hasPermission("cb.vanish.self")){
                        p.sendMessage(Strings.prefix + "Nutze: /vanish [spieler]");
                    }else{
                        if(!vanishedPlayer.contains(p)){
                            p.sendMessage(Strings.prefix + "Du bist nun unsichtbar");
                            vanishedPlayer.add(p);
                            updateVanished();
                        }else{
                            p.sendMessage(Strings.prefix + "Du bist nun sichtbar");
                            vanishedPlayer.remove(p);
                            updateVanished();
                        }

                    }
                }else if(strings.length == 1){
                    if(!p.hasPermission("cb.vanish.others")){
                        p.sendMessage(Strings.prefix + "Nutze: /heal");
                    }else{
                        Player t = Bukkit.getPlayer(strings[0]);
                        if(t != null){
                            if(!vanishedPlayer.contains(t)){
                                p.sendMessage(Strings.prefix + t.getName() + " bist nun unsichtbar");
                                vanishedPlayer.add(t);
                                updateVanished();
                            }else{
                                p.sendMessage(Strings.prefix + t.getName() + " bist nun sichtbar");
                                vanishedPlayer.remove(t);
                                updateVanished();
                            }
                        }else{
                            p.sendMessage(Strings.playerNotFound);
                        }
                    }
                }else{
                    if(!p.hasPermission("cb.vanish.others")){
                        p.sendMessage(Strings.prefix + "Nutze: /vanish [spieler]");
                    }else{
                        p.sendMessage(Strings.prefix + "Nutze: /vanish");
                    }
                }
            }
        }

        return false;
    }
}
