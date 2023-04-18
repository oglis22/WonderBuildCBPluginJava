package dev.lupluv.cb.utils;

import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Warp {

    public static List<Warp> getAllWarps(){
        File file = new File("plugins//Citybuild//Warps");
        List<Warp> warps = new ArrayList<>();
        for(File f : file.listFiles()){
            Warp warp = new Warp(f.getName().replace(".yml", ""));
            if(warp.exists()){
                warp.load();
                warps.add(warp);
            }
        }
        return warps;
    }

    String name;
    Location loc;

    public Warp(String name) {
        this.name = name;
    }

    public Warp(String name, Location loc) {
        this.name = name;
        this.loc = loc;
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

    public File getFile(){
        return new File("plugins//Citybuild//Warps//" + name.toLowerCase() + ".yml");
    }

    public boolean exists(){
        return getFile().exists();
    }

    public void create() throws IOException {
        File file = getFile();
        delete();
        file.createNewFile();
        save();
    }

    public void save() throws IOException {
        File file = getFile();
        if(file.exists()) {
            FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
            cfg.set("Name", name);
            Util.setLocationIntoFileConfiguration(cfg, "Location", loc);
            cfg.save(file);
        }
    }

    public void delete(){
        File file = getFile();
        if(exists()) file.delete();
    }

    public void load(){
        if(exists()){
            FileConfiguration cfg = YamlConfiguration.loadConfiguration(getFile());
            this.loc = Util.getLocation(cfg, "Location");
        }
    }

    public void teleport(Player p){
        if(!p.teleport(this.loc)) return;
        switch (name){
            case "crates" -> {
                p.sendTitle("§6§lCrates", "§aErfolgreich §7teleportiert.");
                break;
            }
            case "industriehaus" -> {
                p.sendTitle("§6§lIndustrie Haus", "§aErfolgreich §7teleportiert.");
                break;
            }
            case "casino" -> {
                p.sendTitle("§6§lCasino", "§aErfolgreich §7teleportiert.");
                break;
            }
            case "laufbursche" -> {
                p.sendTitle("§6§lLaufbursche", "§aErfolgreich §7teleportiert.");
                break;
            }
            case "farmwelt" -> {
                p.sendTitle("§6§lFarmwelt", "§aErfolgreich §7teleportiert.");
                break;
            }
            case "spawn" -> {
                p.sendTitle("§6§lSpawn", "§aErfolgreich §7teleportiert.");
                break;
            }
            case "bank" -> {
                p.sendTitle("§6§lBank", "§aErfolgreich §7teleportiert.");
                break;
            }
            case "item-shop" -> {
                p.sendTitle("§6§lItem Shop", "§aErfolgreich §7teleportiert.");
                break;
            }
            default -> {
                p.sendTitle("§6§l" + this.name, "§aErfolgreich §7teleportiert.");
                break;
            }
        }
    }

}
