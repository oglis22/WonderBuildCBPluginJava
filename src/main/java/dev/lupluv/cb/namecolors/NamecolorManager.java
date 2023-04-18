package dev.lupluv.cb.namecolors;

import org.bukkit.entity.Player;

public class NamecolorManager {

    public static NColor getNameColor(Player player){
        NameColorSelector ncs = new NameColorSelector(player.getUniqueId());
        if(!ncs.existsByUuid()) return NColor.NONE;
        ncs.loadByUuid();
        return NColor.valueOf(ncs.getName_color());
    }

}
