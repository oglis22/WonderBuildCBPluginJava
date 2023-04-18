package dev.lupluv.cb.voting;

import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class VoteListener implements Listener {

    @EventHandler(priority = EventPriority.NORMAL)
    public void onVotifierEvent(VotifierEvent event) {
        Vote vote = event.getVote();

        // minecraft-server.eu
        System.out.println("Received Vote - " + vote.getServiceName() + " - " + vote.getUsername() + " - " + vote.getTimeStamp());
        VoteFetcher vf = new VoteFetcher(VoteSite.vote1, vote.getUsername());
        if(!vf.exists()) vf.create();
        vf.setLatestVote(System.currentTimeMillis());
        vf.setClaimed(false);
        vf.update();

    }
}
