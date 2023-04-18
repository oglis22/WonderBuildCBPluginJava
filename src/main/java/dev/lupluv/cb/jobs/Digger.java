package dev.lupluv.cb.jobs;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class Digger extends Job implements Listener {

    private double clay;
    private double gravel;
    private double dirt;
    private double grass_block;
    private double sand;
    private double red_sand;

    @EventHandler
    public void onBreak(BlockBreakEvent e){
        Player player = e.getPlayer();
        Block block = e.getBlock();
        Material material = block.getType();

        if(material == Material.CLAY){

        }else if(material == Material.GRAVEL){

        }else if(material == Material.DIRT){

        }else if(material == Material.GRASS_BLOCK){

        }else if(material == Material.SAND){

        }else if(material == Material.RED_SAND){

        }

    }

}
