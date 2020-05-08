package edu.ics.uci.minebike.minecraft.worlds;

import edu.ics.uci.minebike.minecraft.worlds.CustomBiomes.OceanBiomeGenerator;
import edu.ics.uci.minebike.minecraft.worlds.CustomBiomes.PondBiomeGenerator;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.WorldType;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class WorldProviderFishingOcean extends WorldProviderFlat{
    public static int DIM_ID = 225;
    public static String DIM_NAME = "Ocean Dimension";
    private WorldGenerator custumGen;
    private int x= 100;
    private int z= 100;
    @Override
    public DimensionType getDimensionType() {
        return DimensionType.getById(DIM_ID);
    }
    public void registerWorldChunkManager() {
        this.setDimension(DIM_ID);
        //BiomeGenWhiteBeach beach= new BiomeGenWhiteBeach();
        QuestWorldChunkProvider fishing = new QuestWorldChunkProvider(this.world);

        fishing.generateChunk(x,z);
        //Biome.BiomeProperties properties = new Biome.BiomeProperties("Flat");
        //this.worldChunkMgr = new net.minecraft.world.biome.WorldChunkManagerHell(new BiomeGenFlatCaves(properties), 0F);
        this.hasSkyLight = false;
        this.terrainType = WorldType.FLAT;

    }

    public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {

    }
    @Override
    public IChunkGenerator createChunkGenerator(){
        //return terrainType.getChunkGenerator(this.world,)
        return new QuestWorldChunkProvider(this.world, new OceanBiomeGenerator());
    }

}
