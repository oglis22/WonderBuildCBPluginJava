package dev.lupluv.cb.commands;

import dev.lupluv.cb.Citybuild;
import dev.lupluv.cb.annotations.RegisterCommand;
import dev.lupluv.cb.utils.Strings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@RegisterCommand(name = "myplot", permission = "cb.plots", aliases = {"wbplot", "wbplots", "plotmenu", "plotgui"})
public class PlotsCmd implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(sender instanceof Player){
            Player p = (Player) sender;
            if(!p.hasPermission("cb.plots")){
                p.sendMessage(Strings.noPerms);
            }else{
                if(args.length == 0){
                    p.openInventory(Citybuild.getInventoryManager().getPlotsHomeInventory(p));
                    return true;
                }
                if(args[0].equalsIgnoreCase("create")){
                    p.openInventory(Citybuild.getInventoryManager().getPlotsCreateInventory(p));
                }else{
                    p.openInventory(Citybuild.getInventoryManager().getPlotsHomeInventory(p));
                }
            }
        }

        return true;
    }
}
