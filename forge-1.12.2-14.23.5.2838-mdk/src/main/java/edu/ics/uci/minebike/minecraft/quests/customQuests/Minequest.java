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
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
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

	private long clientWaitTime = 0;
	private long clientStartWaitTime = 0;
	private long clientEndWaitTime = 0;

	private long waitTime = 10000;//ms 10sec

	public ArrayList<EntityPlayer> playersInGame;

	private int currentZ;
	private HudRectangle rectangle;
	private HudString hudTimer;

	public ArrayList<Vec3d> checkPointLocations;
	public boolean[] checkPointStatus;
	private boolean testSpot;

	private int playerx;
	private int playery;
	private int playerz;

	public Minequest() {
		super();
		//initializing all private variables

		currentTick = 0; // Once game starts the lava should proceed every couple ticks
		clock = Clock.systemDefaultZone();
		points = 0;
		highscore = 0;
		currentZ = 887;

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

		testSpot = false;
	}

	@Override
	protected void setupQuestEnv(World world, EntityPlayer player) {
		return;
	}

	@Override
	public boolean onPlayerJoin(EntityPlayer player) {
		this.player = player;
		player.setSpawnPoint(new BlockPos(-1149, 65, 869),true);
		isStarted = true;
		System.out.println("Player attempting to join");
			setupQuestEnv(player.world, player);
			ServerUtils.telport((EntityPlayerMP) player, this.questStartLocation, this.DIMID);
		player.attemptTeleport(-1167.5 ,63 ,896.5);
			return true;
		}

public void testWall() {
	System.out.println("Starting Test Wall");
	while (currentZ <= 907) {
		if (System.currentTimeMillis() % 1000 == 0) {
			generateLavaWall(-1181, currentZ, player.world);
			currentZ++;
		}
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
		isStarted = true;

	}

	@Override
	public void end() {
		hudTimer.unregister();
		for (EntityPlayer bob : playersInGame) {
			ServerUtils.telport((EntityPlayerMP) bob, Elon.LOCATION, 0);
		}
		playersInGame.removeAll(playersInGame);
		finishGame();
		isStarted = false;
	}

	public void finishGame() {
		resetToAirCont(0, 0, player.world);
		if (score > highscore)
			highscore = 0;
		score = 0;

	}

	private void generateLavaWall(double startx, double z, World world) { //x should be the same down the lane
		System.out.println("Starting Wall");
		for (int x = (int) startx; x < startx + 10; x++) {
			System.out.println("First For Loop Activated");
			for (int y = 63; y < 68; y++) {
				System.out.println("Second For Loop Activated");
				BlockPos gangnam = new BlockPos(x, y, z);
				if (world.getBlockState(gangnam).getBlock() ==  Blocks.AIR) {
					world.setBlockState(gangnam, Blocks.LAVA.getDefaultState());
					System.out.println("Lava was made");
				}
			}
		}
	}


	private void resetToAirCont(double startx, double startz, World world) {

		for (int z = (int) startz + 1; z < startz + 10; z++) {
			for (int y = 21; y < 24; y++) {
				BlockPos seoul = new BlockPos((int) startx, y, z);
				if (world.getBlockState(seoul) == Blocks.LAVA)
					world.setBlockState(seoul, (IBlockState) Blocks.AIR);

			}
		}
	}


	public void updatePoints() {
		for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
			ItemStack stack = player.inventory.getStackInSlot(i);
			//if there's something in the stack...
			if (stack != null) {
				//checks if materials are in player's inventory
				boolean areIron = stack.getItem().getUnlocalizedName().equals("item.iron_ingot");
				boolean areGold = stack.getItem().getUnlocalizedName().equals("item.gold_ingot");
				boolean areLapis = stack.getItem().getUnlocalizedName().equals("item.lapis_ore");
				boolean areDiamond = stack.getItem().getUnlocalizedName().equals("item.diamond");
				boolean areRedstone = stack.getItem().getUnlocalizedName().equals("item.redstone");
				//if there is more than one ingredient in player inventory, reduce it to one
				if (areRedstone) {
					System.out.println("Player got Redstone");
					points += 1;
				}
				if (areIron) {
					System.out.println("Player got Iron");
					points += 2;
				}
				if (areLapis) {
					System.out.println("Player got Lapis");
					points += 3;
				}
				if (areGold) {
					System.out.println("Player got Gold");
					points += 4;
				}
				if (areDiamond) {
					System.out.println("Player got Diamond");
					points += 5;
				}
			}
		}

	}
