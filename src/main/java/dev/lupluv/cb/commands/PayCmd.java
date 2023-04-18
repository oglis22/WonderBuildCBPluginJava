package dev.lupluv.cb.commands;

import dev.lupluv.cb.Citybuild;
import dev.lupluv.cb.annotations.RegisterCommand;
import dev.lupluv.cb.economy.Economy;
import dev.lupluv.cb.utils.Strings;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@RegisterCommand(name = "pay", permission = "cb.money.pay")
public class PayCmd implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if(commandSender instanceof Player){
            Player p = (Player) commandSender;
            if(!p.hasPermission("cb.money.pay")){
                p.sendMessage(Strings.noPerms);
            }else{
                if(strings.length == 2){
                    Player t = Bukkit.getPlayer(strings[0]);
                    if(t != null){
                        if(t != p) {
                            try {
                                long amount = Long.parseLong(strings[1]);
                                if (amount >= 1) {
                                    if (Economy.getBalance(p.getUniqueId()) >= amount) {
                                        Economy.transferMoney(p.getUniqueId(), t.getUniqueId(), amount);
                                        p.sendMessage(Strings.prefix + "Du hast " + t.getName() + " " + amount + " Coins überwiesen");
                                        t.sendMessage(Strings.prefix + p.getName() + " hat dir " + amount + " Coins überwiesen");
                                    } else {
                                        p.sendMessage(Strings.prefix + "Du hast nicht genug Geld");
                                    }
                                } else {
                                    p.sendMessage(Strings.prefix + "Du musst mindestens 1wc überweisen");
                                }
                            } catch (NumberFormatException e) {
                                p.sendMessage(Strings.prefix + "Bitte benutze ein Zahl");
                            }
                        }else{
                            p.sendMessage(Strings.prefix + "Du kannst kein Geld an dich selbst überweisen");
                        }
                    }else{
                        p.sendMessage(Strings.playerNotFound);
                    }
                }else{
                    p.sendMessage(Strings.prefix + "Benutzung: /pay <spieler> <amount>");
                }
            }
        }

        return false;
    }
}
