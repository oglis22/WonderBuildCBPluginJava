package dev.lupluv.cb.commands;

import dev.lupluv.cb.annotations.RegisterCommand;
import dev.lupluv.cb.utils.Home;
import dev.lupluv.cb.utils.Strings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

@RegisterCommand(name = "sethome", permission = "cb.home.set")
public class SethomeCmd implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if(commandSender instanceof Player){
            Player p = (Player) commandSender;
            if(!p.hasPermission("cb.home.set")){
                p.sendMessage(Strings.noPerms);
            }else{
                if(strings.length == 1){
                    String name = strings[0];
                    if(name.length() <= 16){
                        if(name.length() >= 2){
                            Home home = new Home(p.getUniqueId(), name);
                            if(!home.exists()){
                                home.setLoc(p.getLocation());
                                try {
                                    home.save();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                p.sendMessage(Strings.prefix + "Du hast den Home '" + name + "' erstellt!");
                            }else{
                                p.sendMessage(Strings.prefix + "Der Home '" + name + "' existiert bereits!");
                            }
                        }else{
                            p.sendMessage(Strings.prefix + "Der Home-Titel muss mindestens 2 Zeichen lang sein!");
                        }
                    }else{
                        p.sendMessage(Strings.prefix + "Der Home-Titel kann maximal 16 Zeichen lang sein!");
                    }
                }else{
                    p.sendMessage(Strings.prefix + "Benutzung: /sethome <Titel>");
                }
            }
        }

        return false;
    }
}
