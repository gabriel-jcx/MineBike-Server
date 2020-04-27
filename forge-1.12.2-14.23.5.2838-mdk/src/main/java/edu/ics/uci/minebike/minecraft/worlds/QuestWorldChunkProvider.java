package edu.ics.uci.minebike.minecraft.worlds;

import com.google.common.collect.ImmutableList;
import edu.ics.uci.minebike.minecraft.worlds.CustomBiomes.BasicBiomeGenerator;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldEntitySpawner;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.MapGenBase;
import net.minecraftforge.event.terraingen.InitMapGenEvent;
import net.minecraftforge.event.terraingen.TerrainGen;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class QuestWorldChunkProvider implements IChunkGenerator {
    private final World worldObj;
    private Random rand_seed;
    private Biome[] biomesForGeneration;
    private List<Biome.SpawnListEntry> mobs;
    private MapGenBase baseGenerator = new MapGenBase();
    private BasicBiomeGenerator terrianGenerator;// = new BasicBiomeGenerator();
    //private NormalTer
    public QuestWorldChunkProvider(World worldObj){
        this.worldObj = worldObj;
        long seed = worldObj.getSeed();
        this.rand_seed = new Random((seed + 516) * 314); // seems to be arbitrary numbers atm!
        terrianGenerator = new BasicBiomeGenerator();
        terrianGenerator.setup(worldObj,rand_seed);
        baseGenerator = TerrainGen.getModdedMapGen(baseGenerator, InitMapGenEvent.EventType.CUSTOM);
    }
    public QuestWorldChunkProvider(World worldObj, BasicBiomeGenerator generator){
        this.worldObj = worldObj;
        long seed = worldObj.getSeed();
        this.rand_seed = new Random((seed + 516) * 314);
        terrianGenerator = generator;
        terrianGenerator.setup(worldObj, rand_seed);
        baseGenerator = TerrainGen.getModdedMapGen(baseGenerator, InitMapGenEvent.EventType.CUSTOM);


    }
    @Override
    public Chunk generateChunk(int x, int z) {
        ChunkPrimer chunkPrimer = new ChunkPrimer();
        // TODO:fiugre out hwat is x and z?
        this.biomesForGeneration = this.worldObj.getBiomeProvider().getBiomesForGeneration(this.biomesForGeneration, x * 4 -2, z * 4 -2 ,16 ,16);
        //System.out.println("Length of this.biomesForGeneration = " + this.biomesForGeneration.length);
        terrianGenerator.setBiomesForGeneration(biomesForGeneration);
        terrianGenerator.replaceBiomeBlocks(x,z,chunkPrimer,this,biomesForGeneration);

        //Generate terrian?
        this.terrianGenerator.generate(x,z,chunkPrimer);

        Chunk chunk = new Chunk(worldObj,chunkPrimer,x,z);
        // WTH is the following for loop doing!!??
        byte[] biomeArray = chunk.getBiomeArray();
        for(int i = 0; i < biomeArray.length; i++){
            biomeArray[i] = (byte)Biome.getIdForBiome(this.biomesForGeneration[i]);
        }
        chunk.generateSkylightMap();
        return chunk;
    }

    @Override
    public void populate(int x, int z) {
        int i = x * 16;
        int j = z * 16;
        BlockPos pos = new BlockPos(i,0,j);
        Biome biome = this.worldObj.getBiome(pos.add(16,0,16));

        //TODO: HERE add decorations and entities???
        //Add biomes decorations
        //biome.decorate(worldObj,rand_seed, pos);

        // Spawn some entitys in there including animals
        //WorldEntitySpawner.performWorldGenSpawning(worldObj, biome, i + 8, j + 8, 16,16,rand_seed);
    }

    @Override
    public boolean generateStructures(Chunk chunkIn, int x, int z) {
        return false;
    }

    @Override
    public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
        if(creatureType == EnumCreatureType.MONSTER){
            return mobs;
        }
        return ImmutableList.of(); // ????????? What is this ?
    }

    @Nullable
    @Override
    public BlockPos getNearestStructurePos(World worldIn, String structureName, BlockPos position, boolean findUnexplored) {
        return null;
    }

    @Override
    public void recreateStructures(Chunk chunkIn, int x, int z) {

    }

    @Override
    public boolean isInsideStructure(World worldIn, String structureName, BlockPos pos) {
        return false;
    }
}
