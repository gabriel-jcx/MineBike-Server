package edu.ics.uci.minebike.minecraft;

import edu.ics.uci.minebike.minecraft.worlds.WorldProviderSoccerQuest;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

public class CommonProxy {
    public void CommonProxy(){
        DimensionType soccerDType = DimensionType.register("soccerDim", "customDim", WorldProviderSoccerQuest.dimID,WorldProviderSoccerQuest.class, true);
        DimensionManager.registerDimension(soccerDType.getId(),soccerDType);
    }
}