//public void checkIfDead() {
//	for (EntityPlayer obama:playersInGame
//
//		 ) {
//
//	}
//	{

//	}
//}

	public void serverStartTick() {
		if (player.isDead) {
			if(checkPointStatus[7] == true)
				player.setSpawnPoint(new BlockPos(checkPointLocations.get(7).x,checkPointLocations.get(7).y,checkPointLocations.get(7).z),true);
			else if(checkPointStatus[6] == true)
				player.setSpawnPoint(new BlockPos(checkPointLocations.get(6).x,checkPointLocations.get(6).y,checkPointLocations.get(6).z),true);
			else if(checkPointStatus[5] == true)
				player.setSpawnPoint(new BlockPos(checkPointLocations.get(5).x,checkPointLocations.get(5).y,checkPointLocations.get(5).z),true);
			else if(checkPointStatus[4] == true)
				player.setSpawnPoint(new BlockPos(checkPointLocations.get(4).x,checkPointLocations.get(4).y,checkPointLocations.get(4).z),true);
			else if(checkPointStatus[3] == true)
				player.setSpawnPoint(new BlockPos(checkPointLocations.get(3).x,checkPointLocations.get(3).y,checkPointLocations.get(3).z),true);
			else if(checkPointStatus[2] == true)
				player.setSpawnPoint(new BlockPos(checkPointLocations.get(2).x,checkPointLocations.get(2).y,checkPointLocations.get(2).z),true);
			else if(checkPointStatus[1] == true)
				player.setSpawnPoint(new BlockPos(checkPointLocations.get(1).x,checkPointLocations.get(1).y,checkPointLocations.get(1).z),true);

			//if(currentZ ) // Figure out how to check if currentZ of wall is ahead of player death location and if so end the game
			//resetToAirCont(0, 0, player.world);
			//points = 0;
			System.out.println("Player Died");
		}
		updatePoints();
	}

	public void onWorldTick(TickEvent.WorldTickEvent event) {
		//Server Side
		if (!event.world.isRemote && isStarted) {
				serverStartTick();
			}
		}


	public void clientStartTick() {
		if (player.isDead) {
			//resetToAirCont(0, 0, player.world);
			points = 0;
			System.out.println("Player Died");
		}
		updatePoints();
		playerx = player.getPosition().getX();
		playery = player.getPosition().getY();
		playerz = player.getPosition().getZ();
	BlockPos thePos = new BlockPos(playerx,playery,playerz);
		if(thePos.getX() == -1168 && thePos.getY() == 63 && thePos.getZ() ==896 )//&& testSpot == false)
		{
			System.out.println("Player at test location");
			testSpot = true;
			testWall();
		}

		backRectangle = new HudRectangle(-25, 5, 50, 30, 0xffff9f38, true, false);

		thePointsString += points;
		pointsString = new HudString(0, 10, thePointsString, 2.0f, true, false);
	}

	public void onPlayerTick(TickEvent.PlayerTickEvent event) {
		if( isStarted)
			clientStartTick();
	}

}


//
//	public void clientStartWaiting(String waitingTime) {
//		clientWaitTime = Long.parseLong(waitingTime);
//		int waitingSeconds = QuestUtils.getRemainingSeconds(clientWaitTime);
//		clientStartWaitTime = System.currentTimeMillis();
//		clientEndWaitTime = clientStartWaitTime + clientWaitTime;
//		hudTimer = new HudString(0, 35, QuestUtils.formatSeconds(waitingSeconds), 2.0f, true, false);
//		isWaiting = true;
//	}






