package dev.lupluv.cb.plot;

import com.plotsquared.core.PlotAPI;
import com.plotsquared.core.configuration.ConfigurationUtil;
import com.plotsquared.core.player.PlotPlayer;
import com.plotsquared.core.plot.BlockBucket;
import com.plotsquared.core.plot.Plot;
import dev.lupluv.cb.utils.Strings;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class Rand {

    public static void setRand(Player p, Plot plot, RandType rand){
        /*
        if(rand == RandType.BEACON) {
            PlotAPI api = new PlotAPI();
            BlockBucket block = ConfigurationUtil.BLOCK_BUCKET.parseString(Material.BEACON.toString());
            if (plot.getConnectedPlots().size() > 1) {
                for (Plot plots : plot.getConnectedPlots()) {
                    api.getPlotSquared().getPlotAreaManager().getPlotAreaByString("cb").getPlotManager().setComponent(plots.getId(), "border"
                            , block.toPattern(), PlotPlayer.from(p), api.getBlockQueue()
                                    .getNewQueue(api.getPlotSquared().getPlotAreaManager().getPlotAreaByString("cb").getQueue().getWorld()));

                }
                p.sendMessage(Strings.prefix + "Der Rand wurde zu §a" + block.toString() + " §7geändert");
            } else {
                api.getPlotSquared().getPlotAreaManager().getPlotAreaByString("cb").getPlotManager().setComponent(plot.getId(), "border"
                        , block.toPattern(), PlotPlayer.from(p), api.getBlockQueue()
                                .getNewQueue(api.getPlotSquared().getPlotAreaManager().getPlotAreaByString("cb").getQueue().getWorld()));
                p.sendMessage(Strings.prefix + "Der Rand wurde zu §a" + block.toString() + " §7geändert");
            }
        }

         */
    }

    public static RandType getRand(Player p, Plot plot){
        PlotAPI api = new PlotAPI();
        return RandType.NONE;
    }

}
