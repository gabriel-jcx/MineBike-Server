package edu.ics.uci.minebike.minecraft.worlds;

import net.minecraft.world.DimensionType;

public class WorldProviderSoccerQuest extends WorldProviderFlat {
    public static int DIM_ID = 222;
    public static String DIM_NAME = "Soccer Dimension";
    public void WorldProviderSoccerQuest(){
        dimID = DIM_ID;
        dimName = DIM_NAME;
        terrainType = this.world.getWorldInfo().getTerrainType();
    }

    public void registerWorldChunkManager(){

    }

}
