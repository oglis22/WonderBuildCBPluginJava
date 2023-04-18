package dev.lupluv.cb.casino;

import dev.lupluv.cb.casino.coinflip.CoinflipManager;

public class Casino {

    private static Casino instance;

    public Casino() {
        instance = this;
        coinflipManager = new CoinflipManager();
    }

    private CoinflipManager coinflipManager;

    public CoinflipManager getCoinflipManager() {
        return coinflipManager;
    }

    public static Casino getInstance() {
        return instance;
    }
}
