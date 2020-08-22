package edu.ics.uci.minebike.minecraft.quests.customQuests;

import java.time.Clock;

import java.util.ArrayList;

import edu.ics.uci.minebike.minecraft.ClientUtils;
import edu.ics.uci.minebike.minecraft.CommonUtils;
import edu.ics.uci.minebike.minecraft.ServerUtils;
import edu.ics.uci.minebike.minecraft.client.hud.HudRectangle;
import edu.ics.uci.minebike.minecraft.client.hud.HudString;

import edu.ics.uci.minebike.minecraft.npcs.customNpcs.Elon;
import edu.ics.uci.minebike.minecraft.quests.AbstractCustomQuest;
import edu.ics.uci.minebike.minecraft.quests.QuestUtils;
import edu.ics.uci.minebike.minecraft.worlds.WorldProviderMiner;
import edu.ics.uci.minebike.minecraft.constants.EnumPacketServer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.util.math.Vec3d;

import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import noppes.npcs.NoppesUtilPlayer;
import noppes.npcs.Server;

import edu.ics.uci.minebike.minecraft.ServerUtils;
import edu.ics.uci.minebike.minecraft.client.hud.HudRectangle;
import edu.ics.uci.minebike.minecraft.client.hud.HudString;
import edu.ics.uci.minebike.minecraft.quests.AbstractCustomQuest;
import edu.ics.uci.minebike.minecraft.worlds.WorldProviderMiner;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

import net.minecraft.item.ItemStack;
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
public class Minequest extends AbstractCustomQuest
{
	EntityPlayer player = null;
	public int DIMID;
	private boolean isStarted;

	public Vec3d questStartLocation;
	private int score = 0;
	private int ticks = 0;
	public static final int MINERDIMENSIONID = 420;
	private ItemStack[] playerInventory;
	private int currentTick;


	private HudRectangle backRectangle;
	private HudString pointsString;
	private int points;
	private int highscore;
	private static Clock clock;
	private String thePointsString;

	private long serverStartWaitTime = 0;
	private int maxPlayerCount = 5;

	private long lavaGenerationInterval = 1000; // Alter to change pace of lava generation according to player's heart rate, for multiplayer would need an array for each individual lane
	private long lastLavaGen = 0;

	public ArrayList<EntityPlayer> playersInGame;

	private int currentZ;
	private int endZ;
	private HudRectangle rectangle;
	private HudString hudTimer;

	public ArrayList<Vec3d> checkPointLocations;
	public boolean[] checkPointStatus;

	private boolean runStarted; // Signifies if the run started, Need to change to an array for multiplayer
	private boolean generatingWalls;

	public Minequest() {
		super();
		//initializing all private variables

		currentTick = 0; // Once game starts the lava should proceed every couple ticks
		clock = Clock.systemDefaultZone();
		points = 0;
		highscore = 0;
		currentZ = 887;

		// Resources don't seem to work ?
		ResourceLocation[] instructionTextureLocations = new ResourceLocation[]
				{
						new ResourceLocation("textures/GUI/instructions/MinerQuestInstruction1.png"),
						new ResourceLocation("textures/GUI/instructions/MinerQuestInstruction2.png"),
						new ResourceLocation("textures/GUI/instructions/MinerQuestInstruction3.png"),
				};
		String[] instructionStringContents = new String[]
				{
						"Run from the Lava",
						"Collect Gold",
						"Stay alive until time runs out",
				};

		// double check if instructions work
		this.DIMID = WorldProviderMiner.DIM_ID;
		this.questStartLocation = new Vec3d(-1149, 65, 869);
		this.isStarted = false;
		playersInGame = new ArrayList<>();
		checkPointLocations = new ArrayList<>();
		checkPointStatus = new boolean[7];


		runStarted = false;
		generatingWalls = false;

		endZ = 907;
	}

	@Override
	protected void setupQuestEnv(World world, EntityPlayer player) {
		return;
	}

