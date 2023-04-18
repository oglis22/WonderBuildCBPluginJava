package dev.lupluv.cb.casino.coinflip;

import org.bukkit.entity.Player;

import java.util.Random;

public class Coinflip {

    Player p1;
    Player p2;

    double bet;

    CoinSide tip1;
    CoinSide tip2;

    CoinSide result;

    public void flip(){
        Random random = new Random();
        int i = random.nextInt(2);
        if(i == 1){
            result = CoinSide.HEAD;
        }else{
            result = CoinSide.NUMBER;
        }
    }

    public Coinflip(Player p1, Player p2) {
        this.p1 = p1;
        this.p2 = p2;
    }

    public Coinflip(Player p1) {
        this.p1 = p1;
        this.bet = 0;
    }

    public void sendMessage(String message){
        p1.sendMessage(message);
        p2.sendMessage(message);
    }

    public Player getP1() {
        return p1;
    }

    public void setP1(Player p1) {
        this.p1 = p1;
    }

    public Player getP2() {
        return p2;
    }

    public void setP2(Player p2) {
        this.p2 = p2;
    }

    public double getBet() {
        return bet;
    }

    public void setBet(double bet) {
        this.bet = bet;
    }

    public CoinSide getResult() {
        return result;
    }

    public void setResult(CoinSide result) {
        this.result = result;
    }

    public CoinSide getTip1() {
        return tip1;
    }

    public void setTip1(CoinSide tip1) {
        this.tip1 = tip1;
    }

    public CoinSide getTip2() {
        return tip2;
    }

    public void setTip2(CoinSide tip2) {
        this.tip2 = tip2;
    }

    public void higherBet(double amount){
        this.bet+=amount;
    }

    public void lowerBet(double amount){
        this.bet-=amount;
    }

}
