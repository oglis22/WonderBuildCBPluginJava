package dev.lupluv.cb.commands;

import dev.lupluv.cb.annotations.RegisterCommand;
import dev.lupluv.cb.economy.Economy;
import dev.lupluv.cb.utils.Strings;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@RegisterCommand(name = "payall", permission = "cb.pay.all")
public class PayallCmd implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(sender instanceof Player){
            Player p = (Player) sender;
            if(!p.hasPermission("cb.pay.all")){
                p.sendMessage(Strings.noPerms);
            }else{
                if(args.length == 1){
                    try {
                        double amount = Double.parseDouble(args[0]);
                        double balance = Economy.getBalance(p.getUniqueId());
                        if(balance >= (Bukkit.getOnlinePlayers().size()-1)*amount){
                            if(Economy.withdrawPlayer(p.getUniqueId(), amount*(Bukkit.getOnlinePlayers().size()-1)).transactionSuccess()) {
                                Bukkit.getOnlinePlayers().forEach(all -> {
                                    if (all != p) {
                                        if(Economy.depositPlayer(all.getUniqueId(), amount).transactionSuccess()){
                                            all.sendMessage(Strings.prefix + "§7Jeder Spieler hat " + amount + " Coins von " + p.getName() + " erhalten.");
                                        }
                                    }
                                });
                                p.sendMessage(Strings.prefix + "§7Du hast jedem Spieler " + amount + " Coins gegeben. (" + (amount*(Bukkit.getOnlinePlayers().size()-1)) + ")");
                            }else{
                                p.sendMessage(Strings.prefix + "§cFehler.");
                            }
                        }else{
                            p.sendMessage(Strings.prefix + "§cDu hast nicht genug Geld dafür.");
                        }
                    }catch (NumberFormatException e){
                        p.sendMessage(Strings.prefix + "§cBitte gebe einen Wert an.");
                    }
                }else{
                    p.sendMessage(Strings.prefix + "§7Benutzung: /payall §a<Spieler>");
                }
            }
        }

        return false;
    }
}
