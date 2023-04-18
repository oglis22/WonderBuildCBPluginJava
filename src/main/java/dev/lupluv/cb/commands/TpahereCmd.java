package dev.lupluv.cb.commands;

import dev.lupluv.cb.annotations.RegisterCommand;
import dev.lupluv.cb.utils.Strings;
import dev.lupluv.cb.utils.TpRequest;
import dev.lupluv.cb.utils.TpType;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

@RegisterCommand(name = "tpahere", permission = "cb.tpa.here")
public class TpahereCmd implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if(commandSender instanceof Player){
            Player p = (Player) commandSender;
            if(!p.hasPermission("cb.tpa.here")){
                p.sendMessage(Strings.noPerms);
            }else{
                if(strings.length == 1){
                    Player t = Bukkit.getPlayer(strings[0]);
                    if(t != null){
                        if(t != p) {
                            if (TpRequest.getRequest(p) != null) TpRequest.getRequest(p).deny();
                            TpRequest r = new TpRequest(p, t, TpType.HERE);
                            r.create();
                            p.sendMessage(Strings.prefix + "Du hast eine Teleport Here Anfrage an " + t.getName() + " geschickt");
                            t.sendMessage(Strings.prefix + p.getName() + " hat dir eine Teleport Here Anfrage geschickt, akzeptiere sie mit /tpaccept");
                            TextComponent accept = new TextComponent(Strings.prefix + p.getName()
                                    + " hat dir eine Teleport Here Anfrage geschickt, akzeptiere sie mit ");
                            TextComponent button = new TextComponent("§a§l/tpaccept");
                            button.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpaccept " + r.getSender().getName()));
                            accept.addExtra(button);
                            t.spigot().sendMessage(accept);
                        }else{
                            p.sendMessage(Strings.prefix + "Du kannst dir selber keine Teleport Anfrage senden");
                        }
                    }else{
                        p.sendMessage(Strings.playerNotFound);
                    }
                }else{
                    p.sendMessage(Strings.prefix + "Benutzung: /tpahere <spieler>");
                }
            }
        }

        return false;
    }
}
