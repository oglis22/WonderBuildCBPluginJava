package dev.lupluv.cb.commands;

import dev.lupluv.cb.annotations.RegisterCommand;
import dev.lupluv.cb.utils.Item;
import dev.lupluv.cb.utils.Strings;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@RegisterCommand(name = "skull", aliases = {"kopf", "head"}, permission = "cb.skull")
public class SkullCmd implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(!(sender instanceof Player)) return true;
        Player player = (Player) sender;

        if(!player.hasPermission("cb.skull")){
            player.sendMessage(Strings.noPerms);
            return true;
        }

        if(args.length == 1){
            String target = args[0];
            Item item = new Item(Material.PLAYER_HEAD);
            item.setSkullOwner(target);
            item.setDisplayName(target + "'s Kopf");
            player.getInventory().addItem(item.build());
            player.sendMessage(Strings.prefix + "§aDu hast " + target + "'s Kopf erhalten.");
        }else{
            player.sendMessage(Strings.prefix + "§cDu musst einen Spieler angeben.");
        }

        return false;
    }
}
