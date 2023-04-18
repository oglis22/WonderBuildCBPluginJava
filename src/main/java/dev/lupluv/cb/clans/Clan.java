package dev.lupluv.cb.clans;

import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.driver.permission.IPermissionUser;
import de.dytanic.cloudnet.driver.permission.Permission;
import de.dytanic.cloudnet.driver.permission.PermissionUser;
import dev.lupluv.cb.Citybuild;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Clan {

    public static void main(String[] args){
        System.out.println("Winnie ist dumm.");
    }

    long id;
    String name;
    String tag;
    ChatColor color;
    boolean open;
    boolean chat;
    long date;

    public Clan(long id) {
        this.id = id;
    }

    public Clan(String name) {
        this.name = name;
    }

    public Clan(String name, String tag) {
        this.name = name;
        this.tag = tag;
    }

    public Clan(String name, String tag, ChatColor color, boolean open, boolean chat, long date) {
        this.name = name;
        this.tag = tag;
        this.color = color;
        this.open = open;
        this.chat = chat;
        this.date = date;
    }

    public Clan() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public ChatColor getColor() {
        return color;
    }

    public void setColor(ChatColor color) {
        this.color = color;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    public boolean isChat() {
        return chat;
    }

    public void setChat(boolean chat) {
        this.chat = chat;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public boolean existsById(){
        if(Citybuild.getMySQL().isConnected()){
            try{
                ResultSet rs = Citybuild.getMySQL().getResult(
                        "SELECT * FROM cb_clans_clan WHERE id = ?",
                        String.valueOf(this.id)
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
                        "SELECT * FROM cb_clans_clan WHERE name = ?",
                        this.name
                );
                return rs.next();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean existsByTag(){
        if(Citybuild.getMySQL().isConnected()){
            try{
                ResultSet rs = Citybuild.getMySQL().getResult(
                        "SELECT * FROM cb_clans_clan WHERE tag = ?",
                        this.tag
                );
                return rs.next();
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return false;
    }

    public boolean existsByCons(){
        if(Citybuild.getMySQL().isConnected()){
            try{
                ResultSet rs = Citybuild.getMySQL().getResult(
                        "SELECT * FROM cb_clans_clan WHERE name = ? AND tag = ?",
                        this.name,
                        this.tag
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
                    "INSERT INTO cb_clans_clan (id,name,tag,color,open,chat,date) VALUES (?,?,?,?,?,?,?)",
                    String.valueOf(this.id),
                    this.name,
                    this.tag,
                    this.color.getName(),
                    String.valueOf(this.open),
                    String.valueOf(this.chat),
                    String.valueOf(this.date)
            );
        }
    }

    public void loadById(){
        if(Citybuild.getMySQL().isConnected()){
            try{
                ResultSet rs = Citybuild.getMySQL().getResult(
                        "SELECT * FROM cb_clans_clan WHERE id = ?",
                        String.valueOf(this.id)
                );
                while (rs.next()){
                    this.name = rs.getString("name");
                    this.tag = rs.getString("tag");
                    this.color = ChatColor.of(rs.getString("color"));
                    this.open = Boolean.getBoolean(rs.getString("open"));
                    this.chat = Boolean.getBoolean(rs.getString("chat"));
                    this.date = rs.getLong("date");
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
                        "SELECT * FROM cb_clans_clan WHERE name = ?",
                        this.name
                );
                while (rs.next()){
                    this.id = rs.getLong("id");
                    this.tag = rs.getString("tag");
                    this.color = ChatColor.of(rs.getString("color"));
                    this.open = Boolean.getBoolean(rs.getString("open"));
                    this.chat = Boolean.getBoolean(rs.getString("chat"));
                    this.date = rs.getLong("date");
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
    public void loadByTag(){
        if(Citybuild.getMySQL().isConnected()){
            try{
                ResultSet rs = Citybuild.getMySQL().getResult(
                        "SELECT * FROM cb_clans_clan WHERE tag = ?",
                        String.valueOf(this.id)
                );
                while (rs.next()){
                    this.id = rs.getLong("id");
                    this.name = rs.getString("name");
                    this.color = ChatColor.of(rs.getString("color"));
                    this.open = Boolean.getBoolean(rs.getString("open"));
                    this.chat = Boolean.getBoolean(rs.getString("chat"));
                    this.date = rs.getLong("date");
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void delete(){
        if(Citybuild.getMySQL().isConnected()){
            Citybuild.getMySQL().update(
                    "DELETE FROM cb_clans_clan WHERE id = ?",
                    String.valueOf(this.id)
            );
        }
    }

    public List<User> getMembers(){
        List<User> users = new ArrayList<>();
        ClanMember.getAllMembers(this).forEach(all ->{
            User user = new User(all.getUser_id());
            if(user.existsById()) {
                user.loadById();
                users.add(user);
            }
        });
        return users;
    }

    public void createID(){
        for(long i = 0; i < 10000; i++){
            Clan c = new Clan(i);
            if(!c.existsById()){
                this.id = i;
                break;
            }
        }
    }

    public long getMaxMembers(){
        List<User> owners = new ArrayList<>();
        for(ClanMember clanMember : ClanMember.getAllMembers(this)){
            if(clanMember.getClanRole() == ClanRole.OWNER){
                User u = new User(clanMember.getUser_id());
                u.loadById();
                owners.add(u);
            }
        }
        List<IPermissionUser> players = new ArrayList<>();
        for(User owner : owners){
            IPermissionUser pu = CloudNetDriver.getInstance().getPermissionManagement().getUser(owner.getUuid());
            players.add(pu);
        }
        long max = 20;
        for(IPermissionUser permissionUser : players){
            if(permissionUser.hasPermission("*").asBoolean()) return -1;
            if(max <= 20){
                if(permissionUser.hasPermission("clans.members.1000").asBoolean()) {
                    max = 1000;
                    break;
                }else if(permissionUser.hasPermission("clans.members.200").asBoolean()){
                    max = 200;
                }else if(permissionUser.hasPermission("clans.members.100").asBoolean()){
                    max = 100;
                }else if(permissionUser.hasPermission("clans.members.50").asBoolean()){
                    max = 50;
                }
            }else if(max <= 50){
                if(permissionUser.hasPermission("clans.members.1000").asBoolean()) {
                    max = 1000;
                    break;
                }else if(permissionUser.hasPermission("clans.members.200").asBoolean()){
                    max = 200;
                }else if(permissionUser.hasPermission("clans.members.100").asBoolean()){
                    max = 100;
                }
            }else if(max <= 100){
                if(permissionUser.hasPermission("clans.members.1000").asBoolean()) {
                    max = 1000;
                    break;
                }else if(permissionUser.hasPermission("clans.members.200").asBoolean()){
                    max = 200;
                }
            }else if(max <= 200){
                if(permissionUser.hasPermission("clans.members.1000").asBoolean()) {
                    max = 1000;
                    break;
                }
            }else{
                break;
            }
        }
        return max;
    }

}
