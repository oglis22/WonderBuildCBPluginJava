package dev.lupluv.cb.commands;

import dev.lupluv.cb.annotations.RegisterCommand;
import dev.lupluv.cb.utils.Strings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@RegisterCommand(name = "event", aliases = {"events"}, permission = "cb.events", description = "Mache bei Events mit")
public class EventCmd implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(sender instanceof Player){
            Player p = (Player) sender;
            if(!p.hasPermission("cb.events")){
                p.sendMessage(Strings.noPerms);
            }else{
                p.sendMessage(Strings.prefix +"§7Es findet gerade kein Event statt.");
            }
        }

        return false;
    }
}
