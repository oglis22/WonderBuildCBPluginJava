package dev.lupluv.cb.commands;

import dev.lupluv.cb.Citybuild;
import dev.lupluv.cb.annotations.RegisterCommand;
import dev.lupluv.cb.economy.Economy;
import dev.lupluv.cb.utils.Strings;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@RegisterCommand(name = "money", permission = "cb.money.see", aliases = {"balance", "geld", "guthaben", "bal"})
public class MoneyCmd implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if(commandSender instanceof Player){
            Player p = (Player) commandSender;
            if(strings.length > 1){
                if(!p.hasPermission("cb.money.manage")){
                    if(!p.hasPermission("cb.money.see")){
                        p.sendMessage(Strings.noPerms);
                    }else{
                        p.sendMessage(Strings.prefix + "Benutzung: /money [spieler]");
                    }
                }else{
                    if(strings.length == 3){
                        Player t = Bukkit.getPlayer(strings[0]);
                        if(t != null){
                            if(strings[1].equalsIgnoreCase("set")){
                                try {
                                    double amount = Double.parseDouble(strings[2]);
                                    if(amount >= 0){
                                        Economy eco = new Economy(t.getUniqueId());
                                        eco.loadByUuid();
                                        eco.setMoney(amount);
                                        eco.update();
                                        p.sendMessage(Strings.prefix + "Du hast " + t.getName() + "'s Kontostand auf " + amount + " Coins gesetzt");
                                    }else{
                                        p.sendMessage(Strings.prefix + "Du kannst den Kontostand nicht ins Minus setzen");
                                    }
                                }catch (NumberFormatException e){
                                    p.sendMessage(Strings.prefix + "Bitte gebe eine Zahl an");
                                }
                            }else if(strings[1].equalsIgnoreCase("add")){
                                try {
                                    long amount = Long.parseLong(strings[2]);
                                    if(amount >= 1){
                                        Economy.depositPlayer(t.getUniqueId(), amount);
                                        p.sendMessage(Strings.prefix + "Du hast " + t.getName() + " " + amount + " Coins gegeben");
                                        t.sendMessage(Strings.prefix + "§aDu hast " + amount + " Coins erhalten.");
                                    }else{
                                        p.sendMessage(Strings.prefix + "Du musst mindestens 1 Coins hinzufügen");
                                    }
                                }catch (NumberFormatException e){
                                    p.sendMessage(Strings.prefix + "Bitte gebe eine Zahl an");
                                }
                            }else if(strings[1].equalsIgnoreCase("remove")){
                                try {
                                    long amount = Long.parseLong(strings[2]);
                                    if(amount >= 1){
                                        if(Economy.getBalance(t.getUniqueId()) >= amount) {
                                            Economy.withdrawPlayer(t.getUniqueId(), amount);
                                            p.sendMessage(Strings.prefix + "Du hast " + t.getName() + " " + amount + " Coins entfernt!");
                                        }else{
                                            p.sendMessage(Strings.prefix + "Du kannst nicht mehr Geld entfernen als der Spieler hat");
                                        }
                                    }else{
                                        p.sendMessage(Strings.prefix + "Du musst mindestens 1 Coins entfernen!");
                                    }
                                }catch (NumberFormatException e){
                                    p.sendMessage(Strings.prefix + "Bitte gebe eine Zahl an!");
                                }
                            }
                        }else{
                            p.sendMessage(Strings.playerNotFound);
                        }
                    }else if(strings.length == 2){
                        Player t = Bukkit.getPlayer(strings[0]);
                        if(strings[1].equalsIgnoreCase("reset")){
                            if(t != null){
                                Economy eco = new Economy(t.getUniqueId());
                                eco.loadByUuid();
                                eco.setMoney(100);
                                eco.update();
                                p.sendMessage(Strings.prefix + "Du hast " + t.getName() + "'s Kontostand auf 0 Coins gesetzt");
                            }else{
                                p.sendMessage(Strings.playerNotFound);
                            }
                        }else{
                            p.sendMessage(Strings.prefix + "Nutze: /money [spieler] [add/remove/set/reset] [amount]");
                        }
                    }else{
                        p.sendMessage(Strings.prefix + "Nutze: /money [spieler] [add/remove/set/reset] [amount]");
                    }
                }
            }else{
                if(!p.hasPermission("cb.money.see")){
                    p.sendMessage(Strings.noPerms);
                }else{
                    if(strings.length == 0){
                        p.sendMessage(Strings.prefix + "Dein Kontostand: " + Economy.getBalance(p.getUniqueId()) + " Coins");
                    }else if(strings.length == 1){
                        OfflinePlayer t = Bukkit.getOfflinePlayer(strings[0]);
                        if(t != null){
                            if(new Economy(t.getUniqueId()).existsByUuid()){
                                p.sendMessage(Strings.prefix + t.getName() + "'s Kontostand: " + Economy.getBalance(t.getUniqueId()) + " Coins");
                            }else{
                                p.sendMessage(Strings.prefix + t.getName() + "'s Kontostand: 0 Coins");
                            }
                        }else{
                            p.sendMessage(Strings.playerNotFound);
                        }
                    }else{
                        if(!p.hasPermission("cb.money.manage")){
                            p.sendMessage(Strings.prefix + "Nutze: /money [spieler]");
                        }else{
                            p.sendMessage(Strings.prefix + "Nutze: /money [spieler] [add/remove/set/reset] [amount]");
                        }
                    }
                }
            }
        }else{
            if(strings.length > 1){
                    if(strings.length == 3){
                        Player t = Bukkit.getPlayer(strings[0]);
                        if(t != null){
                            if(strings[1].equalsIgnoreCase("set")){
                                try {
                                    double amount = Double.parseDouble(strings[2]);
                                    if(amount >= 0){
                                        Economy eco = new Economy(t.getUniqueId());
                                        eco.loadByUuid();
                                        eco.setMoney(amount);
                                        eco.update();
                                        commandSender.sendMessage(Strings.prefix + "Du hast " + t.getName() + "'s Kontostand auf " + amount + " Coins gesetzt");
                                    }else{
                                        commandSender.sendMessage(Strings.prefix + "Du kannst den Kontostand nicht ins Minus setzen");
                                    }
                                }catch (NumberFormatException e){
                                    commandSender.sendMessage(Strings.prefix + "Bitte gebe eine Zahl an");
                                }
                            }else if(strings[1].equalsIgnoreCase("add")){
                                try {
                                    long amount = Long.parseLong(strings[2]);
                                    if(amount >= 1){
                                        Economy.depositPlayer(t.getUniqueId(), amount);
                                        commandSender.sendMessage(Strings.prefix + "Du hast " + t.getName() + " " + amount + " Coins gegeben");
                                        t.sendMessage(Strings.prefix + "§aDu hast " + amount + " Coins erhalten.");
                                    }else{
                                        commandSender.sendMessage(Strings.prefix + "Du musst mindestens 1 Coins hinzufügen");
                                    }
                                }catch (NumberFormatException e){
                                    commandSender.sendMessage(Strings.prefix + "Bitte gebe eine Zahl an");
                                }
                            }else if(strings[1].equalsIgnoreCase("remove")){
                                try {
                                    long amount = Long.parseLong(strings[2]);
                                    if(amount >= 1){
                                        if(Economy.getBalance(t.getUniqueId()) >= amount) {
                                            Economy.withdrawPlayer(t.getUniqueId(), amount);
                                            commandSender.sendMessage(Strings.prefix + "Du hast " + t.getName() + " " + amount + " Coins entfernt!");
                                        }else{
                                            commandSender.sendMessage(Strings.prefix + "Du kannst nicht mehr Geld entfernen als der Spieler hat");
                                        }
                                    }else{
                                        commandSender.sendMessage(Strings.prefix + "Du musst mindestens 1 Coins entfernen!");
                                    }
                                }catch (NumberFormatException e){
                                    commandSender.sendMessage(Strings.prefix + "Bitte gebe eine Zahl an!");
                                }
                            }
                        }else{
                            commandSender.sendMessage(Strings.playerNotFound);
                        }
                    }else if(strings.length == 2){
                        Player t = Bukkit.getPlayer(strings[0]);
                        if(strings[1].equalsIgnoreCase("reset")){
                            if(t != null){
                                Economy eco = new Economy(t.getUniqueId());
                                eco.loadByUuid();
                                eco.setMoney(100);
                                eco.update();
                                commandSender.sendMessage(Strings.prefix + "Du hast " + t.getName() + "'s Kontostand auf 0 Coins gesetzt");
                            }else{
                                commandSender.sendMessage(Strings.playerNotFound);
                            }
                        }else{
                            commandSender.sendMessage(Strings.prefix + "Nutze: /money [spieler] [add/remove/set/reset] [amount]");
                        }
                    }else{
                        commandSender.sendMessage(Strings.prefix + "Nutze: /money [spieler] [add/remove/set/reset] [amount]");
                    }
            }else if(strings.length == 1){
                        OfflinePlayer t = Bukkit.getOfflinePlayer(strings[0]);
                        if(t != null){
                            if(new Economy(t.getUniqueId()).existsByUuid()){
                                commandSender.sendMessage(Strings.prefix + t.getName() + "'s Kontostand: " + Economy.getBalance(t.getUniqueId()) + " Coins");
                            }else{
                                commandSender.sendMessage(Strings.prefix + t.getName() + "'s Kontostand: 0 Coins");
                            }
                        }else{
                            commandSender.sendMessage(Strings.playerNotFound);
                        }
            }
        }

        return false;
    }
}
