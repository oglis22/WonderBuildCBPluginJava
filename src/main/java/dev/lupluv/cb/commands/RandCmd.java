package dev.lupluv.cb.commands;

import dev.lupluv.cb.annotations.RegisterCommand;
import dev.lupluv.cb.utils.Strings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

//Todo: @RegisterCommand(name = "rand", permission = "cb.plot.rand")
public class RandCmd implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(sender instanceof Player){
            Player p = (Player) sender;
            if(!p.hasPermission("cb.plot.rand")){
                p.sendMessage(Strings.noPerms);
            }else{
                if(args.length == 0){
                    // TODO: Change plot rand
                }
            }
        }

        return false;
    }
}
