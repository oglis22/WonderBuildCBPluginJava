package dev.lupluv.cb.particels;

import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Map;

public class Particel implements Listener {

    public static ArrayList<Player> haseffect1 = new ArrayList<Player>();
    public static ArrayList<Player> haseffect2 = new ArrayList<Player>();
    public static ArrayList<Player> haseffect3 = new ArrayList<Player>();
    public static ArrayList<Player> haseffect4 = new ArrayList<Player>();
    public static ArrayList<Player> haseffect5 = new ArrayList<Player>();
    public static ArrayList<Player> haseffect6 = new ArrayList<Player>();
    public static ArrayList<Player> haseffect7 = new ArrayList<Player>();
    public static ArrayList<Player> haseffect8 = new ArrayList<Player>();
    public static ArrayList<Player> haseffect9 = new ArrayList<Player>();

    public static ArrayList<Player> has_one_effect = new ArrayList<>();

   @EventHandler
   public void onMove(PlayerMoveEvent event){
       Player player = event.getPlayer();



       if(haseffect1.contains(player)){
            player.getWorld().spawnParticle(Particle.HEART, player.getLocation(), 2, 0.3, 0.3,0.3);
       }
       if(haseffect2.contains(player)){
           player.getWorld().spawnParticle(Particle.NOTE, player.getLocation(), 2, 0.3, 0.3,0.3);
       }
       if(haseffect3.contains(player)){
           player.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, player.getLocation(), 2, 0.3, 0.3,0.3);
       }
       if(haseffect4.contains(player)){
           player.getWorld().spawnParticle(Particle.CAMPFIRE_SIGNAL_SMOKE, player.getLocation(), 2, 0.3, 0.3,0.3);
       }
       if(haseffect5.contains(player)){
           player.getWorld().spawnParticle(Particle.LAVA, player.getLocation(), 2, 0.3, 0.3,0.3);
       }
       if(haseffect6.contains(player)){
           player.getWorld().spawnParticle(Particle.VILLAGER_ANGRY, player.getLocation(), 2, 0.3, 0.3,0.3);
       }
       if(haseffect7.contains(player)){
           player.getWorld().spawnParticle(Particle.FALLING_LAVA, player.getLocation(), 2, 0.3, 0.3,0.3);
       }
       if(haseffect8.contains(player)){
           player.getWorld().spawnParticle(Particle.DRAGON_BREATH, player.getLocation(), 2, 0.3, 0.3,0.3);
       }
       if(haseffect9.contains(player)){
           player.getWorld().spawnParticle(Particle.SPELL_MOB, player.getLocation(), 2, 0.3, 0.3,0.3);
       }



   }



}
