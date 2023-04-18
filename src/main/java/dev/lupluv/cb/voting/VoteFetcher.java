package dev.lupluv.cb.voting;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class VoteFetcher {

    String website;
    String username;
    long latestVote;
    boolean claimed;


    public VoteFetcher(String website, String username) {
        this.website = website;
        this.username = username;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public long getLatestVote() {
        return latestVote;
    }

    public void setLatestVote(long latestVote) {
        this.latestVote = latestVote;
    }

    public boolean isClaimed() {
        return claimed;
    }

    public void setClaimed(boolean claimed) {
        this.claimed = claimed;
    }

    public boolean exists(){
        File file = new File("plugins//Citybuild//votes.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        return cfg.get(website + "." + username + ".latest_vote") != null || cfg.get(website + "." + username + ".claimed") != null;
    }

    public void create(){
        File file = new File("plugins//Citybuild//votes.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        cfg.set(website + "." + username + ".latest_vote", -1);
        cfg.set(website + "." + username + ".claimed", false);
        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void load(){
        File file = new File("plugins//Citybuild//votes.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        this.latestVote = cfg.getLong(website + "." + username + ".latest_vote");
        this.claimed = cfg.getBoolean(website + "." + username + ".claimed");
    }

    public void update(){
        File file = new File("plugins//Citybuild//votes.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        cfg.set(website + "." + username + ".latest_vote", this.latestVote);
        cfg.set(website + "." + username + ".claimed", this.claimed);
        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void delete(){
        File file = new File("plugins//Citybuild//votes.yml");
        FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        cfg.set(website + "." + username + ".latest_vote", null);
        cfg.set(website + "." + username + ".claimed", null);
        try {
            cfg.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getResult(){
        if(exists()){
            if(claimed){
                return 2;
            }else{
                if(latestVote != -1) {
                    long current = System.currentTimeMillis();
                    if (current - latestVote <= (60000 * 60) * 24) {
                        return 1;
                    }else{
                        latestVote = -1;
                        claimed = false;
                        update();
                    }
                }else{
                    return 0;
                }
            }
        }
        return 0;
    }

}
