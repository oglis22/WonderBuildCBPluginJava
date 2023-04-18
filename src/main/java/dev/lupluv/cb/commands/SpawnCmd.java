package dev.lupluv.cb.commands;

import dev.lupluv.cb.annotations.RegisterCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;
import org.jetbrains.annotations.NotNull;

@RegisterCommand(name = "spawn", permissionDefault = PermissionDefault.TRUE)
public class SpawnCmd implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if(commandSender instanceof Player){
            Player p = (Player) commandSender;
            p.performCommand("warp spawn");
        }

        return false;
    }
}
