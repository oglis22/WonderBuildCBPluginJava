package dev.lupluv.cb.stats;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.HologramTrait;
import net.citizensnpcs.trait.SkinTrait;

public class StatsNPC {

    public NPC getNPC(int i){
        if(i == 1){
            return CitizensAPI.getNPCRegistry().getById(32);
        }else if(i == 2){
            return CitizensAPI.getNPCRegistry().getById(33);
        }else if(i == 3){
            return CitizensAPI.getNPCRegistry().getById(31);
        }
        return null;
    }

    public void updateNpc(int i, String name, String skin){
        NPC npc = getNPC(i);
        npc.getOrAddTrait(net.citizensnpcs.trait.HologramTrait.class).clear();
        npc.getOrAddTrait(net.citizensnpcs.trait.HologramTrait.class).addLine(name.split("\n")[0]);
        npc.getOrAddTrait(net.citizensnpcs.trait.HologramTrait.class).addLine(name.split("\n")[1]);
        npc.getOrAddTrait(net.citizensnpcs.trait.HologramTrait.class).addLine(name.split("\n")[2]);
        npc.getOrAddTrait(net.citizensnpcs.trait.HologramTrait.class).setDirection(HologramTrait.HologramDirection.TOP_DOWN);
        npc.getOrAddTrait(SkinTrait.class).setSkinName(skin, true);
    }

}
