package dev.lupluv.cb.commands;

import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.driver.permission.IPermissionUser;
import de.dytanic.cloudnet.driver.permission.Permission;
import dev.lupluv.cb.Citybuild;
import dev.lupluv.cb.advent.Reward;
import dev.lupluv.cb.annotations.RegisterCommand;
import dev.lupluv.cb.utils.Crafting;
import dev.lupluv.cb.utils.Strings;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.alchemy.Potion;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;

@RegisterCommand(name = "givereward", permission = "cb.givereward")
public class GiverewardCmd implements CommandExecutor {


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(sender instanceof Player) return true;

        if(args.length == 2){
            Player target = Bukkit.getPlayer(args[0]);
            if(target == null) return true;
            String name = args[1];
            if(name == null) return true;
            if(name.equalsIgnoreCase("CHRISTMAS_NAMECOLOR")){
                IPermissionUser user = CloudNetDriver.getInstance().getPermissionManagement().getUser(target.getUniqueId());
                assert user != null;
                user.addPermission("citybuild", "cb.namecolor.color.GRAD_CHRISTMAS");
                CloudNetDriver.getInstance().getPermissionManagement().updateUser(user);
                target.sendMessage(Strings.prefix + "§aDu hast die Weihnachts Namensfarbe erhalten." +
                        " Du kannst diese sobald die Wartungen vorbei sind auswählen.");
                return true;
            }
            ItemStack reward = getReward(Reward.valueOf(name.toUpperCase()));
            if(reward == null) return true;

            target.getInventory().addItem(reward);
            target.sendMessage(Strings.prefix + "§aDu hast " + reward.getAmount() + " " + reward.getType().toString() + " erhalten.");

            Citybuild.getPlugin().getLogger().log(Level.ALL, "Advent Reward #" + name + " has gone to " + target.getName());

        }

        return false;
    }

    public static ItemStack getReward(Reward reward){
        switch (reward){
            case LIGHT -> {
                return new ItemStack(Material.LIGHT);
            }
            case BREAD_64 -> {
                return new ItemStack(Material.BREAD, 64);
            }
            case CARROTS_64 -> {
                return new ItemStack(Material.CARROT, 64);
            }
            case DIAMOND_SWORD -> {
                return new ItemStack(Material.DIAMOND_SWORD);
            }
            case DIAMONDS_10 -> {
                return new ItemStack(Material.DIAMOND, 10);
            }
            case DRAGON_HEAD -> {
                return new ItemStack(Material.DRAGON_HEAD);
            }
            case ELEVATOR -> {
                return Crafting.getPublicElevator();
            }
            case EMERALDS_32 -> {
                return new ItemStack(Material.EMERALD, 32);
            }
            case FIRE_RESISTANCE_5_MIN -> {
                return getFireRes(6000);
            }
            case GOLDEN_APPLE_3 -> {
                return new ItemStack(Material.GOLDEN_APPLE, 3);
            }
            case IRON_BLOCK_5 -> {
                return new ItemStack(Material.IRON_BLOCK, 5);
            }
            case NETHERITE_INGOTS_5 -> {
                return new ItemStack(Material.NETHERITE_INGOT, 5);
            }
            case OAK_LOGS_64 -> {
                return new ItemStack(Material.OAK_LOG, 64);
            }
            case PIG_SPAWN_EGG -> {
                return new ItemStack(Material.PIG_SPAWN_EGG);
            }
            case QUARTZ_BLOCK_32 -> {
                return new ItemStack(Material.QUARTZ_BLOCK, 32);
            }
            case SHEEP_SPAWN_EGG -> {
                return new ItemStack(Material.SHEEP_SPAWN_EGG);
            }
            case SPONGE_10 -> {
                return new ItemStack(Material.SPONGE, 10);
            }
            case STEAK_64 -> {
                return new ItemStack(Material.COOKED_BEEF, 64);
            }
            case TRIDENT -> {
                return new ItemStack(Material.TRIDENT);
            }
        }
        return null;
    }

    public static ItemStack getFireRes(int time){
        ItemStack potion = new ItemStack(Material.POTION);
        PotionMeta meta = (PotionMeta) potion.getItemMeta();
        meta.addCustomEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, time, 0), true);
        potion.setItemMeta(meta);
        return potion;
    }

}
