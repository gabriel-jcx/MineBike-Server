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

	public ArrayList<EntityPlayer> playersInGame = new ArrayList<>();

	private long lastGenerated;
	private long nextGenerated;
	private HudRectangle rectangle;
	private HudString hudTimer;


	public Minequest() {
		super();
		//initializing all private variables

		currentTick = 0; // Once game starts the lava should proceed every couple ticks
		clock = Clock.systemDefaultZone();
		points = 0;
		highscore = 0;
		lastGenerated = 0;
		nextGenerated = 0;

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
			System.out.println("Attempting to generate Lava Wall");
			generateLavaWall(-1181, 887, player.world);
			return true;
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

	private void generateLavaWall(double startx, double startz, World world) { //figure out y coord and level
		System.out.println("Starting Wall");
		for (int z = (int) startz; z < startz + 20; z++) {
			System.out.println("First For Loop Activated");
			for (int y = 63; y < 68; y++) {
				System.out.println("Second For Loop Activated");
				BlockPos gangnam = new BlockPos((int) startx, y, z);
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
				if (areRedstone)
					points += 1;
				if (areIron)
					points += 2;
				if (areLapis)
					points += 3;
				if (areGold)
					points += 4;
				if (areDiamond)
					points += 5;
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
			resetToAirCont(0, 0, player.world);
			points = 0;
		}
	//	if(clock.millis() % 100 == 0 || isStarted)
	//		generateLavaWall(0, 0, player.world); // Figure out start x and z coords
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
			resetToAirCont(0, 0, player.world);
			points = 0;
			System.out.println("Player Died");
		}
		updatePoints();

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






