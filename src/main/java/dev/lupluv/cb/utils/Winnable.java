package dev.lupluv.cb.utils;

import org.bukkit.inventory.ItemStack;

public class Winnable {

    ItemStack invItem;
    int chance;
    WinnableType winnableType;
    ItemStack winnableItem;
    String winnableCommand;

    public Winnable(ItemStack invItem, int chance) {
        this.invItem = invItem;
        this.chance = chance;
    }

    public Winnable() {
    }

    public ItemStack getInvItem() {
        return invItem;
    }

    public void setInvItem(ItemStack invItem) {
        this.invItem = invItem;
    }

    public int getChance() {
        return chance;
    }

    public void setChance(int chance) {
        this.chance = chance;
    }

    public WinnableType getWinnableType() {
        return winnableType;
    }

    public void setWinnableType(WinnableType winnableType) {
        this.winnableType = winnableType;
    }

    public ItemStack getWinnableItem() {
        return winnableItem;
    }

    public void setWinnableItem(ItemStack winnableItem) {
        this.winnableItem = winnableItem;
    }

    public String getWinnableCommand() {
        return winnableCommand;
    }

    public void setWinnableCommand(String winnableCommand) {
        this.winnableCommand = winnableCommand;
    }
}
