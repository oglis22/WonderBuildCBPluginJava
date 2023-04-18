package dev.lupluv.cb.commands;

import dev.lupluv.cb.Citybuild;
import dev.lupluv.cb.annotations.RegisterCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;
import org.jetbrains.annotations.NotNull;

@RegisterCommand(name = "vote", permissionDefault = PermissionDefault.TRUE, aliases = {"votegui"})
public class VoteCmd implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            p.openInventory(Citybuild.getInventoryManager().getVotingInventory(p));
        }
        return false;
    }
}
