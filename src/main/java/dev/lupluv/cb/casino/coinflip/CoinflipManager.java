package dev.lupluv.cb.casino.coinflip;

import dev.lupluv.cb.utils.Strings;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoinflipManager {

    public Map<Player, CoinflipUI> openedUIs;

    private List<Coinflip> runningGames;
    private Map<Player, Coinflip> waitingGames;
    private Map<Player, Coinflip> creations;

    public CoinflipManager() {
        runningGames = new ArrayList<>();
        openedUIs = new HashMap<>();
        waitingGames = new HashMap<>();
        creations = new HashMap<>();
    }

    public Coinflip getCoinflip(Player player){
        for(Coinflip all : runningGames){
            if(all.getP1() == player || all.getP2() == player){
                return all;
            }
        }
        return null;
    }

    public void createGame(Player player1){
        if(getCoinflip(player1) != null){
            player1.sendMessage(Strings.prefix + "§cDu bist bereits in einem Coinflip Game.");
            return;
        }
        Coinflip coinflip = new Coinflip(player1);
        waitingGames.put(player1, coinflip);
        coinflip.sendMessage(Strings.prefix + "§7Du hast ein Coinflip Game gestartet.");
    }

    public List<Coinflip> getRunningGames() {
        return runningGames;
    }

    public Map<Player, Coinflip> getWaitingGames() {
        return waitingGames;
    }

    public Map<Player, Coinflip> getCreations() {
        return creations;
    }

    public void addCreation(Player player, Coinflip coinflip){
        this.creations.put(player, coinflip);
    }
}
