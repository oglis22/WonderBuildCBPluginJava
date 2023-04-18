package dev.lupluv.cb.jobs;

import dev.lupluv.cb.Citybuild;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class JobFetch {

    public static JobFetch correctName(Player p){
        JobFetch eco = new JobFetch(p.getUniqueId());
        if(!eco.existsByUuid()){
            eco.setJob(JobType.NONE);
            eco.setPlayerName(p.getName());
            eco.create();
        }else{
            eco.loadByUuid();
            if(!eco.getPlayerName().equals(p.getName())) {
                eco.setPlayerName(p.getName());
                eco.update();
            }
        }
        return eco;
    }

    private UUID playerUUID;
    private String playerName;

    private JobType job;

    public JobFetch() {
    }

    public JobFetch(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }

    public JobFetch(String playerName) {
        this.playerName = playerName;
    }

    public JobFetch(UUID playerUUID, String playerName, JobType job) {
        this.playerUUID = playerUUID;
        this.playerName = playerName;
        this.job = job;
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public void setPlayerUUID(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public JobType getJob() {
        return job;
    }

    public void setJob(JobType job) {
        this.job = job;
    }

    public String getStringValue(double d){
        return String.valueOf(d);
    }

    public boolean existsByName(){
        if(Citybuild.getMySQL().isConnected()){
            try{
                ResultSet rs = Citybuild.getMySQL().getResult(
                        "SELECT * FROM cb_jobs WHERE name = ?",
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
                        "SELECT * FROM cb_jobs WHERE uuid = ?",
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
                    "INSERT INTO cb_jobs (uuid,name,job) VALUES (?,?,?)",
                    this.playerUUID.toString(),
                    this.playerName,
                    this.job.toString()
            );
        }
    }

    public void update(){
        if(Citybuild.getMySQL().isConnected()){
            Citybuild.getMySQL().update(
                    "UPDATE cb_jobs SET name = ?, job = ? WHERE uuid = ?",
                    this.playerName,
                    this.job.toString(),
                    this.playerUUID.toString()
            );
        }
    }

    public void loadByName(){
        if(Citybuild.getMySQL().isConnected()){
            try{
                ResultSet rs = Citybuild.getMySQL().getResult(
                        "SELECT * FROM cb_jobs WHERE name = ?",
                        this.playerName
                );
                while (rs.next()){
                    this.playerUUID = UUID.fromString(rs.getString("uuid"));
                    this.job = JobType.valueOf(rs.getString("job"));
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
                        "SELECT * FROM cb_jobs WHERE uuid = ?",
                        this.playerUUID.toString()
                );
                while (rs.next()){
                    this.playerName = rs.getString("name");
                    this.job = JobType.valueOf(rs.getString("job"));
                }
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public void delete(){
        if(Citybuild.getMySQL().isConnected()){
            Citybuild.getMySQL().update(
                    "DELETE cb_jobs WHERE uuid = ?",
                    this.playerUUID.toString()
            );
        }
    }

}
