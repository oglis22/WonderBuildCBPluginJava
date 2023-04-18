package dev.lupluv.cb.clans;

import dev.lupluv.cb.Citybuild;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User {

    public static List<User> getAllUsers(){
        List<User> users = new ArrayList<>();
        if(Citybuild.getMySQL().isConnected()){
            try{
                ResultSet rs = Citybuild.getMySQL().getResult(
                        "SELECT * FROM cb_clans_user"
                );
                while (rs.next()){
                    users.add(new User(rs.getLong("id"), UUID.fromString(rs.getString("uuid")), rs.getString("name")));
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return users;
    }

    public static void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        User user = new User(p.getUniqueId());
        if(!user.existsByUuid()){
            user.setName(p.getName());
            user.createID();
            user.create();
        }else{
            user.loadByUuid();
            if(!user.getName().equalsIgnoreCase(p.getName())){
                user.setName(p.getName());
                user.update();
            }
        }
    }

    long id;
    UUID uuid;
    String name;

    public User(long id) {
        this.id = id;
    }

    public User(UUID uuid) {
        this.uuid = uuid;
    }

    public User(String name) {
        this.name = name;
    }

    public User(long id, UUID uuid, String name) {
        this.id = id;
        this.uuid = uuid;
        this.name = name;
    }

    public User() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean existsById(){
        if(Citybuild.getMySQL().isConnected()){
            try{
                ResultSet rs = Citybuild.getMySQL().getResult(
                        "SELECT * FROM cb_clans_user WHERE id = ?",
                        String.valueOf(this.id)
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
                        "SELECT * FROM cb_clans_user WHERE uuid = ?",
                        this.uuid.toString()
                );
                return rs.next();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean existsByName(){
        if(Citybuild.getMySQL().isConnected()){
            try{
                ResultSet rs = Citybuild.getMySQL().getResult(
                        "SELECT * FROM cb_clans_user WHERE name = ?",
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
                    "INSERT INTO cb_clans_user (id,uuid,name) VALUES (?,?,?)",
                    String.valueOf(this.id),
                    this.uuid.toString(),
                    this.name
            );
        }
    }

    public void update(){
        if(Citybuild.getMySQL().isConnected()){
            Citybuild.getMySQL().update(
                    "UPDATE cb_clans_user SET id = ?, uuid = ?, name = ? WHERE id = ?",
                    String.valueOf(this.id),
                    this.uuid.toString(),
                    this.name,
                    String.valueOf(this.id)
            );
        }
    }

    public void loadById(){
        if(Citybuild.getMySQL().isConnected()){
            try{
                ResultSet rs = Citybuild.getMySQL().getResult(
                        "SELECT * FROM cb_clans_user WHERE id = ?",
                        String.valueOf(this.id)
                );
                while (rs.next()){
                    this.uuid = UUID.fromString(rs.getString("uuid"));
                    this.name = rs.getString("name");
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
                        "SELECT * FROM cb_clans_user WHERE uuid = ?",
                        this.uuid.toString()
                );
                while (rs.next()){
                    this.id = rs.getLong("id");
                    this.name = rs.getString("name");
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void loadByName(){
        if(Citybuild.getMySQL().isConnected()){
            try{
                ResultSet rs = Citybuild.getMySQL().getResult(
                        "SELECT * FROM cb_clans_user WHERE id = ?",
                        String.valueOf(this.id)
                );
                while (rs.next()){
                    this.id = rs.getLong("id");
                    this.uuid = UUID.fromString(rs.getString("uuid"));
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void delete(){
        if(Citybuild.getMySQL().isConnected()){
            Citybuild.getMySQL().update(
                    "DELETE FROM cb_clans_user WHERE id = ?",
                    String.valueOf(this.id)
            );
        }
    }

    public Clan getClan(){
        return ClanMember.getClan(this);
    }

    public void createID(){
        for(long i = 0; i < 10000; i++){
            User u = new User(i);
            if(!u.existsById()){
                this.id = i;
                break;
            }
        }
    }

}
