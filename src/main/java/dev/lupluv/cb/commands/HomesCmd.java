package dev.lupluv.cb.commands;

import dev.lupluv.cb.annotations.RegisterCommand;
import dev.lupluv.cb.utils.Home;
import dev.lupluv.cb.utils.Strings;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@RegisterCommand(name = "homes", permission = "cb.home.list")
public class HomesCmd implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if(commandSender instanceof Player){
            Player p = (Player) commandSender;
            if(!p.hasPermission("cb.home.list")){
                p.sendMessage(Strings.noPerms);
            }else{
                if(strings.length == 0){
                    if(!Home.getHomeList(p.getUniqueId()).isEmpty()){
                        p.sendMessage(Strings.prefix + "Home (" + Home.getHomeList(p.getUniqueId()).size() + "):");
                        for(Home h : Home.getHomeList(p.getUniqueId())){
                            p.spigot().sendMessage(new ComponentBuilder(Strings.prefix + " - " + h.getName())
                                    .event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/home " + h.getName())).create());
                        }
                    }else{
                        p.sendMessage(Strings.prefix + "Du hast noch keine Homes");
                    }
                }else{
                    p.sendMessage(Strings.prefix + "Benutzung: /homes");
                }
            }
        }

        return false;
    }
}
