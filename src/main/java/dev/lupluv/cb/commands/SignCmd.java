package dev.lupluv.cb.commands;

import dev.lupluv.cb.annotations.RegisterCommand;
import dev.lupluv.cb.utils.Strings;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@RegisterCommand(name = "sign", permission = "cb.sign")
public class SignCmd implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(sender instanceof Player){
            Player p = (Player) sender;
            if(!p.hasPermission("cb.sign")){
                p.sendMessage(Strings.noPerms);
            }else{
                if(args.length == 0){
                    ItemStack is = p.getItemInHand();
                    if(is != null && is.getType() != Material.AIR){
                        if(is.getLore() != null && !is.getLore().isEmpty()) {
                            if(is.getLore().size() >= 2){
                                if(is.getLore().get(is.getLore().size()-2).contains("Signiert")){
                                    is.getLore().remove(is.getLore().size()-1);
                                    is.getLore().remove(is.getLore().size()-1);
                                }
                            }
                        }
                        if(is.getLore() == null){
                            List<String> lore = new ArrayList<>();
                            lore.add("§bSigniert von §l" + p.getName());
                            lore.add("§7*");
                            is.setLore(lore);
                            p.updateInventory();
                        }else{
                            List<String> lore = is.getLore();
                            lore.add("§bSigniert von §l" + p.getName());
                            lore.add("§7*");
                            is.setLore(lore);
                            p.updateInventory();
                        }
                    }else{
                        p.sendMessage(Strings.prefix + "§cDu musst ein Item in der Hand halten.");
                    }
                }else{
                    String msg = String.join(" ", args);
                    ItemStack is = p.getItemInHand();
                    if(is != null && is.getType() != null){
                        if(!is.getLore().isEmpty()) {
                            if(is.getLore().size() >= 2){
                                if(is.getLore().get(is.getLore().size()-2).contains("Signiert")){
                                    is.getLore().remove(is.getLore().size()-1);
                                    is.getLore().remove(is.getLore().size()-1);
                                }
                            }
                        }
                        is.getLore().add("§bSigniert von §l" + p.getName());
                        is.getLore().add("§7" + msg.replace("&", "§"));
                    }else{
                        p.sendMessage(Strings.prefix + "§cDu musst ein Item in der Hand halten.");
                    }
                }
            }
        }

        return false;
    }
}
