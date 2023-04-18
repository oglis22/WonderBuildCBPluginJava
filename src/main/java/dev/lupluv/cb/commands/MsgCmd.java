package dev.lupluv.cb.commands;

import dev.lupluv.cb.annotations.RegisterCommand;
import dev.lupluv.cb.utils.Strings;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RegisterCommand(name = "msg", aliases = {"message"}, permission = "cb.msg")
public class MsgCmd implements CommandExecutor {

    public static Map<UUID, UUID> lastDiscussions = new HashMap<>();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(sender instanceof Player){
            Player p = (Player) sender;
            if(!p.hasPermission("cb.msg")){
                p.sendMessage(Strings.noPerms);
            }else{
                if(args.length >= 2){
                    Player t = Bukkit.getPlayer(args[0]);
                    if(t != null){
                        if(!p.getName().equalsIgnoreCase(t.getName())) {
                            args[0] = "";
                            String msg = String.join(" ", args);
                            p.sendMessage(Strings.prefix + "§e[§6Du §7-> §6" + t.getName() + "§e]" + msg);
                            t.sendMessage(Strings.prefix + "§e[§6" + p.getName() + " §7-> §6Du§e]" + msg);
                            if(lastDiscussions.containsKey(p.getUniqueId())) {
                                if (lastDiscussions.get(p.getUniqueId()) != t.getUniqueId()) {
                                    lastDiscussions.remove(p.getUniqueId());
                                    lastDiscussions.put(p.getUniqueId(), t.getUniqueId());
                                }
                            }else{
                                lastDiscussions.put(p.getUniqueId(), t.getUniqueId());
                            }
                        }else{
                            p.sendMessage(Strings.prefix + "§cDu kannst keine Unterhaltung mit dir selbst führen.");
                        }
                    }else{
                        p.sendMessage(Strings.playerNotFound);
                    }
                }else{
                    p.sendMessage(Strings.prefix + "§7Benutzung: /msg §a<Spieler> <Nachricht>");
                }
            }
        }

        return false;
    }
}
