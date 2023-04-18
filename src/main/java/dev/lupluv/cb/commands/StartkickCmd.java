package dev.lupluv.cb.commands;

import dev.lupluv.cb.annotations.RegisterCommand;
import dev.lupluv.cb.moderation.Startkick;
import dev.lupluv.cb.utils.Strings;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@RegisterCommand(name = "startkick", permission = "cb.startkick")
public class StartkickCmd implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(sender instanceof Player){
            Player p = (Player) sender;
            if(!p.hasPermission("cb.startkick")){
                p.sendMessage(Strings.noPerms);
            }else{
                if(args.length == 1){
                    Player target = Bukkit.getPlayer(args[0]);
                    if(target != null){
                        if(target.hasPermission("cb.startkick.exempt") && target.getPlayerTime() < 100000){
                            p.sendMessage(Strings.prefix + "§cDu darfst " + target.getName() + " nicht Startkicken.");
                            return true;
                        }
                        if(Startkick.getStartKick(target.getUniqueId()) != null){
                            p.sendMessage(Strings.prefix + "§c" + target.getName() + " hat bereits einen Startkick.");
                            return true;
                        }

                        // Startkick
                        Startkick startkick = new Startkick(target.getUniqueId(), target.getName(), p.getUniqueId(), p.getName());
                        startkick.setRemainingSeconds(60);
                        startkick.sendAnnounce();
                        startkick.startScheduler();

                    }else{
                        p.sendMessage(Strings.playerNotFound);
                    }
                }else{
                    p.sendMessage(Strings.prefix + "§7Benutzung: /startkick §a<Spieler>");
                }
            }
        }

        return false;
    }
}
