package dev.lupluv.cb.commands;

import dev.lupluv.cb.Citybuild;
import dev.lupluv.cb.annotations.RegisterCommand;
import dev.lupluv.cb.utils.Strings;
import dev.lupluv.cb.utils.Warp;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;
import org.jetbrains.annotations.NotNull;

@RegisterCommand(name = "warp", permission = "cb.warp.warp", aliases = {"w"})
public class WarpCmd implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if(commandSender instanceof Player){
            Player p = (Player) commandSender;
            if(!p.hasPermission("cb.warp.warp")){
                p.sendMessage(Strings.noPerms);
            }else{
                if(strings.length == 1){
                    String name = strings[0];
                    Warp warp = new Warp(name);
                    if(warp.exists()){
                        warp.load();
                        warp.teleport(p);
                    }else{
                        p.sendMessage(Strings.prefix + "Der Warp '" + name + "' existiert nicht");
                    }
                }else if(strings.length == 0){
                    p.openInventory(Citybuild.getInventoryManager().getBeautifulWarpMenuInventory(p));
                }else{
                    p.sendMessage(Strings.prefix + "Nutze: /warp [name]");
                }
            }
        }

        return false;
    }
}
