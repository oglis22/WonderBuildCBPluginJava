package dev.lupluv.cb.namecolors;

import dev.lupluv.cb.Citybuild;
import dev.lupluv.cb.economy.Economy;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class NameColorSelector {

    public static void correctName(Player p){
        NameColorSelector nameColorSelector = new NameColorSelector(p.getUniqueId());
        if(!nameColorSelector.existsByUuid()){
            nameColorSelector.setName_color("NONE");
            nameColorSelector.setPlayerName(p.getName());
            nameColorSelector.create();
        }else{
            nameColorSelector.loadByUuid();
            if(!nameColorSelector.getPlayerName().equals(p.getName())) {
                nameColorSelector.setPlayerName(p.getName());
                nameColorSelector.update();
            }
        }
    }

    public static NColor getNColorValue(String string){
        NColor nColor = NColor.valueOf(string);
        if(nColor == null) nColor = NColor.NONE;
        return nColor;
    }

    public static NColor getNColor(String name){
        NameColorSelector ncs = new NameColorSelector(name);
        if(ncs.existsByName()){
            ncs.loadByName();
            return getNColorValue(ncs.getName_color());
        }
        return null;
    }

    public static NColor getNColor(OfflinePlayer offlinePlayer){
        NameColorSelector ncs = new NameColorSelector(offlinePlayer.getUniqueId());
        if(ncs.existsByUuid()){
            ncs.loadByUuid();
            return getNColorValue(ncs.getName_color());
        }
        return null;
    }

    public static NColor getNColor(UUID uuid){
        NameColorSelector ncs = new NameColorSelector(uuid);
        if(ncs.existsByUuid()){
            ncs.loadByUuid();
            return getNColorValue(ncs.getName_color());
        }
        return null;
    }

    public static void setNColor(Player player, NColor nColor){
        NameColorSelector ncs = new NameColorSelector(player.getUniqueId());
        if(ncs.existsByUuid()){
            ncs.loadByUuid();
            ncs.setName_color(nColor.toString());
            ncs.update();
        }else{
            ncs.setPlayerName(player.getName());
            ncs.setName_color(nColor.toString());
            ncs.create();
        }
    }

    String playerName;
    UUID playerUUID;
    String name_color;

    public boolean existsByName(){
        if(Citybuild.getMySQL().isConnected()){
            try{
                ResultSet rs = Citybuild.getMySQL().getResult(
                        "SELECT * FROM cb_namecolors WHERE name = ?",
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
                        "SELECT * FROM cb_namecolors WHERE uuid = ?",
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
                    "INSERT INTO cb_namecolors (uuid,name,namecolor) VALUES (?,?,?)",
                    this.playerUUID.toString(),
                    this.playerName,
                    this.name_color
            );
        }
    }

    public void update(){
        if(Citybuild.getMySQL().isConnected()){
            Citybuild.getMySQL().update(
                    "UPDATE cb_namecolors SET name = ?, namecolor = ? WHERE uuid = ?",
                    this.playerName,
                    this.name_color,
                    this.playerUUID.toString()
            );
        }
    }

    public void loadByName(){
        if(Citybuild.getMySQL().isConnected()){
            try{
                ResultSet rs = Citybuild.getMySQL().getResult(
                        "SELECT * FROM cb_namecolors WHERE name = ?",
                        this.playerName
                );
                while (rs.next()){
                    this.playerUUID = UUID.fromString(rs.getString("uuid"));
                    this.name_color = rs.getString("namecolor");
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
                        "SELECT * FROM cb_namecolors WHERE uuid = ?",
                        this.playerUUID.toString()
                );
                while (rs.next()){
                    this.playerName = rs.getString("name");
                    this.name_color = rs.getString("namecolor");
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void delete(){
        if(Citybuild.getMySQL().isConnected()){
            Citybuild.getMySQL().update(
                    "DELETE FROM cb_namecolors WHERE uuid = ?",
                    this.playerUUID.toString()
            );
        }
    }


    public NameColorSelector(String playerName, UUID playerUUID, String name_color) {
        this.playerName = playerName;
        this.playerUUID = playerUUID;
        this.name_color = name_color;
    }

    public NameColorSelector(String playerName, UUID playerUUID) {
        this.playerName = playerName;
        this.playerUUID = playerUUID;
    }

    public NameColorSelector(String playerName) {
        this.playerName = playerName;
    }

    public NameColorSelector(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public void setPlayerUUID(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }

    public String getName_color() {
        return name_color;
    }

    public void setName_color(String name_color) {
        this.name_color = name_color;
    }
}
