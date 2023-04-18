package dev.lupluv.cb.utils;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class TpRequest {

    public static List<TpRequest> requests = new ArrayList<>();

    public static List<Player> inTeleport = new ArrayList<>();

    public static TpRequest getRequest(Player p){
        for(TpRequest r : requests){
            if(r.getReceiver() == p || r.getSender() == p){
                return r;
            }
        }
        return null;
    }

    Player sender;
    Player receiver;
    TpType type;

    public TpRequest() {
    }

    public TpRequest(Player sender, Player receiver, TpType type) {
        this.sender = sender;
        this.receiver = receiver;
        this.type = type;
    }

    public Player getSender() {
        return sender;
    }

    public void setSender(Player sender) {
        this.sender = sender;
    }

    public Player getReceiver() {
        return receiver;
    }

    public void setReceiver(Player receiver) {
        this.receiver = receiver;
    }

    public TpType getType() {
        return type;
    }

    public void setType(TpType type) {
        this.type = type;
    }

    public void create(){
        requests.add(this);
    }

    public void teleport(){
        if(type == TpType.NORMAL){
            inTeleport.add(sender);
            sender.teleport(receiver);
            inTeleport.remove(sender);
        }else if(type == TpType.HERE){
            inTeleport.add(receiver);
            receiver.teleport(sender);
            inTeleport.remove(receiver);
        }
    }

    public void accept(){
        teleport();
        receiver.sendMessage(Strings.prefix + "Du hast " + sender.getName() + "'s Teleport Anfrage angenommen");
        sender.sendMessage(Strings.prefix + receiver.getName() + " hat deine Teleport Anfrage angenommen");
        requests.remove(this);
    }

    public void deny(){
        sender.sendMessage(Strings.prefix + receiver.getName() + " hat deine Teleport Anfrage abgelehnt");
        receiver.sendMessage(Strings.prefix + "Du hast " + sender.getName() + "'s Teleport Anfrage abgelehnt");
        requests.remove(this);
    }

}
