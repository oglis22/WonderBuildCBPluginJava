package dev.lupluv.cb.commands;

import dev.lupluv.cb.annotations.RegisterCommand;
import dev.lupluv.cb.economy.BankUI;
import dev.lupluv.cb.utils.Strings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@RegisterCommand(name = "bank", permission = "cb.bank", description = "Öffnet das Bankmenü")
public class BankCmd implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(!(sender instanceof Player player)) return true;

        if(!player.hasPermission("cb.bank.use")){
            player.sendMessage(Strings.noPerms);
            return true;
        }

        if(args.length == 0){
            new BankUI(player).setMainGUI().openGUI();
        }else{
            player.sendMessage(Strings.prefix + "§7Benutzung: /bank");
        }

        return false;
    }
}
