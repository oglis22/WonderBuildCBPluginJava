package dev.lupluv.cb.commands;

import dev.lupluv.cb.annotations.RegisterCommand;
import dev.lupluv.cb.stats.StatsNPC;
import dev.lupluv.cb.utils.Strings;
import dev.lupluv.cb.utils.Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

@RegisterCommand(name = "setstats", permission = "cb.stats.set")
public class SetstatsCmd implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(sender instanceof Player){
            Player p = (Player) sender;
            if(!p.hasPermission("cb.stats.set")){
                p.sendMessage(Strings.noPerms);
            }else{
                if(args.length == 1){
                    if(args[0].equalsIgnoreCase("1")){
                        File file = new File("plugins//Citybuild//stats.yml");
                        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
                        Util.setLocationIntoFileConfiguration(cfg, "NPC.0", p.getLocation());
                        try {
                            cfg.save(file);
                            p.sendMessage(Strings.prefix + "§7Du hast §aerfolgreich §7Stats NPC #1 gesetzt.");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }else if(args[0].equalsIgnoreCase("2")){
                        File file = new File("plugins//Citybuild//stats.yml");
                        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
                        Util.setLocationIntoFileConfiguration(cfg, "NPC.1", p.getLocation());
                        try {
                            cfg.save(file);
                            p.sendMessage(Strings.prefix + "§7Du hast §aerfolgreich §7Stats NPC #2 gesetzt.");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }else if(args[0].equalsIgnoreCase("3")){
                        File file = new File("plugins//Citybuild//stats.yml");
                        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
                        Util.setLocationIntoFileConfiguration(cfg, "NPC.2", p.getLocation());
                        try {
                            cfg.save(file);
                            p.sendMessage(Strings.prefix + "§7Du hast §aerfolgreich §7Stats NPC #3 gesetzt.");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }else{
                        p.sendMessage(Strings.prefix + "§7Benutzung: /setstats §a<1/2/3>");
                    }
                }else{
                    p.sendMessage(Strings.prefix + "§7Benutzung: /setstats §a<1/2/3>");
                }
            }
        }

        return false;
    }
}
