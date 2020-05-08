package edu.ics.uci.minebike.minecraft.worlds;

import edu.ics.uci.minebike.minecraft.worlds.CustomBiomes.BasicBiomeGenerator;
import net.minecraft.world.gen.IChunkGenerator;

public abstract class WorldProviderFlat extends WorldProviderCustomQuests {
    public static int flatBiomeID = 150;


    @Override
    public IChunkGenerator createChunkGenerator(){
        return new QuestWorldChunkProvider(world);
    }
}
