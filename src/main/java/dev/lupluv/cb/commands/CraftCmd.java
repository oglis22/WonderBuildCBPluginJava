package dev.lupluv.cb.commands;

import dev.lupluv.cb.annotations.RegisterCommand;
import dev.lupluv.cb.utils.Strings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@RegisterCommand(name = "craft", aliases = {"werkbank", "crafting"}, permission = "cb.craft", description = "Crafting command")
public class CraftCmd implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(!(sender instanceof Player player)) return true;

        if(!player.hasPermission("cb.craft")){
            player.sendMessage(Strings.noPerms);
            return true;
        }

        if(args.length == 0){
            player.openWorkbench(null, true);
        }else{
            player.sendMessage(Strings.prefix + "§7Benutzung: /werkbank");
        }

        return false;
    }
}
