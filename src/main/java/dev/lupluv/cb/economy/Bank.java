package dev.lupluv.cb.economy;

import dev.lupluv.cb.Citybuild;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Bank {

    public static Bank loadPlayerBank(Player player){
        Bank bank = new Bank(player.getUniqueId().toString(), 0);
        if(!bank.existsByName()){
            bank.create();
        }else{
            bank.loadByName();
        }
        return bank;
    }

    public static Bank getRank(int i){
        if(Citybuild.getMySQL().isConnected()){
                try {
                    ResultSet rs = Citybuild.getMySQL().getResult(
                            "SELECT * FROM cb_economy_banks ORDER BY money DESC"
                    );
                    int p = 1;
                    while (rs.next()){
                        if(p < i){
                            p++;
                        }else if(p == i){
                            return new Bank(rs.getString("name"), rs.getDouble("money"));
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

    public static void transferMoney(String b1, String b2, double amount){
        withdrawBank(b1, amount);
        depositBank(b2, amount);
    }

    public static double getBalance(String name){
        Bank bank = new Bank(name);
        if(bank.existsByName()) {
            bank.loadByName();
            return bank.getMoney();
        }
        return 0;
    }

    public static EconomyResponse getBalanceResponse(String name){
        if (name == null) {
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Bank name can not be null.");
        }
        Bank bank = new Bank(name);
        if(bank.existsByName()) {
            bank.loadByName();
            return new EconomyResponse(-1, bank.getMoney(), EconomyResponse.ResponseType.SUCCESS, null);
        }
        return new EconomyResponse(-1, -1, EconomyResponse.ResponseType.FAILURE, "Bank account not found.");
    }

    public static EconomyResponse depositBank(String name, double amount){
        if (name == null) {
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Bank name can not be null.");
        }
        if (amount < 0) {
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Cannot deposit negative funds");
        }

        Bank bank = new Bank(name);

        if(!bank.existsByName()) return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "User does not exist!");

        bank.loadByName();
        bank.addMoney(amount);
        bank.update();
        return new EconomyResponse(amount, getBalance(name), EconomyResponse.ResponseType.SUCCESS, null);
    }

    public static EconomyResponse withdrawBank(String name, double amount) {
        if (name == null) {
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Bank name cannot be null!");
        }
        if (amount < 0) {
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Cannot withdraw negative funds!");
        }

        Bank bank = new Bank(name);

        if(!bank.existsByName()) return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "User does not exist!");

        bank.loadByName();
        bank.removeMoney(amount);
        bank.update();
        return new EconomyResponse(amount, getBalance(name), EconomyResponse.ResponseType.SUCCESS, null);
    }

    public static EconomyResponse has(String name, double amount){
        if (name == null) {
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "Bank name can not be null.");
        }
        EconomyResponse er = getBalanceResponse(name);
        if(er.transactionSuccess()){
            if(er.balance >= amount){
                return new EconomyResponse(amount, er.balance, EconomyResponse.ResponseType.SUCCESS, null);
            }else{
                return new EconomyResponse(amount, er.balance, EconomyResponse.ResponseType.FAILURE, "Not enough balance");
            }
        }
        return new EconomyResponse(0, 0, EconomyResponse.ResponseType.FAILURE, "I don't know what the fuck went wrong.");
    }

    public static EconomyResponse createBank(String name){
        Bank bank = new Bank(name, 0);
        if(bank.existsByName()){
            return new EconomyResponse(-1, -1, EconomyResponse.ResponseType.FAILURE, "Bank with that name already exists");
        }
        bank.create();
        if(bank.existsByName()){
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.SUCCESS, null);
        }
        return new EconomyResponse(-1, -1, EconomyResponse.ResponseType.FAILURE, "I don't know what the fuck went wrong.");
    }

    public static EconomyResponse deleteBank(String name){
        Bank bank = new Bank(name, 0);
        if(!bank.existsByName()){
            return new EconomyResponse(-1, -1, EconomyResponse.ResponseType.FAILURE, "Bank with that name doesn't exist");
        }
        bank.delete();
        if(!bank.existsByName()){
            return new EconomyResponse(0, 0, EconomyResponse.ResponseType.SUCCESS, null);
        }
        return new EconomyResponse(-1, -1, EconomyResponse.ResponseType.FAILURE, "I don't know what the fuck went wrong.");
    }

    public static List<Bank> getBanks(){
        List<Bank> banks = new ArrayList<>();
        if(Citybuild.getMySQL().isConnected()){
            try{
                ResultSet rs = Citybuild.getMySQL().getResult(
                        "SELECT * FROM cb_economy_banks"
                );
                while (rs.next()){
                    banks.add(new Bank(rs.getString("name"), rs.getDouble("money")));
                }
            }catch (SQLException ex){
                ex.printStackTrace();
            }
        }
        return banks;
    }

    public static List<String> getBankNames(){
        List<String> bankNames = new ArrayList<>();
        if(Citybuild.getMySQL().isConnected()){
            try{
                ResultSet rs = Citybuild.getMySQL().getResult(
                        "SELECT name FROM cb_economy_banks"
                );
                while (rs.next()){
                    bankNames.add(rs.getString("name"));
                }
            }catch (SQLException ex){
                ex.printStackTrace();
            }
        }
        return bankNames;
    }

    private String name;

    private double money;

    public Bank() {
    }

    public Bank(String name) {
        this.name = name;
    }

    public Bank(String name, double money) {
        this.name = name;
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
                        "SELECT * FROM cb_economy_banks WHERE name = ?",
                        this.name
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
                    "INSERT INTO cb_economy_banks (name,money) VALUES (?,?)",
                    this.name,
                    getStringValue(this.money)
            );
        }
    }

    public void update(){
        if(Citybuild.getMySQL().isConnected()){
            Citybuild.getMySQL().update(
                    "UPDATE cb_economy_banks SET money = ? WHERE name = ?",
                    getStringValue(this.money),
                    this.name
            );
        }
    }

    public void loadByName(){
        if(Citybuild.getMySQL().isConnected()){
            try{
                ResultSet rs = Citybuild.getMySQL().getResult(
                        "SELECT * FROM cb_economy_banks WHERE name = ?",
                        this.name
                );
                while (rs.next()){
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
                    "DELETE FROM cb_economy_banks WHERE name = ?",
                    this.name
            );
        }
    }

}
