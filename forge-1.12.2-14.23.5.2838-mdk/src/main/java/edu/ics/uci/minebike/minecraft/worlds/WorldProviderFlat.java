package edu.ics.uci.minebike.minecraft.worlds;

import edu.ics.uci.minebike.minecraft.worlds.CustomBiomes.BasicBiomeGenerator;
import net.minecraft.world.gen.IChunkGenerator;

public abstract class WorldProviderFlat extends WorldProviderCustomQuests {
    public static int flatBiomeID = 150;
    private static String flatGeneratorSettings = "2;7,5x1,3x3,2;" + Integer.toString(flatBiomeID) + ";decoration";


    @Override
    public IChunkGenerator createChunkGenerator(){
        return new QuestWorldChunkProvider(world);
    }
}
