package dev.lupluv.cb.clans;

import dev.lupluv.cb.Citybuild;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ClanRequest {

    long user_id;
    long clan_id;
    RequestType type;
    long date;

    public ClanRequest(long user_id, long clan_id) {
        this.user_id = user_id;
        this.clan_id = clan_id;
    }

    public ClanRequest(long user_id, long clan_id, RequestType type) {
        this.user_id = user_id;
        this.clan_id = clan_id;
        this.type = type;
    }

    public ClanRequest(long user_id, long clan_id, RequestType type, long date) {
        this.user_id = user_id;
        this.clan_id = clan_id;
        this.type = type;
        this.date = date;
    }

    public ClanRequest() {
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getClan_id() {
        return clan_id;
    }

    public void setClan_id(long clan_id) {
        this.clan_id = clan_id;
    }

    public RequestType getType() {
        return type;
    }

    public void setType(RequestType type) {
        this.type = type;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public boolean existsByUserAndClan(){
        if(Citybuild.getMySQL().isConnected()){
            try{
                ResultSet rs = Citybuild.getMySQL().getResult(
                        "SELECT * FROM cb_clans_requests WHERE user_id = ? AND clan_id = ?",
                        String.valueOf(this.user_id),
                        String.valueOf(this.clan_id)
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
                    "INSERT INTO cb_clans_requests (user_id,clan_id,type,date) VALUES (?,?,?,?)",
                    String.valueOf(this.user_id),
                    String.valueOf(this.clan_id),
                    this.type.toString(),
                    String.valueOf(this.date)
            );
        }
    }

    public void loadByUser(){
        if(Citybuild.getMySQL().isConnected()){
            try{
                ResultSet rs = Citybuild.getMySQL().getResult(
                        "SELECT * FROM cb_clans_requests WHERE user_id = ?",
                        String.valueOf(this.user_id)
                );
                while (rs.next()){
                    this.clan_id = rs.getLong("clan_id");
                    this.type = RequestType.valueOf(rs.getString("type"));
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
                    "DELETE FROM cb_clans_requests WHERE user_id = ?",
                    String.valueOf(this.user_id)
            );
        }
    }

}
