package edu.ics.uci.minebike.minecraft.quests.customQuests;

import com.mrcrayfish.soccer.entity.EntitySoccerBall;
import edu.ics.uci.minebike.minecraft.ServerUtils;
import edu.ics.uci.minebike.minecraft.client.HudManager;
import edu.ics.uci.minebike.minecraft.client.hud.HudRectangle;
import edu.ics.uci.minebike.minecraft.client.hud.HudString;
import edu.ics.uci.minebike.minecraft.constants.EnumPacketServer;
import edu.ics.uci.minebike.minecraft.npcs.NpcUtils;
import edu.ics.uci.minebike.minecraft.npcs.customNpcs.Jaya;
import edu.ics.uci.minebike.minecraft.npcs.customNpcs.Renzler;
import edu.ics.uci.minebike.minecraft.quests.AbstractCustomQuest;
import edu.ics.uci.minebike.minecraft.quests.QuestUtils;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.init.Items;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.item.ItemStack;

import net.minecraft.world.WorldServer;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import noppes.npcs.api.NpcAPI;
import noppes.npcs.api.entity.ICustomNpc;
import noppes.npcs.controllers.data.Quest;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.entity.data.DataAI;

import java.time.Clock;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Random;

import static net.minecraftforge.items.ItemHandlerHelper.giveItemToPlayer;




// NOTE: many of the fields can be more optimized i think, getting lazy now LOL
public class TRONQuest extends AbstractCustomQuest {

    private boolean worldLoaded = false;

    public static final String NPC_NAME = "Rinzler"; // Name of npc
    static EntityCustomNpc npc;
    static NpcCommand command;

    private final long GAME_WAITING_TIME = 30000; // millisecond
    //private final long GAME_SESSION_TIME = 300000; // millisecond => equivalent to 5 mins
    private final long GAME_SESSION_TIME = 10000; // millisecond => equivalent to 5 mins
    //private final int GOAL_TICK_TIME = 5; // If ball stays in the goal for 5 ticks
    private final int MAX_PLAYER_COUNT = 3;
    private final int MAX_NPC_COUNT = 2;

        // Player fields
    public ArrayList<EntityPlayerMP> playersInGame  = new ArrayList<>();

    // Player spawn locations
    //ideally, players should spawn
    public ArrayList<BlockPos> playerSpawnLocations = new ArrayList<>();
    public int curr_player_count = 0;

    //NPC spawn playerSpawnLocations
    public ArrayList<BlockPos> NPCSpawnLocations = new ArrayList<>();
    public int curr_npc_count = 0;

    // Server waiting Tracker
    private long server_waitingStartTime = 0;
    private long server_waitingEndTime = 0;
    private long server_waitingTime = GAME_WAITING_TIME;
    private int server_waitingTime_seconds = (int)(GAME_WAITING_TIME/1000);


    // Client waiting Tracker
    private long client_waitingTime  = 0;
    private long client_waitingStartTime = 0;
    private long client_waitingEndTime = 0;
    private int client_waitingTime_seconds = 0;

    // Server start Tracker
    private long server_endTime = 0;
    private long server_startTime = 0;


    // Client start Tracker
    private long client_startTime = 0;
    private long client_endTime = 0;


    // Client Hud Elements
    //private HudString clockStr;
    private int scoreLeft = 0;
    private HudString scoreLeftStr;
    private int scoreRight = 0;
    private HudString scoreRightStr;

    // Temp flag
    public int prev = 0;

    //Where Rinzler will spawn on the start of the quest
    public Vec3d rinzlerCord = new Vec3d(0, 6, 10); //spawn Rinzler a bit a away from the player
    EntityCustomNpc RinzlerNPC;
    private int npcSpeed;
    private int[] warning = { 100, 400 }; // location from where the warningString and warningNumber are based off of
    private HudString warningString; // Displays when player stops moving
    private HudString warningNumber; // Counts down when player stops moving
    private ForgeDirection npcRunDirection; // which direction npc is currently going

    //Variables that will be used the most

    private boolean doOnce;
    private boolean init; //flag to know when everything has been loaded
    private boolean[][] glassPanes;
    public static int[] npcPath; // staging matrix to add to npcPathList
    public static List<int[]> npcPathList; // list of coordinates npc will seek out to
    private int timer; // timer to check how long the player has been standing still

