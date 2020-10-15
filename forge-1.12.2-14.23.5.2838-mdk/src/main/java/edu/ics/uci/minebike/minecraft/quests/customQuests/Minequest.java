package edu.ics.uci.minebike.minecraft.quests.customQuests;

import java.lang.reflect.Array;
import java.time.Clock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.sun.org.apache.xpath.internal.operations.Bool;
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
import noppes.npcs.api.entity.IPlayer;


//
public class Minequest extends AbstractCustomQuest {
    EntityPlayer player = null;
    public int DIMID;
    private boolean isStarted;

    public Vec3d questStartLocation;
    private int score = 0;
    public static final int MINERDIMENSIONID = 420;
    private ItemStack[] playerInventory;
    private final int currentTick;


    private HudRectangle backRectangle;
    private HudString pointsString;
    private int points;
    private int highscore;

    private final long serverStartWaitTime = 0;
    private final int maxPlayerCount = 5;

    private final long lavaGenerationInterval = 800; // Alter to change pace of lava generation according to player's heart rate, for multiplayer would need an array for each individual lane


    private ArrayList<EntityPlayer> playersInServer;
    private final ArrayList<EntityPlayer> playersInLanes;
    private final ArrayList<Boolean> laneStartStatus;
    private final ArrayList<Long> lastLavaGen;
    private final ArrayList<Integer> currentZ;

    private final int endZ;
    private HudRectangle rectangle;
    private HudString hudTimer;

    public ArrayList<BlockPos> laneStartLocations;


    public Minequest() {
        super();
        //initializing all private variables

        currentTick = 0; // Once game starts the lava should proceed every couple ticks

        points = 0;
        highscore = 0;

        this.DIMID = WorldProviderMiner.DIM_ID;
        this.questStartLocation = new Vec3d(-1149, 65, 869);
        this.isStarted = false;

        playersInServer = new ArrayList<>();
        playersInLanes = new ArrayList<>();
        for (int i = 0; i < 5; i++)
            playersInLanes.add(null);
        lastLavaGen = new ArrayList<>();
        for (int i = 0; i < 5; i++)
            lastLavaGen.add((long) 0);
        currentZ = new ArrayList<>();
        for (int i = 0; i < 5; i++)
            currentZ.add(799);

        laneStartStatus = new ArrayList<Boolean>(Arrays.asList(new Boolean[5]));
        Collections.fill(laneStartStatus, Boolean.FALSE);

        laneStartLocations = new ArrayList<>();
        laneStartLocations.add(new BlockPos(-1157, 53, 827));
        laneStartLocations.add(new BlockPos(-1163, 53, 825));
        laneStartLocations.add(new BlockPos(-1165, 53, 818));
        laneStartLocations.add(new BlockPos(-1163, 53, 811));
        laneStartLocations.add(new BlockPos(-1157, 53, 809));


        endZ = 399;
    }

    @Override
    protected void setupQuestEnv(World world, EntityPlayer player) {
        return;
    }

    @Override
    public boolean onPlayerJoin(EntityPlayer player) {
        this.player = player;
        playersInServer.add(player);
        player.world.setSpawnPoint(new BlockPos(-1149, 65, 869));
        isStarted = true;
        System.out.println("Player attempting to join");
        setupQuestEnv(player.world, player);
        ServerUtils.telport((EntityPlayerMP) player, this.questStartLocation, this.DIMID);
        return true;
    }