	@Override
	public boolean onPlayerJoin(EntityPlayer player) {
		this.player = player;
		player.world.setSpawnPoint(new BlockPos(-1149, 65, 869));
		isStarted = true;
		System.out.println("Player attempting to join");
		setupQuestEnv(player.world, player);
		ServerUtils.telport((EntityPlayerMP) player, this.questStartLocation, this.DIMID);
			return true;
		}


public void testReset(){
		System.out.println("Starting Test Reset");
		currentZ = 887;
		while(currentZ<= 907){
			System.out.println("currentZ is now"+ currentZ);
			resetCeiling(-1181,currentZ, player.world);
			currentZ++;
		}
		currentZ=887;
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
	public void start(EntityPlayerSP player) {
		if (isStarted) {
			System.out.println("Game Already Started Going to Waiting");
		}
	}

	@Override
	public void start(EntityJoinWorldEvent event) {

	}
	@Override
	public void start() {
	//	isStarted = true;

	}

	@Override
	public void end() {
		System.out.println("triggered end");
		hudTimer.unregister();
		for (EntityPlayer bob : playersInGame) {
			ServerUtils.telport((EntityPlayerMP) bob, Elon.LOCATION, 0);
		}
		playersInGame.removeAll(playersInGame);
		finishGame();
		//isStarted = false;
	}

	public void finishGame() {

		if (score > highscore)
			highscore = 0;
		score = 0;

	}


	private void makeHoles(double startx, double z, World world) { //x should be the same down the lane
		System.out.println("Starting Holes");
		for (int x = (int) startx; x < startx + 10; x++) {
			System.out.println("First For Loop Activated");
			BlockPos obama = new BlockPos(x, 70, z);
			world.destroyBlock(obama, false);
			System.out.println("Holes were made");
			}
		}



	private void resetCeiling(double startx, double startz, World world) {
		for (int x = (int) startx; x < startx + 10; x++) {
			System.out.println("First For Loop Activated");
			BlockPos obama = new BlockPos(x, 70, startz);
			world.setBlockState(obama, Blocks.STONE_SLAB.getDefaultState());
			System.out.println("Ceiling Reset");
		}
	}

	public void updatePoints() {
		if(runStarted) {
			for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
				ItemStack stack = player.inventory.getStackInSlot(i);
				//if there's something in the stack...
				if (stack != null) {
					//checks if materials are in player's inventory
					String theItem = stack.getItem().getUnlocalizedName();
					switch (theItem) {
						case "item.redstone":
							System.out.println("Player got Redstone");
							points += 1;
							break;
						case "item.iron_ingot":
							System.out.println("Player got Iron");
							points += 2;
							break;
						case "item.lapis_ore":
							System.out.println("Player got Lapis");
							points += 3;
							break;
						case "item.gold_ingot":
							System.out.println("Player got Gold");
							points += 4;
							break;
						case "item.diamond":
							System.out.println("Player got Diamond");
							points += 5;
							break;
						default:
							System.out.println("Nothing in inventory");
					}
				}
			}
		}
	}


public void checkStartLocations(){
		BlockPos laneOne =  new BlockPos(-1157, 53, 827);
		BlockPos laneTwo = new BlockPos(-1163, 53, 825);
		BlockPos laneThree = new BlockPos(-1165, 53, 818);
		BlockPos laneFour = new BlockPos(-1163, 53, 811);
		BlockPos laneFive = new BlockPos(-1157,53,809);
//	if (((EntityPlayerMP) player).getPosition().getX() == -1168 && ((EntityPlayerMP) player).getPosition().getY() == 63 && ((EntityPlayerMP) player).getPosition().getZ() == 900) {
//if(player.interactOn())
//
//		testReset();
//	generatingWalls = false;
	//}
}

@SideOnly(Side.SERVER)
public void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent respawn){
	this.player = respawn.player;
	System.out.println("this.player is pointing to new player");
}

public void serverStartTick() {
		if (player == null)
			return;
		long curTime = System.currentTimeMillis();

		if (generatingWalls == true && curTime - lastLavaGen >= lavaGenerationInterval) {
			if (currentZ > endZ) {
				generatingWalls = false;
				return;
			}
			lastLavaGen = curTime;
			// if (currentTick == 0) {
				System.out.println("currentZ is now" + currentZ);
				makeHoles(-1181, currentZ, player.world);
				currentZ++;
			// }
		}


		BlockPos dab = (new BlockPos(((EntityPlayerMP) player).getPosition().getX(), ((EntityPlayerMP) player).getPosition().getY() - 1, ((EntityPlayerMP) player).getPosition().getZ()));
		BlockPos playerPos = ((EntityPlayerMP)player).getPosition();
		System.out.println(dab);
		if (((EntityPlayerMP)player).world.getBlockState(dab).getBlock() == Blocks.MAGENTA_GLAZED_TERRACOTTA)
		{
			//	System.out.println(((EntityPlayerMP) player).world.getBlockState(new BlockPos(((EntityPlayerMP) player).getPosition().getX(), ((EntityPlayerMP) player).getPosition().getY() - 1, ((EntityPlayerMP) player).getPosition().getZ())).getBlock().toString());
			//	((EntityPlayerMP)player).world.setSpawnPoint(dab);
			//	((EntityPlayerMP)player).setSpawnPoint(dab, true);

				((EntityPlayerMP)player).setSpawnChunk(playerPos, true, 420);
				//.world.setSpawnPoint(dab);
				System.out.println("New Spawn made");
				System.out.println("New Spawn Coordinates are:" + player.getBedLocation(420));
		}

			//if(((EntityPlayerMP)player).getPosition().getZ() == 900) // Simulate reaching end of the course

			if (runStarted && player.isDead) {// this is old code bc runstarted never put to true, need to make an array for each lane
				if (currentZ > player.getBedLocation().getZ()) { // wall is ahead of last checkpoint, automatically lose
					if (points > highscore)
						highscore = points;
					points = 0;
				}
//				for(int x = 7; x>=0; x--) {
//					if (checkPointStatus[x] == true)
//						player.setSpawnPoint(new BlockPos(checkPointLocations.get(x).x, checkPointLocations.get(x).y, checkPointLocations.get(x).z), true);
//				}

				System.out.println("Player Died");
			}
			updatePoints();
		}

	public void teleportPlayer(String coord, EntityPlayerMP playerMP){
		String[] coords = coord.split(" ");
		int x = (int)Double.parseDouble(coords[0]);
		int y = (int)Double.parseDouble(coords[1]);
		int z = (int)Double.parseDouble(coords[2]);
		Vec3d destination = new Vec3d(x,y,z);
		ServerUtils.telport(playerMP,destination, this.DIMID);
		System.out.println("Teleport " + playerMP.getName() + " to " + destination);
	}

	public void onWorldTick(TickEvent.WorldTickEvent event) {
		//Server Side
		if (!event.world.isRemote && isStarted) {
				serverStartTick();
			}
	}


	public void clientStartTick() {
		if (player == null)
			return;
		if (player.isDead) {
			//resetToAirCont(0, 0, player.world);
			points = 0;
			System.out.println("Player Died");
		}


	//	thePointsString += points;
	//	pointsString = new HudString(0, 10, thePointsString, 2.0f, true, false);
	}

	public void onPlayerTick(TickEvent.PlayerTickEvent event) {
		this.player = event.player;
		clientStartTick();

	}

	//needs to be coded
//	public void end(){
//		//server side
//		if(!player.world.isRemote()){
//
//		}else{
//			//client
//		}
//	}

}
