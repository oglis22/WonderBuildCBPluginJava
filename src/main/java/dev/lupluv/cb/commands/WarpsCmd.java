package dev.lupluv.cb.commands;

import dev.lupluv.cb.Citybuild;
import dev.lupluv.cb.annotations.RegisterCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@RegisterCommand(name = "warps", permission = "cb.warps.admin", aliases = {"warplist", "warpslist"})
public class WarpsCmd implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if(commandSender instanceof Player){
            Player p = (Player) commandSender;
            if(strings.length == 1){
                if(strings[0].equalsIgnoreCase("admin")){
                    if(p.hasPermission("cb.warps.admin")){
                        p.openInventory(Citybuild.getInventoryManager().getWarpsInventory(p));
                    }else{
                        p.performCommand("warp");
                    }
                }else{
                    p.performCommand("warp");
                }
            }else{
                p.performCommand("warp");
            }
        }

        return false;
    }
}
