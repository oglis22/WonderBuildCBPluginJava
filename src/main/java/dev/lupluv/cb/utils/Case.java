package dev.lupluv.cb.utils;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Case {

    public static Case getStarterCase(){
        Case c = new Case("Starter Case");
        List<Winnable> winnables = new ArrayList<>();
        Winnable w1 = new Winnable();
        Item i1 = new Item(Material.GOLD_INGOT, 16);
        i1.setDisplayName("§c§l16 Gold");
        w1.setInvItem(i1.build());
        w1.setWinnableType(WinnableType.ITEM);
        ItemStack iw1 = new ItemStack(Material.GOLD_INGOT, 16);
        w1.setWinnableItem(iw1);

        Winnable w2 = new Winnable();
        w2.setWinnableType(WinnableType.COMMAND);
        return c;
    }

    String name;
    ItemStack invItem;
    List<Winnable> winnables;

    public Case(String name) {
        this.name = name;
    }

    public Case(String name, ItemStack invItem, List<Winnable> winnables) {
        this.name = name;
        this.invItem = invItem;
        this.winnables = winnables;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ItemStack getInvItem() {
        return invItem;
    }

    public void setInvItem(ItemStack invItem) {
        this.invItem = invItem;
    }

    public List<Winnable> getWinnables() {
        return winnables;
    }

    public void setWinnables(List<Winnable> winnables) {
        this.winnables = winnables;
    }

}