    public int numStagesPlayer; // steps of delay when laying down glass panes for player
    public int numStagesNpc; // steps of delay when laying down glass panes for npc
    public ArrayList<int[]> playerLocation;
    public ArrayList<int[]> npcLocation;
    boolean setPanePlayer; // flag to tell onWorldTick to place glass pane for player
    boolean setPaneNpc; // flag to tell onWorldTick to place glass pane for npc
    int[] newLoc; // global variable
    int NPCImmobileTimer;

    public TRONQuest() {
        super();
        this.DIMID = 250;
        //setting it to true will mean that a part of onWorldEvent will run and search for the player's coordinates
        this.questStartLocation = new Vec3d (0,     8, 0);
        QuestUtils.populateTRONPlayerLocations(playerSpawnLocations,this.MAX_PLAYER_COUNT);
        QuestUtils.populateTRONNPCLocations(NPCSpawnLocations, this.MAX_NPC_COUNT);
        //define the variables
        doOnce = true;
        init = false; //flag to know when everything has been loaded
        glassPanes = new boolean[201][201];
        npcPath = new int[3]; // staging matrix to add to npcPathList
        npcPathList = new ArrayList<int[]>(); // list of coordinates npc will seek out to
        timer = 0; // timer to check how long the player has been standing still
        numStagesPlayer = 5; // steps of delay when laying down glass panes for player
        numStagesNpc = 8; // steps of delay when laying down glass panes for npc
        playerLocation = initialize(numStagesPlayer); // initialize the array used to lay down glass panes
        // for player
        npcLocation = initialize(numStagesNpc); // initialize the array used to lay down glass panes for
        // npc
        setPanePlayer = false; // flag to tell onWorldTick to place glass pane for player
        setPaneNpc = false; // flag to tell onWorldTick to place glass pane for npc
        newLoc = new int[]{(int) Math.random() * 200 + 1, 4, (int) Math.random() * 200 + 1}; // global variable
        System.out.println("Seeking out coordinates " + newLoc[0] + " and " + newLoc[2]);
        NPCImmobileTimer = 0;
        npcPath = new int[3];
        npcPathList = new ArrayList<int[]>();
        npcSpeed = 4;
        //npcRunDirection = ForgeDirection.EAST;
    }

    // This onPlayerJoin is only called on the server side
    @SideOnly(Side.SERVER)
    @Override
    public boolean onPlayerJoin(EntityPlayer player)
    {
        System.out.println("On PlayerJoin triggerd on server side");
        ServerUtils.telport((EntityPlayerMP)player, this.questStartLocation,this.DIMID);

        WorldServer ws = DimensionManager.getWorld(250);
        Iterator iter = ws.loadedEntityList.iterator();
        while(iter.hasNext()){
            Entity entity = (Entity)iter.next();
            System.out.println(entity.getName() + "is an entity");
            if(entity instanceof EntityCustomNpc){
                if(entity.getName().equals("Rinzler"))
                {
                    ((EntityCustomNpc) entity).delete();
                    System.out.println("Rinzler was deleted!");
                    //once this loop has finished, the quest can start because all the entities have been loaded
                }
            }
        }

        RinzlerNPC = NpcUtils.spawnNpc(rinzlerCord,
                DimensionManager.getWorld(this.DIMID), player.getEntityWorld(), "Rinzler",
                "customnpcs:textures/entity/humanmale/kingsteve.png");
        playersInGame.add((EntityPlayerMP)player);
        this.player = player;
        init = true;
        return true;
    }

    @Override
    public void setupQuestEnv(World world, EntityPlayer player) {
        this.player = player;
        if(!world.isRemote)
        {
        }
    }

    // This is the server side of starting, send a trigger packet to each Player for starting the quest!
    @Override
    public void start(EntityPlayerMP player) {
        System.out.println("start is triggered for " + player.getName());
        // The DIMID is used for mapping QuestStart to this quest
        ServerUtils.sendQuestData(EnumPacketServer.QuestStart, player, Long.toString(this.DIMID));
        // NOTE: this start is temporarily deprecated!
    }

