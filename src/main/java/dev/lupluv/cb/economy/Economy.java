package dev.lupluv.cb.economy;

import dev.lupluv.cb.Citybuild;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class Economy {

    public static Economy getRank(int i){
        if(Citybuild.getMySQL().isConnected()){
                try {
                    ResultSet rs = Citybuild.getMySQL().getResult(
                            "SELECT * FROM cb_economy ORDER BY money DESC"
                    );
                    int p = 1;
                    while (rs.next()){
                        if(p < i){
                            p++;
                        }else if(p == i){
                            return new Economy(UUID.fromString(rs.getString("uuid")), rs.getString("name"), rs.getDouble("money"));
                        }else{
                            break;
                        }
                    }
                }catch (SQLException e){
                    e.printStackTrace();
                }
        }
        return null;
    }

    public static void transferMoney(UUID u1, UUID u2, double amount){
        withdrawPlayer(u1, amount);
        depositPlayer(u2, amount);
    }

    public static void correctName(Player p){
        Economy eco = new Economy(p.getUniqueId());
        if(!eco.existsByUuid()){
            eco.setMoney(100);
            eco.setPlayerName(p.getName());
            eco.create();
        }else{
            eco.loadByUuid();
            if(!eco.getPlayerName().equals(p.getName())) {
                eco.setPlayerName(p.getName());
                eco.update();
            }
        }
    }

    public static double getBalance(String name){
        Economy eco = new Economy(name);
        if(eco.existsByName()) {
            eco.loadByName();
            return eco.getMoney();
        }
        return 0;
    }

    public static double getBalance(OfflinePlayer p){
        Economy eco = new Economy(p.getUniqueId());
        if(eco.existsByUuid()) {
            eco.loadByUuid();
            return eco.getMoney();
        }
        return 0;
    }

    public static double getBalance(UUID uuid){
        Economy eco = new Economy(uuid);
        if(eco.existsByUuid()) {
            eco.loadByUuid();
            return eco.getMoney();
        }
        return 0;
    }

    public static EconomyResponse depositPlayer(String p, double amount){
        if (p == null) {
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Player name can not be null.");
        }
        if (amount < 0) {
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Cannot deposit negative funds");
        }

        Economy eco = new Economy(p);

        if(!eco.existsByName()) return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "User does not exist!");

        eco.loadByName();
        eco.addMoney(amount);
        eco.update();
        return new EconomyResponse(amount, getBalance(p), EconomyResponse.ResponseType.SUCCESS, null);
    }

    public static EconomyResponse depositPlayer(OfflinePlayer p, double amount){
        if (p == null) {
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Player name can not be null.");
        }
        if (amount < 0) {
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Cannot deposit negative funds");
        }

        Economy eco = new Economy(p.getUniqueId());

        if(!eco.existsByUuid()) return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "User does not exist!");

        eco.loadByUuid();
        eco.addMoney(amount);
        eco.update();
        return new EconomyResponse(amount, getBalance(p), EconomyResponse.ResponseType.SUCCESS, null);
    }

    public static EconomyResponse depositPlayer(UUID p, double amount){
        if (p == null) {
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Player name can not be null.");
        }
        if (amount < 0) {
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Cannot deposit negative funds");
        }

        Economy eco = new Economy(p);

        if(!eco.existsByUuid()) return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "User does not exist!");

        eco.loadByUuid();
        eco.addMoney(amount);
        eco.update();
        return new EconomyResponse(amount, getBalance(p), EconomyResponse.ResponseType.SUCCESS, null);
    }

    public static EconomyResponse withdrawPlayer(String p, double amount) {
        if (p == null) {
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Player name cannot be null!");
        }
        if (amount < 0) {
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Cannot withdraw negative funds!");
        }

        Economy eco = new Economy(p);

        if(!eco.existsByName()) return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "User does not exist!");

        eco.loadByName();
        eco.removeMoney(amount);
        eco.update();
        return new EconomyResponse(amount, getBalance(p), EconomyResponse.ResponseType.SUCCESS, null);
    }

    public static EconomyResponse withdrawPlayer(OfflinePlayer p, double amount) {
        if (p == null) {
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Player name cannot be null!");
        }
        if (amount < 0) {
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Cannot withdraw negative funds!");
        }

        Economy eco = new Economy(p.getUniqueId());

        if(!eco.existsByUuid()) return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "User does not exist!");

        eco.loadByUuid();
        eco.removeMoney(amount);
        eco.update();
        return new EconomyResponse(amount, getBalance(p), EconomyResponse.ResponseType.SUCCESS, null);
    }

    public static EconomyResponse withdrawPlayer(UUID p, double amount) {
        if (p == null) {
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Player name cannot be null!");
        }
        if (amount < 0) {
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Cannot withdraw negative funds!");
        }

        Economy eco = new Economy(p);

        if(!eco.existsByUuid()) return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "User does not exist!");

        eco.loadByUuid();
        eco.removeMoney(amount);
        eco.update();
        return new EconomyResponse(amount, getBalance(p), EconomyResponse.ResponseType.SUCCESS, null);
    }

    private UUID playerUUID;
    private String playerName;

    private double money;

    public Economy() {
    }

    public Economy(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }

    public Economy(String playerName) {
        this.playerName = playerName;
    }

    public Economy(UUID playerUUID, String playerName, double money) {
        this.playerUUID = playerUUID;
        this.playerName = playerName;
        this.money = money;
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public void setPlayerUUID(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public void addMoney(){
        this.money++;
    }

    public void addMoney(double amount){
        this.money+=amount;
    }

    public void removeMoney(){
        this.money--;
    }

    public void removeMoney(double amount){
        this.money-=amount;
    }

    public String getStringValue(double d){
        return String.valueOf(d);
    }

    public boolean existsByName(){
        if(Citybuild.getMySQL().isConnected()){
            try{
                ResultSet rs = Citybuild.getMySQL().getResult(
                        "SELECT * FROM cb_economy WHERE name = ?",
                        this.playerName
                );
                return rs.next();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean existsByUuid(){
        if(Citybuild.getMySQL().isConnected()){
            try{
                ResultSet rs = Citybuild.getMySQL().getResult(
                        "SELECT * FROM cb_economy WHERE uuid = ?",
                        this.playerUUID.toString()
                );
                return rs.next();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return false;
    }

    public void create(){
        if(Citybuild.getMySQL().isConnected()){
            Citybuild.getMySQL().update(
                    "INSERT INTO cb_economy (uuid,name,money) VALUES (?,?,?)",
                    this.playerUUID.toString(),
                    this.playerName,
                    getStringValue(this.money)
            );
        }
    }

    public void update(){
        if(Citybuild.getMySQL().isConnected()){
            Citybuild.getMySQL().update(
                    "UPDATE cb_economy SET name = ?, money = ? WHERE uuid = ?",
                    this.playerName,
                    getStringValue(this.money),
                    this.playerUUID.toString()
            );
        }
    }

    public void loadByName(){
        if(Citybuild.getMySQL().isConnected()){
            try{
                ResultSet rs = Citybuild.getMySQL().getResult(
                        "SELECT * FROM cb_economy WHERE name = ?",
                        this.playerName
                );
                while (rs.next()){
                    this.playerUUID = UUID.fromString(rs.getString("uuid"));
                    this.money = rs.getDouble("money");
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void loadByUuid(){
        if(Citybuild.getMySQL().isConnected()){
            try{
                ResultSet rs = Citybuild.getMySQL().getResult(
                        "SELECT * FROM cb_economy WHERE uuid = ?",
                        this.playerUUID.toString()
                );
                while (rs.next()){
                    this.playerName = rs.getString("name");
                    this.money = rs.getDouble("money");
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void delete(){
        if(Citybuild.getMySQL().isConnected()){
            Citybuild.getMySQL().update(
                    "DELETE cb_economy WHERE uuid = ?",
                    this.playerUUID.toString()
            );
        }
    }

}
