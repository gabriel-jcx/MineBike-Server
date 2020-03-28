package edu.ics.uci.minebike.minecraft.worlds.CustomBiomes;

import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.chunk.ChunkPrimer;

public class PondBiomeGenerator extends BasicBiomeGenerator{
    private int water_count = 0;
    public void generate(int chunkX, int chunkZ, ChunkPrimer primer) {
        //generateHeightmap(chunkX * 4, 0, chunkZ * 4);

        byte waterLevel = 63;
        for (int x4 = 0; x4 < 4; ++x4) {
            for (int z4 = 0; z4 < 4; ++z4) {
                for (int h = 0; h < 4; ++h) {

                    for (int x = 0; x < 4; ++x) {
                        for (int z = 0; z < 4; ++z) {
                            if(h == 0){
                                primer.setBlockState(x4 * 4 + x, h, z4 * 4 + z, Blocks.GRASS.getDefaultState());
                                continue;

                            }
                            if(z4 < 4 && x4 < 4 && water_count < 64*4*3 ){
                                primer.setBlockState(x4*4+x, h ,z4*4+z,Blocks.WATER.getDefaultState());
                                water_count ++;
                            }else{
                                if(primer.getBlockState(x4*4+x,h,z4*4+z).getBlock().getBlockState() == Blocks.GRASS.getDefaultState()){
                                    System.out.println("Current BLOCK is Grass");
                                }else{
                                    primer.setBlockState(x4 * 4 + x, h, z4 * 4 + z, Blocks.GRASS.getDefaultState());
                                }
                            }
                        }
                    }
                }
            }
        }
//        }
    }

}
