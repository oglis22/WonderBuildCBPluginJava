package dev.lupluv.cb.commands;


import dev.lupluv.cb.annotations.RegisterCommand;
import dev.lupluv.cb.utils.Strings;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionDefault;
import org.jetbrains.annotations.NotNull;

@RegisterCommand(name = "bewerben", aliases = {"apply"}, permissionDefault = PermissionDefault.TRUE, description = "Zeigt die Bewerbungsseite an")
public class BewerbenCmd implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(!(sender instanceof Player)) return true;
        Player player = (Player) sender;
        if(args.length == 0){
            player.sendMessage(" ");
            player.sendMessage(Strings.prefix + "§7Bewerbe dich jetzt auf unserem §aDiscord §7Server oder auf unserer §aWebsite");
            player.sendMessage(Strings.prefix + "§aDiscord: §7https://discord.wonderbuild.net");
            player.sendMessage(Strings.prefix + "§aWebsite: §7https://wonderbuild.net");
            player.sendMessage(" ");
        }else{
            player.sendMessage(Strings.prefix + "Benutzung /bewerben");
        }

        return false;
    }
}
