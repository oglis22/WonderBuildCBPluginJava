package dev.lupluv.cb.elevators;

import dev.lupluv.cb.utils.Util;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ElevatorBlock {

    public static ElevatorBlock getByLocation(Location loc){
        ElevatorBlock toReturn = null;
        for(ElevatorBlock eb : getAllElevatorBlocks()){
            if(eb.getBlock().getLocation().getBlockX() == loc.getBlockX()
                    && eb.getBlock().getLocation().getBlockY() == loc.getBlockY()
                    && eb.getBlock().getLocation().getBlockZ() == loc.getBlockZ()){
                toReturn = eb;
            }
        }
        return toReturn;
    }

    public static List<ElevatorBlock> getAllElevatorBlocks(){
        File file = new File("plugins//Citybuild//elevators.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        List<ElevatorBlock> list = new ArrayList<>();
        for(String key : cfg.getKeys(true)){
            if(key.endsWith("World")){
                long id = Long.parseLong(key.replace("elevator.", "").replace(".World", ""));
                Block block = Util.getBlockLocation(cfg, "elevator." + id).getBlock();
                list.add(new ElevatorBlock(id, block));
            }
        }
        return list;
    }

    public static ElevatorBlock getByID(long id){
        File file = new File("plugins//Citybuild//elevators.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        ElevatorBlock eb = new ElevatorBlock(id);
        eb.load();
        return eb;
    }

    long id;
    Block block;

    public ElevatorBlock(long id) {
        this.id = id;
    }

    public ElevatorBlock(Block block) {
        this.block = block;
    }

    public ElevatorBlock(long id, Block block) {
        this.id = id;
        this.block = block;
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public boolean exists(){
        File file = new File("plugins//Citybuild//elevators.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        return cfg.getString("elevator." + id + ".World") != null;
    }

    public void load(){
        File file = new File("plugins//Citybuild//elevators.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        this.block = Util.getBlockLocation(cfg, "elevator." + id).getBlock();
    }

    public void create(){
        File file = new File("plugins//Citybuild//elevators.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        Util.setBlockLocationIntoFileConfiguration(cfg, "elevator." + id, block.getLocation());
        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void update(){
        File file = new File("plugins//Citybuild//elevators.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        Util.setBlockLocationIntoFileConfiguration(cfg, "elevator." + id, block.getLocation());
        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void delete(){
        File file = new File("plugins//Citybuild//elevators.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        cfg.set("elevator." + id, null);
        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void giveFreeId(){
        long id = 0;
        while (new ElevatorBlock(id).exists()){
            id++;
        }
        this.id = id;
    }

}
