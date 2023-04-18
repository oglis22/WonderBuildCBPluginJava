package dev.lupluv.cb.namecolors;

import net.melion.rgbchat.api.RGBApi;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public enum NColor {

    ORANGE,
    MAGENTA,
    LIGHT_GREEN,
    BLUE,
    RED,
    CYAN,
    YELLOW,
    VIOLET,
    GRAD_FLAME,
    GRAD_NATURE,
    GRAD_SUN,
    GRAD_AQUA,
    GRAD_CHRISTMAS,
    GRAD_TULP,
    GRAD_EDEL,
    GRAD_DOLPHIN,
    NONE;

    public String format(String string){
        return getColor1() + string + getColor2();
    }

    public String getColor1(){
        switch (this){
            case GRAD_AQUA -> {
                return "<#3776e5>";
            }
            case ORANGE -> {
                return "§6";
            }
            case BLUE -> {
                return "§9";
            }
            case RED -> {
                return "§c";
            }
            case CYAN -> {
                return "§b";
            }
            case VIOLET -> {
                return "§5";
            }
            case YELLOW -> {
                return "§e";
            }
            case MAGENTA -> {
                return "§d";
            }
            case GRAD_CHRISTMAS -> {
                return "<#197a24>";
            }
            case GRAD_DOLPHIN -> {
                return "<#c1eaf1>";
            }
            case GRAD_EDEL -> {
                return "<#ad5c0a>";
            }
            case GRAD_FLAME -> {
                return "<#db2121>";
            }
            case GRAD_NATURE -> {
                return "<#b7e99d>";
            }
            case GRAD_SUN -> {
                return "<#e0e750>";
            }
            case GRAD_TULP -> {
                return "<#ef128e>";
            }
            case LIGHT_GREEN -> {
                return "§a";
            }
            case NONE -> {
                return "§7";
            }
        }
        return "§7";
    }

    public String getColor2(){
        switch (this){
            case GRAD_AQUA -> {
                return "</#60c5d3> ";
            }
            case GRAD_CHRISTMAS -> {
                return "</#a42121>";
            }
            case GRAD_DOLPHIN -> {
                return "</#00d1f1>";
            }
            case GRAD_EDEL -> {
                return "</#77cdb2>";
            }
            case GRAD_SUN -> {
                return "</#ef8e12>";
            }
            case GRAD_TULP -> {
                return "</#9b0aad>";
            }
            case GRAD_FLAME -> {
                return "</#e76d1f>";
            }
            case GRAD_NATURE -> {
                return "</#37e744>";
            }
        }
        return "";
    }

    public Material getMaterial(){
        return Material.ACACIA_CHEST_BOAT;
    }

    public String getValue(){
        switch (this){
            case MAGENTA -> {
                return "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjA" +
                        "1YzE3NjUwZTVkNzQ3MDEwZThiNjlhNmYyMzYzZmQxMWViOTNmODFjNmNlOTliZjAzODk1Y2VmYjkyYmFhIn19fQ==";
            }
            case BLUE -> {
                return "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjh" +
                        "hZmExNTU1ZTlmODc2NDgxZTNjNDI5OWVjNmU5MWQyMmI0MDc1ZTY3ZTU4ZWY4MGRjZDE5MGFjZTY1MTlmIn19fQ==";
            }
            case YELLOW -> {
                return "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzY" +
                        "0MTY4MmY0MzYwNmM1YzlhZDI2YmM3ZWE4YTMwZWU0NzU0N2M5ZGZkM2M2Y2RhNDllMWMxYTI4MTZjZjBiYSJ9fX0=";
            }
            case CYAN -> {
                return "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTBm" +
                        "YjFmMTNlY2I3ZmJiMmZhNDljZDAzZDM1N2ZhN2UyNzg1MDJiNzg3MzA2MDJhYWExMDY1NWU0ZDk0OTBlMSJ9fX0=";
            }
            case ORANGE -> {
                return "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTMzM" +
                        "zBmYmVkMzc3YzI0NGY0ODdlNGJjNTY4MmQxNWFmNDBkM2NlNGMzMmVlMDNmYzI0YTdmOTUyZTdkMjlhOSJ9fX0=";
            }
            case RED -> {
                return "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWZ" +
                        "kZTNiZmNlMmQ4Y2I3MjRkZTg1NTZlNWVjMjFiN2YxNWY1ODQ2ODRhYjc4NTIxNGFkZDE2NGJlNzYyNGIifX19";
            }
            case VIOLET -> {
                return "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGU" +
                        "2M2E2ZmM1ZThlNTM0MGUzOTg2ZDQ5NmE4NzVlNGQ3ZDk0ZTY4MzEyNGE1MjljY2ZmZjc3ZjliYWI0MzMifX19";
            }
            case LIGHT_GREEN -> {
                return "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYT" +
                        "c2OTVmOTZkZGE2MjZmYWFhMDEwZjRhNWYyOGE1M2NkNjZmNzdkZTBjYzI4MGU3YzU4MjVhZDY1ZWVkYzcyZSJ9fX0=";
            }
            case GRAD_DOLPHIN -> {
                return "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2UyO" +
                        "GE0OWNmMjNhNTM0NTM1YmU3MzZiOTA3NzJiMDg5NDE3MjI0YzgyZTIwMTE0NGRhNzEzZWE0ZWExNjdmNSJ9fX0=";
            }
            case GRAD_EDEL -> {
                return "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODdiOT" +
                        "cyZjAwOGQ5ZWNlYjAzNGJkNjY0ZmY5ZTk5M2UxNDJlNmUxNjNmYTJiMTI1NzM4NTIwNTllOWJmMmU5YyJ9fX0=";
            }
            case GRAD_AQUA -> {
                return "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODhhMG" +
                        "Y3YmQzZDU4YzU4ZmI5NWU0OGIyYjQ0OTIzZjVlYWEyYzFkNTRkY2Q3MmZhN2NlZmNiYmMxZDRjODFhZCJ9fX0=";
            }
            case GRAD_CHRISTMAS -> {
                return "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWU0ODYxNW" +
                        "RmNmI3ZGRmM2FkNDk1MDQxODc2ZDkxNjliZGM5ODNhM2ZhNjlhMmFjYTEwN2U4ZjI1MWY3Njg3In19fQ==";
            }
            case GRAD_FLAME -> {
                return "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODM1ZWY5Mj" +
                        "MwMDMxNzY5YTJlODE5MmFiNDZhMTcxNDQxMGQ1NmMzYjliMzhhMDMwMmEwNThlNDc5NzNkN2M0ZCJ9fX0=";
            }
            case GRAD_NATURE -> {
                return "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjc1MDdmNj" +
                        "Y2ZWFmZGNhYzY0MGUxM2JiMDZjNGY1NjA0ZGRmYzNmYWJjZjQ5NmU1MzBiZDkxMzgwODQ0ZjVmOSJ9fX0=";
            }
            case GRAD_SUN -> {
                return "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjE0ODUzNDk" +
                        "wMDZlZDFjOTFiNzk1OWFmZjQ0ZjMzMGRkYWMzNWUzZDlhOTllNGE4MjA1MWY5ODZlY2RhNDc1NSJ9fX0=";
            }
            case GRAD_TULP -> {
                return "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGU2MzY3MmJhNm" +
                        "I5NWEwOGE0ODg3YzljOGUyMjFjZTUwNGYzNTFjNjcxMzE0NjgzYmNkNjExYjQ4ODkwNjczNyJ9fX0=";
            }
        }
        return "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjQ3Y2YwZjN" +
                "iOWVjOWRmMjQ4NWE5Y2Q0Nzk1YjYwYTM5MWM4ZTZlYmFjOTYzNTRkZTA2ZTMzNTdhOWE4ODYwNyJ9fX0=";
    }

    public String getName(){
        switch (this){
            case MAGENTA -> {
                return "Magenta";
            }
            case BLUE -> {
                return "Blau";
            }
            case CYAN -> {
                return "Türkis";
            }
            case ORANGE -> {
                return "Orange";
            }
            case RED -> {
                return "Rot";
            }
            case VIOLET -> {
                return "Violett";
            }
            case YELLOW -> {
                return "Gelb";
            }
            case LIGHT_GREEN -> {
                return "Hellgrün";
            }
            case GRAD_AQUA -> {
                return "Aqua";
            }
            case GRAD_CHRISTMAS -> {
                return "Christmas";
            }
            case GRAD_DOLPHIN -> {
                return "Delfin";
            }
            case GRAD_EDEL -> {
                return "Edel";
            }
            case GRAD_FLAME -> {
                return "Flamme";
            }
            case GRAD_SUN -> {
                return "Sonne";
            }
            case GRAD_NATURE -> {
                return "Natur";
            }
            case GRAD_TULP -> {
                return "Tulpe";
            }
        }
        return "Nicht verfügbar.";
    }

    public ChatColor asColor(){
        switch (this){
            case ORANGE -> {
                return ChatColor.GOLD;
            }
            case BLUE -> {
                return ChatColor.BLUE;
            }
            case RED -> {
                return ChatColor.RED;
            }
            case CYAN -> {
                return ChatColor.DARK_AQUA;
            }
            case VIOLET -> {
                return ChatColor.DARK_PURPLE;
            }
            case YELLOW -> {
                return ChatColor.YELLOW;
            }
            case MAGENTA -> {
                return ChatColor.LIGHT_PURPLE;
            }
            case LIGHT_GREEN -> {
                return ChatColor.GREEN;
            }
        }
        return ChatColor.GRAY;
    }

}
