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


@RegisterCommand(name = "delhome", description = "Deletes a home", permission = "cb.home.del")
public class DelhomeCmd implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if(commandSender instanceof Player){
            Player p = (Player) commandSender;
            if(!p.hasPermission("cb.home.del")){
                p.sendMessage(Strings.noPerms);
            }else{
                if(strings.length == 1){
                    String name = strings[0];
                    Home home = new Home(p.getUniqueId(), name);
                    if(home.exists()){
                        home.load();
                        try {
                            home.delete();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        p.sendMessage(Strings.prefix + "Du hast den Home '" + name + "' gelöscht");
                    }else{
                        p.sendMessage(Strings.prefix + "Der Home '" + name + "' existiert nicht");
                    }
                }else{
                    p.sendMessage(Strings.prefix + "Benutzung: /delhome <name>");
                }
            }
        }

        return false;
    }
}