    @Override
    public void start(EntityPlayerMP playerMP) {
        //this.player = player;

        System.out.println("Start Miner quest");

        System.out.println("Trying to teleport " + playerMP.getName() + " to DIM" + this.DIMID);
        ServerUtils.telport(playerMP, questStartLocation, DIMID);
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

    //	@Override
//	public void end() {
//		System.out.println("triggered end");
//		hudTimer.unregister();
//		for (EntityPlayer bob : playersInGame) {
//			ServerUtils.telport((EntityPlayerMP) bob, Elon.LOCATION, 0);
//		}
//		playersInGame.removeAll(playersInGame);
//		finishGame();
//		//isStarted = false;
//	}


    public void finishGame() {
        if (score > highscore)
            highscore = 0;
        score = 0;

    }





    private void resetCeiling(int laneNumber) {
        // TODO: extract functions and use switch statment!

        if (laneNumber == 1) {
            for(int z = 800; z >= endZ; z--) {
                for (int x = -1152; x <= -1143; x++) {
                    System.out.println("First For Loop Activated");
                    BlockPos obama = new BlockPos(x, 59, z);
                    player.world.setBlockState(obama, Blocks.STONE_SLAB.getDefaultState());
                }
            }
            System.out.println("Ceiling 1 reset");
        }
        else if (laneNumber == 2){
            for(int z = 800; z >= endZ; z--) {
                for (int x = -1141; x <= -1132; x++) {
                    System.out.println("First For Loop Activated");
                    BlockPos obama = new BlockPos(x, 59,z);
                    player.world.setBlockState(obama, Blocks.STONE_SLAB.getDefaultState());
                    System.out.println("Ceiling 2 Reset");
                }
            }
            System.out.println("Ceiling " + laneNumber + "reset");
        }
        else if(laneNumber == 3) {
            for(int z = 800; z >= endZ; z--) {
                for (int x = -1130; x <= -1121; x++) {
                    System.out.println("First For Loop Activated");
                    BlockPos obama = new BlockPos(x, 59, z);
                    player.world.setBlockState(obama, Blocks.STONE_SLAB.getDefaultState());
                    System.out.println("Ceiling 3 Reset");
                }
            }
        System.out.println("Ceiling " + laneNumber + "reset");
        }
        else if(laneNumber ==4){
            for(int z = 800; z >= endZ; z--) {
                for (int x = -1119; x <= -1110; x++) {
                    System.out.println("First For Loop Activated");
                    BlockPos obama = new BlockPos(x, 59, z);
                    player.world.setBlockState(obama, Blocks.STONE_SLAB.getDefaultState());
                    System.out.println("Ceiling 4 Reset");
                }
            }
        System.out.println("Ceiling " + laneNumber + "reset");
        }
        else if(laneNumber ==5){
            for(int z = 800; z >= endZ; z--) {
                for (int x = -1108; x <= -1099; x++) {
                    System.out.println("First For Loop Activated");
                    BlockPos obama = new BlockPos(x, 59, z);
                    player.world.setBlockState(obama, Blocks.STONE_SLAB.getDefaultState());
                    System.out.println("Ceiling 5 Reset");
                }
            }
        System.out.println("Ceiling " + laneNumber + "reset");
        }
    }

    // public void updatePoints() {  work on a point system after getting lanes to work
    // 		for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
    // 			ItemStack stack = player.inventory.getStackInSlot(i);
    // 			//if there's something in the stack...
    // 			if (stack != null) {
    // 				//checks if materials are in player's inventory
    // 				String theItem = stack.getItem().getUnlocalizedName();
    // 				switch (theItem) {
    // 					case "item.redstone":
    // 						System.out.println("Player got Redstone");
    // 						points += 1;
    // 						break;
    // 					case "item.iron_ingot":
    // 						System.out.println("Player got Iron");
    // 						points += 2;
    // 						break;
    // 					case "item.lapis_ore":
    // 						System.out.println("Player got Lapis");
    // 						points += 3;
    // 						break;
    // 					case "item.gold_ingot":
    // 						System.out.println("Player got Gold");
    // 						points += 4;
    // 						break;
    // 					case "item.diamond":
    // 						System.out.println("Player got Diamond");
    // 						points += 5;
    // 						break;
    // 					default:
    // 						System.out.println("Nothing in inventory");
    // 				}
    // 			}
    // 		}
    // }


    public void startLane(int laneNumber, EntityPlayer thePlayer) {
        System.out.println("startLane executed for:" + thePlayer.getName() + "in lane" + laneNumber);
        switch (laneNumber) {
            case 1:
                System.out.println("Case for Lane 1 initiated");
                if (laneStartStatus.get(0) == true)
                    break;
                System.out.println("Lane is open");
                laneStartStatus.set(0, true);
                playersInLanes.set(0,thePlayer);
                thePlayer.setPosition(-1148, 53, 799);
                break;
            case 2:
                System.out.println("Case for Lane 2 initiated");
                if (laneStartStatus.get(1) == true)
                    break;
                laneStartStatus.set(1, true);
                playersInLanes.set(1,thePlayer);
                thePlayer.setPosition(-1137, 53, 799);
                break;
            case 3:
                System.out.println("Case for Lane 3 initiated");
                if (laneStartStatus.get(2) == true)
                    break;
                laneStartStatus.set(2, true);
                playersInLanes.set(2,thePlayer);
                thePlayer.setPosition(-1126, 53, 799);
                break;
            case 4:
                System.out.println("Case for Lane 4 initiated");
                if (laneStartStatus.get(3) == true)
                    break;
                laneStartStatus.set(3, true);
                playersInLanes.set(3,thePlayer);
                thePlayer.setPosition(-1115, 53, 799);
                break;
            case 5:
                System.out.println("Case for Lane 5 initiated");
                if (laneStartStatus.get(4) == true)
                    break;
                laneStartStatus.set(4, true);
                playersInLanes.set(4,thePlayer);
                thePlayer.setPosition(-1104, 53, 799);
                break;
        }
    }


    public void checkStartLocations() {
        System.out.println("Checking Start Locations");
        for (EntityPlayer thePlayer : playersInServer) {
            System.out.println("checkStartLocations shows " + thePlayer.getName() + " location to be: " + thePlayer.getPosition());
            if (thePlayer.getPosition().getX() == laneStartLocations.get(0).getX() && thePlayer.getPosition().getY() == laneStartLocations.get(0).getY() && thePlayer.getPosition().getZ() == laneStartLocations.get(0).getZ()) {
                System.out.println("LaneOne Initiated");
                startLane(1, thePlayer);
            }
            else if (thePlayer.getPosition().getX() == laneStartLocations.get(1).getX() && thePlayer.getPosition().getY() == laneStartLocations.get(1).getY() && thePlayer.getPosition().getZ() == laneStartLocations.get(1).getZ()) {
                System.out.println("LaneTwo Initiated");
                startLane(2, thePlayer);
            }
            else if (thePlayer.getPosition().getX() == laneStartLocations.get(2).getX() && thePlayer.getPosition().getY() == laneStartLocations.get(2).getY() && thePlayer.getPosition().getZ() == laneStartLocations.get(2).getZ()) {
                System.out.println("LaneThree Initiated");
                startLane(3, thePlayer);
            }
            else if (thePlayer.getPosition().getX() == laneStartLocations.get(3).getX() && thePlayer.getPosition().getY() == laneStartLocations.get(3).getY() && thePlayer.getPosition().getZ() == laneStartLocations.get(3).getZ()) {
                System.out.println("LaneFour Initiated");
                startLane(4, thePlayer);
            }
            else if (thePlayer.getPosition().getX() == laneStartLocations.get(4).getX() && thePlayer.getPosition().getY() == laneStartLocations.get(4).getY() && thePlayer.getPosition().getZ() == laneStartLocations.get(4).getZ()) {
                System.out.println("LaneFive Initiated");
                startLane(5, thePlayer);
            }
        }
    }

    @SideOnly(Side.SERVER)
    public void onPlayerRespawn(PlayerEvent.PlayerRespawnEvent respawn) {
        this.player = respawn.player;
        System.out.println("this.player is pointing to new player");
    }

    private void makeHoles(double startx, double z, World world) { //x should be the same down the lane
        System.out.println("Starting Holes");
        for (int x = (int) startx; x < startx + 10; x++) {
            System.out.println("First For Loop Activated");
            BlockPos obama = new BlockPos(x, 59, z);
            world.destroyBlock(obama, false);
            System.out.println("Holes were made");
        }
    }

    public void generateLavaTick() {
        if (laneStartStatus.get(0) == true) {
            System.out.println("lane one open for generateLavaTick");
            if (System.currentTimeMillis() - lastLavaGen.get(0) >= lavaGenerationInterval) {
                System.out.println("Lava gen logic executed");
                if (currentZ.get(0) <= endZ) {
                    System.out.println("currentZ overpasses end in lane 1");
                    endLane(1);
                    return;
                }
                if(playersInLanes.get(0).getPosition().getZ() <= endZ){
                    System.out.println(playersInLanes.get(0).getName() + "has completed lane 1");
                    endLane(1);
                    return;
                }
                lastLavaGen.set(0, System.currentTimeMillis());
                System.out.println("currentZ is now " + currentZ.get(0));
                makeHoles(-1152, currentZ.get(0), playersInLanes.get(0).world);
                currentZ.set(0, currentZ.get(0) - 1);
            }
        }
        if (laneStartStatus.get(1) == true) {
            if (System.currentTimeMillis() - lastLavaGen.get(1) >= lavaGenerationInterval) {
                if (currentZ.get(1) <= endZ) {
                    System.out.println("currentZ overpasses end in lane 2");
                    endLane(1);
                    return;
                }
                if(playersInLanes.get(1).getPosition().getZ() <= endZ){
                    System.out.println(playersInLanes.get(1).getName() + "has completed lane 2");
                    endLane(2);
                    return;
                }
                lastLavaGen.set(1, System.currentTimeMillis());
                System.out.println("currentZ is now" + currentZ);
                makeHoles(-1141, currentZ.get(1), playersInLanes.get(1).world);
                currentZ.set(1, currentZ.get(1) - 1);
            }
        }
        if (laneStartStatus.get(2) == true) {
            if (System.currentTimeMillis() - lastLavaGen.get(2) >= lavaGenerationInterval) {
                if (currentZ.get(2) <= endZ) {
                    System.out.println("currentZ overpasses end in lane 3");
                    endLane(3);
                    return;
                }
                if(playersInLanes.get(2).getPosition().getZ() <= endZ){
                    System.out.println(playersInLanes.get(2).getName() + "has completed lane 3");
                    endLane(3);
                    return;
                }
                lastLavaGen.set(2, System.currentTimeMillis());
                System.out.println("currentZ is now" + currentZ);
                makeHoles(-1130, currentZ.get(2), playersInLanes.get(2).world);
                currentZ.set(2, currentZ.get(2) - 1);
            }

        }
        if (laneStartStatus.get(3) == true) {
            if (System.currentTimeMillis() - lastLavaGen.get(3) >= lavaGenerationInterval) {
                if (currentZ.get(3) <= endZ) {
                    System.out.println("currentZ overpasses end in lane 4");
                    endLane(4);
                    return;
                }
                if(playersInLanes.get(3).getPosition().getZ() <= endZ){
                    System.out.println(playersInLanes.get(3).getName() + "has completed lane 4");
                    endLane(4);
                    return;
                }
                lastLavaGen.set(3, System.currentTimeMillis());
                System.out.println("currentZ is now" + currentZ);
                makeHoles(-1119, currentZ.get(3), playersInLanes.get(3).world);
                currentZ.set(3, currentZ.get(3) - 1);
            }

        }
        if (laneStartStatus.get(4) == true) {
            if (System.currentTimeMillis() - lastLavaGen.get(4) >= lavaGenerationInterval) {
                if (currentZ.get(4) <= endZ) {
                    System.out.println("currentZ overpasses end in lane 5");
                    endLane(5);
                    return;
                }
                if(playersInLanes.get(4).getPosition().getZ() <= endZ){
                    System.out.println(playersInLanes.get(4).getName() + "has completed lane 5");
                    endLane(5);
                    return;
                }
                lastLavaGen.set(4, System.currentTimeMillis());
                System.out.println("currentZ is now" + currentZ);
                makeHoles(-1108, currentZ.get(4), playersInLanes.get(4).world);
                currentZ.set(4, currentZ.get(4) - 1);
            }

        }
    }


    public void serverStartTick() {
        if (player.getPosition() == null) //??? what is this if statement for?
            return;
        checkStartLocations();
        generateLavaTick();
        if(!(this.player instanceof  EntityPlayerMP)){
            System.err.println("ERROR: serverStartTick executed on the client side? Need to understand!");
            return;
        }
        BlockPos dab = (new BlockPos(player.getPosition().getX(), player.getPosition().getY() - 1, player.getPosition().getZ()));
        BlockPos playerPos = player.getPosition();
        if (((EntityPlayerMP) player).world.getBlockState(dab).getBlock() == Blocks.MAGENTA_GLAZED_TERRACOTTA) {
            player.setSpawnChunk(playerPos, true, 420);
            System.out.println("New Spawn made");
            System.out.println("New Spawn Coordinates are:" + player.getBedLocation(420));
        }

//
//        if (player.isDead) {
//            if (currentZ > player.getBedLocation().getZ()) { // wall is ahead of last checkpoint, automatically lose
//                if (points > highscore)
//                    highscore = points;
//                points = 0;
//            }


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
            points = 0;
            System.out.println("Player Died");
        }


    }

    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        this.player = event.player;
        clientStartTick();

    }

    //needs to be coded
    public void endLane(int laneNumber) {
        playersInLanes.get(laneNumber - 1).setPosition(-1157,53,818);
        playersInLanes.set(laneNumber - 1, null);
        resetCeiling(laneNumber);
        laneStartStatus.set(laneNumber -1, false);
        lastLavaGen.set(laneNumber - 1, (long) 0);
        currentZ.set(laneNumber - 1, 799);

    }

    public void end(){

    }

}
