package edu.ics.uci.minebike.minecraft;

import edu.ics.uci.minebike.minecraft.worlds.WorldProviderSoccerQuest;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.DimensionManager;

public class CommonProxy {
    public CommonProxy(){
        System.out.println("Registering Dimension with id = " + WorldProviderSoccerQuest.DIM_ID);
        DimensionType soccerDType = DimensionType.register("soccerDim", "customDim", WorldProviderSoccerQuest.DIM_ID,WorldProviderSoccerQuest.class, true);
        DimensionManager.registerDimension(soccerDType.getId(),soccerDType);
    }
}
