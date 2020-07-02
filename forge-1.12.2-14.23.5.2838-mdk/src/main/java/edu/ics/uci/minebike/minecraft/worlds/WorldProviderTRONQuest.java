package edu.ics.uci.minebike.minecraft.worlds;

import net.minecraft.world.DimensionType;

public class WorldProviderTRONQuest extends WorldProviderFlat {
    public static int DIM_ID = 250;
    public static String DIM_NAME = "TRON Dimension";

    public void WorldProviderTRONQuest(){
        //this.
//        dimID = DIM_ID;
//        dimName = DIM_NAME;
//        terrainType = this.world.getWorldInfo().getTerrainType();
    }
    @Override
    public DimensionType getDimensionType(){

        return DimensionType.getById(DIM_ID);
    }
    public void registerWorldChunkManager(){

    }

}
