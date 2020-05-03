package edu.ics.uci.minebike.minecraft.worlds.CustomBiomes;

import net.minecraft.init.Blocks;
import net.minecraft.world.chunk.ChunkPrimer;

public class OceanBiomeGenerator extends BasicBiomeGenerator{
    public void generate(int chunkX, int chunkZ, ChunkPrimer primer){
        for (int x4 = 0; x4 < 4; ++x4) {
            for (int z4 = 0; z4 < 4; ++z4) {
                for (int h = 0; h < 4; ++h) {

                    for (int x = 0; x < 4; ++x) {
                        for (int z = 0; z < 4; ++z) {
                            if(h == 0){
                                primer.setBlockState(x4 * 4 + x, h, z4 * 4 + z, Blocks.GRASS.getDefaultState());
                                continue;

                            }
                            primer.setBlockState(x4 * 4 + x, h, z4 * 4 + z, Blocks.WATER.getDefaultState());
                        }
                    }
                }
            }
        }
    }
}
