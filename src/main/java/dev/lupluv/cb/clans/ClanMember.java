package dev.lupluv.cb.clans;

import dev.lupluv.cb.Citybuild;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClanMember {

    public static List<ClanMember> getAllMembers(Clan clan){
        List<ClanMember> members = new ArrayList<>();
        if(Citybuild.getMySQL().isConnected()){
            try{
                ResultSet rs = Citybuild.getMySQL().getResult(
                        "SELECT * FROM cb_clans_members WHERE clan_id = ?",
                        String.valueOf(clan.getId())
                );
                while (rs.next()){
                    members.add(new ClanMember(rs.getLong("user_id"), rs.getLong("clan_id"), ClanRole.valueOf(rs.getString("role"))));
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return members;
    }

    public static Clan getClan(User user){
        ClanMember member = new ClanMember(user.getId());
        if(member.existsByUser()){
            member.loadByUser();
            Clan clan = new Clan(member.getClan_id());
            if(clan.existsById()) {
                clan.loadById();
                return clan;
            }
        }
        return null;
    }

    long user_id;
    long clan_id;
    ClanRole clanRole;

    public ClanMember(long user_id) {
        this.user_id = user_id;
    }

    public ClanMember(long user_id, long clan_id) {
        this.user_id = user_id;
        this.clan_id = clan_id;
    }

    public ClanMember(long user_id, long clan_id, ClanRole clanRole) {
        this.user_id = user_id;
        this.clan_id = clan_id;
        this.clanRole = clanRole;
    }

    public ClanMember() {
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

    public ClanRole getClanRole() {
        return clanRole;
    }

    public void setClanRole(ClanRole clanRole) {
        this.clanRole = clanRole;
    }

    public boolean existsByUser(){
        if(Citybuild.getMySQL().isConnected()){
            try{
                ResultSet rs = Citybuild.getMySQL().getResult(
                        "SELECT * FROM cb_clans_members WHERE user_id = ?",
                        String.valueOf(this.user_id)
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
                    "INSERT INTO cb_clans_members (user_id,clan_id,role) VALUES (?,?,?)",
                    String.valueOf(this.user_id),
                    String.valueOf(this.clan_id),
                    this.clanRole.toString()
            );
        }
    }

    public void loadByUser(){
        if(Citybuild.getMySQL().isConnected()){
            try{
                ResultSet rs = Citybuild.getMySQL().getResult(
                        "SELECT * FROM cb_clans_members WHERE user_id = ?",
                        String.valueOf(this.user_id)
                );
                while (rs.next()){
                    this.clan_id = rs.getLong("clan_id");
                    this.clanRole = ClanRole.valueOf(rs.getString("role"));
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void delete(){
        if(Citybuild.getMySQL().isConnected()){
            Citybuild.getMySQL().update(
                    "DELETE FROM cb_clans_members WHERE user_id = ?",
                    String.valueOf(this.user_id)
            );
        }
    }

}
