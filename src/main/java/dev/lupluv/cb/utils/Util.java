package dev.lupluv.cb.utils;

import com.destroystokyo.paper.profile.PlayerProfile;
import com.destroystokyo.paper.profile.ProfileProperty;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;
import java.util.UUID;

public class Util {

    public static ItemStack createCustomSkull(String value, String name, List<String> lore){
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        if(value.isEmpty()) return head;

        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        PlayerProfile profile = Bukkit.createProfile(UUID.randomUUID());
        profile.setProperty(new ProfileProperty("textures", value));
        headMeta.setPlayerProfile(profile);
        headMeta.setDisplayName(name);
        headMeta.setLore(lore);
        head.setItemMeta(headMeta);
        return head;
    }

    public static void setLocationIntoFileConfiguration(FileConfiguration cfg, String path, Location loc){
        if(path == null || path.equalsIgnoreCase("")){
            path = "";
        }else{
            if(!path.endsWith(".")){
                path = path + ".";
            }
        }
        cfg.set(path + "World", loc.getWorld().getName());
        cfg.set(path + "X", loc.getX());
        cfg.set(path + "Y", loc.getY());
        cfg.set(path + "Z", loc.getZ());
        cfg.set(path + "Yaw", loc.getYaw());
        cfg.set(path + "Pitch", loc.getPitch());
    }

    public static void setBlockLocationIntoFileConfiguration(FileConfiguration cfg, String path, Location loc){
        if(path == null || path.equalsIgnoreCase("")){
            path = "";
        }else{
            if(!path.endsWith(".")){
                path = path + ".";
            }
        }
        cfg.set(path + "World", loc.getWorld().getName());
        cfg.set(path + "X", (long) loc.getX());
        cfg.set(path + "Y", (long) loc.getY());
        cfg.set(path + "Z", (long) loc.getZ());
    }

    public static Location getLocation(FileConfiguration cfg, String path){
        if(path == null || path.equalsIgnoreCase("")){
            path = "";
        }else{
            if(!path.endsWith(".")){
                path = path + ".";
            }
        }
        Location loc = new Location(Bukkit.getWorld(cfg.getString(path + "World")), cfg.getDouble(path + "X"), cfg.getDouble(path + "Y")
                , cfg.getDouble(path + "Z"), (float) cfg.getDouble(path + "Yaw"), (float) cfg.getDouble(path + "Pitch"));
        return loc;
    }

    public static Location getBlockLocation(FileConfiguration cfg, String path){
        if(path == null || path.equalsIgnoreCase("")){
            path = "";
        }else{
            if(!path.endsWith(".")){
                path = path + ".";
            }
        }
        Location loc = new Location(Bukkit.getWorld(cfg.getString(path + "World")), cfg.getLong(path + "X"), cfg.getLong(path + "Y")
                , cfg.getLong(path + "Z"));
        return loc;
    }

}
