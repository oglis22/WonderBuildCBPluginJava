package dev.lupluv.cb.utils;

import dev.lupluv.cb.economy.Economy;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MoneyManager {

    public MoneyManager() {
    }

    public File getFile(){
        return new File("plugins//Citybuild//money.yml");
    }

    public FileConfiguration getData(){
        return YamlConfiguration.loadConfiguration(getFile());
    }

    public boolean exists(UUID uuid){
        if(getData().get(uuid.toString()) == null) return false; else return true;
    }

    public List<UUID> getAllEntries(){
        FileConfiguration cfg = getData();
        List<UUID> entries = new ArrayList<>();
        for(String key : cfg.getKeys(true)){
            UUID uuid = UUID.fromString(key);
            entries.add(uuid);
        }
        return entries;
    }

    public UUID getRank(int i){
        if(i == 1){
            UUID place1 = null;
            double place1Money = 0;
            for(UUID uuid : getAllEntries()){
                double money = Economy.getBalance(uuid);
                if(money >= place1Money){
                    place1Money = money;
                    place1 = uuid;
                }
            }
            return place1;
        }else if(i == 2){
            UUID place1 = null;
            double place1Money = 0;
            UUID place2 = null;
            double place2Money = 0;
            for(UUID uuid : getAllEntries()){
                double money = Economy.getBalance(uuid);
                if(money >= place1Money){
                    place2 = place1;
                    place2Money = place1Money;
                    place1Money = money;
                    place1 = uuid;
                }
            }
            System.out.println("Calc Place 2");
            System.out.println("Returning " + place2);
            System.out.println("Place1 " + place1);
            return place2;
        }else if(i == 3){
            UUID place1 = null;
            double place1Money = 0;
            UUID place2 = null;
            double place2Money = 0;
            UUID place3 = null;
            double place3Money = 0;
            for(UUID uuid : getAllEntries()){
                double money = Economy.getBalance(uuid);
                if(money >= place1Money){
                    place3 = place2;
                    place3Money = place2Money;
                    place2 = place1;
                    place2Money = place1Money;
                    place1Money = money;
                    place1 = uuid;
                }
            }
            return place3;
        }
        return null;
    }

}