    @Override
    public void start(EntityPlayerSP player) {

    }
    @Override
    public void start(EntityJoinWorldEvent event) {
        // spawn associated NPC and ball if not spawned
    }


    @Override
    public void start() { // This is the start for client

        client_startTime = System.currentTimeMillis();
        client_endTime = client_startTime + GAME_SESSION_TIME;
    }
    @Override
    public void end() {
        return;
    }

    /* minecraft often oscillates coordinates (they are doubles) */
    public boolean isNewPlayer(int[] coordinate) // checks if the added coordinates are new for the player
    {
        for (int x = 0; x < numStagesPlayer; x++)
        {
            if (coordinate[0] == playerLocation.get(x)[0] && coordinate[1] == playerLocation.get(x)[1])
                return false;
        }
        return true;
    }

    public boolean isNewNpc(int[] coordinate) // checks if the added coordinates are new for the npc
    {
        for (int x = 0; x < numStagesNpc; x++)
        {
            if (coordinate[0] == npcLocation.get(x)[0] && coordinate[1] == npcLocation.get(x)[1])
                return false;
        }
        return true;
    }

    // NOTE: Minecraft runs 20 ticks per second
    //       Every tick is 0.05 seconds and 50 milliseconds
    @Override
    public void onWorldTick(TickEvent.WorldTickEvent event) {
      //System.out.println(event.world.isRemote);
        if(!event.world.isRemote){ // Server side

            if(init)
            {
                if (doOnce) //wipe the arena of all glass panes
                {
                    for (int i = 0; i < 201; i++)
                    {
                        for (int j = 0; j < 201; j++)
                        {
                            glassPanes[i][j] = false;
                            BlockPos block1 = new BlockPos(i - 100, 5, j - 100);
                            BlockPos block2 = new BlockPos(i - 100, 4, j - 100);
                            event.world.setBlockState(block1, (IBlockState) Blocks.AIR.getDefaultState());
                            event.world.setBlockState(block2, (IBlockState) Blocks.AIR.getDefaultState());
                        }
                    }
                    doOnce = false;
                }
                double xPos = RinzlerNPC.wrappedNPC.getX();
                double zPos = RinzlerNPC.wrappedNPC.getZ();
                //RinzlerNPC.wrappedNPC.getAi();
                RinzlerNPC.wrappedNPC.navigateTo(newLoc[0], newLoc[1], newLoc[2], npcSpeed);
                //System.out.println("Homing destination " + RinzlerNPC.wrappedNPC.getHomeX());
                //RinzlerNPC.wrappedNPC.setHome();
                //System.out.println("Navigation path " + RinzlerNPC.wrappedNPC.getNavigationPath());

                //timer to check if Rinzler has stopped moving for ~100 ticks

                if(xPos == newLoc[0] - 3 || zPos == newLoc[2] - 3)
                {
                    //RinzlerNPC.wrappedNPC.clearNavigation();
                    newLoc[0] = ((int) (Math.random() * 201)) - 100;
                    newLoc[2] = ((int) (Math.random() * 201))- 100;
                }
                //RinzlerNPC.wrappedNPC.setMoveForward(10);

                int currentCoX = (int) player.posX;
                int currentCoZ = (int) player.posZ;

                int currentCoXNPC = (int) RinzlerNPC.wrappedNPC.getX();
                int currentCoZNPC = (int) RinzlerNPC.wrappedNPC.getZ();
                System.out.println("Rinzler X location: " + currentCoXNPC);
                System.out.println("Rinzler Z loaction: " + currentCoZNPC);

                System.out.println("Player X " + currentCoX);
                System.out.println("Player Z " + currentCoZ);
                //System.out.println(glassPanes);

                if (glassPanes[currentCoX + 100][currentCoZ + 100])
                {
                    init = false;
                    returnToMainMenu();
                    System.out.println("PLAYER LOST");
                }

                System.out.println("Rinzler's speed is " + RinzlerNPC.wrappedNPC.getMotionX());
                int tempSpeedX = (int) RinzlerNPC.wrappedNPC.getMotionX();
                int tempSpeedZ = (int) RinzlerNPC.wrappedNPC.getMotionZ();

                if (tempSpeedX == 0 && tempSpeedZ == 0)
                {
                    NPCImmobileTimer++;
                    if (NPCImmobileTimer >= 1000) //if Rinzler is continuously immobile for more than 5 seconds
                    {
                        init = false;
                        returnToMainMenu();
                        NPCImmobileTimer = 0;
                        System.out.println("PLAYER WON");
                    }
                }
                else
                {
                    NPCImmobileTimer = 0; //resets back to 0 if Rinzler has begun to move again
                }

                //check if coordinates can be added to playerLocation, used to lay down glass panes
                int[] tempCoordinatePlayer = { currentCoX, currentCoZ };
                int[] tempCoordinateNPC = { currentCoXNPC, currentCoZNPC }; //these are valid coordinates

                System.out.println(playerLocation);
                System.out.println(npcLocation);

                if (!setPanePlayer && isNewPlayer(tempCoordinatePlayer))  //can the glass panes be set down yet for the player
                {
                    setPanePlayer = true; // player may now set down a glass pane
                    int[] newLoc2 = { currentCoX, currentCoZ };
                    playerLocation.add(0, newLoc2); // adds the player coordinates to the beginning of the list
                    int[] tempCo2 = playerLocation.remove(numStagesPlayer); // removes at end of list
                    //Don't forget to remove to keep length of waterfall consistent!
                    int tempX2 = tempCo2[0];
                    int tempZ2 = tempCo2[1];
                    BlockPos block1 = new BlockPos(tempX2, 5, tempZ2);
                    BlockPos block2 = new BlockPos(tempX2, 4, tempZ2);
                    event.world.setBlockState(block1, (IBlockState) Blocks.STAINED_GLASS_PANE.getDefaultState());
                    event.world.setBlockState(block2, (IBlockState) Blocks.STAINED_GLASS_PANE.getDefaultState());
                    glassPanes[tempX2 + 100][tempZ2 + 100] = true;
                    setPanePlayer = false;
                }
                if (!setPaneNpc && isNewNpc(tempCoordinateNPC))  //can the glass panes be set down yet for Rinzler?
                {
                    setPaneNpc = true; // Rinzler may now set down a glass pane
                    int[] newLoc = { currentCoXNPC, currentCoZNPC };
                    npcLocation.add(0, newLoc); // adds the Rinzler coordinates to the beginning of the list
                    int[] tempCo = npcLocation.remove(numStagesNpc); // removes at end of list
                    //Don't forget to remove to keep length of waterfall consistent!
                    int tempX = tempCo[0];
                    int tempZ = tempCo[1];
                    BlockPos block3 = new BlockPos(tempX, 5, tempZ);
                    BlockPos block4 = new BlockPos(tempX, 4, tempZ);
                    event.world.setBlockState(block3, (IBlockState) Blocks.STAINED_GLASS_PANE.getDefaultState());
                    event.world.setBlockState(block4, (IBlockState) Blocks.STAINED_GLASS_PANE.getDefaultState());
                    glassPanes[tempX + 100][tempZ + 100] = true;
                    setPaneNpc = false;
                }
                this.serverStartTick(event);
            }
        } else { // Client Side
//
        }
    }

