package dev.lupluv.cb.listeners;

import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.driver.event.EventListener;
import de.dytanic.cloudnet.driver.event.events.permission.PermissionUpdateGroupEvent;
import de.dytanic.cloudnet.driver.event.events.permission.PermissionUpdateUserEvent;
import de.dytanic.cloudnet.driver.permission.IPermissionUser;
import dev.lupluv.cb.Citybuild;
import dev.lupluv.cb.scoreboard.ScoreboardManager;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class CloudNetSimpleNameTagsListener implements Listener {

    /*
    @EventHandler(priority = EventPriority.HIGHEST)
    public void handle(PlayerJoinEvent event) {
        Bukkit.getScheduler().runTask(Citybuild.getPlugin(), () -> ScoreboardManager.getInstance().updateNameTags(event.getPlayer()));
    }

    @EventListener
    public void handle(PermissionUpdateUserEvent event) {
        Bukkit.getScheduler().runTask(Citybuild.getPlugin(), () -> Bukkit.getOnlinePlayers().stream()
                .filter(player -> player.getUniqueId().equals(event.getPermissionUser().getUniqueId()))
                .findFirst()
                .ifPresent(ScoreboardManager.getInstance()::updateNameTags));
    }

    @EventListener
    public void handle(PermissionUpdateGroupEvent event) {
        Bukkit.getScheduler().runTask(Citybuild.getPlugin(), () -> Bukkit.getOnlinePlayers().forEach(player -> {
            IPermissionUser permissionUser = CloudNetDriver.getInstance().getPermissionManagement()
                    .getUser(player.getUniqueId());

            if (permissionUser != null && permissionUser.inGroup(event.getPermissionGroup().getName())) {
                ScoreboardManager.getInstance().updateNameTags(player);
            }
        }));
    }

     */

}
