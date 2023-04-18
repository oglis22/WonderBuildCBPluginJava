package dev.lupluv.cb.utils;
import java.util.List;

import dev.lupluv.cb.economy.Bank;
import org.bukkit.OfflinePlayer;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;

public class EconomyHolder implements Economy {

    /* In use */

    @Override
    public boolean isEnabled() {
        return true;
    }

    /* Balance */

    @Override
    public double getBalance(String p) {
        return dev.lupluv.cb.economy.Economy.getBalance(p);
    }

    @Override
    public double getBalance(OfflinePlayer p) {
        return dev.lupluv.cb.economy.Economy.getBalance(p);
    }

    @Override
    public double getBalance(String p, String arg1) {
        return dev.lupluv.cb.economy.Economy.getBalance(p);
    }

    @Override
    public double getBalance(OfflinePlayer p, String arg1) {
        return dev.lupluv.cb.economy.Economy.getBalance(p);
    }

    /* Deposit on player account */

    @Override
    public EconomyResponse depositPlayer(String p, double a) {
        //TODO Place here your depsit player code
        return dev.lupluv.cb.economy.Economy.depositPlayer(p, a);
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer p, double a) {
        //TODO Place here your depsit player code
        return dev.lupluv.cb.economy.Economy.depositPlayer(p, a);
    }

    @Override
    public EconomyResponse depositPlayer(String p, String arg1, double a) {
        //TODO Place here your depsit player code
        return dev.lupluv.cb.economy.Economy.depositPlayer(p, a);
    }

    @Override
    public EconomyResponse depositPlayer(OfflinePlayer p, String arg1, double a) {
        //TODO Place here your depsit player code
        return dev.lupluv.cb.economy.Economy.depositPlayer(p, a);
    }

    /* Withdraw from player account */

    @Override
    public EconomyResponse withdrawPlayer(String p, double a) {
        //TODO Place here your withdraw player code
        return dev.lupluv.cb.economy.Economy.withdrawPlayer(p, a);
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer p, double a) {
        //TODO Place here your withdraw player code
        return dev.lupluv.cb.economy.Economy.withdrawPlayer(p, a);
    }

    @Override
    public EconomyResponse withdrawPlayer(String p, String arg1, double a) {
        //TODO Place here your withdraw player code
        return dev.lupluv.cb.economy.Economy.withdrawPlayer(p, a);
    }

    @Override
    public EconomyResponse withdrawPlayer(OfflinePlayer p, String arg1, double a) {
        //TODO Place here your withdraw player code
        return dev.lupluv.cb.economy.Economy.withdrawPlayer(p, a);
    }

    /* Not in use */

    @Override
    public EconomyResponse bankBalance(String arg0) {

        return dev.lupluv.cb.economy.Bank.getBalanceResponse(arg0);
    }

    @Override
    public EconomyResponse bankDeposit(String arg0, double arg1) {

        return dev.lupluv.cb.economy.Bank.depositBank(arg0, arg1);
    }

    @Override
    public EconomyResponse bankHas(String arg0, double arg1) {

        return dev.lupluv.cb.economy.Bank.has(arg0, arg1);
    }

    @Override
    public EconomyResponse bankWithdraw(String arg0, double arg1) {

        return dev.lupluv.cb.economy.Bank.withdrawBank(arg0, arg1);
    }

    @Override
    public EconomyResponse createBank(String arg0, String arg1) {

        return dev.lupluv.cb.economy.Bank.createBank(arg0);
    }

    @Override
    public EconomyResponse createBank(String arg0, OfflinePlayer arg1) {

        return dev.lupluv.cb.economy.Bank.createBank(arg0);
    }

    @Override
    public boolean createPlayerAccount(String arg0) {

        return false;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer arg0) {

        return false;
    }

    @Override
    public boolean createPlayerAccount(String arg0, String arg1) {

        return false;
    }

    @Override
    public boolean createPlayerAccount(OfflinePlayer arg0, String arg1) {

        return false;
    }

    @Override
    public String currencyNamePlural() {
        return "Coins";
    }

    @Override
    public String currencyNameSingular() {
        return "Coin";
    }

    @Override
    public EconomyResponse deleteBank(String arg0) {
        return dev.lupluv.cb.economy.Bank.deleteBank(arg0);
    }

    @Override
    public String format(double arg0) {
        return arg0 + " Coins";
    }

    @Override
    public int fractionalDigits() {
        return 0;
    }

    @Override
    public List<String> getBanks() {
        return dev.lupluv.cb.economy.Bank.getBankNames();
    }

    @Override
    public String getName() {
        return "WonderbuildEconomy";
    }

    @Override
    public boolean has(String arg0, double arg1) {
        double bal = getBalance(arg0);
        return bal >= arg1;
    }

    @Override
    public boolean has(OfflinePlayer arg0, double arg1) {
        double bal = getBalance(arg0);
        return bal >= arg1;
    }

    @Override
    public boolean has(String arg0, String arg1, double arg2) {
        double bal = getBalance(arg0);
        return bal >= arg2;
    }

    @Override
    public boolean has(OfflinePlayer arg0, String arg1, double arg2) {
        double bal = getBalance(arg0);
        return bal >= arg2;
    }

    @Override
    public boolean hasAccount(String arg0) {
        return new dev.lupluv.cb.economy.Economy(arg0).existsByName();
    }

    @Override
    public boolean hasAccount(OfflinePlayer arg0) {
        return new dev.lupluv.cb.economy.Economy(arg0.getUniqueId()).existsByUuid();
    }

    @Override
    public boolean hasAccount(String arg0, String arg1) {
        return new dev.lupluv.cb.economy.Economy(arg0).existsByName();
    }

    @Override
    public boolean hasAccount(OfflinePlayer arg0, String arg1) {
        return new dev.lupluv.cb.economy.Economy(arg0.getUniqueId()).existsByUuid();
    }

    @Override
    public boolean hasBankSupport() {

        return false;
    }

    @Override
    public EconomyResponse isBankMember(String arg0, String arg1) {
        return null;
    }

    @Override
    public EconomyResponse isBankMember(String arg0, OfflinePlayer arg1) {

        return null;
    }

    @Override
    public EconomyResponse isBankOwner(String arg0, String arg1) {

        return null;
    }

    @Override
    public EconomyResponse isBankOwner(String arg0, OfflinePlayer arg1) {
        return null;
    }

}