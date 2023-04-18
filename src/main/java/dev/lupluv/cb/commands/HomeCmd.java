package dev.lupluv.cb.commands;

import dev.lupluv.cb.Citybuild;
import dev.lupluv.cb.annotations.RegisterCommand;
import dev.lupluv.cb.utils.Home;
import dev.lupluv.cb.utils.Strings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@RegisterCommand(name = "home", permission = "cb.home.home")
public class HomeCmd implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if(commandSender instanceof Player){
            Player p = (Player) commandSender;
            if(!p.hasPermission("cb.home.home")){
                p.sendMessage(Strings.noPerms);
            }else{
                if(strings.length == 1){
                    String name = strings[0];
                    Home home = new Home(p.getUniqueId(), name);
                    if(home.exists()){
                        home.load();
                        home.teleport(p);
                        p.sendMessage(Strings.prefix + "Du hast dich zum Home '" + name + "' teleportiert");
                    }else{
                        p.sendMessage(Strings.prefix + "Der Home '" + name + "' existiert nicht");
                    }
                }else if(strings.length == 0){
                    p.openInventory(Citybuild.getInventoryManager().getHomesInventory(p));
                }else{
                    p.sendMessage(Strings.prefix + "Benutzung: /home <spieler>");
                }
            }
        }

        return false;
    }
}