    public void returnToMainMenu()
    {
        //tp the player back to the overworld
        System.out.println("Trying to kill the player!");
        ServerUtils.telport((EntityPlayerMP)player, Renzler.LOCATION,0);
        this.end();

        //reset all the critical variables to wipe the game data
        doOnce = true;
        init = false; //flag to know when everything has been loaded
        glassPanes = new boolean[201][201];
        npcPath = new int[3]; // staging matrix to add to npcPathList
        npcPathList = new ArrayList<int[]>(); // list of coordinates npc will seek out to
        timer = 0; // timer to check how long the player has been standing still
        numStagesPlayer = 5; // steps of delay when laying down glass panes for player
        numStagesNpc = 8; // steps of delay when laying down glass panes for npc
        ArrayList<int[]> playerLocation = initialize(numStagesPlayer); // initialize the array used to lay down glass panes
        // for player
        ArrayList<int[]> npcLocation = initialize(numStagesNpc); // initialize the array used to lay down glass panes for
        // npc
        setPanePlayer = false; // flag to tell onWorldTick to place glass pane for player
        setPaneNpc = false; // flag to tell onWorldTick to place glass pane for npc
        newLoc = new int[]{(int) Math.random() * 200 + 1, 4, (int) Math.random() * 200 + 1}; // global variable
        System.out.println("Seeking out coordinates " + newLoc[0] + " and " + newLoc[2]);
        NPCImmobileTimer = 0;
        npcPath = new int[3];
        npcPathList = new ArrayList<int[]>();
        npcSpeed = 4;
    }

