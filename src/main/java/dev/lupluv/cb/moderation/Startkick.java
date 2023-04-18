package dev.lupluv.cb.moderation;

import dev.lupluv.cb.Citybuild;
import dev.lupluv.cb.utils.Strings;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class Startkick {

    public static Map<UUID, Startkick> startkickMap = new HashMap<>();

    public static Startkick getStartKick(UUID uuid){
        return startkickMap.get(uuid);
    }

    public static Startkick getStartKick(String name){
        for(Startkick startkick : startkickMap.values()){
            if(startkick.toBeKickedName.equalsIgnoreCase(name)){
                return startkick;
            }
        }
        return null;
    }

    UUID toBeKicked;
    String toBeKickedName;
    UUID initiator;
    String initiatorName;

    int yesVotes;
    int noVotes;

    List<UUID> alreadyVoted;

    long remainingSeconds;

    int taskID;

    public void sendAnnounce(){
        TextComponent txt = new TextComponent(Strings.prefix + "§7Vote jetzt §8| ");
        TextComponent yesBtn = new TextComponent("§a§lJA");
        yesBtn.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/ja " + toBeKickedName));
        yesBtn.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7Klicke um für Ja abzustimmen.").create()));
        TextComponent noBtn = new TextComponent("§c§lNEIN");
        noBtn.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/nein " + toBeKickedName));
        noBtn.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§7Klicke um für Nein abzustimmen.").create()));
        TextComponent spacer  = new TextComponent(" §7- ");
        txt.addExtra(yesBtn);
        txt.addExtra(spacer);
        txt.addExtra(noBtn);
        Bukkit.getOnlinePlayers().forEach(all ->{
            all.sendMessage(Strings.prefix + "§8--------------------------------------------");
            all.sendMessage(Strings.prefix + " ");
            all.sendMessage(Strings.prefix + "§7Startkick für §a" + toBeKickedName);
            all.sendMessage(Strings.prefix + "§7von §c" + initiatorName);
            all.sendMessage(Strings.prefix + " ");
            all.sendMessage(txt);
            all.sendMessage(Strings.prefix + " ");
            all.sendMessage(Strings.prefix + "§8--------------------------------------------");
        });
    }

    public void startScheduler(){
        startkickMap.put(toBeKicked, this);
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(Citybuild.getPlugin(), () ->{
            if(remainingSeconds != 0) {
                if(remainingSeconds == 10){
                    Bukkit.getOnlinePlayers().forEach(all ->{
                        all.sendMessage(Strings.prefix + "§7Der Startkick wird in 10 Sekunden ausgerechnet.");
                    });
                }
                remainingSeconds--;
            }else{
                // Times up
                result();
                Bukkit.getScheduler().cancelTask(taskID);
            }
        }, 0, 20);
    }

    public void result(){
        if(yesVotes > noVotes){
            // Yes won
            Bukkit.getOnlinePlayers().forEach(all ->{
                all.sendMessage(Strings.prefix + "§8--------------------------------------------");
                all.sendMessage(Strings.prefix + " ");
                all.sendMessage(Strings.prefix + "§7Startkick für §a" + toBeKickedName);
                all.sendMessage(Strings.prefix + "§7von §c" + initiatorName);
                all.sendMessage(Strings.prefix + " ");
                all.sendMessage(Strings.prefix+ "§7Es wurde für §a§lJA §7abgestimmt");
                all.sendMessage(Strings.prefix + "§a" + toBeKickedName + " §7wurde für §a5 Minuten §7gebannt");
                all.sendMessage(Strings.prefix + " ");
                all.sendMessage(Strings.prefix + "§8--------------------------------------------");
            });
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tempban " + toBeKickedName + " 5m @Startkick-Ban");
        } else if (noVotes > yesVotes) {
            // No won
            Bukkit.getOnlinePlayers().forEach(all ->{
                all.sendMessage(Strings.prefix + "§8--------------------------------------------");
                all.sendMessage(Strings.prefix + " ");
                all.sendMessage(Strings.prefix + "§7Startkick für §a" + toBeKickedName);
                all.sendMessage(Strings.prefix + "§7von §c" + initiatorName);
                all.sendMessage(Strings.prefix + " ");
                all.sendMessage(Strings.prefix+ "§7Es wurde für §c§lNEIN §7abgestimmt");
                all.sendMessage(Strings.prefix + " ");
                all.sendMessage(Strings.prefix + "§8--------------------------------------------");
            });
        } else {
            // Draw
            Bukkit.getOnlinePlayers().forEach(all ->{
                all.sendMessage(Strings.prefix + "§8--------------------------------------------");
                all.sendMessage(Strings.prefix + " ");
                all.sendMessage(Strings.prefix + "§7Startkick für §a" + toBeKickedName);
                all.sendMessage(Strings.prefix + "§7von §c" + initiatorName);
                all.sendMessage(Strings.prefix + " ");
                all.sendMessage(Strings.prefix+ "§7Die Abstimmung ist mit einem §eUnentschieden §7ausgegangen");
                all.sendMessage(Strings.prefix + " ");
                all.sendMessage(Strings.prefix + "§8--------------------------------------------");
            });
        }
        startkickMap.remove(toBeKicked);
    }

    public Startkick() {
        alreadyVoted = new ArrayList<>();
    }

    public Startkick(UUID toBeKicked, String toBeKickedName, UUID initiator, String initiatorName) {
        this.toBeKicked = toBeKicked;
        this.toBeKickedName = toBeKickedName;
        this.initiator = initiator;
        this.initiatorName = initiatorName;
        alreadyVoted = new ArrayList<>();
    }

    public String getInitiatorName() {
        return initiatorName;
    }

    public void setInitiatorName(String initiatorName) {
        this.initiatorName = initiatorName;
    }

    public UUID getToBeKicked() {
        return toBeKicked;
    }

    public void setToBeKicked(UUID toBeKicked) {
        this.toBeKicked = toBeKicked;
    }

    public String getToBeKickedName() {
        return toBeKickedName;
    }

    public void setToBeKickedName(String toBeKickedName) {
        this.toBeKickedName = toBeKickedName;
    }

    public UUID getInitiator() {
        return initiator;
    }

    public void setInitiator(UUID initiator) {
        this.initiator = initiator;
    }

    public int getYesVotes() {
        return yesVotes;
    }

    public void setYesVotes(int yesVotes) {
        this.yesVotes = yesVotes;
    }

    public int getNoVotes() {
        return noVotes;
    }

    public void setNoVotes(int noVotes) {
        this.noVotes = noVotes;
    }

    public void addYes(Player p){
        if(!this.alreadyVoted.contains(p.getUniqueId())) {
            this.yesVotes++;
            this.alreadyVoted.add(p.getUniqueId());
        }
    }

    public void addNo(Player p){
        if(!this.alreadyVoted.contains(p.getUniqueId())) {
            this.noVotes++;
            this.alreadyVoted.add(p.getUniqueId());
        }
    }

    public boolean hasVoted(Player p){
        return this.alreadyVoted.contains(p.getUniqueId());
    }

    public long getRemainingSeconds() {
        return remainingSeconds;
    }

    public void setRemainingSeconds(long remainingSeconds) {
        this.remainingSeconds = remainingSeconds;
    }
}
