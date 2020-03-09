package edu.ics.uci.minebike.minecraft.worlds;

import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldProvider;
import net.minecraft.world.WorldType;

public abstract class WorldProviderCustomQuests extends WorldProvider {
    public static int dimID;
    public static String dimName;
    protected WorldType terrainType;

    @Override
    public DimensionType getDimensionType(){
        return DimensionType.getById(dimID);
    }
}
