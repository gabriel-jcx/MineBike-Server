package edu.ics.uci.minebike.minecraft.worlds;

import net.minecraft.world.DimensionType;

public class WorldProviderMiner extends WorldProviderFlat {
    public static int DIM_ID = 420;
    public static String DIM_NAME = "Miner Dimension";

    public void WorldProviderSoccerQuest(){
        //this.
        dimID = DIM_ID;
        dimName = DIM_NAME;
        terrainType = this.world.getWorldInfo().getTerrainType();
    }
    @Override
    public DimensionType getDimensionType(){

        return DimensionType.getById(DIM_ID);
    }
    public void registerWorldChunkManager(){

    }

}
