package dev.lupluv.cb.commands;

import dev.lupluv.cb.annotations.RegisterCommand;
import dev.lupluv.cb.utils.Strings;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@RegisterCommand(name = "reply", permission = "cb.reply", aliases = {"r"})
public class ReplyCmd implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(sender instanceof Player){
            Player p = (Player) sender;
            if(!p.hasPermission("cb.reply")){
                p.sendMessage(Strings.noPerms);
            }else{
                if(args.length >= 1){
                    if(!MsgCmd.lastDiscussions.containsKey(p.getUniqueId())){
                        p.sendMessage(Strings.prefix + "§cKeine letzte Unterhaltung.");
                        return true;
                    }
                    Player t = Bukkit.getPlayer(MsgCmd.lastDiscussions.get(p.getUniqueId()));
                    if(t != null && t.isOnline()){
                        if(!p.getName().equalsIgnoreCase(t.getName())) {
                            String msg = String.join(" ", args);
                            p.sendMessage(Strings.prefix + "§e[§6Du §7-> §6" + t.getName() + "§e] " + msg);
                            t.sendMessage(Strings.prefix + "§e[§6" + p.getName() + " §7-> §6Du§e] " + msg);
                        }else{
                            p.sendMessage(Strings.prefix + "§cDu kannst keine Unterhaltung mit dir selbst führen.");
                        }
                    }else{
                        p.sendMessage(Strings.prefix + "§cKeine letzte Unterhaltung.");
                    }
                }else{
                    p.sendMessage(Strings.prefix + "§7Benutzung: /reply §a<Nachricht>");
                }
            }
        }

        return false;
    }
}
