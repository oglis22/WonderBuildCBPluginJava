package dev.lupluv.cb.utils;

import dev.lupluv.cb.Citybuild;

public class Worth {

    public static Worth getWorth(CbItem cbItem){
        return Citybuild.getFileManager().getWorth(cbItem);
    }

    private CbItem cbItem;

    private double buy;
    private double sell;

    public Worth(CbItem cbItem, double buy, double sell) {
        this.cbItem = cbItem;
        this.buy = buy;
        this.sell = sell;
    }

    public CbItem getCbItem() {
        return cbItem;
    }

    public void setCbItem(CbItem cbItem) {
        this.cbItem = cbItem;
    }

    public double getBuy() {
        return buy;
    }

    public void setBuy(double buy) {
        this.buy = buy;
    }

    public double getSell() {
        return sell;
    }

    public void setSell(double sell) {
        this.sell = sell;
    }
}
