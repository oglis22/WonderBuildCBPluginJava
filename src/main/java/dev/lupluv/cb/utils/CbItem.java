package dev.lupluv.cb.utils;

import org.bukkit.Material;

public enum CbItem {

    DIAMOND,
    GOLD_INGOT,
    RAW_IRON,
    COPPER_INGOT,
    LAPIS_LAZULI,
    COAL,
    REDSTONE,
    DARK_OAK_LOG,
    JUNGLE_LOG,
    ACACIA_LOG,
    MANGROVE_LOG,
    BIRCH_LOG,
    OAK_LOG,
    SPRUCE_LOG,
    CRIMSON_STEM,
    WARPED_STEM,
    ANCIENT_DEBRIS,
    GLOWSTONE,
    BRICKS,
    BEDROCK,
    AMETHYST_BLOCK,
    SPAWNER,
    BARRIER,
    BEACON,
    LIGHT,
    END_PORTAL_FRAME,
    DRAGON_EGG,
    DRAGON_HEAD;

    public String getDisplayName(){
        switch (this){
            case LIGHT -> {
                return "Licht";
            }
            case ACACIA_LOG -> {
                return "Akazien Stamm";
            }
            case AMETHYST_BLOCK -> {
                return "Amethyst Block";
            }
            case ANCIENT_DEBRIS -> {
                return "Netherit Platten";
            }
            case BARRIER -> {
                return "Barriere";
            }
            case BEACON -> {
                return "Beacon";
            }
            case BEDROCK -> {
                return "Bedrock";
            }
            case BIRCH_LOG -> {
                return "Birken Stamm";
            }
            case BRICKS -> {
                return "Ziegel Steine";
            }
            case COAL -> {
                return "Kohle";
            }
            case COPPER_INGOT -> {
                return "Kupfer Barren";
            }
            case CRIMSON_STEM -> {
                return "Crimson Stamm";
            }
            case DARK_OAK_LOG -> {
                return "Schwarz Eichen Stamm";
            }
            case DIAMOND -> {
                return "Diamant";
            }
            case DRAGON_EGG -> {
                return "Drachen Ei";
            }
            case DRAGON_HEAD -> {
                return "Drachen Kopf";
            }
            case END_PORTAL_FRAME -> {
                return "End Portal Rahmen";
            }
            case GLOWSTONE -> {
                return "Glowstone";
            }
            case MANGROVE_LOG -> {
                return "Mangroven Stamm";
            }
            case GOLD_INGOT -> {
                return "Gold Barren";
            }
            case JUNGLE_LOG -> {
                return "Tropenbaum Stamm";
            }
            case LAPIS_LAZULI -> {
                return "Lapis Lazuli";
            }
            case OAK_LOG -> {
                return "Eichen Stamm";
            }
            case RAW_IRON -> {
                return "Rohes Eisen";
            }
            case REDSTONE -> {
                return "Redstone Staub";
            }
            case SPAWNER -> {
                return "Spawner";
            }
            case SPRUCE_LOG -> {
                return "Fichten Stamm";
            }
            case WARPED_STEM -> {
                return "Warped Stamm";
            }
        }
        return null;
    }

    public Material getMaterial(){
        Material mat;
        mat = Material.getMaterial(this.toString());
        return mat;
    }

}
