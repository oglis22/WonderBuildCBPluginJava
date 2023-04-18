package dev.lupluv.cb.commands;

import dev.lupluv.cb.annotations.RegisterCommand;
import dev.lupluv.cb.utils.Strings;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@RegisterCommand(name = "gamemode", permission = "cb.gamemode", aliases = {"gm"})
public class GamemodeCmd implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if(commandSender instanceof Player){
            Player p = (Player) commandSender;
            if(!p.hasPermission("cb.gamemode")){
                p.sendMessage(Strings.noPerms);
            }else
            if(strings.length == 1){
                if(strings[0].equalsIgnoreCase("0") || strings[0].equalsIgnoreCase("s") || strings[0].equalsIgnoreCase("survival")){
                    p.setGameMode(GameMode.SURVIVAL);
                    p.sendMessage(Strings.prefix + "Du bist nun im Survival Modus");
                }else if(strings[0].equalsIgnoreCase("1") || strings[0].equalsIgnoreCase("c") || strings[0].equalsIgnoreCase("creative")){
                    p.setGameMode(GameMode.CREATIVE);
                    p.sendMessage(Strings.prefix + "Du bist nun im Creative Modus");
                }else if(strings[0].equalsIgnoreCase("2") || strings[0].equalsIgnoreCase("a") || strings[0].equalsIgnoreCase("adventure")){
                    p.setGameMode(GameMode.ADVENTURE);
                    p.sendMessage(Strings.prefix + "Du bist nun im Adventure Modus");
                }else if(strings[0].equalsIgnoreCase("3") || strings[0].equalsIgnoreCase("spec") || strings[0].equalsIgnoreCase("spectator")){
                    p.setGameMode(GameMode.SPECTATOR);
                    p.sendMessage(Strings.prefix + "Du bist nun im Spectator Modus!");
                }else
                    p.sendMessage(Strings.prefix + "Benutzung: /gm <0/1/2/3> [spieler]");
            }else if(strings.length == 2){
                Player t = Bukkit.getPlayer(strings[1]);
                if(t != null){
                    if(strings[0].equalsIgnoreCase("0") || strings[0].equalsIgnoreCase("s") || strings[0].equalsIgnoreCase("survival")){
                        t.setGameMode(GameMode.SURVIVAL);
                        t.sendMessage(Strings.prefix + "Du bist nun im Survival Modus");
                        p.sendMessage(Strings.prefix + t.getName() + " ist nun im Survival Modus");
                    }else if(strings[0].equalsIgnoreCase("1") || strings[0].equalsIgnoreCase("c") || strings[0].equalsIgnoreCase("creative")){
                        t.setGameMode(GameMode.CREATIVE);
                        t.sendMessage(Strings.prefix + "Du bist nun im Creative Modus");
                        p.sendMessage(Strings.prefix + t.getName() + " ist nun im Creative Modus");
                    }else if(strings[0].equalsIgnoreCase("2") || strings[0].equalsIgnoreCase("a") || strings[0].equalsIgnoreCase("adventure")){
                        t.setGameMode(GameMode.ADVENTURE);
                        t.sendMessage(Strings.prefix + "Du bist nun im Adventure Modus");
                        p.sendMessage(Strings.prefix + t.getName() + " ist nun im Adventure Modus");
                    }else if(strings[0].equalsIgnoreCase("3") || strings[0].equalsIgnoreCase("spec") || strings[0].equalsIgnoreCase("spectator")){
                        t.setGameMode(GameMode.SPECTATOR);
                        t.sendMessage(Strings.prefix + "Du bist nun im Spectator Modus");
                        p.sendMessage(Strings.prefix + t.getName() + " ist nun im Spectator Modus");
                    }else
                        p.sendMessage(Strings.prefix + "Benutzung: /gm <0/1/2/3> [spieler]");
                }else
                    p.sendMessage(Strings.playerNotFound);
            }else
                p.sendMessage(Strings.prefix + "Benutzung: /gm <0/1/2/3> [spieler]");
        }

        return false;
    }
}
