package dev.lupluv.cb.economy;

import dev.lupluv.cb.utils.Strings;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.Map;

public class BankHandler implements Listener {

    public BankHandler() {
        this.bankActions = new HashMap<>();
    }

    public Map<Player, BankActionType> bankActions = new HashMap<>();

    public void putPayOut(Player player){
        putBankAction(player, BankActionType.PAY_OUT);
    }

    public void putPayIn(Player player){
        putBankAction(player, BankActionType.PAY_IN);
    }

    public void putBankAction(Player player, BankActionType bankActionType){
        if(!bankActions.containsKey(player)) {
            bankActions.put(player, bankActionType);
            player.closeInventory();
            if(bankActionType == BankActionType.PAY_IN){
                player.sendMessage(Strings.prefix + "§7Wie viel Geld möchtest du einzahlen? §8(§acancel §7zum abbrechen§8)");
            }else {
                player.sendMessage(Strings.prefix + "§7Wie viel Geld möchtest du auszahlen? §8(§acancel §7zum abbrechen§8)");
            }
        }else{
            player.sendMessage(Strings.prefix + "§cEs ist etwas schiefgelaufen...");
            bankActions.remove(player);
        }
    }

    public void chatEvent(Player player, String message){
        if(message.equalsIgnoreCase("cancel")){
            player.sendMessage(Strings.prefix + "§cDu hast die Aktion abgebrochen.");
            bankActions.remove(player);
            return;
        }
        try {
            double amount = Double.parseDouble(message);
            if(bankActions.get(player) == BankActionType.PAY_IN){
                double player_balance = Economy.getBalance(player.getUniqueId());
                if(amount < 1){
                    player.sendMessage(Strings.prefix + "§cDu musst mindestens 1 Coin einzahlen. §8(§acancel §7zum abbrechen§8)");
                    return;
                }
                if(player_balance < amount){
                    player.sendMessage(Strings.prefix + "§cDu hast nicht genug Bargeld. §8(§acancel §7zum abbrechen§8)");
                    return;
                }
                if(Economy.withdrawPlayer(player.getUniqueId(), amount).transactionSuccess()
                        && Bank.depositBank(player.getUniqueId().toString(), amount).transactionSuccess()) {
                    player.sendMessage(Strings.prefix + "§7Einzahlung §aerfolgreich §7abgeschlossen.");
                    bankActions.remove(player);
                    new BankUI(player).setMainGUI().openGUI();
                    return;
                }
                player.sendMessage(Strings.prefix + "§cEtwas ist schiefgelaufen...");
                bankActions.remove(player);
            }else{
                double bank_balance = Bank.getBalance(player.getUniqueId().toString());
                if(amount < 1){
                    player.sendMessage(Strings.prefix + "§cDu musst mindestens 1 Coin auszahlen. §8(§acancel §7zum abbrechen§8)");
                    return;
                }
                if(bank_balance < amount){
                    player.sendMessage(Strings.prefix + "§cDu hast nicht genug Geld auf der Bank. §8(§acancel §7zum abbrechen§8)");
                    return;
                }
                if(Economy.depositPlayer(player.getUniqueId(), amount).transactionSuccess()
                        && Bank.withdrawBank(player.getUniqueId().toString(), amount).transactionSuccess()) {
                    player.sendMessage(Strings.prefix + "§7Auszahlung §aerfolgreich §7abgeschlossen.");
                    bankActions.remove(player);
                    new BankUI(player).setMainGUI().openGUI();
                    return;
                }
                player.sendMessage(Strings.prefix + "§cEtwas ist schiefgelaufen...");
                bankActions.remove(player);
            }
        }catch (NumberFormatException ex){
            player.sendMessage(Strings.prefix + "§cBitte gebe eine Zahl an. §8(§acancel §7zum abbrechen§8)");
        }
    }

}
