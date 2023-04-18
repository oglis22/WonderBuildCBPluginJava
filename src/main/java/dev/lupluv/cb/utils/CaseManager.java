package dev.lupluv.cb.utils;

import dev.lupluv.cb.Citybuild;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class CaseManager {

    public static boolean isUserExists(UUID uuid){
        if(Citybuild.mySQL.isConnected()){
            try{
                PreparedStatement ps = Citybuild.mySQL.getCon().prepareStatement("SELECT * FROM cb_users WHERE UUID = ?");
                ps.setString(1, uuid.toString());
                ResultSet rs = ps.executeQuery();
                while (rs.next()){
                    return true;
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return false;
    }

    public static void updateName(Player p){
        if(Citybuild.mySQL.isConnected()){
            try{
                PreparedStatement ps = Citybuild.mySQL.getCon().prepareStatement("UPDATE cb_users SET NAME = ? WHERE UUID = ?");
                ps.setString(1, p.getName());
                ps.setString(2, p.getUniqueId().toString());
                ps.executeUpdate();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public static void createUser(Player p){
        if(Citybuild.mySQL.isConnected()){
            try{
                PreparedStatement ps = Citybuild.mySQL.getCon().prepareStatement("INSERT INTO cb_users (UUID,NAME,CASES) VALUES (?,?,?)");
                ps.setString(1, p.getUniqueId().toString());
                ps.setString(2, p.getName());
                ps.setString(3, "none");
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public static String getCasesRaw(UUID uuid){
        if(Citybuild.mySQL.isConnected()){
            try{
                PreparedStatement ps = Citybuild.mySQL.getCon().prepareStatement("SELECT * FROM cb_users WHERE UUID = ?");
                ps.setString(1, uuid.toString());
                ResultSet rs = ps.executeQuery();
                while (rs.next()){
                    return rs.getString("CASES");
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Inventory getCasesInventory(Player p){
        Inventory inv = Bukkit.createInventory(null, 9*5, "§b§lDeine Cases: §e§l");



        return inv;

    }

}
