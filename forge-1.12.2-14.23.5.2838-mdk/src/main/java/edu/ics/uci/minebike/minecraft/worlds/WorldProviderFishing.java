package edu.ics.uci.minebike.minecraft.worlds;
import edu.ics.uci.minebike.minecraft.worlds.CustomBiomes.PondBiomeGenerator;
import edu.ics.uci.minebike.minecraft.worlds.QuestWorldChunkProvider;
import edu.ics.uci.minebike.minecraft.worlds.WorldProviderCustomQuests;
import edu.ics.uci.minebike.minecraft.worlds.WorldProviderFlat;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;

import net.minecraft.world.DimensionType;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;
//import org.ngs.bigx.minecraft.quests.custom.FishingQuest;

import biomesoplenty.common.biome.overworld.BiomeGenWhiteBeach;
public class WorldProviderFishing extends WorldProviderFlat {

	public static int DIM_ID = 223;
	public static String DIM_NAME = "Fishing Dimension";
	private WorldGenerator custumGen;
	private int x= 100;
	private int z= 100;
	//x and z are world location
	@Override
	public DimensionType getDimensionType() {
		return DimensionType.getById(DIM_ID);
	}

//	@Override
//	public String getDimensionName() {
//		return fishingDimName;
//	}
	public void registerWorldChunkManager() {
		this.setDimension(DIM_ID);

		BiomeGenWhiteBeach beach= new BiomeGenWhiteBeach();
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
		return new QuestWorldChunkProvider(this.world, new PondBiomeGenerator());
	}

//	public void registerWorldChunkManager() {
//		this.dimensionId = fishingDimID;
//		this.worldChunkMgr = new net.minecraft.world.biome.WorldChunkManagerHell(new BiomeGenFlat(flatBiomeID), 0F);
//		this.hasNoSky = false;
//		this.terrainType = WorldType.FLAT;
//	}

}
