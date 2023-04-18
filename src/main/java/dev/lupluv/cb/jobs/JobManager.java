package dev.lupluv.cb.jobs;

import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public class JobManager {

    // Per Session player -> job map
    private Map<UUID, Job> sessionMap;


    // Initialise Player
    public void initPlayer(Player player){
        JobFetch jobFetch = JobFetch.correctName(player);
        if(jobFetch.getJob() != JobType.NONE) sessionMap.put(player.getUniqueId(), new Job());
    }





















    public JobManager(Map<UUID, Job> sessionMap) {
        this.sessionMap = sessionMap;
    }

    public Map<UUID, Job> getSessionMap() {
        return sessionMap;
    }

    public void setSessionMap(Map<UUID, Job> sessionMap) {
        this.sessionMap = sessionMap;
    }
}
