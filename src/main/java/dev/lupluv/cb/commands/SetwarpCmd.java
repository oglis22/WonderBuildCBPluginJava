package dev.lupluv.cb.commands;

import dev.lupluv.cb.annotations.RegisterCommand;
import dev.lupluv.cb.utils.Strings;
import dev.lupluv.cb.utils.Warp;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

@RegisterCommand(name = "setwarp", permission = "cb.warp.set")
public class SetwarpCmd implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if(commandSender instanceof Player){
            Player p = (Player) commandSender;
            if(!p.hasPermission("cb.warp.set")){
                p.sendMessage(Strings.noPerms);
            }else{
                if(strings.length == 1){
                    String name = strings[0];
                    if(name.length() <= 16){
                        if(name.length() >= 2){
                            Warp warp = new Warp(name);
                            if(!warp.exists()){
                                warp.setLoc(p.getLocation());
                                try {
                                    warp.create();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                p.sendMessage(Strings.prefix + "Du hast den Warp '" + name + "' erstellt!");
                            }else{
                                p.sendMessage(Strings.prefix + "Der Warp '" + name + "' existiert bereits!");
                            }
                        }else{
                            p.sendMessage(Strings.prefix + "Der Warp-Titel muss mindestens 2 Zeichen lang sein!");
                        }
                    }else{
                        p.sendMessage(Strings.prefix + "Der Warp-Titel kann maximal 16 Zeichen lang sein!");
                    }
                }else{
                    p.sendMessage(Strings.prefix + "Benutzung: /setwarp <Titel>");
                }
            }
        }

        return false;
    }
}
