package dev.lupluv.cb.commands;

import dev.lupluv.cb.Citybuild;
import dev.lupluv.cb.annotations.RegisterCommand;
import dev.lupluv.cb.utils.Strings;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@RegisterCommand(name = "neustart", permission = "cb.restart")
public class NeustartCmd implements CommandExecutor {

    public static int taskID = -1;

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(!(sender instanceof Player)) return true;
        Player player = (Player) sender;

        if(!player.hasPermission("cb.restart")){
            player.sendMessage(Strings.noPerms);
            return true;
        }

        if(args.length == 0){
            if(taskID != -1){
                player.sendMessage(Strings.prefix + "§cEs ist bereits ein Neustart geplant.");
                return true;
            }
            taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Citybuild.getPlugin(), new Runnable() {
                int i = 60;
                @Override
                public void run() {
                    if(i == 60 || i == 30 || i == 15 || i <= 5){
                        if(i == 10){
                            Bukkit.getWorld("cb").save();
                        }
                        if(i != 0){
                            Bukkit.broadcastMessage(Strings.prefix + "§c§lDer Server startet in " + i + " Sekunden neu!");
                        }else{
                            Bukkit.getOnlinePlayers().forEach(Citybuild::sendPlayerToLobby);
                            Bukkit.shutdown();
                        }
                    }
                    i--;
                }
            }, 0, 20);
        }else if(args.length == 1){
            if(args[0].equalsIgnoreCase("abort")){
                if(taskID == -1){
                    player.sendMessage(Strings.prefix + "§cEs ist kein Neustart geplant.");
                    return true;
                }
                Bukkit.getScheduler().cancelTask(taskID);
                taskID = -1;
                Bukkit.broadcastMessage(Strings.prefix + "§aDer Neustart wurde abgebrochen.");
            }
        }

        return false;
    }
}
