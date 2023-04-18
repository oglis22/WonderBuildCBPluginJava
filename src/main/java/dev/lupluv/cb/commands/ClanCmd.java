package dev.lupluv.cb.commands;

import dev.lupluv.cb.annotations.RegisterCommand;
import dev.lupluv.cb.clans.*;
import dev.lupluv.cb.economy.Economy;
import dev.lupluv.cb.utils.Strings;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RegisterCommand(name = "clan", aliases = {"clans"}, permission = "cb.clans", description = "Clan command")
public class ClanCmd implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if(!(sender instanceof Player)) return true;
        Player player = (Player) sender;

        if(!player.hasPermission("cb.clans")){
            player.sendMessage(Strings.noPerms);
        }else{
            // /clan help [<page>] - Shows help article and commands list
            // /clan create [<name>] [<tag>] [<color>] - Creates a new clan (costs: 1.000.000 €)
            // /clan join [<name/tag>] - Join a clan
            // /clan info [<player>] - Get a info card (probably a gui) for a guild
            // /clan list - List all the clans
            // /clan menu - Shows the main clan menu
            // /clan promote [<player>] - Upgrade the rank of a player in your guild (only as admin or higher)
            // /clan demote [<player>] - Downgrade the rank of a player in your guild (only as admin or higher)

            // /clan kick [<player>] - Kick a player out of your guild (only as admin or higher)
            // /clan requests - Show your clans requests (only as mod or higher)
            // /clan accept [<player>] - Accept the join request of a player (only as mod or higher)
            // /clan deny [<player>] - Deny the request of a player (only as mod or higher)
            // /clan manage - Open clan manage gui (only as admin or higher)
            // /clan mod - Open clan mod gui (only as mod or higher)
            // /clan change name [<name>] - Change the name of the guild (only as owner or higher) (costs: 10.000 €)
            // /clan change tag [<tag>] - Change the tag of the guild (only as owner or higher) (costs: 20.000 €)

            // /clan toggle [<open/chat/>] - Toggle a few clan settings
            //
            // There is a maximum of 3 owners
            // There is a maximum of 3 admins
            // There is a maximum of 6 mods
            // There is a maximum of 20 members (including staff) (as rank: default)
            // There is a maximum of 50 members (including staff) (as rank: premium)
            // There is a maximum of 100 members (including staff) (as rank: platin)
            // There is a maximum of 200 members (including staff) (as rank: titan)
            // There is a maximum of 1000 members (including staff) (as rank: wonder)
            //

            if(args.length == 0){
                sendCommandList(player, 0);
            }else{
                if(args[0].equalsIgnoreCase("help")){
                    if(args.length == 1){
                        try{
                            int page = Integer.parseInt(args[0]);
                            if(page <= 3 && page >= 1){
                                sendCommandList(player, page-1);
                            }else{
                                player.sendMessage(Strings.prefix + "§cBitte gebe eine gültige Seite an.");
                            }
                        }catch (NumberFormatException e){
                            player.sendMessage(Strings.prefix + "§cBitte gebe eine gültige Seite an.");
                        }
                    }else{
                        player.sendMessage(Strings.prefix + "§cBitte gebe eine gültige Seite an.");
                    }
                }else if(args[0].equalsIgnoreCase("create")){
                    if(args.length == 4){
                        // /clan create [<name>] [<tag>] [<color>]
                        String name = args[1];
                        String tag = args[2];
                        String colorString = args[3];
                        if(name.length() >= 4){
                            if(name.length() <= 16){
                                Clan clanTest1 = new Clan();
                                clanTest1.setName(name);
                                if(!clanTest1.existsByName()){
                                    if(tag.length() >= 2){
                                        if(tag.length() <= 5){
                                            Clan clanTest2 = new Clan();
                                            clanTest2.setTag(tag);
                                            if(!clanTest2.existsByTag()){
                                                ChatColor color = ChatColor.of(colorString);
                                                if(color != null){
                                                    Clan clan = new Clan(name, tag, color, false, true, System.currentTimeMillis());
                                                    if(!clan.existsByCons()){
                                                        double balance = Economy.getBalance(player.getUniqueId());
                                                        if(balance >= 1000000){
                                                            sendCreateConfirm(player, clan);
                                                        }else{
                                                            player.sendMessage(Strings.prefix + "§cDu hast nicht genug Geld.");
                                                        }
                                                    }else{
                                                        player.sendMessage(Strings.prefix + "§cEtwas ist schiefgelaufen. Bitte wende dich an den Support.");
                                                    }
                                                }else{
                                                    player.sendMessage(Strings.prefix + "§cEs wurde keine gültige Farbe angegeben.");
                                                    player.sendMessage(Strings.prefix + "§7Gültige Farben: §a" + ChatColor.values());
                                                    player.sendMessage(Strings.prefix + "§7Oder: §a" + availableColors.toString());
                                                }
                                            }else{
                                                player.sendMessage(Strings.prefix + "§cDer Clan Tag ist bereits vergeben.");
                                            }
                                        }else{
                                            player.sendMessage(Strings.prefix + "§cDer Clan Tag darf nicht länger als 5 Zeichen sein.");
                                        }
                                    }else{
                                        player.sendMessage(Strings.prefix + "§cDer Clan Tag muss länger als 1 Zeichen sein.");
                                    }
                                }else{
                                    player.sendMessage(Strings.prefix + "§cDer Clan Name ist bereits vergeben.");
                                }
                            }else{
                                player.sendMessage(Strings.prefix + "§cDer Clan Name darf nicht länger als 16 Zeichen sein.");
                            }
                        }else{
                            player.sendMessage(Strings.prefix +"§cDer Clan Name muss länger als 3 Zeichen sein.");
                        }
                    }else{
                        player.sendMessage(Strings.prefix + "§7Benutzung: §a/clan create [<name>] [<tag>] [<farbe>]");
                        player.sendMessage(Strings.prefix + "§7Gültige Farben: §a" + ChatColor.values().toString());
                        player.sendMessage(Strings.prefix + "§7Oder: §a" + availableColors.toString().toString());
                        player.sendMessage(Strings.prefix + "§eHinweis: §7Nutze Sternchen im Clan Namen für Leerzeichen. Beispiel: Mein*Clan -> §aMein Clan");
                    }
                }else if(args[0].equalsIgnoreCase("join")){
                    if(args.length == 2){
                        String targetString = args[1];
                        Clan clan = new Clan();
                        clan.setTag(targetString);
                        if(!clan.existsByTag()){
                            clan.loadByTag();
                            if(clan.getMembers().size() < clan.getMaxMembers()){
                                User user = new User(player.getUniqueId());
                                if(!user.existsByUuid()) {
                                    user.createID();
                                    user.setName(player.getName());
                                    user.create();
                                }else{
                                    user.loadByUuid();
                                }
                                ClanRequest request = new ClanRequest(user.getId(), clan.getId());
                                if(!request.existsByUserAndClan()){
                                    request.setDate(System.currentTimeMillis());
                                    request.setType(RequestType.JOIN_REQUEST);
                                    request.create();

                                }else{
                                    if(request.getType() == RequestType.JOIN_REQUEST){
                                        player.sendMessage(Strings.prefix + "§cDu hast bereits angefragt diesem Clan beizutreten.");
                                    }else if(request.getType() == RequestType.INVITE){
                                        // Join successful
                                        // Already invited
                                    }
                                }
                            }else{
                                player.sendMessage(Strings.prefix + "§cDieser Clan ist bereits voll.");
                            }
                        }else{
                            player.sendMessage(Strings.prefix + "§cEs existiert kein Clan mit dem Tag.");
                        }
                    }else{
                        player.sendMessage(Strings.prefix + "§7Benutzung: §a/clan join [<tag>]");
                    }
                }else if(args[0].equalsIgnoreCase("info")){
                    if(args.length == 1){
                        // Own clan
                        User user = new User(player.getUniqueId());
                        if(!user.existsByUuid()) {
                            user.setName(player.getName());
                            user.createID();
                            user.create();
                        }
                        Clan clan = ClanMember.getClan(user);
                        if(clan == null){
                            player.sendMessage(Strings.prefix + "§cDu bist in keinem Clan.");
                            return true;
                        }
                        sendClanInfo(player, clan);
                    }else if(args.length == 2){
                        // Search clan
                    }
                }else if(args[0].equalsIgnoreCase("list")){

                }else if(args[0].equalsIgnoreCase("menu")){

                }else if(args[0].equalsIgnoreCase("promote")){

                }else if(args[0].equalsIgnoreCase("demote")){

                }else if(args[0].equalsIgnoreCase("kick")){

                }else if(args[0].equalsIgnoreCase("requests")){

                }else if(args[0].equalsIgnoreCase("accept")){

                }else if(args[0].equalsIgnoreCase("deny")){

                }else if(args[0].equalsIgnoreCase("manage")){

                }else if(args[0].equalsIgnoreCase("mod")){

                }else if(args[0].equalsIgnoreCase("change")){

                }else{
                    if(args[0].equalsIgnoreCase("cancel")){
                        if(createConfirms.containsKey(player.getUniqueId())){
                            player.sendMessage(Strings.prefix + "§cAktion abgebrochen.");
                            createConfirms.remove(player.getUniqueId());
                            return true;
                        }
                    }else if(args[0].equalsIgnoreCase("confirm")){
                        if(createConfirms.containsKey(player.getUniqueId())){
                            Clan clan = createConfirms.get(player.getUniqueId());
                            if(!clan.existsByCons()) {
                                player.sendMessage(Strings.prefix + "§aAktion bestätigt.");

                                clan.createID();
                                clan.create();

                                player.sendMessage(Strings.prefix + "§6Herzlichen Glückwunsch. §aDu hast deinen Clan erstellt.");
                                player.sendMessage(Strings.prefix + "§7Lade Spieler mit /clan invite [<spieler>] in deinen Clan ein.");

                                // TODO: Create Clan inventory and open it here

                            }else{
                                player.sendMessage(Strings.prefix + "§cDer Clan existiert bereits.");
                            }
                            createConfirms.remove(player.getUniqueId());
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    public static final String[] availableColors = new String[]{
            "§1&1§a",
            "§2&2§a",
            "§3&3§a",
            "§4&4§a",
            "§5&5§a",
            "§6&6§a",
            "§7&7§a",
            "§8&8§a",
            "§9&9§a",
            "§a&a§a",
            "§b&b§a",
            "§c&c§a",
            "§d&d§a",
            "§e&e§a",
            "§f&f§a",
            "§1DARK_BLUE§a",
            "§2DARK_GREEN§a",
            "§3DARK_AQUA§a"
    };

    // /clan help [<page>] - Shows help article and commands list
    // /clan create [<name>] [<tag>] [<color>] - Creates a new clan (costs: 1.000.000 €)
    // /clan join [<name/tag>] - Join a clan
    // /clan info [<player/name/tag>] - Get a info card (probably a gui) for a guild
    // /clan menu - Shows the main clan menu
    // /clan promote [<player>] - Upgrade the rank of a player in your guild (only as admin or higher)
    // /clan demote [<player>] - Downgrade the rank of a player in your guild (only as admin or higher)
    // /clan kick [<player>] - Kick a player out of your guild (only as admin or higher)

    // /clan requests - Show your clans requests (only as mod or higher)
    // /clan accept [<player>] - Accept the join request of a player (only as mod or higher)
    // /clan deny [<player>] - Deny the request of a player (only as mod or higher)
    // /clan manage - Open clan manage gui (only as admin or higher)
    // /clan mod - Open clan mod gui (only as mod or higher)
    // /clan change name [<name>] - Change the name of the guild (only as owner or higher) (costs: 10.000 €)
    // /clan change tag [<tag>] - Change the tag of the guild (only as owner or higher) (costs: 20.000 €)
    // /clan toggle [<open/chat/>] - Toggle a few clan settings

    //
    // There is a maximum of 3 owners
    // There is a maximum of 3 admins
    // There is a maximum of 6 mods
    // There is a maximum of 20 members (including staff) (as rank: default)
    // There is a maximum of 50 members (including staff) (as rank: premium)
    // There is a maximum of 100 members (including staff) (as rank: platin)
    // There is a maximum of 500 members (including staff) (as rank: titan)
    // There is a maximum of 1000 members (including staff) (as rank: wonder)
    //

    public void sendCommandList(Player p, int page){
        if(page == 0){
            p.sendMessage(Strings.prefix + "§eClans §8- §f§lCommands §8- §fSeite (1/2)");
            TextComponent helpCmdPrefix = new TextComponent(Strings.prefix);
            TextComponent helpCmd = new TextComponent(" §f/clan help §e[<seite>] §8- §bSiehe dir die Clan Command Liste an");
            helpCmdPrefix.addExtra(helpCmd);
            TextComponent createCmdPrefix = new TextComponent(Strings.prefix);
            TextComponent createCmd = new TextComponent(" §f/clan create §e[<name>] [<tag>] [<farbe>] §8- §bErstelle deinen Clan für 1.000.000 €");
            createCmdPrefix.addExtra(createCmd);
            TextComponent joinCmdPrefix = new TextComponent(Strings.prefix);
            TextComponent joinCmd = new TextComponent(" §f/clan join §e[<name/tag>] §8- §bTrete einem Clan bei oder send eine Anfrage");
            joinCmdPrefix.addExtra(joinCmd);
            TextComponent infoCmdPrefix = new TextComponent(Strings.prefix);
            TextComponent infoCmd = new TextComponent(" §f/clan info §e[<spieler/name/tag>] §8- §bErhalte Infos über einen Clan");
            infoCmdPrefix.addExtra(infoCmd);
            TextComponent menuCmdPrefix = new TextComponent(Strings.prefix);
            TextComponent menuCmd = new TextComponent(" §f/clan menu §8- §bÖffne das Clan Menu");
            menuCmdPrefix.addExtra(menuCmd);
            TextComponent promoteCmdPrefix = new TextComponent(Strings.prefix);
            TextComponent promoteCmd = new TextComponent(" §f/clan promote §e[<spieler>] §8- §bÄndere die Clan Rolle eines Mitglieds");
            promoteCmdPrefix.addExtra(promoteCmd);
            TextComponent demoteCmdPrefix = new TextComponent(Strings.prefix);
            TextComponent demoteCmd = new TextComponent(" §f/clan demote §e[<spieler>] §8- §bÄndere die Clan Rolle eines Mitglieds");
            demoteCmdPrefix.addExtra(demoteCmd);
            TextComponent kickCmdPrefix = new TextComponent(Strings.prefix);
            TextComponent kickCmd = new TextComponent(" §f/clan kick §e[<spieler>] §8- §bEntferne ein Mitglied aus deinem Clan");
            kickCmdPrefix.addExtra(kickCmd);
            p.sendMessage(helpCmdPrefix);
            p.sendMessage(createCmdPrefix);
            p.sendMessage(joinCmdPrefix);
            p.sendMessage(infoCmdPrefix);
            p.sendMessage(menuCmdPrefix);
            p.sendMessage(promoteCmdPrefix);
            p.sendMessage(demoteCmdPrefix);
            p.sendMessage(kickCmdPrefix);
        }else if(page == 1){
            TextComponent requestsCmdPrefix = new TextComponent(Strings.prefix);
            TextComponent requestsCmd = new TextComponent(" §f/clan requests §8- §bSiehe dir die Clan Command Liste an");
            requestsCmdPrefix.addExtra(requestsCmd);
        }
    }

    public void sendClanInfo(Player player, Clan clan){
        player.sendMessage(Strings.prefix + "§6§l" + clan.getColor() + clan.getName() + " §8| §6§l" + clan.getColor() + clan.getTag());
        player.sendMessage(Strings.prefix + "§8------------------------------------------------------");
        player.sendMessage(Strings.prefix + " ");
        player.sendMessage(Strings.prefix + " §fName: " + clan.getColor() + clan.getName());
        player.sendMessage(Strings.prefix + " §fTag: " + clan.getColor() + clan.getTag());
        player.sendMessage(Strings.prefix + " §fMitglieder: §7(" + clan.getMembers().size() + "/" + clan.getMaxMembers() + ")");
        player.sendMessage(Strings.prefix + " §fErstellt: §7" + clan.getDate());
        player.sendMessage(Strings.prefix + " ");
        player.sendMessage(Strings.prefix + " §fVerifiziert §8-> §c×");
        player.sendMessage(Strings.prefix + " ");
        player.sendMessage(Strings.prefix + "§8------------------------------------------------------");
    }

    public static Map<UUID, Clan> createConfirms = new HashMap<>();

    public void sendCreateConfirm(Player p, Clan clan){
        p.sendMessage(Strings.prefix + "§6Bist du sicher, dass du diesen Clan erstellen willst?");
        p.sendMessage(Strings.prefix + "§eName: §7" + clan.getName());
        p.sendMessage(Strings.prefix + "§eTag: §7" + clan.getTag());
        p.sendMessage(Strings.prefix + "§eColor: §7" + clan.getColor().toString());
        p.sendMessage(Strings.prefix + "§ePreis: §71.000.000 €");
        TextComponent txt = new TextComponent(Strings.prefix + " ");
        TextComponent confirmBtn = new TextComponent("§a§l[BESTÄTIGEN]");
        confirmBtn.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/clan confirm"));
        confirmBtn.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§eKlicke um die Aktion zu bestätigen.").create()));
        TextComponent placeholder = new TextComponent(" §8| ");
        TextComponent cancelBtn = new TextComponent("§c§l[ABBRECHEN]");
        cancelBtn.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/clan cancel"));
        cancelBtn.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§eKlicke um die Aktion abzubrechen.").create()));
        txt.addExtra(confirmBtn);
        txt.addExtra(placeholder);
        txt.addExtra(cancelBtn);
        p.sendMessage(txt);
        createConfirms.put(p.getUniqueId(), clan);
    }

}
