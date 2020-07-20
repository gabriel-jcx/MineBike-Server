package edu.ics.uci.minebike.minecraft.quests.customQuests;

import java.time.Clock;
import java.util.Random;
import edu.ics.uci.minebike.minecraft.ClientUtils;
import edu.ics.uci.minebike.minecraft.ServerUtils;
import edu.ics.uci.minebike.minecraft.client.hud.HudRectangle;
import edu.ics.uci.minebike.minecraft.client.hud.HudString;
import edu.ics.uci.minebike.minecraft.quests.AbstractCustomQuest;
import edu.ics.uci.minebike.minecraft.worlds.WorldProviderMiner;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;


import edu.ics.uci.minebike.minecraft.constants.EnumPacketServer;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;

//


import net.minecraft.init.Blocks;


import net.minecraft.util.ResourceLocation;


//
public class Minequest extends AbstractCustomQuest {
	EntityPlayer player = null;
	public int DIMID;
	private boolean isStarted;
	private boolean isWaiting;
	public Vec3d questStartLocation;
	private int score = 0;
	private int ticks = 0;
	private long gameTime;
	private long waitTime;
	public static final int MINERDIMENSIONID = 420;

	private int currentTick;
	private boolean initialize;

	private HudRectangle timerRectangle;
	private HudString timerString;
	private int seconds;
	private static Clock clock;
	private String secondsString;


	private long TIME;
	private Clock fireClock;
	private long fireTIME;
	private int numMsForFireToSpawn;


	public Minequest() {
		super();
		//initializing all private variables

		currentTick = 0; // Once game starts the lava should proceed every couple ticks
		timerRectangle = new HudRectangle(-25, 5, 50, 30, 0xff0000ff, true, false);
		clock = Clock.systemDefaultZone();
		fireClock = Clock.systemDefaultZone();
		seconds = 60;
		secondsString += seconds;
		timerString = new HudString(0, 10, secondsString, 2.0f, true, false);
		numMsForFireToSpawn = 180;

		ResourceLocation[] instructionTextureLocations = new ResourceLocation[]
		    				{
	     					new ResourceLocation("textures/GUI/instructions/MinerQuestInstruction1.png"),
		     				new ResourceLocation("textures/GUI/instructions/MinerQuestInstruction2.png"),
		     				new ResourceLocation("textures/GUI/instructions/MinerQuestInstruction3.png"),
		     				};
		String[] instructionStringContents = new String[]
		         {
		                 "Run from the fire",
		                 "Collect Gold",
		                 "Stay alive until time runs out",
		         };


		// double check if instructions work
		this.DIMID = WorldProviderMiner.DIM_ID;
		this.questStartLocation = new Vec3d(0, 4, 0); // Need to find new location
		this.isStarted = false;
		this.isWaiting = false;
	}

	 public void onItemPickUp()
	 {
	 }

	enum direction     //<figure out what this does
	{
		//			NORTH,
		//			WEST;
	}

	@Override
	protected void setupQuestEnv(World world, EntityPlayer player) {
		return;
	}

	@Override
	public boolean onPlayerJoin(EntityPlayer player) {
		System.out.println("Player attempting to join");
		if (isStarted) {
			setupQuestEnv(player.world, player);
			ServerUtils.sendQuestData(EnumPacketServer.QuestJoinFailed, (EntityPlayerMP) player, Long.toString(this.waitTime));
			isWaiting = true;
			return false;
		} else {
			if (!isWaiting) {
				WorldServer ws = DimensionManager.getWorld(this.DIMID);
				isWaiting = true;
			}
			ServerUtils.telport((EntityPlayerMP) player, this.questStartLocation, this.DIMID);
			return true;
		}
	}

	@Override
	public void start(EntityPlayerMP playerMP) {
		//this.player = player;

		System.out.println(" Start Miner quest ");

		System.out.println("Trying to teleport " + playerMP.getName() + " to DIM" + this.DIMID);
		ServerUtils.telport((EntityPlayerMP) playerMP, questStartLocation, DIMID);
	}


	@Override
	public Vec3d getStartLocation() {
		return questStartLocation;
	}

	@Override
	public void start() {
		TIME = clock.millis();
	}

	@Override
	public void start(EntityPlayerSP player) {
		if (isStarted) {
			System.out.println("Game Already Started Going to Waiting");
		}
	}

	@Override
	public void start(EntityJoinWorldEvent event) {

	}

	@Override
	public void end() {
		System.out.println(" Start Miner quest ");
	}

	private void generateLavaWall(double startx, double startz, World world) {
		for (int z = (int) startz + 1; z < startz + 10; z++) {
			for (int y = 21; y < 24; y++) {
				BlockPos gangnam = new BlockPos((int) startx, y, z);
				world.setBlockState(gangnam, (IBlockState) Blocks.LAVA);
			}
		}
	}
	private void resetToAirStart(double startx, double startz, World world)
	{
		for(int x = (int) startx; x < 140+startx; x++)
		{
			for(int z = (int) startz+1; z < startz+10; z++)
			{
				for(int y = 21; y < 24; y++)
				{
					BlockPos gangnam = new BlockPos((int) startx, y, z);
					world.setBlockToAir(gangnam);
				}
			}
		}
	}
	private void resetToAirCont(double startx, double startz, World world)
	{

		for(int z = (int) startz+1; z < startz+10; z++)
		{
			for(int y = 21; y < 24; y++)
			{
				BlockPos seoul = new BlockPos((int) startx, y, z);
				world.setBlockToAir(seoul);
			}
		}
	}
	private void generateEmeraldWall(double startx, double startz, World world)
	{
		for(int z = 0; z < 11; z++)
		{
			if(world.isAirBlock(new BlockPos((int) (startx), 21, (int) (startz+z))))
			{
				BlockPos dab = new BlockPos((int) (startx), 21,(int) (startz+z));
				world.setBlockState(dab, (IBlockState) Blocks.EMERALD_ORE);
			}
		}
	}
	@Override

	public void onPlayerTick(TickEvent.PlayerTickEvent event) {
	}

	public void onWorldTick(TickEvent.WorldTickEvent event) {
	}


}