    public void onPlayerTick(TickEvent.PlayerTickEvent event) {
        //System.out.println("onPlayerTick");
        if (init)
        {
            this.clientStartTick(event);
        }
    }

    private void serverStartTick(TickEvent.WorldTickEvent event) {
        long curr = System.currentTimeMillis();

        if(curr >= server_endTime){
            // end the game session for everyone!
            //System.out.println("TIME'S UP");
            this.end();
        }
    }

    private void clientStartTick(TickEvent.PlayerTickEvent event) {
        long curr = System.currentTimeMillis();

        // The logic works based on the fact that the GAME last more than 3 seconds!!!
        if(curr - client_startTime < 5000)
        {
            // 3000ms for displaying GAMESTART!
        }
            //clockStr.text = "GAME START!";
        else if(curr >= client_endTime){
            System.out.println("Game END on client!");
            this.end();
        }
        else {
            long remaining_millisecs = client_endTime - curr;
            //clockStr.text = QuestUtils.formatSeconds(QuestUtils.getRemainingSeconds(remaining_millisecs));
        }
    }


    private void serverWaitingTick(TickEvent.WorldTickEvent event){
        long curr = System.currentTimeMillis();
        int elapsed_seconds = QuestUtils.getRemainingSeconds(curr,server_waitingStartTime);
//        System.out.println(elapsed_seconds + " " + server_waitingTime_seconds);

        if(elapsed_seconds < server_waitingTime_seconds){
            // Decrement milliseconds count for Client

//            server_waitingTime = server_waitingEndTime - curr;  It seems unnecessary to do this computation here

        } else {
            // NOTE: this section re-initialize the waiting state and trigger start for User

            server_waitingTime = GAME_WAITING_TIME; // resetting the timer param


            //soccerWS = DimensionManager.getWorld(this.DIMID);

            //soccerWS.setWorldTime(500); // set time to day

            for(EntityPlayerMP player: this.playersInGame){
                this.start(player); // event game start triggered
            }
            server_startTime = System.currentTimeMillis();
            server_endTime = server_startTime + GAME_SESSION_TIME;
            // Set game state
        }
    }

    public void clientStartWaiting(String waitingTime){ //never called
        client_waitingTime = Long.parseLong(waitingTime);
        client_waitingStartTime = System.currentTimeMillis();

        client_waitingTime_seconds = QuestUtils.getRemainingSeconds(client_waitingTime);
        client_waitingEndTime = client_waitingStartTime + client_waitingTime;
        System.out.println("Client waiting for " + client_waitingTime_seconds + " seconds");
//        QuestUtils.formatSeconds(QuestUtils.getRemainingSeconds(client_waitingTime));

        //clockRect = new HudRectangle(-30, 30, 60, 30, 0x00000000, true, false);
        //clockStr = new HudString(0, 35, QuestUtils.formatSeconds(client_waitingTime_seconds),2.0f,true, false);
    }

    public void clientWaitingTick(TickEvent.PlayerTickEvent event){

//        int elpased_seconds = QuestUtils.getRemainingSeconds(System.currentTimeMillis(),client_waitingStartTime);
        client_waitingTime = client_waitingEndTime - System.currentTimeMillis();

        int remaining_seconds = QuestUtils.getRemainingSeconds(client_waitingTime);
        if(remaining_seconds >= 0 ){
            //clockStr.text = QuestUtils.formatSeconds(remaining_seconds);
        }

//        System.out.println("Client have " + clockStr.text + "left");
    }

    public ArrayList<int[]> initialize(int howMany) // initialize the numStages array for npc and player
    {
        ArrayList<int[]> outerArr = new ArrayList<int[]>();
        for (int i = 0; i < howMany; i++)
        {
            int[] myInt = { 2, 2 };
            outerArr.add(myInt);
        }
        return outerArr;
    }

}
