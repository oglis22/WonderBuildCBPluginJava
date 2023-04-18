package dev.lupluv.cb.utils;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Home {

    public static List<String> getHomeListRaw(UUID owner){
        File file = new File("plugins//Citybuild//homes.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        return cfg.getStringList(owner.toString() + ".List");
    }

    public static List<Home> getHomeList(UUID owner){
        File file = new File("plugins//Citybuild//homes.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        List<Home> hl = new ArrayList<>();
        List<String> rl = cfg.getStringList(owner.toString() + ".List");
        for(String s : rl){
            Home h = new Home(owner, s);
            if(h.exists()){
                h.load();
                hl.add(h);
            }
        }
        return hl;
    }

    UUID owner;
    String name;
    Location loc;

    public Home(UUID owner, String name) {
        this.owner = owner;
        this.name = name;
    }

    public Home(UUID owner, String name, Location loc) {
        this.owner = owner;
        this.name = name;
        this.loc = loc;
    }

    public UUID getOwner() {
        return owner;
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLoc() {
        return loc;
    }

    public void setLoc(Location loc) {
        this.loc = loc;
    }

    public boolean exists(){
        File file = new File("plugins//Citybuild//homes.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        if(cfg.getString(owner.toString() + "." + name + ".World") != null) return true; else return false;
    }

    public void save() throws IOException {
        File file = new File("plugins//Citybuild//homes.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        Util.setLocationIntoFileConfiguration(cfg, owner.toString() + "." + name, loc);
        cfg.save(file);
        addToHomeList();
    }

    public void load(){
        File file = new File("plugins//Citybuild//homes.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        this.loc = Util.getLocation(cfg, owner.toString() + "." + name);
    }

    public void delete() throws IOException {
        if(exists()){
            File file = new File("plugins//Citybuild//homes.yml");
            FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
            cfg.set(owner.toString() + "." + name + ".World", null);
            cfg.set(owner.toString() + "." + name + ".X", null);
            cfg.set(owner.toString() + "." + name + ".Y", null);
            cfg.set(owner.toString() + "." + name + ".Z", null);
            cfg.set(owner.toString() + "." + name + ".Yaw", null);
            cfg.set(owner.toString() + "." + name + ".Pitch", null);
            cfg.set(owner.toString() + "." + name, null);
            cfg.save(file);
            removeFromHomeList();
        }
    }

    public void teleport(Player p){
        TpRequest.inTeleport.add(p);
        p.teleport(this.loc);
        TpRequest.inTeleport.remove(p);
    }

    public void addToHomeList(){
        File file = new File("plugins//Citybuild//homes.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        List<String> l = getHomeListRaw(this.owner);
        if(!l.contains(name)) l.add(name);
        cfg.set(owner.toString() + ".List", l);
        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void removeFromHomeList(){
        File file = new File("plugins//Citybuild//homes.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        List<String> l = getHomeListRaw(this.owner);
        l.remove(name);
        cfg.set(owner.toString() + ".List", l);
        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
