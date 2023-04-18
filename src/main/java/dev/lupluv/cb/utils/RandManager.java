package dev.lupluv.cb.utils;

public class RandManager {

    /*
    public static void setRand(Player p, Material material){
        PlotAPI api = new PlotAPI();
        PlotPlayer pp = api.wrapPlayer(p.getUniqueId());
        Plot plot = api.getPlotSquared().getPlotAreaManager().getPlotAreaByString("cb").getPlot(pp.getLocation());
        final PlotBlock[] pb = (PlotBlock[]) Configuration.BLOCKLIST.parseString(id);
        PlotManager pm = api.
        if (plot.getConnectedPlots().size() > 1) {
            for (final Plot plots : plot.getConnectedPlots()) {
                plots.getPlotModificationManager().setComponent("", PatternUtil.parse(pp, ""), pp);
            }
            p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 0.0f);
            if (msg) {
                p.sendMessage(Data.angepasst.replaceAll("%prefix", Data.pr).replaceAll("%id", id).replaceAll("%name", event.getCurrentItem().getItemMeta().getDisplayName()));
            }
        }
        else {
            Main.api.getPlotManager(p.getWorld()).setComponent(plot.getArea(), plot.getId(), "border", pb);
            p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 0.0f);
            if (msg) {
                p.sendMessage(Data.angepasst.replaceAll("%prefix", Data.pr).replaceAll("%id", id).replaceAll("%name", event.getCurrentItem().getItemMeta().getDisplayName()));
            }
        }
        Bukkit.getScheduler().scheduleAsyncDelayedTask((Plugin)Main.getPlugin(), (Runnable)new Runnable() {
            @Override
            public void run() {
                plot.setSign();
            }
        }, 10L);
    }
     */
}
